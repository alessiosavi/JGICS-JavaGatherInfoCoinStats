package Blocco;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.JsonElement;

public class Block implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2521990011570220787L;

	private String hash;
	private int confirmations;
	private int size;
	private int height;
	private int version;
	private String merkleroot;
	private float mint;
	private Date time;
	private int nonce;
	private String bits;
	private float difficulty;
	private String blocktrust;
	private String chaintrust;
	private String previousBlockHash;
	private String nextBlockHash;
	private String flags; // proof-of-work,proof-of-stake
	private String proofhash; // equals to hash
	private int entropyBit;
	private String modifier;
	private String modifierChecksum;
	private List<Tx> tx = new ArrayList<Tx>();
	private String signature;

	public Block() {

	}

	public Block(JsonElement hash, JsonElement confirmations, JsonElement size, JsonElement height, JsonElement version,
			JsonElement merkleroot, JsonElement mint, String time, JsonElement nonce, JsonElement bits,
			JsonElement difficulty, JsonElement blocktrust, JsonElement chaintrust, JsonElement previousBlockHash,
			JsonElement nextBlockHash, JsonElement flags, JsonElement proofhash, JsonElement entropyBit,
			JsonElement modifier, JsonElement modifierChecksum) {
		super();
		this.hash = hash.getAsString();
		this.confirmations = confirmations.getAsInt();
		this.size = size.getAsInt();
		this.height = height.getAsInt();
		this.version = version.getAsInt();
		this.merkleroot = merkleroot.getAsString();
		this.mint = mint.getAsFloat();
		this.time = new Date(Long.valueOf(time));
		this.nonce = nonce.getAsInt();
		this.bits = bits.getAsString();
		this.difficulty = difficulty.getAsFloat();
		this.blocktrust = blocktrust.getAsString();
		this.chaintrust = chaintrust.getAsString();
		this.previousBlockHash = previousBlockHash.getAsString();
		this.nextBlockHash = nextBlockHash.getAsString();
		this.flags = flags.getAsString();
		this.proofhash = proofhash.getAsString();
		this.entropyBit = entropyBit.getAsInt();
		this.modifier = modifier.getAsString();
		this.modifierChecksum = modifierChecksum.getAsString();
		this.signature = "";
	}

	/**
	 * Add a Tx object to the current list
	 * 
	 * @param tx
	 */
	public void addTx(Tx tx) {
		this.tx.add(tx);
	}

	/**
	 * Take in input the file name. The path is setted as first input parameter in
	 * Main
	 * 
	 * @param fileName
	 */
	public void dumpToFile(String path) {
		String string = "Block[" + height + "]->" + "[hash=" + hash + ", confirmations=" + confirmations + ", size="
				+ size + ", version=" + version + ", merkleroot=" + merkleroot + ", mint=" + mint + ", time=" + time
				+ ", nonce=" + nonce + ", bits=" + bits + ", difficulty=" + difficulty + ", blocktrust=" + blocktrust
				+ ", chaintrust=" + chaintrust + ", previousBlockHash=" + previousBlockHash + ", nextBlockHash="
				+ nextBlockHash + ", flags=" + flags + ", proofhash=" + proofhash + ", entropyBit=" + entropyBit
				+ ", modifier=" + modifier + ", modifierChecksum=" + modifierChecksum + ", tx=" + tx + ", signature="
				+ signature + "]\n";

		try (FileWriter fw = new FileWriter(path, true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw)) {
			out.println(string);

		} catch (Exception e) {
			System.err.println(e.getStackTrace());
			System.err.println(e.getMessage());
		}
	}

	public String getBits() {
		return bits;
	}

	public String getBlocktrust() {
		return blocktrust;
	}

	public String getChaintrust() {
		return chaintrust;
	}

	public int getConfirmations() {
		return confirmations;
	}

	public float getDifficulty() {
		return difficulty;
	}

	public int getEntropyBit() {
		return entropyBit;
	}

	public String getFlags() {
		return flags;
	}

	public String getHash() {
		return hash;
	}

	public int getHeight() {
		return height;
	}

	public String getMerkleroot() {
		return merkleroot;
	}

	public float getMint() {
		return mint;
	}

	public String getModifier() {
		return modifier;
	}

	public String getModifierChecksum() {
		return modifierChecksum;
	}

	public String getNextBlockHash() {
		return nextBlockHash;
	}

	public int getNonce() {
		return nonce;
	}

	public String getPreviousBlockHash() {
		return previousBlockHash;
	}

	public String getProofhash() {
		return proofhash;
	}

	public String getSignature() {
		return signature;
	}

	public int getSize() {
		return size;
	}

	public Date getTime() {
		return time;
	}

	public List<Tx> getTx() {
		return tx;
	}

	public int getVersion() {
		return version;
	}

	public void setBits(String bits) {
		this.bits = bits;
	}

	public void setBlocktrust(String blocktrust) {
		this.blocktrust = blocktrust;
	}

	public void setChaintrust(String chaintrust) {
		this.chaintrust = chaintrust;
	}

	public void setConfirmations(int confirmations) {
		this.confirmations = confirmations;
	}

	public void setDifficulty(float difficulty) {
		this.difficulty = difficulty;
	}

	public void setEntropyBit(int entropyBit) {
		this.entropyBit = entropyBit;
	}

	public void setFlags(String flags) {
		this.flags = flags;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setMerkleroot(String merkleroot) {
		this.merkleroot = merkleroot;
	}

	public void setMint(float mint) {
		this.mint = mint;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public void setModifierChecksum(String modifierChecksum) {
		this.modifierChecksum = modifierChecksum;
	}

	public void setNextBlockHash(String nextBlockHash) {
		this.nextBlockHash = nextBlockHash;
	}

	public void setNonce(int nonce) {
		this.nonce = nonce;
	}

	public void setPreviousBlockHash(String previousBlockHash) {
		this.previousBlockHash = previousBlockHash;
	}

	public void setProofhash(String proofhash) {
		this.proofhash = proofhash;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public void setTx(List<Tx> tx) {
		this.tx = tx;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "Block[" + height + "]->" + "[hash=" + hash + ", confirmations=" + confirmations + ", size=" + size
				+ ", version=" + version + ", merkleroot=" + merkleroot + ", mint=" + mint + ", time=" + time
				+ ", nonce=" + nonce + ", bits=" + bits + ", difficulty=" + difficulty + ", blocktrust=" + blocktrust
				+ ", chaintrust=" + chaintrust + ", previousBlockHash=" + previousBlockHash + ", nextBlockHash="
				+ nextBlockHash + ", flags=" + flags + ", proofhash=" + proofhash + ", entropyBit=" + entropyBit
				+ ", modifier=" + modifier + ", modifierChecksum=" + modifierChecksum + ", tx=" + tx + ", signature="
				+ signature + "]\n";
	}

}
