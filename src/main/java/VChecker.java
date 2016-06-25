import Model.*;
import java.util.*;


abstract class VChecker {
	
	/*
	 * Abstract class to define the check method that each checker will implement accordingly
	 */
	
	abstract ArrayList<String> check(String server,String ip, List<Art> artifacts);
	
	abstract boolean output();
	
}
