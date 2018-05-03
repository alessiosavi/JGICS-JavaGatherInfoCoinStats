package Blocco;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;

public class ScriptPubKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4666347330506514740L;
	public String asm = "";
	public String hex = "";
	public int reqSigs = -1;
	public String type = ""; // pubkey,pubkeyhash,nontstandard
	public List<String> addresses = new ArrayList<String>();

	// public ScriptPubKey() {
	// this.asm = "";
	// this.hex = "";
	// this.reqSigs = -1;
	// this.type = "";
	// this.addresses = new ArrayList<String>();
	// }

	public ScriptPubKey() {

	}

	public ScriptPubKey(JsonElement asm, JsonElement hex, JsonElement reqSigs, JsonElement type) {
		super();
		if (asm != null)
			this.asm = asm.toString();
		if (hex != null)
			this.hex = hex.toString();
		if (reqSigs != null)
			this.reqSigs = Integer.valueOf(reqSigs.toString());
		if (type != null)
			this.type = type.toString();
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
		return reqSigs;
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
		this.reqSigs = rqSigs;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "ScriptPubKey [asm=" + asm + ", hex=" + hex + ", rqSigs=" + reqSigs + ", type=" + type + ", addresses="
				+ addresses + "]";
	}

}
