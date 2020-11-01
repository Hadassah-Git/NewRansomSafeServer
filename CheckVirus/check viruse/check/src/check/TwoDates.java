package check;
import java.util.Date;

public class TwoDates
{
	Date date; Date time;
	public TwoDates(Date date, Date time) 
	{ 
		super(); 
		this.date = date; 
		this.time = time; 
	}
	
	public Date getDate() 
	{ 
		return date; 
	}
	
	public void setDate(Date date) 
	{ 
		this.date = date; 
	}
	
	public Date getTime() 
	{ 
		return time; 
	}
	
	public void setTime(Date time) 
	{ 
		this.time = time; 
	}
}