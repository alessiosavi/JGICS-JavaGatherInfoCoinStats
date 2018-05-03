package Blocco;

import java.io.Serializable;

import com.google.gson.JsonElement;

public class ScriptSig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7462236099441388693L;
	String asm;
	String hex;

	public ScriptSig() {
		this.asm = "";
		this.hex = "";
	}

	public ScriptSig(JsonElement asm, JsonElement hex) {
		super();
		if (asm != null)
			this.asm = asm.toString();
		if (hex != null)
			this.hex = hex.toString();
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

	@Override
	public String toString() {
		return "ScriptSig [asm=" + asm + ", hex=" + hex + "]";
	}

}
