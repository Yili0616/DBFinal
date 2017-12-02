import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CreateStation {

    public static void display() throws Exception {
        Parent root = FXMLLoader.load(CreateStation.class.getResource("CreateStation.fxml"));


        Stage window = new Stage();

        Scene scene = new Scene(root, 600, 500);

        window.setTitle("Create Station");
        window.setScene(scene);
        window.show();
    }
}


