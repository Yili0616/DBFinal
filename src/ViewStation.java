import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewStation {
    public static void display() throws Exception {
        Parent root = FXMLLoader.load(ManageStation.class.getResource("ViewStation.fxml"));


        Stage window = new Stage();

        Scene scene = new Scene(root, 600, 500);

        window.setTitle("View Station");
        window.setScene(scene);
        window.show();
    }
}
