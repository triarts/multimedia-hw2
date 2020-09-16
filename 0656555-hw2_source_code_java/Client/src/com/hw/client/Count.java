package com.hw.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Count {

    public static void countChar(String filename,char counted_char) throws FileNotFoundException {
        Scanner scannerFile = null;

        try {
            scannerFile = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
        }

        int starNumber = 0; // number of *'s

        while (scannerFile.hasNext()) {
            String character = scannerFile.next();
            int index =0;
            char star = counted_char;
            while(index<character.length()) {

                if(character.charAt(index)==star){
                    starNumber++;
                }
                index++;
            } 
        }
        System.out.println(starNumber);
    }
}