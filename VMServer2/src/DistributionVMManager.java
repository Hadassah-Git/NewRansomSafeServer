
import java.awt.font.TransformAttribute;
import java.util.*;


//Virtual machine distribution management
public class DistributionVMManager {

	GlobalMaps maps=GlobalMaps.GetMaps();
	public Object lock = new Object();
	 //Return IP of free machine
  	public String getFreeVirtualMachine() 
	{
		for (Map.Entry<String, Boolean> thisVM : maps.VMFree.entrySet())
		{
			//blocked
			synchronized(lock){
				if(thisVM.getValue())
				{
					//Updates the status of the machine	
					SetVMState(thisVM.getKey(), false);
					return thisVM.getKey();
				}
			}
		}
		return null;
	}
	
	//Updates the status of the machine if is free or not 
	public  void SetVMState(String ip, boolean state)
	{
		maps.VMFree.put(ip, state);
	}
	
}
