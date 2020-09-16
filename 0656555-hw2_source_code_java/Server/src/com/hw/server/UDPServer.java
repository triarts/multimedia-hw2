package com.hw.server;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.shared.RTPpaket.RTPPacket;
public class UDPServer {
	static FileInputStream bin;
	   public static void main(String args[]) throws Exception
	      {
		   System.out.println("server sender");
		   
		   BufferedReader inFromUser =
	      new BufferedReader(new InputStreamReader(System.in));
	      DatagramSocket clientSocket = new DatagramSocket();
	      InetAddress IPAddress = InetAddress.getByName("localhost");
	      byte[] sendData = new byte[1024];
	      byte[] receiveData = new byte[1];
	      
	      List<String> dataText = new ArrayList<>();
			try (Stream<String> stream = Files.lines(Paths.get("text.txt"))) {
			
				//convert it into a List
				dataText = stream.collect(Collectors.toList());
			
			} catch (IOException e) {
				e.printStackTrace();
			}
			byte[] array = Files.readAllBytes(new File("text.txt").toPath());
			RTPPacket testpacket = new RTPPacket();
			//testpacket.set(0);
			byte[] filetobyte = toStream(testpacket);
			System.out.println("len : "+filetobyte.length);
			
		int counter = 0;
		while (counter < dataText.size())
		while (counter < 1)
		//while ((bytesReceived = bin.read(sendData)) > 0) {
		{
		  sendData = dataText.get(counter).getBytes();
		  //DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
		  DatagramPacket sendPacket = new DatagramPacket(filetobyte, filetobyte.length, IPAddress, 9876);
		  clientSocket.send(sendPacket);
		  try
		  {
		      Thread.sleep(1000);
		  }
		  catch(InterruptedException ex)
		  {
		      Thread.currentThread().interrupt();
		  }
		  DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		  clientSocket.receive(receivePacket);
		  String clientResponse = new String(receivePacket.getData());
		  //clientResponse.toString().replaceAll("\"","");
		  System.out.println("l "+receivePacket.getData().length);
		  //modifiedSentence.equals("a")
		  if(Integer.valueOf(clientResponse) == 0)
		  {
			  System.out.println("feddback:" + clientResponse);
			  counter++;
		  }
		  System.out.println("c:" + counter);
		  
		 }

	      clientSocket.close();

		  
	      }
	   
	   public static byte[] toStream(RTPPacket stu) {
		    // Reference for stream of bytes
		    byte[] stream = null;
		    // ObjectOutputStream is used to convert a Java object into OutputStream
		    try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
		            ObjectOutputStream oos = new ObjectOutputStream(baos);) {
		        oos.writeObject(stu);
		        stream = baos.toByteArray();
		    } catch (IOException e) {
		        // Error in serialization
		        e.printStackTrace();
		    }
		    return stream;
		}
}


