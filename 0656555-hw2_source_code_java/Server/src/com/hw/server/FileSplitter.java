package com.hw.server;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileSplitter {
    public static void splitFile(File f) throws IOException {
        int partCounter = 1;//I like to name parts from 001, 002, 003, ...
                            //you can change it to 0 if you want 000, 001, ...
        String videoPartPATH = "server_video_part/";
        int sizeOfFiles = 1024 * 1024;// 1MB
        byte[] buffer = new byte[sizeOfFiles];

        String fileName = f.getName();

        //try-with-resources to ensure closing stream
        try (FileInputStream fis = new FileInputStream(f);
             BufferedInputStream bis = new BufferedInputStream(fis)) {

            int bytesAmount = 0;
            while ((bytesAmount = bis.read(buffer)) > 0) {
                //write each chunk of data into separate file with different number in name
                String filePartName = String.format("%s.%03d", fileName, partCounter++);
                //File newFile = new File(f.getParent(), filePartName);
                File newFile = new File(videoPartPATH, filePartName);
                try (FileOutputStream out = new FileOutputStream(newFile)) {
                    out.write(buffer, 0, bytesAmount);
                }
            }
        }
        //System.out.println("finish split file");
    }
    
    //READ list file
    public static List<File> listOfFilesToMerge(File oneOfFiles) {
        String tmpName = oneOfFiles.getName();//{name}.{number}
        String destFileName = tmpName.substring(0, tmpName.lastIndexOf('.'));//remove .{number}
        //System.out.println(tmpName+" | "+destFileName);
        File[] files = oneOfFiles.getParentFile().listFiles(
                (File dir, String name) -> name.matches(destFileName + "[.]\\d+"));
        Arrays.sort(files);//ensuring order 001, 002, ..., 010, ...
        return Arrays.asList(files);
    }
    
    //"split/5mb.mp4.001"
    public static List<File> listOfFilesToMerge(String oneOfFiles) {
        return listOfFilesToMerge(new File(oneOfFiles));
    }
    
    public static List<String> pathList(String filename)
    {
    	List<String> listPath = new ArrayList<>();
    	for(File temp : listOfFilesToMerge(filename))
    	{
    		 System.out.println(temp.getPath());
    		 listPath.add(temp.getPath());
    	}
    	return listPath;
    }
    //-----------

    public static void main(String[] args) throws IOException {
    	//String filePath = args[0];
        //splitFile(new File("5mb.mp4"));
    	splitFile(new File(args[0]));
    	//pathList("split/5mb.mp4.001");
    }
    
}
