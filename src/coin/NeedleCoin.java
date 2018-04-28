package coin;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonObject;

import Blocco.Block;
import util.Util;

public class NeedleCoin {

	public String NDC_LOG;
	public String FILE_NAME = "ndcDump";

	public Logger logger = LogManager.getLogger(NeedleCoin.class);

	public String name = "NeedleCoin";
	public String ticker = "NDC";
	public List<Block> blockchain;
	public int heightChain;
	public String urlExplorer = "http://79.20.51.52:28017/";

	/**
	 * The constructor initialize the object setting the properly path
	 * 
	 * @param path
	 * @throws MalformedURLException
	 */

	public NeedleCoin() {
		Util.populateApiMap();
	}

	public NeedleCoin(String path) throws MalformedURLException {

		if (path.lastIndexOf("/") < path.length() - 1) {
			NDC_LOG = path + "/" + FILE_NAME;
		} else {
			NDC_LOG = path + FILE_NAME;

			logger.info("Needle Coin Created\n");
			logger.info("PATH -> " + NDC_LOG);
			blockchain = new ArrayList<Block>();
		}
	}

	public void addBlockchainPiece(List<Block> blockchainPiece) {
		this.blockchain.addAll(blockchainPiece);
	}

	public List<Block> getBlockchain() {
		return blockchain;
	}

	public String getName() {
		return name;
	}

	public String getTicker() {
		return ticker;
	}

	public void setBlockchain(List<Block> blockchain) {
		this.blockchain.addAll(blockchain);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	/**
	 * This method download the complete chain from a given start number
	 * 
	 * @throws MalformedURLException
	 */
	public void syncChain() throws MalformedURLException {
		String blockHash;
		JsonObject blockRAW;
		Block blocco;
		logger.info("*** STARTED SYNC CHAIN *** \n");

		heightChain = Integer.valueOf((String) Util.getJSON(Util.populateApiMap().get("getblockcount")));
		// logger.info("Initializing sync:\n Total block -> " + heightChain + "\n");
		int myBlock = 1;
		while (myBlock < heightChain) {
			// logger.debug("Fetching block " + myBlock + "\n");
			blockHash = Util.getBlockHash(myBlock, Util.populateApiMap().get("getblockhash"));
			blockRAW = (JsonObject) Util.getJSON(Util.populateApiMap().get("getblock") + blockHash);
			blocco = Util.parseBlock(blockRAW);
			blocco.dumpToFile(NDC_LOG);
			blockchain.add(blocco);
			logger.debug(blocco.toString());
			myBlock++;
		}
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
		List<Block> blockChainPiece = new ArrayList<Block>();
		// logger.info("*** STARTED SYNC CHAIN *** \n");

		// logger.info("Initializing sync:\n Going to download from " + blockStart + "
		// To -> " + blockStop);
		while (blockStart < blockStop) {
			// logger.debug("Fetching block " + blockStart + "\n");
			blockHash = Util.getBlockHash(blockStart, Util.populateApiMap().get("getblockhash"));
			blockRAW = (JsonObject) Util.getJSON(Util.populateApiMap().get("getblock") + blockHash);
			blocco = Util.parseBlock(blockRAW);
			blocco.dumpToFile(NDC_LOG);
			// blockChainPiece.add(blocco);
			// logger.debug(blocco.toString());
			blockStart++;
		}
		return blockChainPiece;
	}

	@Override
	public String toString() {
		String s = "\n******** DUMPING BLOCKCHAIN  START*******\n";
		logger.info(s);
		StringBuilder sb = new StringBuilder();
		for (Block block : blockchain) {
			sb.append(block + "\n");
		}
		return "NeedleCoin [name=" + name + ", ticker=" + ticker + ", blockchain=" + sb + ", heightChain=" + heightChain
				+ ", urlExplorer=" + urlExplorer + "]" + s;

	}

}
