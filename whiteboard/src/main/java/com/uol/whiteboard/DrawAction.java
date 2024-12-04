package com.uol.whiteboard;

//DrawAction class to represent drawing data (x, y, color, etc.)
public class DrawAction {
    private double x;
    private double y;
    private double prevX;
    private double prevY;
    private String color;

    // Getters and setters
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getPrevX() {
        return prevX;
    }

    public void setPrevX(double prevX) {
        this.prevX = prevX;
    }

    public double getPrevY() {
        return prevY;
    }

    public void setPrevY(double prevY) {
        this.prevY = prevY;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}