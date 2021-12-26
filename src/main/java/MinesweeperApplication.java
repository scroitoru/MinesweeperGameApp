import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class MinesweeperApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/minesweeper_application.fxml")));

        Scene scene = new Scene(root, 800, 600);

        stage.setTitle("Minesweeper");
        stage.setScene(scene);
        stage.show();
    }
}
