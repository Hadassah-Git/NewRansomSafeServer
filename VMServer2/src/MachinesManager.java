
import org.virtualbox_6_0.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.plaf.ProgressBarUI;

public class MachinesManager {
	
	GlobalMaps maps=GlobalMaps.GetMaps();
	static int nameVM=0;//Variable to the new machine name
	
	public VirtualBoxManager virtualBoxManager;
	public IVirtualBox vbox;
	public List<IMachine> machines;


public static void main(String[] args) {
		
		MachinesManager manager= new MachinesManager();	
	}

	
	//Should be done once
	public  MachinesManager() {
	
	virtualBoxManager = VirtualBoxManager.createInstance(null);
	virtualBoxManager.connect("http://localhost:18083", "", "");
	
	 vbox = virtualBoxManager.getVBox();

	//רשימת המכונות הקיימות
	machines= vbox.getMachines();
	
	for (IMachine myachine : machines) {
		
		String ip= myachine.getGuestPropertyValue("/VirtualBox/GuestInfo/Net/0/V4/IP");
		maps.VMFree.put(ip, true);
		maps.VMachines.put(ip, myachine);
	}
	//Creates a number of new machines to be ready 
	//cloneMachine(2);
}
	

	//Creating a clone machine
	public  void cloneMachine(int loop) {
			
	IMachine original= machines.get(4);//Receiving the original machine
	for (int i = 0; i < loop; i++) 
	{
		//Creating the new machine
	    String os= original.getOSTypeId();
	    String newName=getNewName();
	    IMachine myClone =  vbox.createMachine(null,newName,null, os, null);
		
	  //Settings for copying the new machine
	  	CloneMode myState=CloneMode.AllStates;
	  	List<CloneOptions> Options=new ArrayList<>();

	  	IProgress clone_progress = original.cloneTo(myClone, CloneMode.MachineState, Options); //הפעלת תהליך תיעתוק
	 	 progressBar(clone_progress); 
	 	
	 	 //Saving the data	 
	   	myClone.saveSettings();
	 	//Add to the machine manager
	 	vbox.registerMachine(myClone);
	 	

	 	String ip= myClone.getGuestPropertyValue("/VirtualBox/GuestInfo/Net/<nicId>/V4/IP");
	 	maps.VMFree.put(ip, true);
	 	maps.VMachines.put(ip, myClone);
	 	
	    //Loading the machine (operating)
		ISession session = virtualBoxManager.getSessionObject();
		IProgress prog = myClone.launchVMProcess(session, "gui", "");

		}	
	}
	
	//Removing an infected machine
  	public void DeleteMachine(String ip) {
  		
  		IMachine myDelete = maps.VMachines.get(ip);
  		
  		ISession iSession = virtualBoxManager.getSessionObject();   
        myDelete.lockMachine(iSession, LockType.Shared);
        IConsole iConsole = iSession.getConsole();
        IProgress powerDown_progress= iConsole.powerDown(); 	
        progressBar(powerDown_progress); 
        
  		CleanupMode cleanMode =CleanupMode.DetachAllReturnHardDisksOnly;
	 	List<IMedium> mediu= myDelete.unregister(cleanMode);
	 	IProgress delete_progress = myDelete.deleteConfig(mediu);
	 	 progressBar(delete_progress); 

	 	 
	 	 cloneMachine(1);
	}

	//New name for the machine 
	static String getNewName() {
		
		String name= "my"+nameVM;
		nameVM++;
		return name;
	}
	
	//Wait function until the process is complete and prints the percentages
  	static boolean progressBar(IProgress p)
    {
        while (!p.getCompleted())
        {
           System.out.println(p.getPercent() + " % ");
        }
        return true;
    }

	
	
	
  	
 

}