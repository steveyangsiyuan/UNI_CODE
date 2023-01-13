package com.example.demo1;

import java.util.ArrayList;
import java.util.List;

public class InteractionModel {
    private List<AppIModelListener> subscribers;
    SMTransitionLink SelectedL;
    SMStateNode SelectedN;
    public InteractionModel(){
        subscribers = new ArrayList<>();
    }

    public void addSubscriber(AppIModelListener sub) {
        subscribers.add(sub);
    }

    private void notifySubscribers() {
        subscribers.forEach(s -> s.modelChanged());
    }

    public void setSelectedN(SMStateNode n) {
        SelectedN = n;
        notifySubscribers();
    }

    public void setSelectedL(SMTransitionLink l) {
        SelectedL = l;
        notifySubscribers();
    }

    public void NodeUnselect() {
        SelectedN = null;
        notifySubscribers();
    }

    public void LinkUnselect() {
        SelectedL = null;
        notifySubscribers();
    }

    public SMStateNode getSelectedNode() {
        return (SMStateNode) SelectedN;
    }

    public SMTransitionLink getSelectedLink() {
        return (SMTransitionLink) SelectedL;
    }


}
