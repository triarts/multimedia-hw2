package com.hw.server;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.omg.CORBA.FieldNameHelper;

import net.rudp.ReliableServerSocket;
import net.rudp.ReliableSocket;

public class RUDPserver implements Runnable {

ReliableServerSocket rss;
ReliableSocket rs;
String filename;

FileInputStream bin;
static String fname = "";

public RUDPserver() throws IOException {
  rss = new ReliableServerSocket(9876);
  Thread serverthread = new Thread(this);
  serverthread.start();

 }

 public void run() {
  while (true) {
      try {
		 rs =  (ReliableSocket)rss.accept();
		 //rs.setSendBufferSize(1048576);
		 System.out.println("Connection Accepted");
		 System.out.println("" + rs.getRemoteSocketAddress());

        filename = "";
        filename += fname;
        Long size=0L;
//        size+=10*1024*1024;
        
//		byte[] array = Files.readAllBytes(new File(filename).toPath());
//		RTPPacket testpacket = new RTPPacket();
//		testpacket.set(0,array);
//		save(testpacket, "data.gg");
		//System.out.println("len : "+filetobyte.length);

      RandomAccessFile r1= new RandomAccessFile(filename,"rw");
//      r1.setLength(size);
      System.out.println("file length" +r1.length());
      r1.setLength(r1.length());
      byte[] sendData = new byte[1024];

      OutputStream os = rs.getOutputStream();
      //FileOutputStream wr = new FileOutputStream(new File(filename));
      bin= new FileInputStream(filename);
      //bin= new FileInputStream("data.gg");
      int bytesReceived = 0;//byte send
      int progress = 0;
      int counter = 0;
      while ((bytesReceived = bin.read(sendData)) > 0) {
          /* Write to the file */
          os.write(sendData, 0, bytesReceived);
          progress += bytesReceived;
          counter++;
          System.out.println("byte send : "+progress+" packet send : "+counter + " timestamp : "+System.currentTimeMillis());
		  try
		  {
		      Thread.sleep(10);
		  }
		  catch(InterruptedException ex)
		  {
		      Thread.currentThread().interrupt();
		  }
      }



      } catch (IOException ex) {
          //Logger.getLogger(udpServer.class.getName()).log(Level.SEVERE, null, ex);
      }

  }
}

public static void main(String[] args) throws IOException {
	fname = args[0];
  new RUDPserver().run();
}

//public static void save(RTPPacket stu, String fileName) {
//    try (FileOutputStream fos = new FileOutputStream(fileName);
//            ObjectOutputStream oos = new ObjectOutputStream(fos);) {
//        oos.writeObject(stu);
//    } catch (FileNotFoundException e) {
//        // Error in accessing the file
//        e.printStackTrace();
//    } catch (IOException e) {
//        // Error in converting the Student
//        e.printStackTrace();
//    }
//}

}