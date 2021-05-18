package sdu.sem2.se17.presentation.cms;

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

    private final static int maxSize = 5;
    private final static int minSearchSize = 3;

    private final CreditManagementHandler handler;
    private final ContextMenu popUp = new ContextMenu();

    private Participant selectedParticipant;

    public SearchTextField(Participant s, CreditManagementHandler handler) {
        super(Optional.ofNullable(s).map(Participant::getName).orElse(""));
        this.handler = handler;
        this.selectedParticipant = s;
        configureListeners();
    }

    public Participant getSelectedParticipant(){
        return selectedParticipant;
    }

    private void configureListeners(){
        textProperty().addListener((oV, oS, nS) -> {
            if (getText().length() < minSearchSize) {
                popUp.hide();
            } else {
                ArrayList<Participant> participantArrayList = handler.findParticipant(getText());
                if (participantArrayList.size() > 0) {
                    populatePopup(participantArrayList);
                    if (!popUp.isShowing()) {
                        popUp.show(SearchTextField.this, Side.BOTTOM, 0, 0);
                    }
                } else {
                    popUp.hide();
                }
            }
        });
        focusedProperty().addListener((oV, oB, nB) -> popUp.hide());
    }

    private void populatePopup(ArrayList<Participant> searchResult) {
        List<CustomMenuItem> menuItems = new LinkedList<>();
        int index = searchResult.size();
        if (index > maxSize) {
            index = maxSize;
        }
        for (int i = 0; i < index; i++)
        {
            Participant participant = searchResult.get(i);
            Label entryLabel = new Label(getParticipantHistoryName(participant));

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

    private String getParticipantHistoryName(Participant participant){
            StringBuilder resultString = new StringBuilder(participant.getName());

            var productionNames = handler.recentlyParticipatedIn(participant)
                    .stream()
                    .map(Production::getName)
                    .limit(maxSize)
                    .collect(Collectors.toList());

            if(productionNames.size() > 0){
                resultString.append(" - fra ");
                for (int i = 0; i < productionNames.size(); i++) {
                    resultString.append(productionNames.get(i));
                    if (i  < productionNames.size()- 1 ){
                        resultString.append(", ");
                    }
                }
            }

            return resultString.toString();
    }

    private void selectParticipant(Participant p){
        selectedParticipant = p;
    }



}
