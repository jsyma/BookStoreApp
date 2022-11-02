
package coe528fxml;
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
import javafx.stage.Stage;

public class CustomerCostScreenController implements Initializable {
   @FXML
   private Label points;
   @FXML
   private Label status;
   @FXML
   private Label cost;
        
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
 
    
    
    @FXML
    public void changeToLoginScreen(ActionEvent event) throws Exception{
        Parent root2 = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene2 = new Scene(root2);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();    
    }
}
