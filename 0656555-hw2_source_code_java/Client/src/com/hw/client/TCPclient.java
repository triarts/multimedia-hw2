package com.hw.client;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.shared.RTPpaket.RTPPacket;

class TCPClient {
	public static void main(String argv[]) throws Exception {
        String FromServer;
        String ToServer;
        
        String rtpPATH = "client_rtp_packet"; // unzip result
        String videoPartPATH = "client_video_part"; // video part
        String zipFilePath = "client_zip_file"; // received packet

        Socket clientSocket = new Socket("localhost", 5000);
        //Socket clientSocket = new Socket("localhost", 5000);
        List<String> packet = new ArrayList<>();
        List<String> timestamp = new ArrayList<>();
        
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(
                System.in));
//
        PrintWriter outToServer = new PrintWriter(
                clientSocket.getOutputStream(), true);
//
//        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(
//                clientSocket.getInputStream()));
//        
        DataInputStream dIn = new DataInputStream(clientSocket.getInputStream());

        int counter = 0;
        List<String> listRecFile = new ArrayList<>();
        RTPPacket test;
        while (true) {

                
                char status = dIn.readChar();
	              if (status == 'q') {
	            	  outToServer.println("q");
	        		  try
	        		  {
	        		      Thread.sleep(1000);
	        		  }
	        		  catch(InterruptedException ex)
	        		  {
	        		      Thread.currentThread().interrupt();
	        		  }
		              clientSocket.close();
		              break;
	              } 
	              
                int length = dIn.readInt();                    // read length of incoming message
                System.out.println("length :"+length);
                if(length>0) {
                    byte[] message = new byte[length];
                    dIn.readFully(message, 0, message.length); // read the message
                    System.out.println("---RECIEVED in client:---");
                    //File outputFile = new File("result.txt");
                    //String output_file_name = "compressed.zip";
                    String output_file_name = zipFilePath+"/"+"compressed.packet."+counter;
                    listRecFile.add(output_file_name);
                    File outputFile = new File(output_file_name);

                    try ( FileOutputStream outputStream = new FileOutputStream(outputFile); ) {
                    	
                    	System.out.println("Write Packet");
                        outputStream.write(message);  //write the bytes and your done. 
                        
                        System.out.println("UnzipFile Packet");
                        UnzipFile.main(new String[] {output_file_name});
                        
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("err write");
                    }
                    //System.out.println("file received in client");
                    long time = System.nanoTime();
                    System.out.println("packet :"+counter+" timestamp: "+time);
                    //UnzipFile.main(new String[] {output_file_name});
                    packet.add(String.valueOf(counter+1));
                    timestamp.add(String.valueOf(time));
                    
                }
                
                counter++;
                   

                //ToServer = inFromUser.readLine();
                ToServer = "y";
                //ToServer = 
                if (ToServer.equals("Y") || ToServer.equals("y")) {
                	System.out.println("Client Request next packet:");
                	outToServer.println(ToServer);

                } else {
                    outToServer.println(ToServer);
                    clientSocket.close();
                    break;
                }
                
                //--------------------
                
        }
        System.out.println("---Reorder and combine Packet------");
        
//        //UNZIP
//        for(String filename : listRecFile)
//        {
//        	UnzipFile.main(new String[] {filename});
//        }
        
        
        List<String> listVideoPart = new ArrayList<>();
        
        // get file name
        File folder = new File(rtpPATH);
        File[] listOfFiles = folder.listFiles();
        //List<File> listUnzipFile = Arrays.asList(listOfFiles);
        List<String> listUnzipFilename = new ArrayList<>();

        for (int i = 0; i < listOfFiles.length; i++) {
          if (listOfFiles[i].isFile()) {
        	  listUnzipFilename.add(rtpPATH+"/"+listOfFiles[i].getName());
            System.out.println("File " + listOfFiles[i].getName());
          } else if (listOfFiles[i].isDirectory()) {
            //System.out.println("Directory " + listOfFiles[i].getName());
          }
        }
        
        // get video part from RTPpacket
        System.out.println("-extract video part from RTPpacket-");
        int c = 1;
        for(String filename : listUnzipFilename)
        {
            test = read(filename);
            System.out.println("seq :"+test.getSequence()+" | byte length :"+test.getData().length);
            
			  File outputFileData = new File(videoPartPATH+"/"+test.getDataName());
			  listVideoPart.add(outputFileData.getPath());
			  try ( FileOutputStream outputStreamData = new FileOutputStream(outputFileData); ) {
			  	outputStreamData.write(test.getData());
			  }catch (Exception e) {
			      e.printStackTrace();
			      System.out.println("err write");
			  }
			  c++;
        }
        
        System.out.println("-combine video part-");
        test = read(listUnzipFilename.get(0));
        String tmpName = test.getDataName();//{name}.{number}
        String resultName = tmpName.substring(0, tmpName.lastIndexOf('.'));
        FileJoiner.main(new String[] {listVideoPart.get(0),resultName});
        
        System.out.println("---- writing report ----");
        Path file1 = Paths.get("report_packet_client.txt");
        Files.write(file1, packet, Charset.forName("UTF-8"));
        
        Path file2 = Paths.get("report_timestamp_client.txt");
        Files.write(file2, timestamp, Charset.forName("UTF-8"));
        

        System.out.println("---- Done, pls check at root folder ----");

    }
    public static RTPPacket read(String fileName) {
    	RTPPacket stu = null;
        try (FileInputStream fis = new FileInputStream(fileName); ObjectInputStream ois = new ObjectInputStream(fis);) {
            stu = (RTPPacket) ois.readObject();
        } catch (FileNotFoundException e) {
            // Error in accessing the file
            e.printStackTrace();
        } catch (IOException e) {
            // Error in converting the Student
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // You are converting an invalid stream to Student
            e.printStackTrace();
        }
        return stu;
    }
}