package com.peanutbutte7;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileCreator {
    static void createFile(){
        try {
            File myObj = new File("output.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
                PrintWriter pw = new PrintWriter("output.txt");
                pw.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    static void writeLine(String solution){
        try {
            FileWriter fw = new FileWriter("output.txt", true);
            fw.write(solution);
            fw.write(System.getProperty( "line.separator" ));
            fw.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
