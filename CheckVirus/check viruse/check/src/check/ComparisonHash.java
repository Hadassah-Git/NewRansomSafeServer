package check;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List; 
import java.util.Map;
import java.io.*; 
import java.util.ArrayList; 
import java.util.Date;

public class ComparisonHash
{ 
	public static int Comp() 
	{
		Global globals= Global.GetGlobals();
		//LocalDate localDate = LocalDate.now();
		//System.out.println(localDate);
		//Date today = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

		//int numA=globals.hashFilesAfterSend.size();
		//int count=0; 
		
		
		for (int i=globals.numAllAfterSend-1; i>-1 ; i--) 
		{
			

			/*if(globals.hashFilesBeforeSend==null)
			{
				
			}*/
			if (!globals.hashFilesBeforeSend.get(i).equals(globals.hashFilesAfterSend.get(i)))//HASH values ​​are not equal
			{
				/*count++;
				if (count>5) 
				{
					return 1;
				}*/
				return 1;
			}
		}
		return 0;
	}
}