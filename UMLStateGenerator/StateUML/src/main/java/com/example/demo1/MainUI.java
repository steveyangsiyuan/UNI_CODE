package com.example.demo1;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class MainUI extends StackPane {

    public MainUI() {
        // create objects
        ToolPalette toolPalette = new ToolPalette();
        DiagramView diagramView = new DiagramView();
        NodePropertiesView nodePropertiesView = new NodePropertiesView();
        LinkPropertiesView linkPropertiesView = new LinkPropertiesView();

        AppController controller = new AppController();
        SMModel model = new SMModel();
        InteractionModel iModel = new InteractionModel();

        // connect models
        controller.setModel(model);
        toolPalette.setModel(model);
        diagramView.setModel(model);
        nodePropertiesView.setModel(model);
        linkPropertiesView.setModel(model);

        controller.setIModel(iModel);
        toolPalette.setIModel(iModel);
        diagramView.setIModel(iModel);
        nodePropertiesView.setIModel(iModel);
        linkPropertiesView.setIModel(iModel);


        // add Subscriber
        model.addSubscriber(toolPalette);
        model.addSubscriber(diagramView);
        model.addSubscriber(nodePropertiesView);
        model.addSubscriber(linkPropertiesView);
        iModel.addSubscriber(toolPalette);
        iModel.addSubscriber(diagramView);
        iModel.addSubscriber(nodePropertiesView);
        iModel.addSubscriber(linkPropertiesView);

        // add controller
        toolPalette.setController(controller);
        diagramView.setController(controller);
        nodePropertiesView.setController(controller);
        linkPropertiesView.setController(controller);
        HBox root = new HBox();
        root.getChildren().addAll(toolPalette, diagramView, nodePropertiesView, linkPropertiesView);
        toolPalette.setViewOrder(1);
        nodePropertiesView.setViewOrder(1);
        linkPropertiesView.setViewOrder(1);

        root.setPrefWidth(100+800+240);
        this.getChildren().add(root);
    }
}
