package sdu.sem2.se17.presentation.cms;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sdu.sem2.se17.domain.CreditManagementHandler;
import sdu.sem2.se17.domain.credit.Participant;
import sdu.sem2.se17.domain.production.Production;

import java.util.*;
import java.util.stream.Collectors;

public class SearchTextField extends TextField {

    private CreditManagementHandler handler;
    private ContextMenu popUp = new ContextMenu();
    private final SortedSet<String> entries = new TreeSet<>();
    private final static int maxSize = 5;
    private final static int minSearchSize = 3;
    private Participant selectedParticipant;

    private ArrayList<Participant> participantArrayList = new ArrayList<>();

    public SearchTextField(CreditManagementHandler handler) {
        super();
        this.handler = handler;
        configureListeners();
    }

    public SearchTextField(String s, CreditManagementHandler handler) {
        super(s);
        this.handler = handler;
        configureListeners();
    }

    private void configureListeners(){

        textProperty().addListener((observableValue, oldString, newString) -> {
            if (getText().length() < minSearchSize) {
                popUp.hide();
            } else {

                participantArrayList.clear();
                participantArrayList.addAll(handler.findParticipant(getText()));


                HashMap<Participant, String> searchResult = new HashMap<>();

                participantArrayList.forEach(p -> {
                    StringBuilder resultString = new StringBuilder(p.getName());
                    var productionNames = handler.recentlyParticipatedIn(p)
                            .stream()
                            .map(Production::getName)
                            .limit(maxSize).collect(Collectors.toList());
                    if(productionNames.size() > 0){
                        resultString.append(" - in ");
                        for (String name: productionNames){
                            resultString.append(name).append(", ");
                        }
                    }
                    searchResult.put(p, resultString.toString());
                });



                if (participantArrayList.size() > 0) {
                    populatePopup(searchResult);
                    if (!popUp.isShowing()) {
                        popUp.show(SearchTextField.this, Side.BOTTOM, 0, 0);
                    }
                } else {
                    popUp.hide();
                }
            }
        });

        focusedProperty().addListener((observableValue, b1, b2) -> popUp.hide());

    }

    private void populatePopup(HashMap<Participant, String> searchResult) {

        List<CustomMenuItem> menuItems = new LinkedList<>();
        var srParticipants = new ArrayList<>(searchResult.keySet());
        int index = srParticipants.size();
        if (index > maxSize) {
            index = maxSize;
        }
        for (int i = 0; i < index; i++)
        {
            Participant participant = srParticipants.get(i);
            Label entryLabel = new Label(searchResult.get(participant));
            CustomMenuItem item = new CustomMenuItem(entryLabel, true);
            item.setOnAction(actionEvent -> {
                setText(participant.getName());
                selectParticipant(participant);
                popUp.hide();
            });
            menuItems.add(item);
        }
        popUp.getItems().clear();
        popUp.getItems().addAll(menuItems);

    }


    private void selectParticipant(Participant p){
        selectedParticipant = p;
    }

    public Participant getSelectedParticipant(){
        return selectedParticipant;
    }

}
