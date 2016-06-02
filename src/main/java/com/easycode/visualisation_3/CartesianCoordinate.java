/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easycode.visualisation_3;

/**
 *
 * @author Luuk
 */
public class CartesianCoordinate {
    private float xAxis;
    private float yAxis;
    private float zAxis;

    @Override
    public String toString() {
        return "CartesianCoordinate{" + "xAxis=" + xAxis + ", yAxis=" + yAxis + ", zAxis=" + zAxis + '}';
    }

    public float getxAxis() {
        return xAxis;
    }

    public void setxAxis(float xAxis) {
        this.xAxis = xAxis;
    }

    public float getyAxis() {
        return yAxis;
    }

    public void setyAxis(float yAxis) {
        this.yAxis = yAxis;
    }

    public float getzAxis() {
        return zAxis;
    }

    public void setzAxis(float zAxis) {
        this.zAxis = zAxis;
    }

    public CartesianCoordinate(float xAxis, float yAxis, float zAxis) {
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.zAxis = zAxis;
    }
}
