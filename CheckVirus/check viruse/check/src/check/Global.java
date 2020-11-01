package check;
import java.util.ArrayList;
import java.util.List; 
import java.util.Date; 
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap; 
import java.util.Hashtable;
import java.util.Map;

public class Global //Singlton class
{
	static Global forGlobalclass= null;
	public static int forFileName=0;
	public Map<Date, List<Date>> timesFilesBeforeSend;//Access dates before submitting a request to the site
	public Map<Date, List<Date>> timesFilesAfterSend;//Access dates after submitting a request to the site
	public List<String> hashFilesBeforeSend;//HASH on the files before sending
	public List<String> hashFilesAfterSend;//HASH on the files after sending
	public int numAllBeforeSend;//Number of existing files before sending
	public int numAllAfterSend;//Number of existing files after sending
	
	private Global() 
	{ 
		timesFilesBeforeSend = new HashMap<Date, List<Date>>();
		timesFilesAfterSend = new HashMap<Date, List<Date>>();
		hashFilesBeforeSend = new ArrayList<String>();
		hashFilesAfterSend = new ArrayList<String>();
		numAllBeforeSend=0;
		numAllAfterSend=0;
		
	}
	
	public static Global GetGlobals() 
	{ 
		if (forGlobalclass == null) 
		{
			forGlobalclass =new Global(); 
		} 
	return forGlobalclass;
	}
}
