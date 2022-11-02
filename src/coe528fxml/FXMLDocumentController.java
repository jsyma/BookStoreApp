
package coe528fxml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;
import java.io.FileReader;
import java.io.IOException;


public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private TextField Username;
    @FXML
    private PasswordField Password;
    @FXML 
    private Label welcome;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");//make sure to set fxid in fxml file (found under the code panel when you lcick an element)
    }
    
    @FXML 
    public void verify(ActionEvent event) throws Exception{ //Next screen based on whether the customer or the owner is logging in
        String un = Username.getText();
        String pw = Password.getText();
        
        if(un.equals("admin") && pw.equals("admin")){
            System.out.println("Successful Admin Login");
            changeToOwnerStartScreen(event);
        }
        else if (un.isEmpty()||pw.isEmpty()){
            label.setText("Empty Entry. Try Again.");
        }
        else{
            System.out.println("Validate Customer Login");
            validateCustomer(un, pw, event); //Checks if the customer login credentials are correct
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //label.setText("Hello World!");
    }    
    
    private void validateCustomer(String enteredUN, String enteredPW, ActionEvent event) throws FileNotFoundException, IOException, Exception{
        File readFile = new File("customers.txt");
        BufferedReader reader = new BufferedReader(new FileReader(readFile));
        String line;
        String fileUN;
        String filePW;
        int count=0;
        
        while((line=reader.readLine())!= null){
            String temp[] = line.split(", "); //Splits the line at a comma
            fileUN = temp[0]; 
            filePW = temp[1];
            
            if((fileUN.equals(enteredUN))&&(filePW.equals(enteredPW))){ //compares the entered and file username and password
                count++; //if matched count incremented
            }
        }
        reader.close();
        if(count==1){ //Valid customer username and password was found, can switch screens
            changeToCustomerStartScreen(event);
        }
        else{
            label.setText("Invalid Customer Login.");
        }
    }
    
    public void changeToOwnerStartScreen(ActionEvent event) throws Exception{
        Parent root2 = FXMLLoader.load(getClass().getResource("OwnerStartScreen.fxml"));
        Scene scene2 = new Scene(root2);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();        
    }
    
    public void changeToCustomerStartScreen(ActionEvent event) throws Exception{
        Parent root2 = FXMLLoader.load(getClass().getResource("CustomerStartScreen.fxml"));
        Scene scene2 = new Scene(root2);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        welcome.setText("Welcome");
        window.show();        
    }
    
 
}
