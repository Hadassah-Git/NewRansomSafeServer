import java.util.HashMap;
import java.util.Map;

import org.virtualbox_6_0.IMachine;


public class GlobalMaps {
	
	static GlobalMaps golbal=null;
	public Map<String, Boolean> VMFree;// Free or not
	public Map<String, IMachine> VMachines; //Machine by IP 
	
	private GlobalMaps() {
		super();
		VMFree = new HashMap<>();
		VMachines =  new HashMap<>();
	}
	
	public static GlobalMaps GetMaps() {
		if (golbal==null) {
			golbal=new GlobalMaps();
		}
		return golbal;
	}
}
