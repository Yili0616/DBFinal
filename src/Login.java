/*
Login page
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.ResultSet;



public class Login {

    @FXML
    private TextField Username;
    @FXML
    private TextField Password;
    @FXML
    private Button Login;

    private static Stage window;

    public static void display() throws Exception{
        Parent root = FXMLLoader.load(Login.class.getResource("Login.fxml"));
        window = new Stage();

        Scene scene = new Scene(root, 600, 500);

        window.setTitle("Login");
        window.setScene(scene);
        window.show();

//        Stage window = new Stage();
//        window.initModality(Modality.APPLICATION_MODAL);
//        window.setTitle("Login");
//
//        //Form
//        TextField nameInput = new TextField();
//        TextField password = new TextField();
//        Button button = new Button("submit");
//
//        button.setOnAction((ActionEvent e) -> {
//            String name = nameInput.getText();
//            String pass = password.getText();
//            System.out.println(name);
//            System.out.println(pass);
//            ConnectionConfig con = new ConnectionConfig();
//            System.out.println("Select Password from USER where Username = "  + "'" + name + "'" );
//            ResultSet rs = con.getResult("Select Password from USER where Username = " + "'" + name + "'" );
//            try {
//                if (!rs.next()) {
//                    System.out.println("The account does not exist ");
//                } else {
//                    rs.beforeFirst();
//                    while (rs.next()) {
//
//                        MessageDigest m = MessageDigest.getInstance("MD5");
//                        m.update(pass.getBytes(), 0, pass.length());
//                        String myHash = String.format("%032x", new BigInteger(1, m.digest()));
////                    //String myHash = new BigInteger(1,m.digest()).toString(16);
////                    System.out.println("MD5: "+ myHash.trim().length());
////
////                    System.out.println(rs.getString("Password").length());
////                    System.out.println(myHash.equals(rs.getString("Password")));
//                        if (myHash.equals(rs.getString("Password").trim())) {
//                            System.out.println("correct");
//                        } else {
//                            System.out.println("Please try again!");
//                        }
////                    System.out.println("password encoded" + rs.getString("Password"));
//                    }
//                }
//            }catch (Exception f) {
//                System.err.println("Exception: " + f.getMessage());
//
//            }
//            con.close();
//
//        });
//
//
//        VBox layout = new VBox(10);
//        layout.getChildren().addAll(nameInput,password, button);
//        layout.setAlignment(Pos.CENTER);
//        Scene scene = new Scene(layout, 300, 250);
//        window.setScene(scene);
//        window.showAndWait();
    }

    public void loginOnAction(ActionEvent actionEvent) {
        String name = Username.getText();
        String pass = Password.getText();
        System.out.println(name);
        System.out.println(pass);
        ConnectionConfig con = new ConnectionConfig();
        System.out.println("Select Password , IsAdmin from USER where Username = " + "'" + name + "'");
        ResultSet rs = con.getResult("Select Password, IsAdmin from USER where Username = " + "'" + name + "'");
        try {
            if (!rs.next()) {
                System.out.println("The account does not exist ");
            } else {
                rs.beforeFirst();
                while (rs.next()) {
                    System.out.println("I am here");
                    MessageDigest m = MessageDigest.getInstance("MD5");
                    m.update(pass.getBytes(), 0, pass.length());
                    String myHash = String.format("%032x", new BigInteger(1, m.digest()));
//                    //String myHash = new BigInteger(1,m.digest()).toString(16);
//                    System.out.println("MD5: "+ myHash.trim().length());
//
//                    System.out.println(rs.getString("Password").length());
//                    System.out.println(myHash.equals(rs.getString("Password")));
                    if (myHash.equals(rs.getString("Password").trim())) {
                        String isAdmin = rs.getString("IsAdmin").trim();
                        System.out.println(isAdmin.length()+"jere");
                        if (isAdmin.equals("1")) {
                            System.out.println("here");
                            Manager.display();
                            window.close();

                        } else {

                        }
                    } else {
                        System.out.println("Np");
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Error");
                        alert.setHeaderText("Incorrect UserName / Password");
                        alert.showAndWait();
                    }
//                    System.out.println("password encoded" + rs.getString("Password"));
                }
            }
        } catch (Exception f) {
            System.err.println("Exception: " + f.getMessage());

        }
        con.close();

    }


}
