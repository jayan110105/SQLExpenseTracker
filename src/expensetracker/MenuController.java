package expensetracker;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MenuController implements Initializable
{
    private Stage stage;
    private Scene scene;
    private Parent root;

    Font myFont ;
    //Font myFont = Font.loadFont(getClass().getResourceAsStream("src/expensetracker/Font/Helvetica Neue Condensed Bold.ttf"), 14);


    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        myFont = Font.loadFont(getClass().getResourceAsStream(
        "D:/ExpenseTracker/Expense_Tracker/src/expensetracker/Font/Helvetica Neue Condensed Bold.ttf"),14);
    }
    public void AddExpenseCategory(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddExpenseCategory.fxml"));
		root = loader.load();	
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Category.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    public void AddPersonalExpense(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPersonalExpense.fxml"));
		root = loader.load();	
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("PersonalExpense.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void CreateGroup(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateGroup.fxml"));
		root = loader.load();	
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Groups.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void AddGroupMember(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddGroupMembers.fxml"));
		root = loader.load();	
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void AddGroupExpense(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddGroupExpense.fxml"));
		root = loader.load();	
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("GroupExpense.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void AddPaymentMethod(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPaymentMethod.fxml"));
		root = loader.load();	
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Payment.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    public void ViewExpenses(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Expenses.fxml"));
		root = loader.load();	
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
