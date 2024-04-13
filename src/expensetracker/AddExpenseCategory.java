package expensetracker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;  

public class AddExpenseCategory implements Initializable
{
    @FXML
    private TextField category; 

    @FXML
    private Label Error; 

    Statement stmt;
    Connection con;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        try{   
            Class.forName("oracle.jdbc.driver.OracleDriver");  
            con=DriverManager.getConnection(  
            "jdbc:oracle:thin:@localhost:1521:xe","system",AppGlobal.password);  
            stmt=con.createStatement();  
        }
        catch(Exception e){ System.out.println(e);}  
    }

    public void Add(ActionEvent event) throws IOException
    {
        Error.setText("");
        String  category_name = category.getText();
        int id = 0;
        //System.out.println(user_name+", "+password);
        try
        {
            ResultSet rs = stmt.executeQuery("select max(CategoryId) from ExpenseCategory"); 
            while(rs.next())
            id  = rs.getInt(1)+1;

            PreparedStatement ps =  con.prepareStatement("INSERT INTO ExpenseCategory VALUES (?, ?)");
            ps.setInt(1, id);
            ps.setString(2, category_name);
            ps.executeUpdate();

            System.out.println("Category Added !!");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
            root = loader.load();	
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("Home.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
            // Error.setText("Please pick a User name !!");
        }
        catch(java.sql.SQLIntegrityConstraintViolationException e)
        {
            if(category_name == "")
            Error.setText("Please enter a Category name !!");
            else
            Error.setText("Category already exists !!");
        }
        catch(Exception e)
        { 
            System.out.println(e);
        }  
    }
    public void back(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
        root = loader.load();	
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Home.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
