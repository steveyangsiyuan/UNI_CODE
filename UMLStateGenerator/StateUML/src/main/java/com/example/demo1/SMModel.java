package com.example.demo1;

import javafx.scene.Cursor;
import javafx.scene.Scene;

import java.util.ArrayList;
import java.util.List;

public class SMModel {
    private List<AppModelListener> subscribers;
    private List<SMStateNode> nodes;
    private List<SMTransitionLink> links;

    /**
     * change cursor according to user selection
     */
    private Cursor cursor;

    String whichHit;

    /**
     * location of mini map
     */
    double miniX1, miniY1, miniX2, miniY2;

    /**
     * location of main map
     */
    double WinX, WinY;

    boolean hitMini;


    public SMModel(){
        subscribers = new ArrayList<>();
        nodes = new ArrayList<>();
        links = new ArrayList<>();
        cursor = Cursor.DEFAULT;
        whichHit = "node";
        miniX1 = 0;
        miniY1 = 0;
        miniX2 = 400;
        miniY2 = 400;
        hitMini = false;
        WinX = 0;
        WinY = 0;
    }
    public void updateStateName(SMStateNode s, String name){
        s.updateStateName(name);
        notifySubscribers();
    }
    // update cursor
    public void setCursor(Cursor c){
        this.cursor = c;
    }

    // get current cursor
    public Cursor getCursor(){
        return this.cursor;
    }

    // add node to node list
    public void addNode(double x, double y){
        nodes.add(new SMStateNode(x, y));
        notifySubscribers();
    }

    // update minimap pos
    public void updateMini(double dx, double dy){
        miniX1 += dx;
        miniY2 += dy;
        miniX2 = miniX1 + 400;
        miniY2 = miniY2 + 400;
        notifySubscribers();
    }

    // update main map pos
    public void updateWin(double dx, double dy){
        WinX = 2*dx;
        WinY = 2*dy;
        notifySubscribers();
    }

    // move a certain node
    public void moveNode(SMStateNode node, double x, double y){
        node.move(x, y);
        notifySubscribers();
    }

    // move a certain link
    public void moveLinkT(SMTransitionLink link, double x, double y){
        link.moveTrans(x, y);
        notifySubscribers();
    }

    // add link to link list
    public void addLink(double x, double y){
        links.add(new SMTransitionLink(x, y));
        notifySubscribers();
    }

    // move a certain link
    public void moveLink(double x, double y){
        links.get(links.size()-1).move(x, y);
        notifySubscribers();
    }

    // delete a certain node
    public void removeNode(SMStateNode n){
        this.nodes.remove(n);
        notifySubscribers();
    }

    // delete a certain link
    public void removeLink(SMTransitionLink l){
        this.links.remove(l);
        notifySubscribers();
    }

    public void removeNodeIn(int i){
        this.nodes.remove(i);
        notifySubscribers();
    }

    // remove link by index
    public void removeLinkIn(int i){
        this.links.remove(i);
        notifySubscribers();
    }

    // initialize position of transition rec
    public void setLinkRec(int i, double x1, double y1){
        this.links.get(i).tX1 = x1;
        this.links.get(i).tY1 = y1;
        this.links.get(i).tX2 = this.links.get(i).tX1 + this.links.get(i).transWidth;
        this.links.get(i).tY2 = this.links.get(i).tY1 + this.links.get(i).transHeight;
        notifySubscribers();
    }


    public List<SMStateNode> getNodes() {
        return nodes;
    }

    public List<SMTransitionLink> getLinks() {
        return links;
    }

    public boolean hitNode(double x, double y) {
        for (SMStateNode n : nodes) {
            if (n.contains(x,y)) return true;
        }
        return false;
    }

    public SMStateNode whichHit(double x, double y) {
        for (SMStateNode n : nodes) {
            if (n.contains(x,y)) return n;
        }
        return null;
    }

    public boolean hitLink(double x, double y) {
        for (SMTransitionLink l : links) {
            if (l.contains(x,y)) return true;
        }
        return false;
    }

    public SMTransitionLink whichHitLink(double x, double y) {
        for (SMTransitionLink l : links) {
            if (l.contains(x,y)) return l;
        }
        return null;
    }

    public boolean hitMini(double x, double y) {
        if (miniX1 <= x && miniX2 >= x & miniY1 <= y && miniY2 >= y){
            return true;
        }
        else {
            return false;
        }
    }

    public void addSubscriber(AppModelListener sub) {
        subscribers.add(sub);
    }

    private void notifySubscribers() {
        subscribers.forEach(s -> s.modelChanged());
    }

    public void call(){
        notifySubscribers();
    }
}
