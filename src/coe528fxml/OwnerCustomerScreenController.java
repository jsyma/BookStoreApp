
package coe528fxml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class OwnerCustomerScreenController implements Initializable {
    
    @FXML
    public TableView<Users> userView;
    @FXML
    public TableColumn<Users, String> usernames;
    @FXML
    public TableColumn<Users, String> passwords;
    @FXML
    public TableColumn<Users, Integer> points;
    
    @FXML
    private TextField textFieldUsername;
    @FXML
    private TextField textFieldPassword;
    @FXML
    private Label WARNING;
    
    ObservableList<Users> userList = FXCollections.observableArrayList(new Users("admin","admin"));//initial user will be the owner's username and password
    
    @FXML
    private void addButton(ActionEvent event){
        if(textFieldUsername.getText().isEmpty() || textFieldPassword.getText().isEmpty()){
            WARNING.setText("Please input a Username and Password");
        }
        else{
            addUser();
        }
    }
    
    private void addUser(){
        String username = textFieldUsername.getText();
        String password = textFieldPassword.getText();
        int points = 0;
        userList.addAll(new Users(username, password));
        try{
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("customers.txt", true)));
            writer.println(username + ", " + password);
            writer.flush();
            writer.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol();
        userList = fromTxt();
        userView.setItems(userList);
    }    
    private void initCol(){//initialized each column to have the data returned from the get___() methods in the Users.java class
        usernames.setCellValueFactory(new PropertyValueFactory<>("Username"));
        passwords.setCellValueFactory(new PropertyValueFactory<>("Password"));
        points.setCellValueFactory(new PropertyValueFactory<>("Points"));
    }

    @FXML
    public void deleteUser(ActionEvent event)throws FileNotFoundException, IOException{
        File readFile = new File("customers.txt");
        File buff = new File("bufferCustomers.txt");
        
        BufferedReader reader = new BufferedReader(new FileReader(readFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(buff));
        
        ObservableList<Users> allUsers, singleUsers;
        allUsers = userView.getItems();
        singleUsers = userView.getSelectionModel().getSelectedItems();
        Users userToRemove = userView.getSelectionModel().getSelectedItem();
        singleUsers.forEach(allUsers::remove);
        
        String currentLine;
        String lineToRemove = userToRemove.getUsername();
        
        
        while((currentLine = reader.readLine()) != null){
            String tLine = currentLine.trim();
            if(tLine.contains(lineToRemove)){
                continue;
            }
            writer.write(currentLine+'\n');
            System.out.println(currentLine);
        }
        writer.close();
        reader.close();
        readFile.delete();
        buff.renameTo(readFile);
    }
    
    @FXML
    public void changeToStartScreen(ActionEvent event) throws Exception{
        Parent root2 = FXMLLoader.load(getClass().getResource("OwnerStartScreen.fxml"));
        Scene scene2 = new Scene(root2);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();    
    }
    
    public ObservableList<Users> fromTxt(){
        String Username, Password;
        
        try{
            BufferedReader reader = new BufferedReader(new FileReader("customers.txt"));
            String line = reader.readLine();
            while(line != null){
                String info[] = line.split(", ");
                Username = info[0];
                Password = info[1];
                
                userList.addAll(new Users(Username, Password));
                line = reader.readLine();
            }
            reader.close();
        } catch(Exception e){
            System.out.println(e);
        }
        return userList;
    }    
    
}
