import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Manager {
    private static Stage window;

    public static void display() throws Exception {
        Parent root = FXMLLoader.load(ManageStation.class.getResource("Manage.fxml"));

        window = new Stage();

        Scene scene = new Scene(root, 600, 500);

        window.setTitle("Manager");
        window.setScene(scene);
        window.show();

    }
    public void manageStationOnAction(ActionEvent actionEvent) {
        try {
            ManageStation.display();

        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    //test

    public void logout(ActionEvent actionEvent) {
        window.close();

    }

}
