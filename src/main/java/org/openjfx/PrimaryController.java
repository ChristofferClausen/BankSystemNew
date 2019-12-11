package org.openjfx;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class PrimaryController {


    @FXML private TextField accountId, selectOneId, amountId, idAmount, idTextTo, firstNameId, lastNameId, ageId, bankId, amountDeposit, amountWithDraw, amountAvailableSelected;
    @FXML private TextArea textAreaTransfer, textAreaDeposit, textAreaWithDraw;
    @FXML private TextField accountIdSelected =new TextField();
    @FXML private Label firstNameClient = new Label();
    @FXML private Label lastNameClient = new Label();
    @FXML private ChoiceBox<String> bankNameId;
    @FXML private ComboBox<String> personId;
    @FXML private Button selectId, newClientId, newBankIId, newAccountId;
    @FXML private ListView<String> listView;
    private int tempId = 0;
    private Stage primaryStage;
    private List<BankName> filterList = new ArrayList<>();
    Model model;
    private AccountingSystem accountingSystem;


    public PrimaryController(Model model) {
        this.model = model;
    }

    public void setAccountId(TextField accountId) {
        this.accountId = accountId;
    }

    public void setSelectOneId(TextField selectOneId) {
        this.selectOneId = selectOneId;
    }

    public void setAccountIdSelected(TextField accountIdSelected) {
        this.accountIdSelected = accountIdSelected;
    }

    public void setAmountId(TextField amountId) {
        this.amountId = amountId;
    }

    public void setIdAmount(TextField idAmount) {
        this.idAmount = idAmount;
    }

    public void setIdTextTo(TextField idTextTo) {
        this.idTextTo = idTextTo;
    }

    public void setFirstNameId(TextField firstNameId) {
        this.firstNameId = firstNameId;
    }

    public void setLastNameId(TextField lastNameId) {
        this.lastNameId = lastNameId;
    }

    public void setAgeId(TextField ageId) {
        this.ageId = ageId;
    }

    public void setBankId(TextField bankId) {
        this.bankId = bankId;
    }

    public void setAmountDeposit(TextField amountDeposit) {
        this.amountDeposit = amountDeposit;
    }

    public void setAmountWithDraw(TextField amountWithDraw) {
        this.amountWithDraw = amountWithDraw;
    }

    public void setAmountAvailableSelected(TextField amountAvailableSelected) {
        this.amountAvailableSelected = amountAvailableSelected;
    }

    public void initialize() {
        TextField[] paths = new TextField[] {accountId, selectOneId, accountIdSelected, amountId, idAmount, idTextTo, firstNameId, lastNameId, ageId, bankId, amountDeposit, amountWithDraw, amountAvailableSelected};
        model = new Model();
        BankName.getInstance().dataForTest();
        //model.textProperty().bindBidirectional(accountIdClient.textProperty());
        //model.textProperty().bindBidirectional(saldoClient.textProperty());
        //model.accountList = BankName.getInstance().accountList;
        for (String str : model.bankNameList){
            bankNameId.getItems().add(str);
        }
        for (String str : model.personList){
            personId.getItems().add(str);
        }

    }
    @FXML
    private void createPerson() throws IOException {
        App.setRoot("person");
    }
    @FXML
    private void createBankName() throws IOException {
        App.setRoot("bank");
    }
    @FXML
    private void createAccount() throws IOException {
        for (String str : model.bankNameList){
            bankNameId.getItems().add(str);
        }
        for (String str : model.personList){
            personId.getItems().add(str);
        }

        App.setRoot("account");
    }
    @FXML
    private void backToHome() throws IOException {
        App.setRoot("home");
    }
    @FXML
    private void goToNyKund() throws IOException {
        App.setRoot("nykund");
    }
    @FXML
    private void goToRedanKund() throws IOException {
        App.setRoot("selectClient");
    }
    @FXML
    private void goToRedanKundMenu() throws IOException {
        App.setRoot("redankund");
    }
    @FXML
    private void goToWithdraw() throws IOException {
        App.setRoot("withdraw");
    }
    @FXML
    private void goToDeposit() throws IOException {
        App.setRoot("deposit");
    }
    @FXML
    private void goToTransfer() throws IOException {
        App.setRoot("transfer");
    }
    @FXML
    private void goTobankStatements() throws IOException{
        for (String item : model.operationList){
            listView.getItems().add(item);
        }
        App.setRoot("bankStatements");
    }
    @FXML
    private void selectOneAccount(ActionEvent actionEvent) throws IOException {
        int id = Integer.parseInt(selectOneId.getText());
        selectOneId.setText("");
        if (model.checkAccountId(id)){
            tempId = id;
            filterList = BankName.getInstance().accountList.stream()
                    .filter(e -> e.getId()==id)
                    .collect(Collectors.toList());
            accountIdSelected.setText(String.valueOf(id).toString());
            firstNameClient.setText(filterList.get(0).getPerson().getFirstName());
            lastNameClient.setText(filterList.get(0).getPerson().getLastName());
            amountAvailableSelected.setText(String.valueOf(filterList.get(0).getSaldo()));
            App.setRoot("redankund");
        }else {
            selectOneId.setText("");
            errorLogin();
        }
    }
    @FXML
    private void createNewAccount(ActionEvent actionEvent){
        String textPerson = personId.getAccessibleText();
        String[] words=textPerson.split(" ");
        String firstName = words[0];
        String lastName = words[1];
        int age = Integer.parseInt(words[2]);
        BankName.getInstance().accountList = accountingSystem.createAccount(bankNameId.getAccessibleText(), Float.parseFloat(amountId.getText()), new Person(firstName, lastName, age));
        amountId.setText("");
    }
    @FXML
    private void createNewPerson(ActionEvent actionEvent){
        //model.personList.add(accountingSystem.createPerson(firstNameId.getText(), lastNameId.getText(), Integer.parseInt(ageId.getText())));
        model.personList.add(firstNameId.getText()+" "+lastNameId.getText()+" "+ageId.getText());
        firstNameId.setText("");
        lastNameId.setText("");
        ageId.setText("");
    }
    @FXML
    private void createNewBank(ActionEvent actionEvent){
        //model.bankNameList.add(accountingSystem.createBank(bankId.getText()));
        model.bankNameList.add(bankId.getText());
        bankId.setText("");
    }
    @FXML
    private void deposit(ActionEvent actionEvent) throws IOException{
        BankName.getInstance().deposit(tempId, Integer.parseInt(amountDeposit.getText()));
        model.operationList.add("DEPOSIT :"+"Amount :"+amountDeposit.getText()+"kr"+" "+textAreaDeposit.getText());
        /*filterList.clear();
        amountDeposit.setText("");
        filterList = BankName.getInstance().accountList.stream()
                .filter(e -> e.getId()==tempId)
                .collect(Collectors.toList());
        accountIdSelected.setText(String.valueOf(tempId));
        firstNameClient.setText(filterList.get(0).getPerson().getFirstName());
        lastNameClient.setText(filterList.get(0).getPerson().getLastName());
        accountIdSelected.setText(String.valueOf(filterList.get(0).getSaldo()));*/
        App.setRoot("redankund");
    }
    @FXML
    private void withDraw(ActionEvent actionEvent) throws IOException {
        BankName.getInstance().withDraw(tempId, Integer.parseInt(amountWithDraw.getText()));
        model.operationList.add("WITHDRAW :"+"Amount :"+amountWithDraw.getText()+"kr"+" "+textAreaWithDraw.getText());
        /*filterList.clear();
        amountWithDraw.setText("");
        filterList = BankName.getInstance().accountList.stream()
                .filter(e -> e.getId() == tempId)
                .collect(Collectors.toList());
        accountIdSelected.setText(String.valueOf(tempId));
        firstNameClient.setText(filterList.get(0).getPerson().getFirstName());
        lastNameClient.setText(filterList.get(0).getPerson().getLastName());
        accountIdSelected.setText(String.valueOf(filterList.get(0).getSaldo()));
        App.setRoot("redankund");*/
    }
    @FXML
    private void makeTransfer(ActionEvent actionEvent) throws IOException{
        BankName.getInstance().transfer(tempId, Integer.parseInt(idTextTo.getText()), Float.parseFloat(idAmount.getText()));
        model.operationList.add("TRANSFER to :"+idTextTo.getText()+"Amount :"+idAmount.getText()+"kr"+" "+textAreaTransfer.getText());
        /*filterList.clear();
        idTextTo.setText("");
        idAmount.setText("");
        textAreaTransfer.setText("");
        filterList = BankName.getInstance().accountList.stream()
                .filter(e -> e.getId() == tempId)
                .collect(Collectors.toList());
        accountIdSelected.setText(String.valueOf(tempId));
        firstNameClient.setText(filterList.get(0).getPerson().getFirstName());
        lastNameClient.setText(filterList.get(0).getPerson().getLastName());
        accountIdSelected.setText(String.valueOf(filterList.get(0).getSaldo()));*/
        App.setRoot("redankund");
    }
    @FXML
    private void getSaldo(ActionEvent actionEvent){
        BankName.getInstance().account(tempId, Integer.parseInt(amountWithDraw.getText()));
        amountWithDraw.setText("");
    }
    @FXML
    private void goToMainMenu(ActionEvent actionEvent) throws IOException{
        /*accountIdClient.setText("0");
        firstNameClient.setText(null);
        lastNameClient.setText(null);
        saldoClient.setText("0.0");
        textId.setText("");
        tempId = 0;
        if (filterList.size() >= 1){
            filterList.clear();
            App.setRoot("home");
        }else {*/
            App.setRoot("home");
       // }
    }
    private void errorLogin(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("LOGIN ERROR");
        alert.setHeaderText(null);
        alert.setContentText("THIS ID DOES NOT EXISTE!");
        alert.showAndWait();
    }
    public void errorAmount(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("WRONG AMOUNT");
        alert.setHeaderText(null);
        alert.setContentText("THE AMOUNT AVAILABLE IS LESS THAN WHAT IT HAS BEEN CHOSEN!");
        alert.showAndWait();
    }
    @FXML
    public void goBack() throws IOException{
        App.setRoot("nykund");
    }
    @FXML
    public void saveTextFile(ActionEvent actionEvent) {
        int counter = 0;
        //int index = BankName.getInstance().accountList.indexOf(filterList.get(0));
        String path = System.getProperty("user.home") +
                File.separator + "Documents" +
                File.separator + "CustomFolder";

        File dir = new File(path);

        if (dir.exists())
            System.out.println("Folder exist");
        else if (dir.mkdir())
            System.out.println("Folder created");
        else
            System.out.println("Folder not created");

        File filePath = new File(path + File.separator + "Kvittot.txt");

        try (FileWriter out = new FileWriter(filePath + ".txt")) {
            out.write("------------------------------------------------------------------"+"\n");
            out.write("AccountID :"+tempId+"  "+"|"+" "+"Firstname :"+BankName.getInstance().accountList.get(1).getPerson().getFirstName()+"  "+"|"+" "+"Lastname :"+BankName.getInstance().accountList.get(1).getPerson().getLastName() +"\n");

            out.write("------------------------------------------------------------------" +"\n");
            for (String str : model.operationList) {
                counter++;
                out.write(counter +" | "+str +"\n");
            }
            out.write("------------------------------------------------------------------" +"\n");
            out.write("Amount available :"+BankName.getInstance().accountList.get(1).getSaldo()+"kr" +"\n");
            out.write("------------------------------------------------------------------" +"\n");
        } catch (IOException ex) {
            System.out.println("Error!");
        }



        /*FileChooser savefile = new FileChooser();
        savefile.setTitle("Save File");
        File filePath = savefile.showSaveDialog(primaryStage);
        int index = BankName.getInstance().accountList.indexOf(filterList.get(0));
        try (FileWriter out = new FileWriter(filePath + ".txt")) {
            out.write("------------------------------------------------------------------");
            out.write("AccountID :"+tempId+"  "+model.accountList.get(index).getPerson().getFirstName()+" "+model.accountList.get(index).getPerson().getLastName());
            out.write("------------------------------------------------------------------");
            for (String str : model.operationList) {
                counter++;
                out.write(counter +"-"+str);
            }
            out.write("------------------------------------------------------------------");
            out.write("Amount available :"+model.accountList.get(index).getSaldo()+"kr");
            out.write("------------------------------------------------------------------");
        } catch (IOException ex) {
            System.out.println("Error!");
        }*/
    }
}
