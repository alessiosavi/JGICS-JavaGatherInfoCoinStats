import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import coin.NeedleCoin;
import coin.SyncChain;

public class Main {
	public static Logger logger = LogManager.getLogger(Main.class);
	static List<SyncChain> syncChain = new ArrayList<SyncChain>();

	public static int nThread = 5;

	public static int nBlocchi = 100;

	public static int nBlocchiXthread = nBlocchi / nThread;

	public static void main(String[] args) throws MalformedURLException, InterruptedException {
		// TODO Auto-generated method stub
		@SuppressWarnings("unused")
		NeedleCoin needle = new NeedleCoin(args[0]);
		populateSyncChainList(nThread, nBlocchiXthread);

		for (SyncChain s : syncChain) {
			s.start();
		}
		while (!syncChain.isEmpty()) {
			Iterator<SyncChain> it = syncChain.iterator();
			while (it.hasNext()) {
				SyncChain sync = it.next();
				if (!sync.isAlive()) {
					logger.debug(sync.getBlockchainPiece().toString() + "\n");
					it.remove();
				}

			}
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
