import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {

	public static void main(String[] args) {
		JsonController.getInstance().loadTextsJson();
		launch(args);
	}

	@Override public void start(Stage primaryStage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("assets/ui/launcher.fxml"));
		Parent root = fxmlLoader.load();
		Stage stage = new Stage();

		stage.setTitle("RELICTUS Launcher");
		stage.setScene(new Scene(root));
		stage.setResizable(false);
		stage.centerOnScreen();
		stage.show();
	}
}
