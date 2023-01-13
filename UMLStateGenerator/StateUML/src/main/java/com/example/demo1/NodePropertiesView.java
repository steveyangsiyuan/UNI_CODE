package com.example.demo1;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class NodePropertiesView extends StackPane implements AppModelListener, AppIModelListener {
    double prefW;
    SMModel model;
    InteractionModel iModel;

    VBox root = new VBox();
    Label Node;
    Label editProm;
    TextField editName;

    String name = "Default";

    public NodePropertiesView() {
        // create views
        prefW = 240;
        Node = new Label("State");
        Node.setPrefHeight(75);
        Node.setPrefWidth(200);
        Node.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 22));
        Node.setAlignment(Pos.CENTER);
        Node.setBackground(Background.EMPTY);
        editProm = new Label("State Name:");
        editName = new TextField(this.name);

        root.setPrefHeight(800);
        root.setPrefWidth(prefW);
        root.setSpacing(10);
        root.getChildren().addAll(Node, editProm, editName);
        this.getChildren().add(root);
    }

    @Override
    public void modelChanged() {
        // when select node, make this visible
        // invisible otherwise
        if (iModel.SelectedN != null) {
            root.setPrefWidth(240);
            root.setVisible(true);
        } else {
            root.setPrefWidth(0);
            root.setVisible(false);
        }
    }

    public void setModel(SMModel model) {
        this.model = model;
    }

    public void setIModel(InteractionModel iModel) {
        this.iModel = iModel;
    }

    public void setController(AppController controller) {
        editName.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                    iModel.SelectedN.stateName = editName.textProperty().getValue();
                    model.call();
                }
            }
        });
    }


}
