package Blocco;

import com.google.gson.JsonElement;

public class Vin {

	String coinbase;
	double sequence;

	@Override
	public String toString() {
		return "Vin [coinbase=" + coinbase + ", sequence=" + sequence + "]";
	}

	public Vin(JsonElement coinbase, JsonElement sequence) {
		this.coinbase = coinbase.toString();
		this.sequence = Double.valueOf(sequence.toString());
	}

	public Vin() {
	}

	public String getCoinbase() {
		return coinbase;
	}

	public void setCoinbase(String coinbase) {
		this.coinbase = coinbase;
	}

	public double getSequence() {
		return sequence;
	}

	public void setSequence(double sequence) {
		this.sequence = sequence;
	}

}
