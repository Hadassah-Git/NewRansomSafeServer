package virtualMacMan;
import org.virtualbox_6_0.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.plaf.ProgressBarUI;

public class MachinesManager {
	
	public Map<String, Boolean> VMFree = new HashMap<>();// פנוי או לא
	public Map<String, IMachine> VMachines = new HashMap<>();//IP מכונה לפי 
	static int nameVM=0;//משתנה לשם חדש למכונה
	
	public VirtualBoxManager virtualBoxManager;
	public IVirtualBox vbox;
	public List<IMachine> machines;
	public Object lock = new Object();

	 private static String errorIfNull(String systemProperty) {
         String value = System.getProperty(systemProperty);
         if (value != null) {
            return value;
         } else {
            throw new RuntimeException(systemProperty + " is not set");
         }
      }
	
public static void main(String[] args) {
		
		MachinesManager manager= new MachinesManager();	
		manager.cloneMachine(2);
	}

	
	//אמור להתבצע אחת
	public  MachinesManager() {
	
		
		String url = "http://localhost:18083";
		String user = "";
		String passwd = "";

	     
		
	virtualBoxManager = VirtualBoxManager.createInstance(null);
	virtualBoxManager.connect(url, user, passwd);
	//virtualBoxManager.connect("http://localhost:18083", "", "");

	 vbox = virtualBoxManager.getVBox();

	//רשימת המכונות הקיימות
	machines= vbox.getMachines();
	
	//ISession session = virtualBoxManager.getSessionObject();
	//IConsole console=session.getConsole();
	//IGuest guest=console.getGuest();
	//IGuestSession gS=guest.createSession("", "", "", "1");
	
	
	
	IMachine original= machines.get(0);
	//ISession session = virtualBoxManager.getSessionObject();
	//original.lockMachine(session,  LockType.Shared);
//	IConsole console = session.getConsole();
//	IGuest guest = console.getGuest();

	//IGuestSession guestSession = guest.createSession("bob", "password", "", "");
	///guestSession.processCreate("/usr/bin", null, null, null, 0L);
	
	
	ISession session = virtualBoxManager.getSessionObject();
	session.unlockMachine();
	  ISession session1 = virtualBoxManager.getSessionObject();
	  original.lockMachine(session, LockType.Shared);
      
      IConsole console = session.getConsole();
      IGuest guest = console.getGuest();
      Long time = 50L;
      IGuestProcess gp = null;
      
      System.out.println("Logging into Guest System");
      
      IGuestSession gs = guest.createSession("IEUser", "Passw0rd!", "", "");
      
      System.out.println("Hoping to be accepted...");
      
      GuestSessionWaitResult result = gs.waitFor(1L, time);

      if(result == GuestSessionWaitResult.Start )
      {
    	  gs.processCreate("C:\\eclipse luna\\eclipse luna\\eclipse.exe", null, null, null, 0L);
      }
	
}
	

	//יצירת מכונה מועתקת
	public  void cloneMachine(int loop) {
			
	IMachine original= machines.get(0);//קבלת המכונה המקורית
	for (int i = 0; i < loop; i++) 
	{
		//יצירת המכונה החדשה
	    String os= original.getOSTypeId();
	    String newName=getNameM();
	    IMachine myClone =  vbox.createMachine(null,newName,null, os, null);
		
	  //הגדרות להעתקה של מכונה החדשה
	  	CloneMode myState=CloneMode.AllStates;
	  	List<CloneOptions> Options=new ArrayList<>();

	  	IProgress clone_progress = original.cloneTo(myClone, CloneMode.MachineState, Options); //הפעלת תהליך תיעתוק
	 	 progressBar(clone_progress); 
	 	
	 	 //שמירת הנתונים	 
	   	myClone.saveSettings();
	 	//הוספה למנהל המכונות
	 	vbox.registerMachine(myClone);
	 	
	 	 //טעינת המכונה (הפעלה)
		ISession session = virtualBoxManager.getSessionObject();
		IProgress launch_progress = myClone.launchVMProcess(session, "gui", "");
		progressBar(launch_progress);
	 	//הכנסת הIP לרשימות
	 	String ip= myClone.getGuestPropertyValue("/VirtualBox/GuestInfo/Net/0/V4/IP");
	 	VMFree.put(ip, true);
	 	VMachines.put(ip, myClone);
	 	
	   

		}	
	}

	//שם למכונה חדשה
	static String getNameM() {
		
		String name= "my"+nameVM;
		nameVM++;
		return name;
	}
	
	//פונקציית המתנה עד שיושלם התהליך ומדפיס את האחוזים
  	static boolean progressBar(IProgress p)
    {
        while (!p.getCompleted())
        {
           System.out.println(p.getPercent() + " % ");
        }
        return true;
    }

	
	
	//הסרת מכונה נגועה
  	public void DeleteMachine(String ip) {

  		IMachine myDelete = VMachines.get(ip);
  		
  		ISession iSession = virtualBoxManager.getSessionObject();   
        myDelete.lockMachine(iSession, LockType.Shared);
        IConsole iConsole = iSession.getConsole();
        IProgress powerDown_progress= iConsole.powerDown(); 	
        progressBar(powerDown_progress); 
        
  		CleanupMode cleanMode =CleanupMode.DetachAllReturnHardDisksOnly;
	 	List<IMedium> mediu= myDelete.unregister(cleanMode);
	 	IProgress delete_progress = myDelete.deleteConfig(mediu);
	 	 progressBar(delete_progress); 
	}
  	
  //מחזירה IP של מכונה פנויה
  	public  String getFreeVirtualMachine() 
	{
		for (Map.Entry<String, Boolean> thisVM : VMFree.entrySet())
		{
			//חסימה
			synchronized(lock){
				if(thisVM.getValue())
				{
					//קריאה לפונקציה שמעדכנת את הסטטוס של המכונה לתפוס	
					SetVMState(thisVM.getKey(), false);
					return thisVM.getKey();
				}
			}
		}
		return null;
	}
	
	//מעדכנת את הסטטוס של המכונה פנויה או לא 
	public  void SetVMState(String ip, boolean state)//מכניס את המכונות ומעדכן את הסטוס כל פעם
	{
		VMFree.put(ip, state);
	}

}