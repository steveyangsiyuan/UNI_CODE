package com.example.demo1;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class DiagramView extends StackPane implements AppModelListener, AppIModelListener {

    GraphicsContext gc;

    GraphicsContext miniGc;
    Canvas myCanvas;

    Canvas miniCanvas;
    SMModel model;
    InteractionModel iModel;

    public DiagramView(){
        myCanvas = new Canvas(1600,1600);
        myCanvas.setHeight(800);
        myCanvas.setLayoutX(500);
        myCanvas.setWidth(800);
        myCanvas.setFocusTraversable(true);
        myCanvas.addEventFilter(MouseEvent.ANY, e -> myCanvas.requestFocus());

        gc = myCanvas.getGraphicsContext2D();
        gc.setLineWidth(3);
        gc.setFill(Color.AQUA);
        gc.fillRect(0, 0, 1600, 1600);
        this.getChildren().add(myCanvas);

    }

    private void draw() {
        gc.clearRect(0,0,myCanvas.getWidth(),myCanvas.getHeight());
        gc.moveTo(model.WinX, model.WinY);
        gc.setGlobalAlpha(1);
        gc.fillRect(0,0,myCanvas.getWidth(),myCanvas.getHeight());
        gc.setFill(Color.AQUA);
        model.getNodes().forEach(n -> {
            gc.setFill(Color.YELLOW);
            if (n == iModel.getSelectedNode()) {
                gc.setStroke(Color.TOMATO);
            } else {
                gc.setStroke(Color.BLACK);
            }
            gc.fillRect(n.x, n.y,  n.width, n.height);
            gc.strokeRect(n.x, n.y,  n.width, n.height);

            gc.setFill(Color.BLACK);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.fillText(n.stateName,
                    n.x + n.width/2,
                    n.y + n.height/2);
        });
        gc.setStroke(Color.BLACK);
        model.getLinks().forEach(l -> {
            gc.setFill(Color.LEMONCHIFFON);

            if (l == iModel.getSelectedLink()) {
                gc.setStroke(Color.TOMATO);
            } else {
                gc.setStroke(Color.BLACK);
            }
            if (l.connected) {
                gc.fillRect(l.tX1, l.tY1, l.transWidth, l.transHeight);
                gc.strokeRect(l.tX1, l.tY1, l.transWidth, l.transHeight);
                gc.setTextAlign(TextAlignment.JUSTIFY);
                gc.setFill(Color.BLACK);
                gc.fillText("-Event:\n"+l.event+"\n-Context:\n" + l.context+"\n-Select Item:\n" + l.sideEffects,
                        l.tX1 + 5,
                        l.tY1 + 20);
            }

            gc.setStroke(Color.BLACK);
            gc.strokeLine(l.startX, l.startY, l.endX, l.endY);

            gc.setFill(Color.BLACK);
            gc.strokeLine(l.m1X1, l.m1Y1, l.m1X2, l.m1Y2);
            gc.fillRect(l.m1X2-2, l.m1Y2-2, 10, 10);
            gc.strokeLine(l.m2X1, l.m2Y1, l.m2X2, l.m2Y2);
            gc.fillRect(l.m2X2, l.m2Y2, 10, 10);
            gc.setFill(Color.BLACK);
        });
    }

    public void miniDraw(){
        gc.setFill(Color.AQUA);
        gc.save();
        gc.setGlobalBlendMode(BlendMode.MULTIPLY);
        gc.setGlobalAlpha(0);
        gc.setFill(Color.CADETBLUE);
        gc.scale(0.5, 0.5);
        draw();
        gc.restore();
    }
    @Override
    public void modelChanged() {
        draw();
        miniDraw();
    }

    public void setModel(SMModel model) {
        this.model = model;
    }

    public void setIModel(InteractionModel iModel){
        this.iModel = iModel;
    }

    public void setController(AppController controller) {
        myCanvas.setOnMousePressed(controller::handlePressed);
        myCanvas.setOnMouseDragged(controller::handleDragged);
        myCanvas.setOnMouseReleased(controller::handleReleased);
        myCanvas.setOnKeyPressed(controller::HandleDeletePressed);
    }


}
