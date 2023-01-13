package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class AppController {

    SMModel model;
    InteractionModel iModel;
    double prevX,prevY;
    double dX,dY;

    boolean selfCreateOut = false;

    SMStateNode start, end;

    SMStateNode Cur;

    enum State {READY,PREPARE_CREATE, DRAGGING}
    enum MiniState {READY, DRAGGING}
    enum LinkState {READY, PREPARE_CREATE, DRAGGING}
    State currentState = State.READY;
    LinkState currentLinkState = LinkState.READY;

    MiniState currentMiniState = MiniState.READY;

    public AppController(){}

    public void handleCursorClikced(ActionEvent event) {
        model.setCursor(Cursor.DEFAULT);
    }

    public void handleCrossHairClikced(ActionEvent event) {
        model.setCursor(Cursor.CROSSHAIR);
    }

    public void handleDirCrossClikced(ActionEvent event) {
        model.setCursor(Cursor.MOVE);
    }
    public void setModel(SMModel model) {
        this.model = model;
    }

    public void setIModel(InteractionModel iModel) {
        this.iModel = iModel;
    }

    /**
     * handle mouse pressed
     * three situations:
     * 1: cursor==default:
     *      - set selected node or link
     * 2: cursor==move:
     *      - ready to move mini
     * 3: cursor==crosshair
     *      - ready to create link
     * @param event
     */
    public void handlePressed(MouseEvent event) {
        if (model.getCursor().equals(Cursor.DEFAULT)) {
            switch (currentState) {
                case READY -> {
                    if (model.hitNode(event.getX(), event.getY())) {
                        SMStateNode n = model.whichHit(event.getX(), event.getY());
                        iModel.setSelectedN(n);
                        iModel.LinkUnselect();
                        prevX = event.getX();
                        prevY = event.getY();
                        currentState = State.DRAGGING;
                    }
                    else if (model.hitLink(event.getX(), event.getY())) {
                        SMTransitionLink l = model.whichHitLink(event.getX(), event.getY());
                        iModel.setSelectedL(l);
                        iModel.NodeUnselect();
                        prevX = event.getX();
                        prevY = event.getY();
                        currentState = State.DRAGGING;
                    }  else {
                        currentState = State.PREPARE_CREATE;
                    }
                }
            }
        }
        else if (model.getCursor().equals(Cursor.MOVE)) {
            switch (currentMiniState) {
                case READY -> {
                    if (model.hitMini(event.getX(), event.getY())) {
                        System.out.println("success1");
                        model.hitMini = true;
                        prevX = event.getX();
                        prevY = event.getY();
                        currentMiniState = MiniState.DRAGGING;
                    }
                }
            }
        }
        else if (model.getCursor().equals(Cursor.CROSSHAIR)) {
            switch (currentLinkState) {
                case READY -> {
                    if (model.hitNode(event.getX(), event.getY())){
                        start = model.whichHit(event.getX(), event.getY());
                        iModel.setSelectedN(start);
                        model.addLink(event.getX(), event.getY());
                        currentLinkState = LinkState.PREPARE_CREATE;
                    }
                    else {
                        model.addLink(event.getX(), event.getY());
                        currentLinkState = LinkState.DRAGGING;
                    }
                }
            }
        }

    }

    /**
     * handle mouse dragged
     * three situations:
     * 1: cursor==default:
     *      - move objects
     * 2: cursor==move:
     *      - move mini
     * 3: cursor==crosshair
     *      - move link line before created
     * @param event
     */
    public void handleDragged(MouseEvent event) {
        if (model.getCursor().equals(Cursor.DEFAULT)) {
            switch (currentState) {
                case PREPARE_CREATE -> {
                    currentState = State.READY;
                }
                case DRAGGING -> {
                    dX = event.getX() - prevX;
                    dY = event.getY() - prevY;
                    prevX = event.getX();
                    prevY = event.getY();
                    if (iModel.SelectedL!= null ){
                        model.moveLinkT(iModel.getSelectedLink(), dX, dY);
                        iModel.getSelectedLink().getNodes().forEach(n -> {
                            n.getLinks().forEach(l -> {
                                    l.updateLink1(l.getNode1().x, l.getNode1().y, l.tX1, l.tY1);
                                    l.updateLink2(l.tX2, l.tY2, l.getNode2().x, l.getNode2().y);
                            });

                        });
                    }

                    else if(iModel.SelectedN != null) {
                        model.moveNode(iModel.getSelectedNode(), dX, dY);
                        iModel.getSelectedNode().getLinks().forEach(l -> {
                            l.updateLink1(l.getNode1().x, l.getNode1().y, l.tX1, l.tY1);
                            l.updateLink2(l.tX2, l.tY2, l.getNode2().x, l.getNode2().y);
                        });
                    }
                }
            }
        }
        else if (model.getCursor().equals(Cursor.MOVE)) {
            switch (currentMiniState) {
                case DRAGGING -> {
                    dX = event.getX() - prevX;
                    dY = event.getY() - prevY;
                    prevX = event.getX();
                    prevY = event.getY();
                    System.out.println(dX);
                    model.updateWin(dX, dY);
                    model.updateMini(dX, dY);
                }
            }
        }
        else if (model.getCursor().equals(Cursor.CROSSHAIR)) {
            switch (currentLinkState) {
                case DRAGGING -> {
                    model.moveLink(event.getX(), event.getY());
                }
                case PREPARE_CREATE ->{
                    model.moveLink(event.getX(), event.getY());
                    if (!model.hitNode(event.getX(), event.getY())){
                        selfCreateOut = true;
                    }
                }
            }
        }
    }

    /**
     * handle mouse released
     * three situations:
     * 1: cursor==default:
     *      - create node
     * 2: cursor==move:
     *      - move completed
     * 3: cursor==crosshair
     *      - create link and trans
     * @param event
     */
    public void handleReleased(MouseEvent event) {
        if (model.getCursor().equals(Cursor.DEFAULT)) {
            switch (currentState) {
                case PREPARE_CREATE -> {
                    model.addNode(event.getX(), event.getY());
                    currentState = State.READY;
                }
                case DRAGGING -> {
                    //iModel.NodeUnselect();
                    currentState = State.READY;
                }
            }
        }
        else if (model.getCursor().equals(Cursor.MOVE)) {
            switch (currentMiniState) {
                case DRAGGING -> {
                    //System.out.println(model.WinX);
                    currentMiniState = MiniState.READY;
                }
            }
        }
        else if (model.getCursor().equals(Cursor.CROSSHAIR)) {
            switch (currentLinkState) {
                case DRAGGING-> {
                    model.getLinks().remove(model.getLinks().size()-1);
                    currentLinkState = LinkState.READY;
                    model.call();
                }
                case PREPARE_CREATE -> {
                    if (model.hitNode(event.getX(), event.getY())){

                        end = model.whichHit(event.getX(), event.getY());

                        model.setLinkRec(model.getLinks().size()-1, (start.x + end.x)/2 - 50,
                                (start.y + end.y)/2 - 50);

                        model.getLinks().get(model.getLinks().size()-1).setNode(start, end);
                        model.getLinks().get(model.getLinks().size()-1).setConnected(true);
                        start.addLink(model.getLinks().get(model.getLinks().size()-1));
                        end.addLink(model.getLinks().get(model.getLinks().size()-1));
                        model.getLinks().get(model.getLinks().size()-1).reset();

                        // line1 start to trans
                        model.getLinks().get(model.getLinks().size()-1).m1X1 = start.x;
                        model.getLinks().get(model.getLinks().size()-1).m1Y1 = start.y;
                        model.getLinks().get(model.getLinks().size()-1).m1X2 =
                                model.getLinks().get(model.getLinks().size()-1).tX1;
                        model.getLinks().get(model.getLinks().size()-1).m1Y2 =
                                model.getLinks().get(model.getLinks().size()-1).tY1;

                        // line2 trans to end
                        model.getLinks().get(model.getLinks().size()-1).m2X1 =
                                model.getLinks().get(model.getLinks().size()-1).tX1 + 100;
                        model.getLinks().get(model.getLinks().size()-1).m2Y1 =
                                model.getLinks().get(model.getLinks().size()-1).tY1 + 100;
                        model.getLinks().get(model.getLinks().size()-1).m2X2 = end.x;
                        model.getLinks().get(model.getLinks().size()-1).m2Y2 = end.y;
                        model.call();
                    }
                    else{
                        model.removeLinkIn(model.getLinks().size()-1);
                        model.call();
                    }
                    currentLinkState = LinkState.READY;
                }
            }
        }
    }


    public void HandleDeletePressed(KeyEvent keyEvent) {
        System.out.println(keyEvent.getCode());
        if (keyEvent.getCode().equals(KeyCode.BACK_SPACE) || keyEvent.getCode().equals(KeyCode.DELETE)){
            if (iModel.SelectedN != null){
                iModel.SelectedN.getLinks().forEach(l ->{
                    model.removeLink(l);
                });
                model.removeNode(iModel.SelectedN);
                iModel.NodeUnselect();
            }
            else if (iModel.SelectedL != null){
                model.removeLink(iModel.SelectedL);
                iModel.LinkUnselect();
            }
        }
    }

}
