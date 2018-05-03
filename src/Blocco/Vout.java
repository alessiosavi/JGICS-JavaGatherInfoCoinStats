package Blocco;

import java.io.Serializable;

import com.google.gson.JsonElement;

public class Vout implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 124124493252327744L;

	float value = -1f;
	int n = -1;
	ScriptPubKey scriptPubKey=new ScriptPubKey();

	public Vout() {
		this.value = -1f;
		this.n = -1;
	}

	public Vout(JsonElement value, JsonElement n) {
		super();
		if (value != null)
			this.value = Float.valueOf(value.toString());
		if (n != null)
			this.n = Integer.valueOf(n.toString());
	}

	public int getN() {
		return n;
	}

	public ScriptPubKey getScriptPubKey() {
		return scriptPubKey;
	}

	public float getValue() {
		return value;
	}

	public void setN(int n) {
		this.n = n;
	}

	public void setScriptPubKey(ScriptPubKey scriptPubKey) {
		this.scriptPubKey = scriptPubKey;
	}

	public void setValue(float value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Vout [value=" + value + ", n=" + n + ", scriptPubKey=" + scriptPubKey.toString() + "]";
	}

}
