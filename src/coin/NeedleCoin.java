package coin;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonObject;

import Blocco.Block;
import util.Util;

public class NeedleCoin {

	private String NDC_LOG;
	private static String FILE_NAME = "ndcDump";

	private Logger logger = LogManager.getLogger(NeedleCoin.class);

	private String name = "NeedleCoin";
	private String ticker = "NDC";
	private List<String> marketList;
	private List<Block> blockchain;
	private int heightChain;
	private String urlExplorer = "http://79.20.51.52:28017/";
	private Map<String, String> apiCommands = new HashMap<String, String>(); // contains the api command

	/**
	 * The constructor initialize the object setting the properly path
	 * 
	 * @param path
	 * @throws MalformedURLException
	 */
	public NeedleCoin(String path) throws MalformedURLException {
		if (path.lastIndexOf("/") < path.length() - 1) {
			NDC_LOG = path + "/" + FILE_NAME;
		} else {
			NDC_LOG = path + FILE_NAME;

		}
		logger.info("Needle Coin Created\n");
		logger.info("PATH -> " + NDC_LOG);
		blockchain = new ArrayList<Block>();
		populateApiMap();
		syncChain();
	}

	/**
	 * This method download the portion of chain between the 2 input parameters
	 * 
	 * @param blockStart
	 * @param blockStop
	 * @throws MalformedURLException
	 */
	@SuppressWarnings("unused")
	private void syncChain(int blockStart, int blockStop) throws MalformedURLException {
		String blockHash;
		logger.info("*** STARTED SYNC CHAIN *** \n");
		for (int i = blockStart; i < blockStop; i++) {
			blockHash = Util.getBlockHash(i, apiCommands.get("getblockhash"));
			JsonObject blockRAW = (JsonObject) Util.getJSON(apiCommands.get("getblock") + blockHash);
			Block blocco = Util.parseBlock(blockRAW);
			blockchain.add(blocco);
			logger.debug(blocco.toString());
		}

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

		heightChain = Integer.valueOf((String) Util.getJSON(apiCommands.get("getblockcount")));
		logger.info("Initializing sync:\n Total block -> " + heightChain + "\n");
		int myBlock = 1;
		while (myBlock < heightChain) {
			logger.debug("Fetching block " + myBlock + "\n");
			blockHash = Util.getBlockHash(myBlock, apiCommands.get("getblockhash"));
			blockRAW = (JsonObject) Util.getJSON(apiCommands.get("getblock") + blockHash);
			blocco = Util.parseBlock(blockRAW);
			blocco.dumpToFile(NDC_LOG);
			blockchain.add(blocco);
			logger.debug(blocco.toString());
			myBlock++;
		}
	}

	/**
	 * Populate the map with the url of every Explorer methods getRAWtransaction &&
	 * gettransactions have to splitted by "&" character for insert the hash value
	 */
	private void populateApiMap() {
		logger.info("*** INITIALIZE API MAP *** \n");
		apiCommands.put("getdifficulty", urlExplorer + "api/getdifficulty");
		apiCommands.put("getconnectioncount", urlExplorer + "api/getconnectioncount");
		apiCommands.put("getblockcount", urlExplorer + "api/getblockcount");
		apiCommands.put("getblockhash", urlExplorer + "api/getblockhash?index=");
		apiCommands.put("getblock", urlExplorer + "api/getblock?hash=");
		apiCommands.put("getRAWtransaction", urlExplorer + "api/getrawtransaction?txid=&decrypt=0");// split for &
		apiCommands.put("gettransaction", urlExplorer + "api/getrawtransaction?txid=&decrypt=1");// split for &
		apiCommands.put("getnetworkhashps ", urlExplorer + "api/getnetworkhashps");
		apiCommands.put("getmoneysupply", urlExplorer + "ext/getmoneysupply");
		apiCommands.put("getdistribution ", urlExplorer + "ext/getdistribution ");
		apiCommands.put("getaddress", urlExplorer + "ext/getaddress/");
		apiCommands.put("getbalance", urlExplorer + "ext/getbalance/");
		apiCommands.put("getlasttxs", urlExplorer + "ext/getlasttxs/10/100"); // return tx
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public List<String> getMarketList() {
		return marketList;
	}

	public void setMarketList(List<String> marketList) {
		this.marketList = marketList;
	}

	public List<Block> getBlockchain() {
		return blockchain;
	}

	public void setBlockchain(List<Block> blockchain) {
		this.blockchain = blockchain;
	}

	public int getHeightChain() {
		return heightChain;
	}

	public void setHeightChain(int heightChain) {
		this.heightChain = heightChain;
	}

	public String getUrlExplorer() {
		return urlExplorer;
	}

	public void setUrlExplorer(String urlExplorer) {
		this.urlExplorer = urlExplorer;
	}

	public Map<String, String> getApiCommands() {
		return apiCommands;
	}

	public void setApiCommands(Map<String, String> apiCommands) {
		this.apiCommands = apiCommands;
	}

}
