package check;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


public class ToHash {

	public static String GetHash(File fileToHash) throws NoSuchAlgorithmException, IOException {
		
	   byte[] buffer= new byte[8192];
	    int count;
	    
	//    String fileName=fileToHash.getName();
	    MessageDigest digest;
	    
	    digest = MessageDigest.getInstance("SHA-256");
		
	    BufferedInputStream bis;
			bis = new BufferedInputStream(new FileInputStream(fileToHash));
	    while ((count = bis.read(buffer)) !=-1) {
	        digest.update(buffer, 0, count);
	    }
	    bis.close();
	    byte[] hash = digest.digest();
	    String hashValue=Base64.getEncoder().encodeToString(hash);
	    System.out .println(hashValue);
	    return hashValue;
    }
}

