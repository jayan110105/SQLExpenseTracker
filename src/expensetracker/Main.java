package expensetracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("expensetracker.fxml"));
        primaryStage.setTitle("EXPENSE TRACKER");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("img/icons-expense-tracker2.png")));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
