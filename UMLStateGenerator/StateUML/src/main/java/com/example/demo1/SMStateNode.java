package com.example.demo1;

import java.util.ArrayList;
import java.util.List;

public class SMStateNode extends SMItem{

    /**
     * node draw info
     */
    double x, y;
    double width;
    double height;

    /**
     * node name
     */
    String stateName;

    /**
     * store a list of connected links
     */
    ArrayList<SMTransitionLink> links;
    /**
     * connected:
     * if this node is connected
     */
    boolean connected;
    /**
     * the property : node
     */
    String name;


    public SMStateNode(double x, double y){
        this.x = x;
        this.y = y;
        width = 90;
        height = 50;
        stateName = "Default";
        connected = false;
        links = new ArrayList<SMTransitionLink>();
        name = "node";
    }

    // move a node
    public void move(double dx, double dy) {
        x += dx;
        y += dy;

    }

    public boolean contains(double dx, double dy) {
        return dx >= x && dx <= x + width && dy >= y && dy <= y + height;
    }

    public void addLink(SMTransitionLink link) {
        this.links.add(link);
    }

    public List<SMTransitionLink> getLinks() {
        return links;
    }

    public void resetLinks() {
         this.links = null;
    }

    public void updateStateName(String name){
        stateName = name;
    }

}
