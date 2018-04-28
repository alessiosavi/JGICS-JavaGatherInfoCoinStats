package util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import Blocco.Tx;
import Blocco.Vin;
import Blocco.Vout;

public class Util {
	public static String transaction = "http://79.20.51.52:28017/api/getrawtransaction?txid=&decrypt=1";
	public Logger logger = LogManager.getLogger(Util.class);
	private static Map<String, String> apiCommands = new HashMap<String, String>(); // contains the api command

	public String tx2 = "48621f85969b053f04e572977d57f4944244cb2247694d0b2566a3fe2b162945";

	/**
	 * This method return the block hash as a String
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

	/**
	 * This method feth a json from a given url and return the contenet in a String
	 * format
	 * 
	 * It return an "Object" datatype, because the response can be a string or a
	 * JsonObject
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
		JsonParser jp = new JsonParser(); // from gson
		JsonElement root = null;
		try {
			root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} // Convert the input stream to a json element
		JsonObject oggetto;
		String stringa;
		if (root.isJsonObject()) {
			oggetto = root.getAsJsonObject();
			return oggetto;
		} else {
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

	/*
	 * This method take as input the TX has and return a <Tx> object
	 */
	public static Tx parseTx(String TX) throws MalformedURLException {

		String[] link = new String[1]; // apiMethods splitted
		String urlAPI;// composed by splitting apiMethods & concatenating with TX
		JsonObject jsonTx; // Tx in JsonObject format
		Tx tx; // return object
		Vin vin = new Vin();
		Vout vout;
		List<String> addressList = new ArrayList<String>();
		ScriptPubKey scriptPubKey;
		link = transaction.split("&");
		urlAPI = link[0] + TX + "&" + link[1];
		urlAPI = urlAPI.replace("\"", "");
		jsonTx = (JsonObject) getJSON(urlAPI);

		tx = new Tx(jsonTx.get("hex"), jsonTx.get("txid"), jsonTx.get("version"), jsonTx.get("time").toString(),
				jsonTx.get("locktime"), jsonTx.get("blockhash"), jsonTx.get("confirmations"),
				jsonTx.get("blocktime").toString());
		if (jsonTx.get("vin").getAsJsonArray().get(0).getAsJsonObject() != null) {
			try {
				vin = new Vin(jsonTx.get("vin").getAsJsonArray().get(0).getAsJsonObject().get("coinbase"),
						jsonTx.get("vin").getAsJsonArray().get(0).getAsJsonObject().get("sequence"));
			} catch (NullPointerException e) {
				System.err.println();
			}
		}
		tx.setVinList(vin);

		scriptPubKey = new ScriptPubKey(
				jsonTx.get("vout").getAsJsonArray().get(0).getAsJsonObject().get("scriptPubKey").getAsJsonObject()
						.get("asm"),
				jsonTx.get("vout").getAsJsonArray().get(0).getAsJsonObject().get("scriptPubKey").getAsJsonObject()
						.get("hex"),
				jsonTx.get("vout").getAsJsonArray().get(0).getAsJsonObject().get("scriptPubKey").getAsJsonObject()
						.get("type"));
		vout = new Vout(jsonTx.get("vout").getAsJsonArray().get(0).getAsJsonObject().get("value"),
				jsonTx.get("vout").getAsJsonArray().get(0).getAsJsonObject().get("n"), scriptPubKey);

		if (jsonTx.get("vout").getAsJsonArray().get(0).getAsJsonObject().get("scriptPubKey").getAsJsonObject()
				.get("addresses") != null) {
			for (JsonElement string : jsonTx.get("vout").getAsJsonArray().get(0).getAsJsonObject().get("scriptPubKey")
					.getAsJsonObject().get("addresses").getAsJsonArray()) {
				// logger.info("*** Address -> " + string.toString().replace("\"", "") + " ***
				// \n");
				addressList.add(string.toString().replace("\"", ""));
			}
		}
		scriptPubKey.setAddresses(addressList);
		tx.setVoutList(vout);
		// logger.debug(tx + "\n");
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
	public static Tx parseTx(String apiMethods, String TX) throws MalformedURLException {
		String[] link = new String[1]; // apiMethods splitted
		String urlAPI;// composed by splitting apiMethods & concatenating with TX
		JsonObject jsonTx; // Tx in JsonObject format
		Tx tx; // return object
		Vin vin = new Vin();
		Vout vout;
		List<String> addressList = new ArrayList<String>();
		ScriptPubKey scriptPubKey;

		link = apiMethods.split("&");
		urlAPI = link[0] + TX + "&" + link[1];
		jsonTx = (JsonObject) getJSON(urlAPI);

		tx = new Tx(jsonTx.get("hex"), jsonTx.get("txid"), jsonTx.get("version"), jsonTx.get("time").toString(),
				jsonTx.get("locktime"), jsonTx.get("blockhash"), jsonTx.get("confirmations"),
				jsonTx.get("blocktime").toString());
		if (jsonTx.get("vin").getAsJsonArray().get(0).getAsJsonObject() != null) {
			try {
				vin = new Vin(jsonTx.get("vin").getAsJsonArray().get(0).getAsJsonObject().get("coinbase"),
						jsonTx.get("vin").getAsJsonArray().get(0).getAsJsonObject().get("sequence"));
			} catch (NullPointerException e) {
			}
		}
		tx.setVinList(vin);

		scriptPubKey = new ScriptPubKey(
				jsonTx.get("vout").getAsJsonArray().get(0).getAsJsonObject().get("scriptPubKey").getAsJsonObject()
						.get("asm"),
				jsonTx.get("vout").getAsJsonArray().get(0).getAsJsonObject().get("scriptPubKey").getAsJsonObject()
						.get("hex"),
				jsonTx.get("vout").getAsJsonArray().get(0).getAsJsonObject().get("scriptPubKey").getAsJsonObject()
						.get("type"));
		vout = new Vout(jsonTx.get("vout").getAsJsonArray().get(0).getAsJsonObject().get("value"),
				jsonTx.get("vout").getAsJsonArray().get(0).getAsJsonObject().get("n"), scriptPubKey);

		if (jsonTx.get("vout").getAsJsonArray().get(0).getAsJsonObject().get("scriptPubKey").getAsJsonObject()
				.get("addresses") != null) {
			for (JsonElement string : jsonTx.get("vout").getAsJsonArray().get(0).getAsJsonObject().get("scriptPubKey")
					.getAsJsonObject().get("addresses").getAsJsonArray()) {
				// logger.info("*** Address -> " + string.toString().replace("\"", "") + " ***
				// \n");
				addressList.add(string.toString().replace("\"", ""));
			}
		}
		scriptPubKey.setAddresses(addressList);
		tx.setVoutList(vout);
		// logger.debug(tx + "\n");
		return tx;
	}

	/**
	 * Populate the map with the url of every Explorer methods getRAWtransaction &&
	 * gettransactions have to splitted by "&" character for insert the hash value
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

}
