
import java.io.*;
import java.net.*;


public class HttpsCTransfer extends Thread {

	Socket inSocket;
	Socket outSocket;
	InputStream in;
	OutputStream out;
	
	public HttpsCTransfer(Socket in, Socket out) {
		super();
		this.inSocket = in;
		this.outSocket = out;
	}
	
	private boolean sendToVM(String req)
	{
		try {
			Socket VMSock=new Socket("127.0.0.1",8086);
			DataOutputStream VMOut=new DataOutputStream(VMSock.getOutputStream());
			VMOut.writeUTF(req);
			DataInputStream VMIn=new DataInputStream(VMSock.getInputStream());
			int checkFlag=VMIn.readInt();
			if (checkFlag==1)
				//	Ransom page
				return false;
			else if (checkFlag==2)
				// Page back to safe shore
				return false;
			
				
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally{
			return true;
		}
	}
	
	public void run()
	{
		
		try {
			in=inSocket.getInputStream();
			out=outSocket.getOutputStream();
			// Read byte by byte from client and send directly to server
		//	byte[] buffer = new byte[4096];
		//	int read;
			String requestString="";
			byte[] buffer = new byte[2048];
			int read=0;
			
				while (inSocket.isConnected() && outSocket.isConnected())
				 do
		            {
					    read = in.read(buffer, 0, buffer.length);
					    if (read>=0)
					    {
					    	String s=new String (buffer,0,buffer.length);
					    	requestString += s;
					    }

		           } while (read == buffer.length);
		           // }while (read!=0 && read!=-1 || read == buffer.length);
				//requestString = proxyToClientIn.readUTF();
			
	if (!(requestString=="")) 
		if (!(requestString.equals(".*google.*")))
		if( sendToVM(requestString))
		{
			out.write(requestString.getBytes());
			out.flush();
		}
	}
			
		/*	while (inSocket.isConnected() && outSocket.isConnected())
			{
			do {
				read = in.read(buffer,0,buffer.length);
				if (read >= 0) {
				//	out.write(buffer, 0, read);
				//	if (in.available() < 1) {
					//	out.flush();
					//}
				}
			} while (read != 0);
			}
		}*/
		catch (SocketTimeoutException ste) {
			// TODO: handle exception
		}
		catch (IOException e) {
			System.out.println("Proxy to client HTTPS read timed out");
			e.printStackTrace();
		}
		
		finally
		{
			if( inSocket.isConnected())
				try {
					inSocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (outSocket.isConnected())
				try {
					outSocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}

