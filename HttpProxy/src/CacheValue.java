

import java.io.*;


public class CacheValue {

	long lastFileModify;
	File file;
	
	public CacheValue(File file, long lastFileModify) {
		super();
		this.file = file;
		this.lastFileModify = lastFileModify;
	}

	public long getLastFileModify() {
		return lastFileModify;
	}

	public void setLastFileModify(long lastFileModify) {
		this.lastFileModify = lastFileModify;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
}
