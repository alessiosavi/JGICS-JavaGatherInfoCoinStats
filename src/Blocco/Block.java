package Blocco;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.JsonElement;

public class Block {

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

	public Block() {

	}

	public void addTx(Tx tx) {
		this.tx.add(tx);
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public int getConfirmations() {
		return confirmations;
	}

	public void setConfirmations(int confirmations) {
		this.confirmations = confirmations;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getMerkleroot() {
		return merkleroot;
	}

	public void setMerkleroot(String merkleroot) {
		this.merkleroot = merkleroot;
	}

	public float getMint() {
		return mint;
	}

	public void setMint(float mint) {
		this.mint = mint;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public int getNonce() {
		return nonce;
	}

	public void setNonce(int nonce) {
		this.nonce = nonce;
	}

	public String getBits() {
		return bits;
	}

	public void setBits(String bits) {
		this.bits = bits;
	}

	public float getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(float difficulty) {
		this.difficulty = difficulty;
	}

	public String getBlocktrust() {
		return blocktrust;
	}

	public void setBlocktrust(String blocktrust) {
		this.blocktrust = blocktrust;
	}

	public String getChaintrust() {
		return chaintrust;
	}

	public void setChaintrust(String chaintrust) {
		this.chaintrust = chaintrust;
	}

	public String getPreviousBlockHash() {
		return previousBlockHash;
	}

	public void setPreviousBlockHash(String previousBlockHash) {
		this.previousBlockHash = previousBlockHash;
	}

	public String getNextBlockHash() {
		return nextBlockHash;
	}

	public void setNextBlockHash(String nextBlockHash) {
		this.nextBlockHash = nextBlockHash;
	}

	public String getFlags() {
		return flags;
	}

	public void setFlags(String flags) {
		this.flags = flags;
	}

	public String getProofhash() {
		return proofhash;
	}

	public void setProofhash(String proofhash) {
		this.proofhash = proofhash;
	}

	public int getEntropyBit() {
		return entropyBit;
	}

	public void setEntropyBit(int entropyBit) {
		this.entropyBit = entropyBit;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getModifierChecksum() {
		return modifierChecksum;
	}

	public void setModifierChecksum(String modifierChecksum) {
		this.modifierChecksum = modifierChecksum;
	}

	public List<Tx> getTx() {
		return tx;
	}

	public void setTx(List<Tx> tx) {
		this.tx = tx;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public void dumpToFile(String fileName) {
		String special = "*********************************************************\n";
		String string = "Block[" + height + "]->" + "[hash=" + hash + ", confirmations=" + confirmations + ", size="
				+ size + ", version=" + version + ", merkleroot=" + merkleroot + ", mint=" + mint + ", time=" + time
				+ ", nonce=" + nonce + ", bits=" + bits + ", difficulty=" + difficulty + ", blocktrust=" + blocktrust
				+ ", chaintrust=" + chaintrust + ", previousBlockHash=" + previousBlockHash + ", nextBlockHash="
				+ nextBlockHash + ", flags=" + flags + ", proofhash=" + proofhash + ", entropyBit=" + entropyBit
				+ ", modifier=" + modifier + ", modifierChecksum=" + modifierChecksum + ", tx=" + tx + ", signature="
				+ signature + "]\n";

		try (FileWriter fw = new FileWriter(fileName, true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw)) {
			out.println(special + string + special);

		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			System.out.println(e.getMessage());

		}
	}

	@Override
	public String toString() {
		return "Block[" + height + "]" + "{\nhash=" + hash + "\n confirmations=" + confirmations + "\n size=" + size
				+ "\n version=" + version + "\n merkleroot=" + merkleroot + "\n mint=" + mint + "\n time=" + time
				+ "\n nonce=" + nonce + "\n bits=" + bits + "\n difficulty=" + difficulty + "\n blocktrust="
				+ blocktrust + "\n chaintrust=" + chaintrust + "\n previousBlockHash=" + previousBlockHash
				+ "\n nextBlockHash=" + nextBlockHash + "\n flags=" + flags + "\n proofhash=" + proofhash
				+ "\n entropyBit=" + entropyBit + "\n modifier=" + modifier + "\n modifierChecksum=" + modifierChecksum
				+ "\n tx=" + tx + "\n signature=" + signature + "\n}";
	}

}
