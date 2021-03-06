/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easycode.visualisation_3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import processing.core.PVector;

/**
 *
 * @author Luuk
 */
public class CSVParser {
    public ArrayList<PVector> parseCSV(String fileLocation) {
        ArrayList<PVector> returnList = new ArrayList<>();
        String thisLine = null;
        float statueLocationX = 92800f;
        float statueLocationY = 436955f;
        try {
            System.out.println("STARTING");
            BufferedReader br = new BufferedReader(new FileReader(fileLocation));
            br.readLine();
            while((thisLine = br.readLine()) != null) {
                String[] lineSplit = thisLine.split(",");
            
                float X = Float.parseFloat(lineSplit[0]);
                float Y = Float.parseFloat(lineSplit[1]);
                float Z = Float.parseFloat(lineSplit[2]);
                
                float xABS = (float) Math.abs(statueLocationX - X);
                float yABS = (float) Math.abs(statueLocationY - Y);

                if (xABS < 500 && yABS < 500) {
                    PVector CC = new PVector(X, Y, Z);
                    returnList.add(CC);
                }
            }
        } catch (IOException | NumberFormatException ex) {
            System.out.println(ex);
        }
        System.out.println("DONE");
        return returnList;
    }
}
