package check;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScaningFiles {
	
	public static void ScaningFirst() {
		
		Global globals= Global.GetGlobals();
		TwoDates myD=null;
		int i=0;
		List<Date> dateArrTemp=new ArrayList<Date>();//Array the size of all files for dates
		//List<Integer> hashArrTemp=new ArrayList<Integer>();//Array the size of all HASH files


		//Go through the predefined folders
		File file = new File("C:\\Users\\208026054\\Desktop\\pic");
		File[] files = file.listFiles();
		
		for(File f: files)//Scan the "honeypot" files
		{
			try {
				myD= AccessFiles.DateAndTimeAccessFile(f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//Get the latest file access time
			System.out.print(myD);
			dateArrTemp=globals.timesFilesBeforeSend.get(myD.getDate());
			System.out.println("this is the time:    "+myD.getTime());
			if(dateArrTemp==null)
			{
				dateArrTemp=new ArrayList<Date>();
			}
			dateArrTemp.add(myD.getTime());
			globals.timesFilesBeforeSend.put(myD.getDate(), dateArrTemp); 
		//	hashArrTemp=globals.hashFilesBeforeSend.get(myD.getDate());
		//	if(globals.hashFilesBeforeSend==null)
		//	{
		//		globals.hashFilesBeforeSend=new ArrayList<Integer>();
		//	}
			try {
				globals.hashFilesBeforeSend.add(ToHash.GetHash(f));
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//	globals.hashFilesBeforeSend.put(myD.getDate(), hashArrTemp);
			i++;
		//	System.out.print(f.hashCode()); 
			
			globals.numAllBeforeSend++;
		}
		//globals.timesFilesBeforeSend.put(myD.getDate(),dateArrTemp);
		//globals.hashFilesBeforeSend.put(myD.getDate(),hashArrTemp );
		
	}
}
