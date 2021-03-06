package check;
import java.io.File; 
import java.io.IOException; 
import java.nio.file.Files; 
import java.nio.file.Path; 
import java.nio.file.Paths; 
import java.nio.file.attribute.BasicFileAttributes; 
import java.nio.file.attribute.FileTime; 
import java.text.ParseException; 
import java.text.SimpleDateFormat; 
import java.util.Date; 
import java.util.HashMap;


public class AccessFiles{
	
	public static TwoDates DateAndTimeAccessFile(File file) throws IOException {
		
		Path path=file.toPath(); 
		BasicFileAttributes fatrr = Files.readAttributes(path,BasicFileAttributes.class);
		String FullDate= fatrr.lastModifiedTime().toString();
		String date= FullDate.substring(0, FullDate.indexOf('T'));
		String time=FullDate.substring(FullDate.indexOf('T')+1, FullDate.indexOf('Z'));
		//Date myDateTime=new Date(dateTime);
		
		Date newDate=null, newTime=null;
		SimpleDateFormat dt = new SimpleDateFormat ("yyyy-MM-dd");
		System.out.print(date + " Parses as ");
		try { 
			newDate = dt.parse(date); 
			System. out.println(newDate);
		}
		catch (ParseException e) { 
			System. out.println("Unparseable using " + dt); 
			}
		SimpleDateFormat tt = new SimpleDateFormat ("HH:mm:ss");
		System.out.print(time + " Parses as ");
		try {
			newTime = tt.parse(time); System. out.println(newTime); 
			}
		catch (ParseException e) {
			System.out.println("Unparseable using " + tt); 
			}
		TwoDates dateTime; 
		dateTime=new TwoDates(newDate, newTime); 
		return dateTime;
	}
}