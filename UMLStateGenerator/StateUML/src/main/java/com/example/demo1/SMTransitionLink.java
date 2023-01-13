package com.example.demo1;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class SMTransitionLink extends SMItem{

    /**
     * link line info before create
     */
    String name;
    double startX, startY, endX, endY;
    /**
     * link line info after create
      */
    double m1X1, m1Y1, m1X2, m1Y2;
    double m2X1, m2Y1, m2X2, m2Y2;

    Color color;
    /**
     * nodes connect to the link
     */
    SMStateNode node1, node2;
    boolean connected;

    /** transition link info
     *
     */
    double transHeight, transWidth;
    double tX1, tY1, tX2, tY2;
    String event, context, sideEffects;
    boolean self;
    /**
     * info for draw circle(no implementation)
     */
    double centerx, centery, radius;


    SMTransitionLink(double startX, double startY){
        this.startX = startX;
        this.startY = startY;
        this.endX = startX;
        this.endY = startY;

        transHeight = 100;
        transWidth = 100;
        event = "No Event";
        context = "No Context";
        sideEffects = "No Side Effect";
        name = "Default";

        self = false;
        centerx = 0;
        centery = 0;
        radius = 0;
    }

    // move link line before create
    public void move(double cx, double cy){
        endX = cx;
        endY = cy;
    }

    // move link trans rec
    public void moveTrans(double cx, double cy){
        tX1 += cx;
        tY1 += cy;
        tX2 += cx;
        tY2 += cy;

    }

    // update link line1 after
    public void updateLink1(double nx1, double ny1, double nx2, double ny2){
        this.m1X1 = nx1;
        this.m1Y1 = ny1;
        this.m1X2 = nx2;
        this.m1Y2 = ny2;
    }

    // update link line2 after
    public void updateLink2(double nx1, double ny1, double nx2, double ny2){
        this.m2X1 = nx1;
        this.m2Y1 = ny1;
        this.m2X2 = nx2;
        this.m2Y2 = ny2;
    }


    // clear line before create link
    public void reset(){
        this.startX = 0;
        this.startY = 0;
        this.endX = 0;
        this.endY = 0;
    }

    // save nodes connected
    public void setNode(SMStateNode node1, SMStateNode node2) {
        this.node1 = node1;
        this.node2 = node2;
    }

    public SMStateNode getNode1() {
        return node1;
    }

    public SMStateNode getNode2() {
        return node2;
    }

    // get connected node as a list
    public ArrayList<SMStateNode> getNodes(){
        ArrayList<SMStateNode> temp = new ArrayList<>();
        temp.add(this.node1);
        temp.add(this.node2);
        return temp;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public boolean contains(double dx, double dy) {
        return dx >= tX1 && dx <= tX1 + transWidth && dy >= tY1 && dy <= tY1 + transHeight;
    }
}
