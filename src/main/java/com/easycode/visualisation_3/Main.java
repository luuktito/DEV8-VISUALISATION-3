/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easycode.visualisation_3;

import java.util.ArrayList;
import processing.core.PApplet;
/**
 *
 * @author Luuk
 */
public class Main extends PApplet{
   
    ArrayList<CartesianCoordinate> coordinatesList;
    float minValueX;
    float minValueY; 
    float maxValueX; 
    float maxValueY; 
    
    public static void main(String[] args) {
     
        PApplet.main( new String[]{"com.easycode.visualisation_3.Main"} ); 
    }
    
    @Override
    public void settings() {
        size(1050, 1050);
    }
    
    @Override
    public void setup(){
        noLoop();
        background(255,255,255);
        CSVParser newParser = new CSVParser();
        coordinatesList = newParser.parseCSV("resource/rotterdamopendata_hoogtebestandtotaal_oost.csv");
        
//        CartesianCoordinate minX =  coordinatesList
//                .stream()
//                .min((v1, v2) -> Float.compare(v1.getxAxis(), v2.getxAxis()))
//                .get();
//
//        CartesianCoordinate minY = coordinatesList
//                .stream()
//                .max((o1, o2) -> Float.compare(o1.getY(), o2.getY()))
//                .get();
//        CartesianCoordinate maxX =  coordinatesList
//                .stream()
//                .min((o1, o2) -> Float.compare(o1.getX(), o2.getX()))
//                .get();
//        CartesianCoordinate maxY = coordinatesList
//                .stream()
//                .max((o1, o2) -> Float.compare(o1.getY(), o2.getY()))
//                .get();
//        
        minValueX = calculateMin("X").getxAxis();
        minValueY = calculateMin("Y").getyAxis();
        maxValueX = calculateMax("X").getxAxis();
        maxValueY = calculateMax("Y").getyAxis();
    }

    @Override
    public void draw() {
        //fill(0);
        drawCoordinates(coordinatesList);
    }
       
    public CartesianCoordinate calculateMin(String type) {
        CartesianCoordinate minValue = null;
        if (type.equals("X")) {
            for (CartesianCoordinate CC : coordinatesList) {
                minValue = (minValue == null || CC.getxAxis() < minValue.getxAxis()) ? CC:minValue;
            }
        }
        else {
            for (CartesianCoordinate CC : coordinatesList) {
                minValue = (minValue == null || CC.getyAxis() < minValue.getyAxis()) ? CC:minValue;
            }            
        }
        return minValue;
    }
    
    public CartesianCoordinate calculateMax(String type) {
        CartesianCoordinate maxValue = null;
        if (type.equals("X")) {
            for (CartesianCoordinate CC : coordinatesList) {
                maxValue = (maxValue == null || CC.getxAxis() > maxValue.getxAxis()) ? CC:maxValue;
            }
        }
        else {
            for (CartesianCoordinate CC : coordinatesList) {
                maxValue = (maxValue == null || CC.getyAxis() > maxValue.getyAxis()) ? CC:maxValue;
            }            
        }
        return maxValue;
    }
    
    public void drawCoordinates(ArrayList<CartesianCoordinate> coordinatesList) {
        System.out.println("STARTING DRAWING");
        float xAs = 0.0f;
        float yAs = 0.0f;
        strokeWeight((float) 0.0);
//        for (int i = 0; i < 10; i++) {
//            xAs = map(coordinatesList.get(i).getxAxis(), 0, 500000f, 0, 1000);
//            yAs = map(coordinatesList.get(i).getyAxis(), 0, 500000f, 0, 1000);
//            ellipse(xAs + 25, yAs + 25, 1, 1);
//        }
        for (CartesianCoordinate CC : coordinatesList) {
            int c = color(255, (130 + (CC.getzAxis() * 20)), 0);
            //int c = color(255, 0, 255);
            fill(c);
            //System.out.println(CC.getxAxis() + ", " + CC.getyAxis() + ", " + CC.getzAxis());
            xAs = map(CC.getxAxis(), minValueX, maxValueX, 0, 1000);
            yAs = map(CC.getyAxis(), minValueY, maxValueY, 0, 1000);
            //ellipse(xAs + 25, yAs + 25, 1, 1);
            rect(xAs + 25, yAs + 25, 1, 1);
        }
        System.out.println("DONE DRAWING");
    }
}
