import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import coin.SyncChain;

public class Main {
	public static Logger logger = LogManager.getLogger(Main.class);
	static List<SyncChain> syncChain;

	public static String PATH_LOG_FILE;
	public static int nThread = 2;

	public static int nBlocchi = 28;

	/**
	 * Take as first argument the path where dump data
	 * 
	 * @param args
	 * @throws NumberFormatException
	 * @throws MalformedURLException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws NumberFormatException, MalformedURLException, InterruptedException {

		PATH_LOG_FILE = getPath(args);
		syncChain = new ArrayList<SyncChain>();

		iterationDispatcher(5);

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
			syncChain.add(new SyncChain(start, stop, PATH_LOG_FILE));
		}
	}

	/*
	 * This method is developed for manage the iteration of download. The following
	 * logic will be implemented:
	 * 
	 * For optimize the I/O operation, every thread have to download a predefined
	 * number of block and store into memory.
	 * 
	 * Let's assume that the total height chain is 2kk block (2'000'000 block).
	 * 
	 * This method have to choose how many block have to be download by thread and
	 * how many cycle of downlaod have to be done.
	 * 
	 * It take in input the number of block that have to be fetched in every
	 * iteration. It have also to manage the spawn of the additional thread until
	 * the blockchain is download.
	 * 
	 * After every iteration, the content of the object fetched from the explorer
	 * API ny the "SyncChain" Class(the "List<Block>" data-structure) will be dumped
	 * in a file using the writeOject.
	 * 
	 * The object tath will be dumped will be a List<Block>. Every list of block
	 * will be concatenated in a properly list that will be the final blockchain.
	 * 
	 * 
	 */
	public static int iterationDispatcher(int nBlocchiXthread) throws InterruptedException {
		// this var will be the block that contains the number of block that every
		// thread have to download at every iteration.

		int totalIteration = nBlocchi / (nBlocchiXthread * nThread);
		logger.info("TOTAL NUMBER OF ITERATION -> " + totalIteration + "\n");
		logger.info("BLOCCHI X THREAD-> " + nBlocchiXthread + "\n");
		logger.info("TOTAL NUMBER OF THEAD -> " + nThread + "\n");

		// This block are the ones that are excluded by the float approximation, and
		// have to be downloaded in a separate cycle.
		int additionalBlock = nBlocchi - (nBlocchiXthread * nThread * totalIteration);
		logger.info("TOTAL NUMBER OF Additional Block -> " + additionalBlock + "\n");
		int start = 0, stop = 1;
		for (int i = 0; i < totalIteration; i++) {
			logger.info("ITERATION " + (i + 1) + ") From Block -> " + (i * nBlocchiXthread * nThread) + " To Block -> "
					+ ((i + 1) * nBlocchiXthread * nThread) + "\n");
			for (int j = 0; j < nThread; j++) {
				start = stop;
				stop = start + nBlocchiXthread - 1;

				System.err.println("START -> " + start);
				System.err.println("STOP -> " + stop);
				stop++;

				// Create blockchain thread
				syncChain.add(new SyncChain(start, stop, PATH_LOG_FILE)); // Create a new Thread Object
				syncChain.get(syncChain.size() - 1).start(); // Start the last thread created
			}

		}

		// Additional block can be only <= nBlocchiXthread
		logger.info("ADDITIONAL BLOCK -> " + additionalBlock + "\n");

		for (int i = nBlocchi - additionalBlock; i < nBlocchi; i++) {
			logger.info("ADDITIONAL BLOCK -> " + i + "\n");
		}
		syncChain.add(new SyncChain(nBlocchi - additionalBlock, nBlocchi, PATH_LOG_FILE));
		syncChain.get(syncChain.size() - 1).start();

		return 1;
	}

	public static String getPath(String[] args) {
		String pathDumpChain;
		if (args != null && args.length > 0) {
			pathDumpChain = args[0].trim();
		} else {
			pathDumpChain = System.getProperty("user.dir").trim();
			logger.error("NO PARAMETER PROVIDED!! USING WORKING DIR AS DEFAULT LOG_DIR \n");
			logger.error("Working Directory = \"" + pathDumpChain + "\"\n");
		}
		return pathDumpChain;
	}
}
