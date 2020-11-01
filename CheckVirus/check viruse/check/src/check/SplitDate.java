package check;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SplitDate {
	
	public void gf() throws ParseException
	{
		Date d= new Date();
		int dd = Calendar.getInstance().DAY_OF_MONTH;
		int mm = Calendar.getInstance().DAY_OF_WEEK_IN_MONTH;
		int yy = Calendar.getInstance().DAY_OF_WEEK;
		System.out.println(dd);
		
		System.out.println(d);
		

		
		int idate=d.getDay();
		int imonth=d.getMonth();
		int iyear=d.getYear();
		
		System.out.println(idate);
		System.out.println(imonth);
		System.out.println(iyear);
		
		String s= iyear+"-"+imonth+"-"+idate;
		System.out.println(s);
		Date newDate=null, newTime=null;
		SimpleDateFormat dt = new SimpleDateFormat ("yyyy-MM-dd");
		//System.out.print(idate + " Parses as ");

		newDate = dt.parse(s); 
			System. out.println(newDate);
			
		
	}
}
