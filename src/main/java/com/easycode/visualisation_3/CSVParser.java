/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easycode.visualisation_3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luuk
 */
public class CSVParser {
    public ArrayList<CartesianCoordinate> parseCSV(String fileLocation) {
        ArrayList<CartesianCoordinate> returnList = new ArrayList<>();
        String thisLine = null;
        float schoolLocationX = 92800f;
        float schoolLocationY = 436955f;
        try {
            System.out.println("STARTING");
            BufferedReader br = new BufferedReader(new FileReader(fileLocation));
            br.readLine();
            while((thisLine = br.readLine()) != null) {
                String[] lineSplit = thisLine.split(",");
            
                float X = Float.parseFloat(lineSplit[0]);
                float Y = Float.parseFloat(lineSplit[1]);
                float Z = Float.parseFloat(lineSplit[2]);
                
                float xABS = (float) Math.abs(schoolLocationX - X);
                float yABS = (float) Math.abs(schoolLocationY - Y);

                if (xABS < 500 && yABS < 500) {
                    CartesianCoordinate CC = new CartesianCoordinate(X, Y, Z);
                    returnList.add(CC);
                }
                //System.out.println(X + ", " + Y + ", " + Z);
            }
//            for (int i = 0; i < 10000000; i++) {
//                thisLine = br.readLine();
//                String[] lineSplit = thisLine.split(",");
//            
//                float X = Float.parseFloat(lineSplit[0]);
//                float Y = Float.parseFloat(lineSplit[1]);
//                float Z = Float.parseFloat(lineSplit[2]);
//
//                //System.out.println(X + ", " + Y + ", " + Z);
//                CartesianCoordinate CC = new CartesianCoordinate(X, Y, Z);
//                returnList.add(CC);
//            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        System.out.println("DONE");
        return returnList;
    }
}
