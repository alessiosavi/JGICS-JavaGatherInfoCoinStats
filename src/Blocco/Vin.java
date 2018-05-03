package Blocco;

import java.io.Serializable;

import com.google.gson.JsonElement;

public class Vin implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2601612925330344291L;
	public String txID;
	public int vout;
	public String coinbase;
	public double sequence;
	ScriptSig scriptSig;

	public Vin() {
		this.txID = "";
		this.vout = -1;
		coinbase = "";
		sequence = -1L;
		scriptSig = new ScriptSig();

	}

	public Vin(JsonElement txID, JsonElement vout, JsonElement coinbase, JsonElement sequence) {
		if (txID != null)
			this.txID = txID.toString();
		if (vout != null)
			this.vout = Integer.valueOf(vout.toString());
		if (coinbase != null)
			this.coinbase = coinbase.toString();
		if (sequence != null)
			this.sequence = Double.valueOf(sequence.toString());
	}

	public String getCoinbase() {
		return coinbase;
	}

	public double getSequence() {
		return sequence;
	}

	public void setCoinbase(String coinbase) {
		this.coinbase = coinbase;
	}

	public void setSequence(double sequence) {
		this.sequence = sequence;
	}

	public String getTxID() {
		return txID;
	}

	public void setTxID(String txID) {
		this.txID = txID;
	}

	public int getVout() {
		return vout;
	}

	public void setVout(int vout) {
		this.vout = vout;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ScriptSig getScriptSig() {
		return scriptSig;
	}

	public void setScriptSig(ScriptSig scriptSig) {
		this.scriptSig = scriptSig;
	}

	@Override
	public String toString() {
		return "Vin [txID=" + txID + ", vout=" + vout + ", coinbase=" + coinbase + ", sequence=" + sequence
				+ ", scriptSig=" + scriptSig + "]";
	}

}
