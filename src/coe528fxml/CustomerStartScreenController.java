
package coe528fxml;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class CustomerStartScreenController implements Initializable {
    
    @FXML
    public TableView<Books> customerBookView;
    @FXML
    public TableColumn<Books, String>names;
    @FXML
    public TableColumn<Books, String>prices;
    @FXML
    public TableColumn<Books, String>select;
    @FXML 
    private Label welcome;
    
    
    ObservableList<Users> userList = FXCollections.observableArrayList();
    ObservableList<Books> list = FXCollections.observableArrayList();
    //initializes checkbox
    public void checkbox(){
         ObservableList<Books> allBooks = customerBookView.getItems();
         ObservableList<Books> Selected = FXCollections.observableArrayList();
         double cost= 0; 
         for (Books book : allBooks ){
            if(book.getSelect().isSelected()){
                Selected.add(book);
                Books.addCart(book);
                cost += Double.parseDouble(book.getPrice().trim().substring(1,book.getPrice().trim().length()));
             } 
           }
           Books.setCost(cost);
           allBooks.forEach(Selected::remove);
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol();
        list = fromTxt();
        customerBookView.setItems(list);
        welcome.setText("Welcome to the BookStore App");
    }
    
    private void initCol(){
        names.setCellValueFactory(new PropertyValueFactory<>("BookName"));
        prices.setCellValueFactory(new PropertyValueFactory<>("BookPrice"));
        select.setCellValueFactory(new PropertyValueFactory<>("Select"));
    }
    
 
    public ObservableList<Books> fromTxt(){
        String name, price;
        try{
            list.removeAll();
            BufferedReader reader = new BufferedReader(new FileReader("books.txt"));
            String line = reader.readLine();
            while(line != null){
                String info[] = line.split(", ");
                name = info[0];
                price = info[1];
                
                list.addAll(new Books(name, price));
                line = reader.readLine();
            }
            reader.close();
        } catch(Exception e){
            System.out.println(e);
        }
        return list;
    }
    
    @FXML
    public void buyBook(ActionEvent event) throws IOException{
        //add buy book method
        Parent root2 = FXMLLoader.load(getClass().getResource("CustomerCostScreen.fxml"));
        Scene scene2 = new Scene(root2);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
        System.out.println("In Buy Book");
    }

    @FXML
    public void buyRedeemBook(ActionEvent event) throws IOException{
        //add redeem points method
        Parent root2 = FXMLLoader.load(getClass().getResource("CustomerCostScreen.fxml"));
        Scene scene2 = new Scene(root2);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
        System.out.println("In Buy and Redeem Book");
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
