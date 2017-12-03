import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.sql.ResultSet;

public class breezeCardManagement {
    private static Stage window;
    @FXML
    private static TableView tableView;


    private static ObservableList<ObservableList> data;
    public static void display() throws Exception {
        Parent root = FXMLLoader.load(ManageStation.class.getResource("breezeCardManagement.fxml"));

        try {
            ConnectionConfig con = new ConnectionConfig();
            ResultSet rs = con.getResult("select * from Breezecard");
            data = FXCollections.observableArrayList();
            while(rs.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added "+row );
                data.add(row);

            }

            //FINALLY ADDED TO TableView
            System.out.println(tableView == null);
            tableView.setItems(data);

            window = new Stage();

            Scene scene = new Scene(root, 600, 500);

            window.setTitle("Breeze Card Management");
            window.setScene(scene);
            window.show();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
