
import java.io.*;
import java.net.*;


public class HttpsSTransfer extends Thread {

	Socket inSocket;
	Socket outSocket;
	InputStream in;
	OutputStream out;
	
	public HttpsSTransfer(Socket in, Socket out) {
		super();
		this.inSocket = in;
		this.outSocket = out;
	}
	
	public void run()
	{
		
		try {
			in=inSocket.getInputStream();
			out=outSocket.getOutputStream();
			// Read byte by byte from client and send directly to server
			byte[] buffer = new byte[4096];
			int read;
			while (inSocket.isConnected() && outSocket.isConnected())
			{
			do {
				read = in.read(buffer,0,buffer.length);
				if (read >= 0) {
					out.write(buffer, 0, read);
				//	if (in.available() < 1) {
						out.flush();
					//}
				}
			} while (read != 0);
			}
		}
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

