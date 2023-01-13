package com.example.demo1;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class LinkPropertiesView extends StackPane implements AppModelListener, AppIModelListener {
    SMModel model;
    InteractionModel iModel;

    double prefW;

    VBox root = new VBox();
    Label Link;
    Label event;
    TextField editEvent;

    Label context;
    TextField editContext;

    Label sideEffect;
    TextField editSideEffect;

    Button update;

    String EventStr = "No event";
    String ContentStr = "No Context";
    String EffectStr = "No Side Effect";

    public LinkPropertiesView(){
        prefW = 0;
        Link = new Label("Transition");
        Link.setAlignment(Pos.CENTER);
        Link.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 22));


        Label one = new Label();
        Label two = new Label();

        event = new Label("Event");
        editEvent = new TextField(EventStr);
        editEvent.setPrefColumnCount(20);
        context = new Label("Context");
        editContext = new TextField(ContentStr);
        sideEffect = new Label("Side Effect");
        editSideEffect = new TextField(EffectStr);

        update = new Button("Update");

        root.setPrefWidth(prefW);
        System.out.println(prefW);

        VBox.setVgrow(root, Priority.ALWAYS);
        root.setSpacing(10);
        root.getChildren().addAll(one, Link, event, editEvent, context, editContext, sideEffect, editSideEffect, update);
        this.getChildren().add(root);
    }
    @Override
    public void modelChanged() {
        if (iModel.SelectedL != null){
            root.setPrefWidth(240);
            root.setVisible(true);
        }
        else {
            root.setPrefWidth(0);
            root.setVisible(false);
        }
    }

    public void setModel(SMModel model) {
        this.model = model;
    }

    public void setIModel(InteractionModel iModel){
        this.iModel = iModel;
    }

    public void setController(AppController controller) {
        editEvent.setOnKeyPressed(e-> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                iModel.SelectedL.event = editEvent.textProperty().getValue();
                model.call();
            }
        });
        editContext.setOnKeyPressed(e-> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                iModel.SelectedL.context = editContext.textProperty().getValue();
                model.call();
            }
        });
        editSideEffect.setOnKeyPressed(e-> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                iModel.SelectedL.sideEffects = editSideEffect.textProperty().getValue();
                model.call();
            }
        });
        update.setOnAction(e -> {
            iModel.SelectedL.event = editEvent.textProperty().getValue();
            iModel.SelectedL.context = editContext.textProperty().getValue();
            iModel.SelectedL.sideEffects = editSideEffect.textProperty().getValue();
            model.call();
        });
    }

}
