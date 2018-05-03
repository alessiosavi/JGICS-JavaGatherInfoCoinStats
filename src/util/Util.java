
package util;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Blocco.Block;
import Blocco.ScriptPubKey;
import Blocco.ScriptSig;
import Blocco.Tx;
import Blocco.Vin;
import Blocco.Vout;

public class Util {
	public static String transaction = "http://79.20.51.52:28017/api/getrawtransaction?txid=&decrypt=1";
	public static Map<String, String> apiCommands = new HashMap<String, String>(); // contains the api command

	public static Logger logger = LogManager.getLogger(Util.class);

	public String tx2 = "48621f85969b053f04e572977d57f4944244cb2247694d0b2566a3fe2b162945";

	/**
	 * This method return the block from a given height number. It use the getJSON
	 * method for fetch the number of the block.
	 * 
	 * This method is used for fetch a specified block hash from the height of the
	 * block
	 * 
	 * @param height
	 * @param url
	 * @return block hash as a String
	 * @throws MalformedURLException
	 */
	public static String getBlockHash(int height, String url) throws MalformedURLException {
		String blockhash = getJSON(url + height).toString().replace("\"", "");
		return blockhash;
	}

	// @formatter:off

	/**
	 * 
	 * This method return an Object (String or JsonObject) fetched from the URL
	 * passed as a parameter. It is used for fetch block & heigh of a block.
	 * 
	 * This method return a "non-specified" Object. In reality can be: 1) JsonObject
	 * 2) String
	 * 
	 * @param URL
	 * @return
	 * @throws MalformedURLException
	 */
	public static Object getJSON(String URL) throws MalformedURLException {
		URL url = new URL(URL);
		HttpURLConnection request = null;

		try {
			request = (HttpURLConnection) url.openConnection();
			request.connect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Convert to a JSON object to print data
		JsonParser jp = new JsonParser();
		JsonElement root = null;
		try {
			root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} // Convert the input stream to a json element
		JsonObject oggetto;
		String stringa;
		// If is a JsonObject return the object
		if (root.isJsonObject()) {
			oggetto = root.getAsJsonObject();
			return oggetto;
		} else { // if is a simple String return the String
			stringa = root.toString();
			return stringa;
		}
	}

	/**
	 * This method take the Block in JsonObject datatype as input, and use the Block
	 * constructor to initialize a block
	 * 
	 * @param blockmemberName
	 * @return
	 * @throws MalformedURLException
	 */
	public static Block parseBlock(JsonObject block) throws MalformedURLException {
		Block blocco = new Block(block.get("hash"), block.get("confirmations"), block.get("size"), block.get("height"),
				block.get("version"), block.get("merkleroot"), block.get("mint"), block.get("time").getAsString(),
				block.get("nonce"), block.get("bits"), block.get("difficulty"), block.get("blocktrust"),
				block.get("chaintrust"), block.get("previousblockhash"), block.get("nextblockhash"), block.get("flags"),
				block.get("proofhash"), block.get("entropybit"), block.get("modifier"), block.get("modifierchecksum"));
		for (JsonElement je : block.get("tx").getAsJsonArray()) {
			String string = je.toString();
			blocco.addTx(parseTx(string));
		}
		// logger.info("BLOCK NUMBER --> " + blocco.getHeight() + "\n");
		return blocco;
	}

	/**
	 * This method take care of initializate the path with correct string format
	 * 
	 * @param path
	 * @return
	 */
	public static String parsePath(String path) {
		String pathLogFile;
		String LOG_FILE_NAME = "ndcDump";
		if (path.lastIndexOf("/") < path.length() - 1) {
			pathLogFile = path + "/" + LOG_FILE_NAME;
		} else {
			pathLogFile = path + LOG_FILE_NAME;
		}
		return pathLogFile;
	}

	/*
	 * This method take as input the TX has and return a <Tx> object
	 */
	public static Tx parseTx(String TX) throws MalformedURLException {

		String[] link = new String[1];
		String urlAPI;
		JsonObject jsonTx; // Tx in JsonObject format
		Tx tx; // return object

		link = transaction.split("&");
		urlAPI = link[0] + TX + "&" + link[1];
		urlAPI = urlAPI.replace("\"", "");
		jsonTx = (JsonObject) Util.getJSON(urlAPI);

		tx = new Tx(jsonTx.get("hex"), jsonTx.get("txid"), jsonTx.get("version"), jsonTx.get("time").toString(),
				jsonTx.get("locktime"), jsonTx.get("blockhash"), jsonTx.get("confirmations"),
				jsonTx.get("blocktime").toString());

		/*************************** GET VIN DATA ***************************/

		if (jsonTx.get("vin").getAsJsonArray() != null) {
			for (JsonElement data : jsonTx.get("vin").getAsJsonArray()) {

				Vin vin = new Vin(data.getAsJsonObject().get("txid"), data.getAsJsonObject().get("vout"),
						data.getAsJsonObject().get("coinbase"), data.getAsJsonObject().get("sequence"));
				if (data.getAsJsonObject().get("scriptSig") != null) {
					JsonElement scriptSigJson = data.getAsJsonObject().get("scriptSig");
					ScriptSig scriptSig = new ScriptSig(scriptSigJson.getAsJsonObject().get("asm"),
							scriptSigJson.getAsJsonObject().get("hex"));
					vin.setScriptSig(scriptSig);
				}
				tx.addVin(vin);
			}
		}

		/*************************** GET VOUT DATA ***************************/
		if (jsonTx.get("vout").getAsJsonArray() != null)

		{
			for (JsonElement data : jsonTx.get("vout").getAsJsonArray()) {
				List<String> addressList = new ArrayList<String>();
				Vout vout = new Vout(data.getAsJsonObject().get("value"), data.getAsJsonObject().get("n"));
				JsonElement scriptPubKeyJson = data.getAsJsonObject().get("scriptPubKey");
				logger.trace("TX -> " + tx.getTxId());
				ScriptPubKey scriptPubKey = new ScriptPubKey(scriptPubKeyJson.getAsJsonObject().get("asm"),
						scriptPubKeyJson.getAsJsonObject().get("hex"),
						scriptPubKeyJson.getAsJsonObject().get("reqSigs"),
						scriptPubKeyJson.getAsJsonObject().get("type"));
				try {
					for (JsonElement address : scriptPubKeyJson.getAsJsonObject().get("addresses").getAsJsonArray()) {
						addressList.add(address.toString().replace("\"", ""));
					}
				} catch (NullPointerException nonMiGuardare) {
					// The block related to this transaction have a signature
					logger.error("[******* NONSTANDARD_TX *******] NONSTANDARD TX -> " + tx.getTxId());
				}
				scriptPubKey.setAddresses(addressList);
				vout.setScriptPubKey(scriptPubKey);
				tx.addVout(vout);
			}
		}
		// System.out.println("TX -> " + tx.toString());

		return tx;

	}

	/**
	 * This method return a Tx object that represent a transaction. It take the url
	 * for fetch API end the TX hash
	 * 
	 * @param apiMethods
	 * @param TX
	 * @return
	 * @throws MalformedURLException
	 */
	// public static Tx parseTx(String apiMethods, String TX) throws
	// MalformedURLException {
	// String[] link = new String[1]; // apiMethods splitted
	// String urlAPI;// composed by splitting apiMethods & concatenating with TX
	// JsonObject jsonTx; // Tx in JsonObject format
	// Tx tx; // return object
	// Vin vin = new Vin();
	// Vout vout;
	// List<String> addressList = new ArrayList<String>();
	// ScriptPubKey scriptPubKey;
	//
	// link = apiMethods.split("&");
	// urlAPI = link[0] + TX + "&" + link[1];
	// jsonTx = (JsonObject) getJSON(urlAPI);
	//
	// tx = new Tx(jsonTx.get("hex"), jsonTx.get("txid"), jsonTx.get("version"),
	// jsonTx.get("time").toString(),
	// jsonTx.get("locktime"), jsonTx.get("blockhash"), jsonTx.get("confirmations"),
	// jsonTx.get("blocktime").toString());
	// if (jsonTx.get("vin").getAsJsonArray().get(0).getAsJsonObject() != null) {
	// try {
	// vin = new
	// Vin(jsonTx.get("vin").getAsJsonArray().get(0).getAsJsonObject().get("coinbase"),
	// jsonTx.get("vin").getAsJsonArray().get(0).getAsJsonObject().get("sequence"));
	// } catch (NullPointerException e) {
	// }
	// }
	// tx.setVinList(vin);
	//
	// scriptPubKey = new ScriptPubKey(
	// jsonTx.get("vout").getAsJsonArray().get(0).getAsJsonObject().get("scriptPubKey").getAsJsonObject()
	// .get("asm"),
	// jsonTx.get("vout").getAsJsonArray().get(0).getAsJsonObject().get("scriptPubKey").getAsJsonObject()
	// .get("hex"),
	// jsonTx.get("vout").getAsJsonArray().get(0).getAsJsonObject().get("scriptPubKey").getAsJsonObject()
	// .get("type"));
	// vout = new
	// Vout(jsonTx.get("vout").getAsJsonArray().get(0).getAsJsonObject().get("value"),
	// jsonTx.get("vout").getAsJsonArray().get(0).getAsJsonObject().get("n"),
	// scriptPubKey);
	//
	// if
	// (jsonTx.get("vout").getAsJsonArray().get(0).getAsJsonObject().get("scriptPubKey").getAsJsonObject()
	// .get("addresses") != null) {
	// for (JsonElement string :
	// jsonTx.get("vout").getAsJsonArray().get(0).getAsJsonObject().get("scriptPubKey")
	// .getAsJsonObject().get("addresses").getAsJsonArray()) {
	// // logger.info("*** Address -> " + string.toString().replace("\"", "") + "
	// ***
	// // \n");
	// addressList.add(string.toString().replace("\"", ""));
	// }
	// }
	// scriptPubKey.setAddresses(addressList);
	// tx.setVoutList(vout);
	// // logger.debug(tx + "\n");
	// return tx;
	// }

	/**
	 * Populate the map with the url of every Explorer methods getRAWtransaction &&
	 * gettransactions have to splitted by "&" character for insert the hash value
	 * 
	 * This metod contains every method for a specified "BlockChainExplorer" site
	 */
	public static Map<String, String> populateApiMap() {
		String urlExplorer = "http://79.20.51.52:28017/";
		// logger.info("*** INITIALIZE API MAP *** \n");
		apiCommands.put("getdifficulty", urlExplorer + "api/getdifficulty");
		apiCommands.put("getconnectioncount", urlExplorer + "api/getconnectioncount");
		apiCommands.put("getblockcount", urlExplorer + "api/getblockcount");
		apiCommands.put("getblockhash", urlExplorer + "api/getblockhash?index=");
		apiCommands.put("getblock", urlExplorer + "api/getblock?hash=");
		apiCommands.put("getRAWtransaction", urlExplorer + "api/getrawtransaction?txid=&decrypt=0");// split for &
		apiCommands.put("gettransaction", urlExplorer + "api/getrawtransaction?txid=&decrypt=1");// split for &
		apiCommands.put("getnetworkhashps ", urlExplorer + "api/getnetworkhashps");
		apiCommands.put("getmoneysupply", urlExplorer + "ext/getmoneysupply");
		apiCommands.put("getdistribution ", urlExplorer + "ext/getdistribution ");
		apiCommands.put("getaddress", urlExplorer + "ext/getaddress/");
		apiCommands.put("getbalance", urlExplorer + "ext/getbalance/");
		apiCommands.put("getlasttxs", urlExplorer + "ext/getlasttxs/10/100"); // return tx
		return apiCommands;
	}

	// This method is used for dump a piece of blockchain (SyncChain object)
	public static void dumpSyncChain(String path, Object obj) throws IOException {
		FileOutputStream fw = new FileOutputStream(path + "/" + Constants.NDC_SYNCHAIN_DUMP, true);
		ObjectOutputStream ow = new ObjectOutputStream(fw);
		ow.writeObject(obj);
		ow.flush();
		ow.close();
		fw.flush();
		fw.close();
	}

	/*
	 * This method return the blockchain read from file
	 */
	public static void readSyncChain(String path) throws IOException, ClassNotFoundException {
		FileInputStream fw = new FileInputStream(path + "/" + Constants.NDC_SYNCHAIN_DUMP);
		ObjectInputStream oi = null;
		try {
			while (true) {

				oi = new ObjectInputStream(fw);
				Object object = oi.readObject();
				logger.debug(object + "\n");
			}
		} catch (EOFException e) {
			logger.error("END OF FILE REACHED\n");
			oi.close();

		}

	}

}
