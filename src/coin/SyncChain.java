package coin;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonObject;

import Blocco.Block;
import util.Util;

public class SyncChain extends Thread implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static Logger logger = LogManager.getLogger(SyncChain.class);

	/**
	 * This method return the piece of blockchin between the 2 start number
	 * 
	 * @param blockStart
	 * @param blockStop
	 * @return
	 */
	String LOG_FILE_NAME = "ndcDump";

	String pathLogFile = "";
	int blockStart, blockStop;
	List<Block> blockchainPiece = new ArrayList<Block>();

	/**
	 * Initialize number of block to download
	 * 
	 * @param blockStart
	 * @param blockStop
	 */
	public SyncChain(int blockStart, int blockStop, String pathLogFile) {
		this.blockStart = blockStart;
		this.blockStop = blockStop;
		this.pathLogFile = Util.parsePath(pathLogFile);
	}

	public List<Block> getBlockchainPiece() {
		return blockchainPiece;
	}

	public int getBlockStart() {
		return blockStart;
	}

	public int getBlockStop() {
		return blockStop;
	}

	@Override
	public void run() {
		try {
			blockchainPiece = syncChain(this.blockStart, this.blockStop);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void setBlockchainPiece(List<Block> blockchainPiece) {
		this.blockchainPiece = blockchainPiece;
	}

	public void setBlockStart(int blockStart) {
		this.blockStart = blockStart;
	}

	public void setBlockStop(int blockStop) {
		this.blockStop = blockStop;
	}

	/**
	 * This method download the portion of chain between the 2 input parameters. It
	 * take 2 integer as input for delimit the bounds of the blockchain's block to
	 * download (from which block start and from which block stop).
	 * 
	 * It use the blockStart (the height of that block) for get the "BlockHash"
	 * (getBlockHash). Then try to fetch the JsonObject block and add to the chain.
	 * 
	 * @param blockStart
	 * @param blockStop
	 * @throws IOException
	 */
	public List<Block> syncChain(int blockStart, int blockStop) throws IOException {
		String blockHash;
		JsonObject blockRAW;
		Block blocco;
		while (blockStart <= blockStop) {
			blockHash = Util.getBlockHash(blockStart, Util.populateApiMap().get("getblockhash"));
			blockRAW = (JsonObject) Util.getJSON(Util.populateApiMap().get("getblock") + blockHash);
			blocco = Util.parseBlock(blockRAW);
			blockchainPiece.add(blocco);
			logger.info(blocco);
			blockStart++;
		}
		return blockchainPiece;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Block block : blockchainPiece) {
			sb.append(block.toString() + "\n");
		}
		return sb.toString() + "\n";
	}

}
