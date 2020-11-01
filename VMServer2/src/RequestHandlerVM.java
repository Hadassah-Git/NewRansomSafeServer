import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


//Communication between the virtual machine server and the machines
public class RequestHandlerVM extends Thread {
	
	ServerSocket serverSocket;
	Socket sockProxy,socetVM;
	String vmHost;
	MachinesManager MachinesManager;
	DistributionVMManager distributionVMManager;
	Socket sockVM;
	
	public RequestHandlerVM(String vmIp,Socket sockProxy) {
		
		super();
		this.vmHost = vmIp;
		this.sockProxy=sockProxy;
		MachinesManager=new MachinesManager();
		distributionVMManager=new DistributionVMManager();

	}

	public void run() {
		
		// Data from the server of the virtual machines to the proxy server 
		DataInputStream proxyIn;
		
		try {
			proxyIn = new DataInputStream(sockProxy.getInputStream());
			String msg=proxyIn.readUTF();
			System.out.println("msg"+msg);
			
			//Get IP address of fre machine
		//	vmHost=distributionVMManager.getFreeVirtualMachine();
			
			//Data from the current server to the virtual machine 
			sockVM=new Socket("192.168.50.3",8087);
			//sockVM=new Socket("127.0.0.1",8087);
			DataOutputStream vmOut=new DataOutputStream(sockVM.getOutputStream());
			
			vmOut.writeUTF(msg);
		//	vmOut.writeUTF("https://dl-web.dropbox.com");
			//Data from the virtual machine to the current server
			DataInputStream vmIn= new DataInputStream(sockVM.getInputStream());
			byte[] bufVM = new byte[1];
		
			int check = vmIn.read(bufVM);
			System.out.println("check:"+check);
			DataOutputStream proxyOut=new DataOutputStream(sockProxy.getOutputStream());

			proxyOut.write(check);
					
			if(vmIn.available()!=-1)
			{
				// Sending 2 error
				proxyOut.writeInt(2);
				
			}
			
			if(check!= 0)
			{
				MachinesManager.DeleteMachine(vmHost);
			}
			
		//	
		  boolean aunser=vmIn.readBoolean();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	finally
	{
		try {
			sockProxy.close();
			sockVM.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	}
}
