package coe528fxml;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Users {
        private  SimpleStringProperty Username;
        private  SimpleStringProperty Password;
        private  SimpleIntegerProperty Points;
        private String un;
        private String pw;
        private Membership membership;
        
    /**
     *
     * @param Username
     * @param Password
     * @param Points
     */
    public Users(String Username, String Password){
            this.Username = new SimpleStringProperty(Username);
            this.Password = new SimpleStringProperty(Password);
            this.Points = new SimpleIntegerProperty(0);
            this.un = Username; 
            this.pw = Password;
            membership = new Silver();
        }
        
        public String getUsername(){
            return Username.get();
        }
        public String getPassword(){
            return Password.get();
        }
        public int getPoints(){
            return Points.get();
        }
        public void setUsername(String Username){
            this.Username = new SimpleStringProperty(Username);
        }
        public void setPassword(String Password){
            this.Password = new SimpleStringProperty(Password);
        }
        public void setPoints(int Points){//i dont expect this to be used
            this.Points = new SimpleIntegerProperty(Points);
        }
        public String getUn(){
            return un;
        }
        public String getPw(){
            return pw;
        }
         public void setMembership(double points){
            if (points < 0.0){
                System.out.println("points cannot be less than zero");
            }
            if (points < 1000.0){
                this.membership = new Silver();
            }
            if (points >= 1000.0){
                this.membership = new Gold();
            }
        }
        public Membership getMembership(){
            return membership;
        }
}
