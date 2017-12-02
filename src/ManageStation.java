import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXML;

public class ManageStation {


    public static void display()throws Exception{
        Parent root = FXMLLoader.load(ManageStation.class.getResource("ManageStation.fxml"));


        Stage window = new Stage();

        Scene scene = new Scene(root, 600, 500);

        window.setTitle("Manage Station");
        window.setScene(scene);
        window.show();
    }


    public void createStationOnAction(ActionEvent actionEvent) {
        try {
            CreateStation.display();

        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    public void viewStationOnAction(ActionEvent actionEvent) {
        try {
            ViewStation.display();

        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}
