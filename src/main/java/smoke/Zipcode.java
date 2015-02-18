package smoke;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zipcode {

	public static void main(String[] args) {
		
		
		byte[] buffer = new byte[1024];
		 
    	try{
 
    		FileOutputStream fos = new FileOutputStream("E:\\MyFile.zip");
    		ZipOutputStream zos = new ZipOutputStream(fos);
    		ZipEntry ze= new ZipEntry("girl1.jpg");
    		zos.putNextEntry(ze);
    		FileInputStream in = new FileInputStream("E:\\Projects\\Selenium\\SeleniumIGAF\\Reports\\style\\girl.jpg");
 
    		int len;
    		while ((len = in.read(buffer)) > 0) {
    			zos.write(buffer, 0, len);
    		}
 
    		in.close();
    		zos.closeEntry();
 
    		//remember close it
    		zos.close();
 
    		System.out.println("Done");
 
    	}catch(IOException ex){
    	   ex.printStackTrace();
    	}
		
		
	}

}
