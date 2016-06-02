/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easycode.visualisation_3;

import java.util.ArrayList;
import processing.core.PApplet;
import controlP5.*;
import processing.core.PVector;
import processing.event.MouseEvent;

/**
 *
 * @author Luuk
 */
public class Main extends PApplet{
   
    ArrayList<PVector> coordinatesList;
    ArrayList<Float> xMap = new ArrayList<>();
    ArrayList<Float> yMap = new ArrayList<>();
    boolean pauseStatus;
    float minValueX;
    float minValueY; 
    float maxValueX; 
    float maxValueY; 
    float scale = 2.0f;
    float angle = 0;
//    ControlP5 cp5;
//    static CallbackListener cb;
    
    public static void main(String[] args) {
        PApplet.main( new String[]{"com.easycode.visualisation_3.Main"} ); 
    }
    
    @Override
    public void settings() {
        size(1000, 1000, P3D);
    }
    
    public void resetSimulation() {
    }
    
    public void PlayPause() {
        if (pauseStatus == true) {
            pauseStatus = false;
        } else {
            pauseStatus = true;
        }
    }
     
    public void takeScreenshot() {
    }

//    public void controlEvent(ControlEvent event) {
//        if ("Set to range to 500m".equals(event.getController().getName())) {
//            scale = 2.0f;
//            System.out.println("KEY=S");
//            redrawMap = true;
//        }
//        if ("Set to range to 1000m".equals(event.getController().getName())) {
//            scale = 1f;
//            System.out.println("KEY=A");
//            redrawMap = true;
//        }
//    }
    
    @Override
    public void mouseDragged() {
        if (mouseButton == LEFT) {
            angle =  angle + (radians(-(mouseX- pmouseX))/2);
        } 
    }
    
    @Override
    public void mouseWheel(MouseEvent event) {
        float e = event.getCount();
        scale = (float) (scale - e/5.0);
        if (scale < 1)
            scale = 1;
        else if (scale > 3)
            scale = 3;
    }
    
    
    @Override
    public void setup(){
//        cp5 = new ControlP5(this);
//        
//        cp5.addButton("Set to range to 500m")
//                .setPosition(30, 970)
//                .setWidth(200)
//                .setValue(1);
//        cp5.addButton("Set to range to 1000m")
//                .setPosition(250, 970)
//                .setWidth(200)
//                .setValue(1);
        background(255);
        
        CSVParser newParser = new CSVParser();
        coordinatesList = newParser.parseCSV("resource/rotterdamopendata_hoogtebestandtotaal_oost.csv");

        minValueX = calculateMin("X").x;
        minValueY = calculateMin("Y").y;
        maxValueX = calculateMax("X").x;
        maxValueY = calculateMax("Y").y;
        
        calculateCoordinates(coordinatesList);
    }

    @Override
    public void draw() {
        clear();
        lights();
        
        translate(width/2, height/2);
        rotateX(1.2f);
        rotateZ(angle);
       
        pushMatrix();
        drawCoordinates(coordinatesList);
        popMatrix();
    }

    public void keyPressed() {
        if (key == 's') {
            scale = 2.0f;
            System.out.println("KEY=S");
            redraw();
        }
        if (key == 'a') {
            scale = 1.0f;
            System.out.println("KEY=A");
            redraw();
        }
    }
    
    public PVector calculateMin(String type) {
        PVector minValue = null;
        if (type.equals("X")) {
            for (PVector CC : coordinatesList) {
                minValue = (minValue == null || CC.x < minValue.x) ? CC:minValue;
            }
        }
        else {
            for (PVector CC : coordinatesList) {
                minValue = (minValue == null || CC.y < minValue.y) ? CC:minValue;
            }            
        }
        return minValue;
    }
    
    public PVector calculateMax(String type) {
        PVector maxValue = null;
        if (type.equals("X")) {
            for (PVector CC : coordinatesList) {
                maxValue = (maxValue == null || CC.x > maxValue.x) ? CC:maxValue;
            }
        }
        else {
            for (PVector CC : coordinatesList) {
                maxValue = (maxValue == null || CC.y > maxValue.y) ? CC:maxValue;
            }            
        }
        return maxValue;
    }
    
    public void calculateCoordinates(ArrayList<PVector> coordinatesList) {
        float xAs = 0.0f;
        float yAs = 0.0f;
        for (PVector CC : coordinatesList) {
            xAs = (float) map(CC.x, minValueX, maxValueX, 0, (1000));
            yAs = (float) map(CC.y, minValueY, maxValueY, 0, (1000));
            xMap.add(xAs);
            yMap.add(yAs);
        }
    }
    
    public void drawCoordinates(ArrayList<PVector> coordinatesList) {
//        System.out.println("STARTING DRAWING");
        noStroke();
        for (int i = 0; i < (coordinatesList.size() - 1); i++) {
            int c = color((100 + (coordinatesList.get(i).z * 20)), (100 + (coordinatesList.get(i).z * 20)), (100 + (coordinatesList.get(i).z * 20)));
            fill(c);
            pushMatrix();
            translate((xMap.get(i) - 500f) * scale, (1000f - yMap.get(i) - 500f) * scale, 0);
            box(2 * scale, 2 * scale, (coordinatesList.get(i).z + 10) * 2*scale);
            popMatrix();
        }
//        System.out.println("DONE DRAWING");
    }
}
