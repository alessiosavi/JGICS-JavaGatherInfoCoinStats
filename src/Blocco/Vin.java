package Blocco;

import java.io.Serializable;

import com.google.gson.JsonElement;

public class Vin implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2601612925330344291L;
	public String coinbase;
	public double sequence;

	public Vin() {
	}

	public Vin(JsonElement coinbase, JsonElement sequence) {
		this.coinbase = coinbase.toString();
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

	@Override
	public String toString() {
		return "Vin [coinbase=" + coinbase + ", sequence=" + sequence + "]";
	}

}
