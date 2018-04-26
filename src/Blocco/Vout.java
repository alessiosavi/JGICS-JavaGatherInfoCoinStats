package Blocco;

import org.apache.logging.log4j.Logger;

import com.google.gson.JsonElement;

public class Vout {
	private static Logger logger;

	float value;
	int n;
	ScriptPubKey scriptPubKey = new ScriptPubKey();

	public Vout() {
	}

	public Vout(JsonElement value, JsonElement n, ScriptPubKey scriptPubKey) {
		super();
		this.value = Float.valueOf(value.toString());
		this.n = Integer.valueOf(n.toString());
		this.scriptPubKey = scriptPubKey;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		Vout.logger = logger;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public ScriptPubKey getScriptPubKey() {
		return scriptPubKey;
	}

	public void setScriptPubKey(ScriptPubKey scriptPubKey) {
		this.scriptPubKey = scriptPubKey;
	}

	@Override
	public String toString() {
		return "Vout [value=" + value + ", n=" + n + ", scriptPubKey=" + scriptPubKey.toString() + "]";
	}

}
