package com.hw.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int flag = 2;
//		String a = "0";
//		byte[] test = a.getBytes();
//		System.out.println("l "+test.length);
		
		if(flag == 0)
		{
			try {
			RUDPserver.main(new String[] {"1mb.txt"});
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(flag == 2)
		{
			//NOTE : RUN THIS ONE!!!
//			ZipFile zipFile = new ZipFile();
//			try {
//				zipFile.main(new String[] {"test.mp4","compressed.zip"});
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
	        String rtpPATH = "server_rtp_packet";
	        String videoPartPATH = "server_video_part";
	        String zipFilePath = "server_zip_file";
	        
			for (File file : new File(rtpPATH).listFiles())
			{
			    if (!file.isDirectory()) 
			        file.delete();
			}
			for (File file : new File(videoPartPATH).listFiles())
			{
			    if (!file.isDirectory()) 
			        file.delete();
			}
			for (File file : new File(zipFilePath).listFiles())
			{
			    if (!file.isDirectory()) 
			        file.delete();
			}
			
			new Thread(new Runnable() { 
	          public void run() 
	          { 
	              // perform any operation 
	              System.out.println("Performing TCP server"); 
	
	      		try {
	      			//TCPServer.main(new String[] {zipFile.getOutput_zip_name()});
	      			TCPServer.main(new String[] {"akiyo_cif.y4m"});
	      			//TCPServer.main(new String[] {"5mb.mp4"});
	      		} catch (Exception e) {
	      			// TODO: handle exception
	      		}
	          } 
	      }).start(); 
		}
		else if(flag == 1)
		{
			try {
				Count.countChar("1mb.txt",'x');
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(flag == 3)
		{
			try {
				UDPServer.main(new String[] {"3000"});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			
		}
		

		

		
//		new Thread(new Runnable() { 
//            public void run() 
//            { 
//  
//                // perform any operation 
//                System.out.println("Performing UDP SERVER"); 
//  
//        		try {
//        			UDPServer.main(new String[] {"7077"});
//        		} catch (Exception e) {
//        			// TODO: handle exception
//        		}
//            } 
//        }).start(); 
		
		

	}

}
