/*
Login page
 */

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
    public static void display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Login");

        //Form
        TextField nameInput = new TextField();
        TextField password = new TextField();
        Button button = new Button("submit");

        button.setOnAction((ActionEvent e) -> {
            String name = nameInput.getText();
            String pass = password.getText();
            System.out.println(name);
            System.out.println(pass);
            ConnectionConfig con = new ConnectionConfig();
            System.out.println("Select Password from USER where Username = "  + "'" + name + "'" );
            ResultSet rs = con.getResult("Select Password from USER where Username = " + "'" + name + "'" );
            try {
                while (rs.next()) {

                    MessageDigest m=MessageDigest.getInstance("MD5");
                    m.update(pass.getBytes(),0,pass.length());
                    String myHash = String.format("%032x", new BigInteger(1, m.digest()));
//                    //String myHash = new BigInteger(1,m.digest()).toString(16);
//                    System.out.println("MD5: "+ myHash.trim().length());
//
//                    System.out.println(rs.getString("Password").length());
//                    System.out.println(myHash.equals(rs.getString("Password")));
                    if (myHash.equals(rs.getString("Password").trim())) {
                        System.out.println("correct");
                    } else  {
                        System.out.println("Please try again!");
                    }
//                    System.out.println("password encoded" + rs.getString("Password"));
                }
            } catch (Exception f) {
                System.err.println("Exception: " + f.getMessage());

            }
            con.close();

        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(nameInput,password, button);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 300, 250);
        window.setScene(scene);
        window.showAndWait();
    }
}
