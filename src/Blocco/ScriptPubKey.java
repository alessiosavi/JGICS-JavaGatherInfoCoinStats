package Blocco;

import org.apache.logging.log4j.Logger;

import com.google.gson.JsonElement;

public class ScriptPubKey {
	private static Logger logger;

	String asm;
	String hex;
	int rqSigs;
	String type; // pubkey,pubkeyhash,nontstandard

	public ScriptPubKey(JsonElement asm, JsonElement hex, JsonElement type) {
		super();
		this.asm = asm.toString();
		this.hex = hex.toString();
		this.type = type.toString();
	}

	public ScriptPubKey() {

	}

	@Override
	public String toString() {
		return "ScriptPubKey [asm=" + asm + ", hex=" + hex + ", rqSigs=" + rqSigs + ", type=" + type + "]";
	}

	public String getAsm() {
		return asm;
	}

	public void setAsm(String asm) {
		this.asm = asm;
	}

	public String getHex() {
		return hex;
	}

	public void setHex(String hex) {
		this.hex = hex;
	}

	public int getRqSigs() {
		return rqSigs;
	}

	public void setRqSigs(int rqSigs) {
		this.rqSigs = rqSigs;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
