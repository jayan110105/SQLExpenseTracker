package expensetracker;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;


public class Controller {

    private Stage stage;
    private Scene scene;
    private Parent root;

    

    public void SignUp(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
		root = loader.load();	
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Login.css").toExternalForm());
        //System.out.println(scene.getStylesheets());
        stage.setScene(scene);
        stage.show();
    }
    public void LogIn(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LogIn.fxml"));
		root = loader.load();	
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Login.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
