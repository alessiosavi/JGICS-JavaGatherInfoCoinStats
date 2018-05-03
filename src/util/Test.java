package util;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import Blocco.ScriptPubKey;
import Blocco.ScriptSig;
import Blocco.Tx;
import Blocco.Vin;
import Blocco.Vout;

public class Test {
	public static String transaction = "http://79.20.51.52:28017/api/getrawtransaction?txid=&decrypt=1";
	public static String tx2 = "44152e5d928936d20d2c666a147faf46e2f7bd39e17b7d2796303c015675469c";

	public Test() {

	}

	public static void test() throws MalformedURLException {
		String[] link = new String[1];
		String urlAPI;
		JsonObject jsonTx; // Tx in JsonObject format
		Tx tx; // return object

		link = transaction.split("&");
		urlAPI = link[0] + tx2 + "&" + link[1];
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
				JsonElement scriptSigJson = data.getAsJsonObject().get("scriptSig");
				ScriptSig scriptSig = new ScriptSig(scriptSigJson.getAsJsonObject().get("asm"),
						scriptSigJson.getAsJsonObject().get("hex"));

				System.out.println("SCRIPT_SIG -> " + scriptSig.toString());
				vin.setScriptSig(scriptSig);
				tx.addVin(vin);
			}
		}

		/*************************** GET VOUT DATA ***************************/
		if (jsonTx.get("vout").getAsJsonArray() != null) {
			for (JsonElement data : jsonTx.get("vout").getAsJsonArray()) {
				List<String> addressList = new ArrayList<String>();
				Vout vout = new Vout(data.getAsJsonObject().get("value"), data.getAsJsonObject().get("n"));
				JsonElement scriptPubKeyJson = data.getAsJsonObject().get("scriptPubKey");
				ScriptPubKey scriptPubKey = new ScriptPubKey(scriptPubKeyJson.getAsJsonObject().get("asm"),
						scriptPubKeyJson.getAsJsonObject().get("hex"),
						scriptPubKeyJson.getAsJsonObject().get("reqSigs"),
						scriptPubKeyJson.getAsJsonObject().get("type"));
				for (JsonElement address : scriptPubKeyJson.getAsJsonObject().get("addresses").getAsJsonArray()) {
					addressList.add(address.toString().replace("\"", ""));
				}
				System.out.println(addressList);
				scriptPubKey.setAddresses(addressList);

				vout.setScriptPubKey(scriptPubKey);

				tx.addVout(vout);
			}
		}
		System.out.println("TX -> " + tx);

	}
}
