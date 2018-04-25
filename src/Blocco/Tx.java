package Blocco;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Tx {

	String rawHash;
	String hex;
	String txId;
	String version;
	Instant time;
	int locktime;
	List<vin> vinList = new ArrayList<vin>();
	List<vout> voutList = new ArrayList<vout>();
	String blockhash;
	int confirmations;
	Instant blocktime;
}
