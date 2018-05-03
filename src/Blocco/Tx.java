package Blocco;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.JsonElement;

public class Tx implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 826615976123040287L;
	public String hex;
	public String txId;
	public String version;
	public Date time;
	public int locktime;
	public List<Vin> vinList = new ArrayList<Vin>();
	public List<Vout> voutList = new ArrayList<Vout>();
	public String blockhash;
	public int confirmations;
	public Date blocktime;

	/**
	 * Initialize a Tx object. It use an internal ad-hoc cast for every JsonElement
	 * 
	 * @param hex
	 * @param txId
	 * @param version
	 * @param time
	 * @param locktime
	 * @param blockhash
	 * @param confirmations
	 * @param blocktime
	 */
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

	public Tx() {
		this.hex = this.txId = "";
		this.version = "";
		this.time = new Date();
		this.locktime = -1;
		this.vinList = new ArrayList<Vin>();
		this.voutList = new ArrayList<Vout>();
		this.blockhash = "";
		this.confirmations = -1;
		this.blocktime = new Date();

	}

	public String getBlockhash() {
		return blockhash;
	}

	public Date getBlocktime() {
		return blocktime;
	}

	public int getConfirmations() {
		return confirmations;
	}

	public String getHex() {
		return hex;
	}

	public int getLocktime() {
		return locktime;
	}

	public Date getTime() {
		return time;
	}

	public String getTxId() {
		return txId;
	}

	public String getVersion() {
		return version;
	}

	public void setBlockhash(String blockhash) {
		this.blockhash = blockhash;
	}

	public void setBlocktime(Date blocktime) {
		this.blocktime = blocktime;
	}

	public void setConfirmations(int confirmations) {
		this.confirmations = confirmations;
	}

	public void setHex(String hex) {
		this.hex = hex;
	}

	public void setLocktime(int locktime) {
		this.locktime = locktime;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public void setTxId(String txId) {
		this.txId = txId;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<Vin> getVinList() {
		return vinList;
	}

	public void setVinList(List<Vin> vinList) {
		this.vinList = vinList;
	}

	public List<Vout> getVoutList() {
		return voutList;
	}

	public void setVoutList(List<Vout> voutList) {
		this.voutList = voutList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void addVin(Vin vin) {
		this.vinList.add(vin);
	}

	public void addVout(Vout vout) {
		this.voutList.add(vout);
	}

	@Override
	public String toString() {
		String string = "Hex :" + hex + ", txid :" + txId + ", version :" + version + ", time :" + time + ", locktime :"
				+ locktime + ", blockhash :" + blockhash + ", confirmations :" + confirmations + ", blocktime :"
				+ blocktime + ", vin :" + vinList.toString() + ", vout :" + voutList.toString();
		return string;
	}

}
