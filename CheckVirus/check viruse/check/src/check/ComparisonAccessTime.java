package check;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List; 
import java.util.Map;
import java.io.*; 
import java.util.ArrayList; 
import java.util.Date;

import javax.print.attribute.standard.Compression;

public class ComparisonAccessTime
{ 
	public static int Comp() 
	{
		Global globals= Global.GetGlobals();
		LocalDate localDate = LocalDate.now();
		System.out.println(localDate);
		Date today = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		List<Date> todayFilesAftrS = globals.timesFilesAfterSend.get(today);
		List<Date> todayFilesBfrS = globals.timesFilesBeforeSend.get(today);
		int count=0;//Counts the number of access dates that are not equal
		
	
		if (globals.numAllBeforeSend!=globals.numAllAfterSend) //There is a change in the number of files - returns an error
		{
			return 1;
		}
		
		
		if (todayFilesAftrS==null)//Did not access the files today 
		{
			return 0;
		}
		
		for (int i=0; i<todayFilesAftrS.size() ; i++) 
		{
			if(todayFilesBfrS==null)
			{
				return ComparisonHash.Comp();
			}
			//System.out.print(todayFilesBfrS.get(0));
			//System.out.print(todayFilesAftrS.get(0));
			if (!todayFilesBfrS.get(i).equals(todayFilesAftrS.get(i)))//Access dates are not equal
			{
				count++;
				if (count>3) // Accessed more than 3 files
				{
					return ComparisonHash.Comp();//Checking the equality of HASH values
				}
				//return ComparisonHash.Comp();
			}
		}
		return 0;
	}
}


