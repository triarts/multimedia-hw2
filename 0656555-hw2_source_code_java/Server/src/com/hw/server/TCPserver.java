package com.hw.server;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.management.openmbean.ArrayType;
import javax.xml.transform.Templates;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import com.shared.RTPpaket.RTPPacket;

class TCPServer {
    public static void main(String argv[]) throws Exception {
        String fromclient;
        String toclient;
        
        String rtpPATH = "server_rtp_packet/";
        String videoPartPATH = "server_video_part/";
        String zipFilePath = "server_zip_file/";
        List<String> packet = new ArrayList<>();
        List<String> timestamp = new ArrayList<>();
        
        String filename_source = argv[0];

        ServerSocket Server = new ServerSocket(5000);

        System.out.println("TCPServer Waiting for client on port 5000");
        
        FileInputStream is = null;
        OutputStream output;
        byte [] byteArr;
        while (true) {
            Socket connected = Server.accept();
            System.out.println(" THE CLIENT" + " " + connected.getInetAddress()
                    + ":" + connected.getPort() + " IS CONNECTED ");

            BufferedReader inFromUser = new BufferedReader(
                    new InputStreamReader(System.in));

            BufferedReader inFromClient = new BufferedReader(
                    new InputStreamReader(connected.getInputStream()));

            PrintWriter outToClient = new PrintWriter(
                    connected.getOutputStream(), true);
            
            DataOutputStream dOut = new DataOutputStream(connected.getOutputStream());
            

            
            //String filename = "gg.txt";
            // video file name
            String filename = filename_source;
            
            //split file video
            FileSplitter.main(new String[] {filename});
            //RTPpacket
            
            
            //RTP packet name
            List<File> Filelist = FileSplitter.listOfFilesToMerge(videoPartPATH+filename+".001");
            List<String> rtpPacketListName = new ArrayList<>();
            System.out.println("fileListSize :"+Filelist.size());
//            int seq = 0;
//            for(File singleFile : Filelist)
//            {
//                String rtpPacketname = singleFile.getName();
//        		//byte[] array = Files.readAllBytes(new File(filename).toPath());
//                byte[] array = Files.readAllBytes(singleFile.toPath());
//        		System.out.println("File "+rtpPacketname);
//        		RTPPacket testpacket = new RTPPacket();
//        		testpacket.set(seq,array,rtpPacketname,Filelist.size());
//        		String rtpFolderPath = "rtppacket/";
//        		String outputname = rtpFolderPath+"rtp_"+rtpPacketname;
//        		save(testpacket, outputname);
//        		//rtpPacketListName.add("rtp_"+rtpPacketname);
//        		rtpPacketListName.add(outputname);
//        		seq++;
//            }
            
            //compress each packet
            List<String> RTPpacketZIPList = new ArrayList<>();
//            //ZipFile zipFile = new ZipFile();
//            int cc = 1;
//            for(String temp : rtpPacketListName)
//            {
//    			try {
//    				String realname = temp.replace("rtppacket/","");
//    				String outname = "zipfile/"+"compressed_"+realname+".zip";
//    				ZipFile.main(new String[] {temp,outname});
//    				RTPpacketZIPList.add(outname);
//    				System.out.println("zip "+outname);
//    			} catch (IOException e1) {
//    				// TODO Auto-generated catch block
//    				e1.printStackTrace();
//    			}
//            }
            
		      System.out.println("server : SEND(Type Y or y to Start send):");
		      inFromUser.readLine();
            int counter = 0;//counter untuk loop RTPpacket
            while (true) 
            {
//                System.out.println("server : SEND(Type Y or y to Start send):");
//                toclient = inFromUser.readLine();
            	toclient = "y";
            	if(counter>=Filelist.size()) 
            	{
            		toclient = "q";
            	}

                if (toclient.equals("q") || toclient.equals("Q")) {
                    //outToClient.println(toclient);
                    dOut.writeChar(toclient.toCharArray()[0]);
                    connected.close();
                    break;
                } else {
                	
                	//outToClient.println(filename);
                	
                	System.out.println("Create RTP packet");
                	// RTP
                    String rtpPacketname = Filelist.get(counter).getName();
            		//byte[] array = Files.readAllBytes(new File(filename).toPath());
                    byte[] array = Files.readAllBytes(Filelist.get(counter).toPath());
            		System.out.println("File "+rtpPacketname);
            		RTPPacket testpacket = new RTPPacket();
            		testpacket.set(counter,array,rtpPacketname,Filelist.size());
            		String rtpFolderPath = rtpPATH;
            		String outputname = rtpFolderPath+"rtp_"+rtpPacketname;
            		save(testpacket, outputname);
            		//rtpPacketListName.add("rtp_"+rtpPacketname);
            		rtpPacketListName.add(outputname);
            		
            		System.out.println("Compress RTP packet");
        			try {
        				String realname = rtpPacketListName.get(counter).replace(rtpPATH,"");
        				String outname = zipFilePath+"compressed_"+realname+".zip";
        				ZipFile.main(new String[] {rtpPacketListName.get(counter),outname});
        				RTPpacketZIPList.add(outname);
        				System.out.println("zip "+outname);
        			} catch (IOException e1) {
        				// TODO Auto-generated catch block
        				e1.printStackTrace();
        			}
            		
            		
                    try
                    {
                    	System.out.println("sending to client");
                        //is = new FileInputStream("akiyo_cif.y4m");
                    	is = new FileInputStream(RTPpacketZIPList.get(counter));
                    	System.out.println("sending : "+RTPpacketZIPList.get(counter));
                        //is = new FileInputStream(filename);
                        byteArr = IOUtils.toByteArray(is);
                        dOut.writeChar(toclient.toCharArray()[0]);
                        dOut.writeInt(byteArr.length);
                        dOut.write(byteArr);
                        long time = System.nanoTime();
                        System.out.println("packet :"+counter+" timestamp: "+time);
                        packet.add(String.valueOf(counter+1));
                        timestamp.add(String.valueOf(time));
                    }
                    catch (IOException ioe)
                    {
                    	System.out.println("err byte");break;
                    }
                }

                fromclient = inFromClient.readLine();

                if (fromclient.equals("q") || fromclient.equals("Q")) {
                	System.out.println("---- last packet ----");
                    connected.close();
                    break;
                } else {
                    System.out.println("Server continue send next paket -------------->");
                }
                
                counter++;

//                System.out.println("server : SEND(Type  or q to Quit):");
//                toclient = inFromUser.readLine();
//
//                if (toclient.equals("q") || toclient.equals("Q")) {
//                    outToClient.println(toclient);
//                    connected.close();
//                    break;
//                } else {
//                    outToClient.println(toclient);
//                }
//
//                fromclient = inFromClient.readLine();
//
//                if (fromclient.equals("q") || fromclient.equals("Q")) {
//                    connected.close();
//                    break;
//                } else {
//                    System.out.println("RECIEVED in server:" + fromclient);
//                }

            }
            
            //List<String> lines = Arrays.asList("The first line", "The second line");
            
            System.out.println("---- Done sending ----");
            System.out.println("---- writing report ----");
            Path file1 = Paths.get("report_packet_server.txt");
            Files.write(file1, packet, Charset.forName("UTF-8"));
            
            Path file2 = Paths.get("report_timestamp_server.txt");
            Files.write(file2, timestamp, Charset.forName("UTF-8"));
            break;
        }
    }
    
  public static void save(RTPPacket stu, String fileName) {
  try (FileOutputStream fos = new FileOutputStream(fileName);
          ObjectOutputStream oos = new ObjectOutputStream(fos);) {
      oos.writeObject(stu);
  } catch (FileNotFoundException e) {
      // Error in accessing the file
      e.printStackTrace();
  } catch (IOException e) {
      // Error in converting the Student
      e.printStackTrace();
  }
}
}