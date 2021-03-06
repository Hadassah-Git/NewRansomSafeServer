package check;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.*; 
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date; 
import java.util.List;

import javax.imageio.ImageIO;

public class Main 
{
	public static void main(String[] args) throws IOException, ParseException, URISyntaxException 
	{	
		int result=0;
	    ServerSocket socFromServ=new ServerSocket(8087);
	    Socket socket=socFromServ.accept();
	    DataOutputStream vmOutStream=new DataOutputStream(socket.getOutputStream());
	    DataInputStream servToVMIn = new DataInputStream(socket.getInputStream());
	    URI uri;
	    String requestString="";
	    File fileToClinet=null;
		
		//Send to first scan
		ScaningFiles.ScaningFirst();
		
		
		//The requests
	    
	    byte[] buffer = new byte[2048];
		int read;
		try{
			 do
	            {
	                read = servToVMIn.read(buffer, 0, buffer.length);
	                String s=new String (buffer,0,buffer.length);
	                requestString += s;

	            } while (read == buffer.length);
			//requestString = proxyToClientIn.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error reading request from client");
			return;
		}
			
		if (read>=0)
		{
		// Parse out URL
		String reqSplit[] = requestString.split(" ",3);
		String method=reqSplit[0];
		String remoteUri=reqSplit[1];
		
		//send request to server
			if (method.equals("CONNECT")) {
			
				uri=uri = new URI("https://" + remoteUri);
				Socket sendSReq=new Socket(uri.getHost(),443);	
			}
		
			else {
			
				uri = new URI(remoteUri);
				Socket sendNReq=new Socket(uri.getHost(),80);
			}
		}
		
		
	/*////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	  // save in download path	
		try{
			// If file is an image write data to client using buffered image.
			String fileExtension = fileToClinet.getName().substring(fileToClinet.getName().lastIndexOf('.'));
			
			// Response that will be sent to the server
			String response;
			if((fileExtension.contains(".png")) || fileExtension.contains(".jpg") ||
					fileExtension.contains(".jpeg") || fileExtension.contains(".gif")){
				// Read in image from storage
				BufferedImage image = ImageIO.read(fileToClinet);
				
				if(image == null ){
					System.out.println("Image " + fileToClinet.getName() + " was null");
					response = "HTTP/1.0 404 NOT FOUND \n" +
							"Proxy-agent: ProxyServer/1.0\n" +
							"\r\n";
					vmOutStream.write(response.getBytes());
					vmOutStream.flush();
				} else {
					response = "HTTP/1.0 200 OK\n" +
							"Proxy-agent: ProxyServer/1.0\n" +
							"\r\n";
					vmOutStream.write(response.getBytes());
					vmOutStream.flush();
					//����� ������ �������
					ImageIO.write(image, fileExtension.substring(1), socket.getOutputStream());
				}
			} 
			
			// Standard text based file requested
			else {
				BufferedReader fileBufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileToClinet)));

				response = "HTTP/1.0 200 OK\n" +
						"Proxy-agent: ProxyServer/1.0\n" +
						"\r\n";
				vmOutStream.write(response.getBytes());
				vmOutStream.flush();

				String line;
				while((line = fileBufferedReader.readLine()) != null){
					vmOutStream.write(line.getBytes());
				}
				vmOutStream.flush();
				// Close resources
				if(fileBufferedReader != null){
					fileBufferedReader.close();
				}	
			}
			// Close Down Resources
			if(vmOutStream != null){
				vmOutStream.close();
			}

		} 
		catch (IOException e) {
			System.out.println("Error Sending file to client");
			e.printStackTrace();
		}
			
	  
		String downloadFilePath=fileToClinet.getName();
		String fileExtension = fileToClinet.getName().substring(downloadFilePath.lastIndexOf('.'));
		FileOutputStream fileOutputStream = new FileOutputStream(fileToClinet.getName().substring(downloadFilePath.lastIndexOf('/'))+fileExtension);//�� ����� + �����
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		objectOutputStream.writeObject("��������");//the path to download 
		objectOutputStream.close();
		fileOutputStream.close();
		System.out.println("site written to VM");
		
		// run as process
		ProcessBuilder builder=new ProcessBuilder(downloadFilePath);
		Process process=builder.start();
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////// */
		
		//Run the downloaded files
		Desktop desktop = Desktop.getDesktop();
		String downloadFilePath="\\������";
		File file = new File(downloadFilePath);
		File[] files = file.listFiles();
			
		//Go through all the files that download with the request and check if they are not infected
		for (File f : files) {
			
			//Run the file
			desktop.open(f);
			
			//Re-scan after sending the request and running the file
			SecondScann.ScannAgain();

			
			//Comparison test
			result=ComparisonAccessTime.Comp();
			System.out.println("result:   "+result);
			
		
			//Returning an answer to the server
			//1- The site is infested
			if (result==1) {
				
				vmOutStream.writeInt(1);
				socFromServ.close();
			}
			
			//Delete the checked file
			f.delete();
		}
		
		//Returning an answer to the server
		//0- The site is safe
		vmOutStream.writeInt(0);
		socFromServ.close();
	
	}
}
