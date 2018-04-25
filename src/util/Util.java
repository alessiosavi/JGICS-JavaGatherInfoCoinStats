package util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Blocco.Block;

public class Util {

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
	 * @param block
	 * @return
	 */
	public static Block parseBlock(JsonObject block) {
		Block blocco = new Block(block.get("hash"), block.get("confirmations"), block.get("size"), block.get("height"),
				block.get("version"), block.get("merkleroot"), block.get("mint"), block.get("time").getAsString(),
				block.get("nonce"), block.get("bits"), block.get("difficulty"), block.get("blocktrust"),
				block.get("chaintrust"), block.get("previousblockhash"), block.get("nextblockhash"), block.get("flags"),
				block.get("proofhash"), block.get("entropybit"), block.get("modifier"), block.get("modifierchecksum"));
		return blocco;
	}

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

	public static void getTx(String tx, String url) {

	}

}
