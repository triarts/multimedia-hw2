package com.hw.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.naming.spi.DirStateFactory.Result;

import org.apache.commons.io.FileUtils;



public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int flag = 2;
		
		if(flag == 0)
		{
			try {
				//5000000
				//11000000
			RUDPclient.main(new String[] {"gga.txt","1000000"});
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(flag == 1)
		{
			try {
				Count.countChar("Result.txt",'x');
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(flag == 2)
		{
			
	        String rtpPATH = "client_rtp_packet"; // unzip result
	        String videoPartPATH = "client_video_part"; // video part
	        String zipFilePath = "client_zip_file"; // received packet
	        
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
		
		          	System.out.println("Performing TCP client"); 
		
		      		try {
		      			TCPClient.main(new String[] {"a","b","c"});
		      		} catch (Exception e) {
		      			// TODO: handle exception
		      		}
		          } 
		      }).start(); 
		}
		else if(flag == 3)
		{
			try {
				UDPClient.main(new String[] {"localhost","3000"});
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
//                // perform any operation 
//                System.out.println("Performing UDP client"); 
//  
//        		try {
//        			UDPClient.main(new String[] {"192.168.0.3","7077"});
//        		} catch (Exception e) {
//        			// TODO: handle exception
//        		}
//            } 
//        }).start(); 

		
		
		

	}

}
