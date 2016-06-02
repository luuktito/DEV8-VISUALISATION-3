/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easycode.visualisation_3;

import java.util.ArrayList;
import processing.core.PApplet;
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
    boolean pauseStatus = true;
    float minValueX;
    float minValueY; 
    float minValueZ;
    float maxValueX; 
    float maxValueY; 
    float maxValueZ;
    float scale = 2.0f;
    float angle = 0;
    float waterHeight = -5f;
    float timePassed = 0;
    
    public static void main(String[] args) {
        PApplet.main( new String[]{"com.easycode.visualisation_3.Main"} ); 
    }
    
    @Override
    public void settings() {
        size(1000, 1000, P3D);
    }
    
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
        background(255);
        textSize(14);
        
        CSVParser newParser = new CSVParser();
        coordinatesList = newParser.parseCSV("resource/rotterdamopendata_hoogtebestandtotaal_oost.csv");

        calculateMinMax();
        calculateCoordinates(coordinatesList);
    }

    @Override
    public void draw() {
        clear();
        lights();
           
        fill(255);
        text("Hold left mouse button and drag to rotate the map", 20, 30);
        text("Scroll to zoom the map in and out", 20, 50);
        text("Press s to save a screenshot of the simulation", 390, 30);
        text("Press p to pause/start the simulation", 730, 30);
        text("Press r to reset the simulation", 730, 50);
        text("Paused: " + pauseStatus, 730, 70);
        text("Water height: " + waterHeight + "m", 730, 90);
        text("Time passed: " + timePassed + " hours", 730, 110);
        
        translate(width/2, height/2);
        rotateX(1.2f);
        rotateZ(angle);
       
        pushMatrix();
        drawCoordinates(coordinatesList);
        drawWaterHeight();
        popMatrix();
    }

    public void keyPressed() {
        if (key == 'x') {
            scale = 3.0f;
            System.out.println("KEY=X");
            redraw();
        }
        if (key == 'z') {
            scale = 1.0f;
            System.out.println("KEY=Z");
            redraw();
        }
        if (key == 's') {
            saveFrame("screenshot-######.png");
        }
        if (key == 'p') {
            if (pauseStatus == true) {
                pauseStatus = false;
            } else {
                pauseStatus = true;
            }
        }
        if (key == 'r') {
            waterHeight = minValueZ;
            timePassed = 0;
        }
    }
    public void calculateMinMax() {
        minValueX =  coordinatesList
        .stream()
        .min((o1, o2) -> Float.compare(o1.x, o2.x))
        .get()
        .x;
        
        minValueY =  coordinatesList
        .stream()
        .min((o1, o2) -> Float.compare(o1.y, o2.y))
        .get()
        .y;
        
        minValueZ =  coordinatesList
        .stream()
        .min((o1, o2) -> Float.compare(o1.z, o2.z))
        .get()
        .z;
                
        maxValueX =  coordinatesList
        .stream()
        .max((o1, o2) -> Float.compare(o1.x, o2.x))
        .get()
        .x;
        
        maxValueY =  coordinatesList
        .stream()
        .max((o1, o2) -> Float.compare(o1.y, o2.y))
        .get()
        .y;
        
        maxValueZ =  coordinatesList
        .stream()
        .max((o1, o2) -> Float.compare(o1.z, o2.z))
        .get()
        .z;

        waterHeight = minValueZ;
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
        noStroke();
        for (int i = 0; i < (coordinatesList.size() - 1); i++) {
            int c = color((100 + (coordinatesList.get(i).z * 20)), (100 + (coordinatesList.get(i).z * 20)), (100 + (coordinatesList.get(i).z * 20)));
            fill(c);
            pushMatrix();
            translate((xMap.get(i) - 500f) * scale, (1000f - yMap.get(i) - 500f) * scale, 0);
            box(2 * scale, 2 * scale, (float) ((coordinatesList.get(i).z + 10) * 2*scale));
            popMatrix();
        }
    }
    
    public void drawWaterHeight() {
        if (!pauseStatus && waterHeight <= maxValueZ) {
            waterHeight+=0.2f;
            timePassed+=0.4f;
        }
        fill(0, 0 , 255, 96);
        pushMatrix();
            translate(0, 0, 0);
            box(1000 * scale, 1000 * scale, (float) (waterHeight + 10) * 2 * scale);
        popMatrix();
    }
}
