
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


public class OwnerBooksScreenController implements Initializable {
    
    @FXML
    public TableView<Books> bookView;
    @FXML
    public TableColumn<Books, String> names;
    @FXML
    public TableColumn<Books, String> prices;
    
    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldPrice;
    @FXML
    private Label warning;
    
    ObservableList<Books> list = FXCollections.observableArrayList();
    
    @FXML
    private void addButton(ActionEvent event) {
        if(textFieldPrice.getText().isEmpty() || textFieldName.getText().isEmpty()){
            warning.setText("Please input a Name and a Price");
        }
        else{
            addBook();
        }
    }
    
    private void addBook(){
        String name = textFieldName.getText();
        String price = textFieldPrice.getText();
        list.addAll(new Books(name, price));
        try{
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("books.txt", true)));
            writer.println(name + ", " + price);
            writer.flush();
            writer.close();
        }
        catch (IOException e){
            System.out.println(e);
        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol();
        list = fromTxt();
        bookView.setItems(list);
    }    
    
    private void initCol(){
        names.setCellValueFactory(new PropertyValueFactory<>("BookName"));
        prices.setCellValueFactory(new PropertyValueFactory<>("BookPrice"));
    }
    
    
    
    
    @FXML
    public void delete(ActionEvent event)throws FileNotFoundException, IOException{
        File readFile = new File("books.txt");
        File buff = new File("bufferBooks.txt");
        
        BufferedReader reader = new BufferedReader(new FileReader(readFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(buff));
        
        ObservableList<Books> allBooks, singleBooks;
        allBooks = bookView.getItems();
        singleBooks = bookView.getSelectionModel().getSelectedItems();
        Books bookToRemove = bookView.getSelectionModel().getSelectedItem();
        singleBooks.forEach(allBooks::remove);

        String currentLine;
        String lineToRemove = bookToRemove.getBookName();
        //while loop will write to a buffer file all the lines that arent the lineToDelete, and that buffer file is renamed to book.txt
        //and the old file is deleted
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
    
    public ObservableList<Books> fromTxt(){
        String name, price;
        
        try{
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
    
}
/*
for some reason, my code onyl works when you delete from the bottom of the tableview
if you delete from the middle or top, it does not work properly
*/