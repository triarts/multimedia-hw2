package com.hw.client;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.WriteAbortedException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Base64;
import java.util.Scanner;
public class UDPClient {
	static int sizeInByte;
	   public static void main(String args[]) throws Exception
	   {
		   System.out.println("client receiver");
	       DatagramSocket serverSocket = new DatagramSocket(9876);
           //byte[] receiveData = new byte[1024];
           byte[] receiveData = new byte[142];
           byte[] sendData = new byte[1];
           //byte[] reponseData = new byte[1024];
           while(true)
              {
                 DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                 serverSocket.receive(receivePacket);
                 //RTPPacket result = toClass(receivePacket.getData());
                 //System.out.println("seq"+result.getSequence());
//                 try {
//                	 WriteToFile("gg.txt", result.getData());
//				} catch (Exception e) {
//					// TODO: handle exception
//				}
                 
//                 String sentence = new String( receivePacket.getData());
//                 System.out.println("RECEIVED: " + sentence);
                 
                 
                 InetAddress IPAddress = receivePacket.getAddress();
                 int port = receivePacket.getPort();
                 //String capitalizedSentence = "response "+sentence.toUpperCase();
                 String response = "0";
                 sendData = response.getBytes();
                 DatagramPacket sendPacket =
                 new DatagramPacket(sendData, sendData.length, IPAddress, port);
                 serverSocket.send(sendPacket);
              }

	   }
//	    public static RTPPacket toClass(byte[] stream) {
//	        RTPPacket stu = null;
//
//	        try (ByteArrayInputStream bais = new ByteArrayInputStream(stream);
//	                ObjectInputStream ois = new ObjectInputStream(bais);) {
//	            stu = (RTPPacket) ois.readObject();
//	        } catch (IOException e) {
//	            // Error in de-serialization
//	            e.printStackTrace();
//	        } catch (ClassNotFoundException e) {
//	            // You are converting an invalid stream to Student
//	            e.printStackTrace();
//	        }
//	        return stu;
//	    }
	    
	    public static void WriteToFile(String filename, byte[] input) {
        	File outputFile = new File(filename);
            try ( FileOutputStream outputStream = new FileOutputStream(outputFile); ) {
			    
                outputStream.write(input);  //write the bytes and your done. 

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("err write");
            }
		}
	}