package org.openjfx;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Model {
    //TODO Set Model. Halim will fixe it
    private StringProperty text = new SimpleStringProperty();
    //List<BankName> accountList = new ArrayList<>();
    List<String> personList = new ArrayList<>();
    List<String> bankNameList = new ArrayList<>();
    List<String> operationList = new ArrayList<>();


    public Model() {

    }


    public String getText() {
        return text.get();
    }

    public void setText(String text) {
        this.text.setValue(text);
    }

    public StringProperty textProperty() {
        return text;
    }

    public boolean checkAccountId(int id) {
        List<BankName> filterList = BankName.getInstance().accountList.stream()
                .filter(e -> e.getId()==id)
                .collect(Collectors.toList());
        return filterList.size() == 1;
    }
}
