package com.example.demo1;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ToolPalette extends StackPane implements AppModelListener, AppIModelListener {

    VBox root;

    SMModel model;
    Button cursor;
    Button crossHair;
    Button dirCross;
    InteractionModel iModel;
    public ToolPalette(){
        Label one = new Label();
        root = new VBox();
        root.setSpacing(10);
        cursor = new Button("default");
        crossHair = new Button("crossHair");
        dirCross = new Button("dirCross");
        root.setSpacing(10);
        root.getChildren().addAll(one, cursor, crossHair, dirCross);
        VBox.setVgrow(root, Priority.ALWAYS);
        this.getChildren().add(root);

    }
    @Override
    public void modelChanged() {

    }

    public void setModel(SMModel model) {
        this.model = model;
    }

    public void setIModel(InteractionModel iModel){
        this.iModel = iModel;
    }

    public void setController(AppController controller) {
        cursor.setOnAction(controller::handleCursorClikced);
        crossHair.setOnAction(controller::handleCrossHairClikced);
        dirCross.setOnAction(controller::handleDirCrossClikced);
    }


}
