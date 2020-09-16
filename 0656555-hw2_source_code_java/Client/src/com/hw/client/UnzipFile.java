package com.hw.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnzipFile {
    public static void main(String[] args) throws IOException {
        String fileZip = args[0]; 
        String rtpPATH = "client_rtp_packet"; // unzip result
        System.out.println(fileZip);
        File destDir = new File(rtpPATH);
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            //File newFile = newFile(destDir, zipEntry);
        	File newFile = newFile(destDir, zipEntry);
            FileOutputStream fos = new FileOutputStream(newFile);
            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            zipEntry = zis.getNextEntry();
        }
        //System.out.println("end zip");
        zis.closeEntry();
        zis.close();
    }
     
    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());
         
        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();
         
        if (!destFilePath.startsWith(destDirPath + File.separator)) {
        	System.out.println("sumting wrroong");
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
            
        }
        System.out.println(destFile);
         
        return destFile;
    }
}