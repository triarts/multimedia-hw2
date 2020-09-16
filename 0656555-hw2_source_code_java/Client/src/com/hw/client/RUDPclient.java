package com.hw.client;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import net.rudp.ReliableSocket;

public class RUDPclient 
{
	static String fname;
	static int sizeInByte;
	ReliableSocket server;
	   public void run() throws IOException 
	   {
		    server = new ReliableSocket();
		    //server.setReceiveBufferSize(1048576);
		    server.connect(new InetSocketAddress("127.0.0.1", 9876));

		    

		    byte[] buffer = new byte[1024];
		    
		    int count,progress=0;
		    InputStream in = server.getInputStream();
		    
		    byte[] combined = new byte[buffer.length];
		    byte[] one = new byte[buffer.length];
		    
		    int counter_flag = 0;
		    while((count=in.read(buffer)) > 0)
		    {
		    	int temp_length = count;
		    	//buffer.length
		    	if(counter_flag==0)
		    	{
//		    		combined = new byte[temp_length];
//		    		combined = buffer;
		    		
		    		byte[] tempb = new byte[temp_length];
		    		
			    	System.arraycopy(buffer,0,tempb,0         ,temp_length);
			    	
			    	combined = null;
			    	combined = tempb;
		    	}
		    	else 
		    	{
		    		//combined = null;
		    		byte[] tempb = new byte[combined.length + temp_length];
		    		
			    	System.arraycopy(combined,0,tempb,0         ,combined.length);
			    	System.arraycopy(buffer,0,tempb,combined.length,temp_length);
			    	
			    	combined = null;
			    	combined = tempb;

//		    		for (int i = 0; i < combined.length; ++i)
//		    		{
//		    		    combined[i] = i < combined.length ? combined[i] : buffer[i - combined.length];
//		    		}
//		    		combined = new byte[one.length+buffer.length];
//			    	System.arraycopy(one,0,combined,0         ,one.length);
//			    	System.arraycopy(buffer,0,combined,one.length,buffer.length);
		    	}

			    progress+=count;
			    counter_flag++;
			    //System.out.println("progess : "+progress);
			    System.out.println("combined : "+combined.length + " Packet Receive : "+counter_flag + " timestamp : "+System.currentTimeMillis());
	            if(combined.length > sizeInByte)
	            {
	            	File outputFile = new File(fname);
	                try ( FileOutputStream outputStream = new FileOutputStream(outputFile); ) {
	    			    
	                    outputStream.write(combined);  //write the bytes and your done. 
	                    
	        		    //System.out.println("finish");
	        		    
	    
	                } catch (Exception e) {
	                    e.printStackTrace();
	                    System.out.println("err write");
	                }
	            }
	            
	  		  try
			  {
			      Thread.sleep(10);
			  }
			  catch(InterruptedException ex)
			  {
			      Thread.currentThread().interrupt();
			  }
	            
			   
		    }
		    
//            File outputFile = new File("result.mp4");
//
//            try ( FileOutputStream outputStream = new FileOutputStream(outputFile); ) {
//
//                outputStream.write(combined);  //write the bytes and your done. 
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                System.out.println("err write");
//            }
		    
		    server.close();    
	    }
	   
	    public static void main(String[] args) throws IOException
	    {
	    	fname = args[0];
	    	sizeInByte = Integer.valueOf(args[1]);

	    	try {
	    		new RUDPclient().run();
			} catch (Exception e) {
				// TODO: handle exception
			}
	    	
	    }


}

