package coin;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonObject;

import Blocco.Block;
import util.Util;

public class SyncChain extends Thread {
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

	public void run() {
		try {
			blockchainPiece = syncChain(this.blockStart, this.blockStop);
		} catch (MalformedURLException e) {
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
	 * This method download the portion of chain between the 2 input parameters
	 * 
	 * @param blockStart
	 * @param blockStop
	 * @throws MalformedURLException
	 */
	public List<Block> syncChain(int blockStart, int blockStop) throws MalformedURLException {
		String blockHash;
		JsonObject blockRAW;
		Block blocco;
		while (blockStart <= blockStop) {
			blockHash = Util.getBlockHash(blockStart, Util.populateApiMap().get("getblockhash"));
			blockRAW = (JsonObject) Util.getJSON(Util.populateApiMap().get("getblock") + blockHash);
			blocco = Util.parseBlock(blockRAW);
			blocco.dumpToFile(pathLogFile);
			blockchainPiece.add(blocco);
			logger.debug(blocco.toString() + "\n");
			blockStart++;
		}
		return blockchainPiece;
	}

	public List<Block> syncChainNeedleCoin(int blockStart, int blockStop) throws MalformedURLException {
		while (blockStart < blockStop) {
			blockchainPiece = this.syncChain(blockStart, blockStop);
			blockStart++;
		}
		return blockchainPiece;

	}

}
