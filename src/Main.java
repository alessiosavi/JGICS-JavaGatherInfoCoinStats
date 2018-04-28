import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import coin.SyncChain;

public class Main {
	public static Logger logger = LogManager.getLogger(Main.class);
	static List<SyncChain> syncChain;

	public static int nThread = 25;

	public static int nBlocchi = 1000;
	public static int nBlocchiXthread;

	/**
	 * Take as first argument the path where dump data
	 * 
	 * @param args
	 * @throws NumberFormatException
	 * @throws MalformedURLException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws NumberFormatException, MalformedURLException {
		// nBlocchi = Integer.valueOf((String)
		// Util.getJSON(Util.populateApiMap().get("getblockcount")));

		nBlocchiXthread = (nBlocchi / nThread) / 1000;
		System.out.println("Blocchi X Thread : " + nBlocchiXthread);
		System.out.println("Blocchi Totali : " + nBlocchi);

		syncChain = new ArrayList<SyncChain>();
		populateSyncChainList(nThread, nBlocchiXthread);
		logger.info("*** SyncChain Size -> " + syncChain.size() + "*** \n");

		for (SyncChain s : syncChain) {
			s.start();
		}

	}

	/**
	 * This method populate the list setting which block range have to be downloaded
	 * 
	 * @param nThread
	 * @param nBlocchiXthread
	 */
	public static void populateSyncChainList(int nThread, int nBlocchiXthread) {
		for (int i = 0; i < nThread; i++) {
			int start = i * nBlocchiXthread + 1;
			int stop = (i + 1) * nBlocchiXthread;
			syncChain.add(new SyncChain(start, stop));
		}

	}

}
