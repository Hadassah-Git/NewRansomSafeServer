package check;
import java.io.File; 
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SecondScann
{
	public static void ScannAgain() throws IOException 
	{
		Global globals= Global.GetGlobals();
		TwoDates myD=null;
		int i=0;
		List<Date> dateArrTemp=new ArrayList<Date>();//Array the size of all files for dates
		//List<Integer> hashArrTemp=new ArrayList<Integer>();//

		//Go through the predefined folders
		File file = new File("C:\\Users\\208026054\\Desktop\\pic");
		File[] files = file.listFiles(); 
		
		for(File f: files)//Scan the "honeypot" files
		{
			myD= AccessFiles.DateAndTimeAccessFile(f);
			dateArrTemp=globals.timesFilesAfterSend.get(myD.getDate());
			if(dateArrTemp==null)
			{
				dateArrTemp=new ArrayList<Date>();
			}
			dateArrTemp.add(myD.getTime());
			globals.timesFilesAfterSend.put(myD.getDate(), dateArrTemp);
			//hashArrTemp=globals.hashFilesAfterSend.get(myD.getDate());
			//if(hashArrTemp==null)
			//{
			//	hashArrTemp=new ArrayList<Integer>();
			//}
			try {
				globals.hashFilesAfterSend.add(ToHash.GetHash(f));
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//globals.hashFilesAfterSend.put(myD.getDate(), hashArrTemp);
			i++;
		//	System.out.print(f.hashCode());
			
			globals.numAllAfterSend++;
		}
		//globals.timesFilesAfterSend.put(myD.getDate(),dateArrTemp);
		//globals.hashFilesAfterSend.put(myD.getDate(),hashArrTemp );
		
	}
}