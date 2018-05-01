package Blocco;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;

public class ScriptPubKey implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4666347330506514740L;
	public String asm;
	public String hex;
	public int rqSigs;
	public String type; // pubkey,pubkeyhash,nontstandard
	public List<String> addresses;

	public ScriptPubKey() {
	}

	public ScriptPubKey(JsonElement asm, JsonElement hex, JsonElement type) {
		super();
		this.asm = asm.toString();
		this.hex = hex.toString();
		this.type = type.toString();
		this.addresses = new ArrayList<String>();
	}

	public List<String> getAddresses() {
		return addresses;
	}

	public String getAsm() {
		return asm;
	}

	public String getHex() {
		return hex;
	}

	public int getRqSigs() {
		return rqSigs;
	}

	public String getType() {
		return type;
	}

	public void setAddresses(List<String> addresses) {
		this.addresses = addresses;
	}

	public void setAsm(String asm) {
		this.asm = asm;
	}

	public void setHex(String hex) {
		this.hex = hex;
	}

	public void setRqSigs(int rqSigs) {
		this.rqSigs = rqSigs;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "ScriptPubKey [asm=" + asm + ", hex=" + hex + ", rqSigs=" + rqSigs + ", type=" + type + ", addresses="
				+ addresses + "]";
	}

}
