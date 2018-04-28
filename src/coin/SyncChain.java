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
	public static Logger logger = LogManager.getLogger(NeedleCoin.class);

	/**
	 * This method return the piece of blockchin between the 2 start number
	 * 
	 * @param blockStart
	 * @param blockStop
	 * @return
	 */

	int blockStart, blockStop;
	List<Block> blockchainPiece = new ArrayList<Block>();

	/**
	 * Initialize number of block to download
	 * 
	 * @param blockStart
	 * @param blockStop
	 */
	public SyncChain(int blockStart, int blockStop) {
		this.blockStart = blockStart;
		this.blockStop = blockStop;
	}

	public List<Block> syncChainNeedleCoin(int blockStart, int blockStop) throws MalformedURLException {
		while (blockStart < blockStop) {
			blockchainPiece = this.syncChain(blockStart, blockStop);
			blockStart++;
		}
		return blockchainPiece;

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
		// logger.info("*** STARTED SYNC CHAIN *** \n");

		// logger.info("Initializing sync:\n Ging to download from " + blockStart + " To
		// -> " + blockStop);
		while (blockStart <= blockStop) {
			// logger.debug("Fetching block " + blockStart + "\n");
			blockHash = Util.getBlockHash(blockStart, Util.populateApiMap().get("getblockhash"));
			blockRAW = (JsonObject) Util.getJSON(Util.populateApiMap().get("getblock") + blockHash);
			blocco = Util.parseBlock(blockRAW);
			blocco.dumpToFile("ndcDump");
			blockchainPiece.add(blocco);
			// logger.debug(blocco.toString());
			blockStart++;
		}
		return blockchainPiece;
	}

	public void run() {
		try {
			blockchainPiece = syncChain(this.blockStart, this.blockStop);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public int getBlockStart() {
		return blockStart;
	}

	public void setBlockStart(int blockStart) {
		this.blockStart = blockStart;
	}

	public int getBlockStop() {
		return blockStop;
	}

	public void setBlockStop(int blockStop) {
		this.blockStop = blockStop;
	}

	public List<Block> getBlockchainPiece() {
		return blockchainPiece;
	}

	public void setBlockchainPiece(List<Block> blockchainPiece) {
		this.blockchainPiece = blockchainPiece;
	}

	
}
