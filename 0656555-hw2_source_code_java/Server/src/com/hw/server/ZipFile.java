package com.hw.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFile {
	static String output_zip_name;
    public static void main(String[] args) throws IOException {
    	String sourceFile_name = args[0];
    	output_zip_name = args[1];
        String sourceFile = sourceFile_name;
        FileOutputStream fos = new FileOutputStream(output_zip_name);
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        File fileToZip = new File(sourceFile);
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        zipOut.close();
        fis.close();
        fos.close();
    }
    
    public static String getOutput_zip_name() {
		return output_zip_name;
	}
}