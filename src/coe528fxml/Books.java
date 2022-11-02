
package coe528fxml;

import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;

    public class Books{
        private  SimpleStringProperty bookName;
        private  SimpleStringProperty bookPrice;
        private String price;
        private String name;
        private CheckBox select;
        private static double cost;
        private static ArrayList<Books> cart = new ArrayList<Books>();
        
        public Books(String bookName, String bookPrice){
            this.bookName = new SimpleStringProperty(bookName);
            this.bookPrice = new SimpleStringProperty(bookPrice);
            this.select = new CheckBox();
        }
        
        public String getBookName(){
            return bookName.get();
        }
        public String getBookPrice(){
            return bookPrice.get();
        }
        public void setBookPrice(String price){
            this.bookPrice = new SimpleStringProperty(price);
        }
        public void setBookName(String name){
            this.bookName = new SimpleStringProperty(name);
        }
        public String getPrice() {
            return price;
        }
        public void setPrice(String price) {
            this.price = price;
        }
        public CheckBox getSelect(){
            return select;
        }
        public void setSelect(CheckBox c){
            this.select = c;
        }  
        public static void setCost(double a){
            cost= a;
        }
        public static double getCost(){
            return cost;
        }
        public static void addCart(Books book){
            cart.add(book);
        }
        public static ArrayList<Books> getCart(){
            return cart;
        }
    }