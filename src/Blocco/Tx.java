package Blocco;

import java.util.Date;

import com.google.gson.JsonElement;

public class Tx {

	public String hex;
	public String txId;
	public String version;
	public Date time;
	public int locktime;
	public Vin vinList;
	public Vout voutList;
	public String blockhash;
	public int confirmations;
	public Date blocktime;

	public Tx(JsonElement hex, JsonElement txId, JsonElement version, String time, JsonElement locktime,
			JsonElement blockhash, JsonElement confirmations, String blocktime) {
		super();
		this.hex = hex.getAsString();
		this.txId = txId.getAsString();
		this.version = version.getAsString();
		this.time = new Date(Long.valueOf(time));
		this.locktime = locktime.getAsInt();
		this.blockhash = blockhash.getAsString();
		this.confirmations = confirmations.getAsInt();
		this.blocktime = new Date(Long.valueOf(blocktime));
	}

	public Vin getVinList() {
		return vinList;
	}

	public void setVinList(Vin vinList) {
		this.vinList = vinList;
	}

	public Vout getVoutList() {
		return voutList;
	}

	public void setVoutList(Vout voutList) {
		this.voutList = voutList;
	}

	public String getHex() {
		return hex;
	}

	public void setHex(String hex) {
		this.hex = hex;
	}

	public String getTxId() {
		return txId;
	}

	public void setTxId(String txId) {
		this.txId = txId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public int getLocktime() {
		return locktime;
	}

	public void setLocktime(int locktime) {
		this.locktime = locktime;
	}

	public String getBlockhash() {
		return blockhash;
	}

	public void setBlockhash(String blockhash) {
		this.blockhash = blockhash;
	}

	public int getConfirmations() {
		return confirmations;
	}

	public void setConfirmations(int confirmations) {
		this.confirmations = confirmations;
	}

	public Date getBlocktime() {
		return blocktime;
	}

	public void setBlocktime(Date blocktime) {
		this.blocktime = blocktime;
	}

	@Override
	public String toString() {
		String string = "Hex :" + hex + "\ntxid :" + txId + "\nversion :" + version + "\ntime :" + time + "\nlocktime :"
				+ locktime + "\nblockhash :" + blockhash + "\nconfirmations :" + confirmations + "\nblocktime :"
				+ blocktime + "\nvin :" + vinList.toString() + "\nvout :" + voutList.toString();
		return string;
	}

}
