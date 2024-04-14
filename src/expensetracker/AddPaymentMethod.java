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

public class AddPaymentMethod implements Initializable
{
    Statement stmt;
    Connection con;

    @FXML
    private TextField Method;

    @FXML
    private Label Error;

    private Stage stage;
    private Scene scene;
    private Parent root;   

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        try
        {   
            Class.forName("oracle.jdbc.driver.OracleDriver");  
            con=DriverManager.getConnection(  
            "jdbc:oracle:thin:@localhost:1521:xe","system",AppGlobal.password);  
            stmt=con.createStatement();  
        }
        catch(Exception e){ System.out.println(e);}  
    }

    public void Add(ActionEvent event) throws IOException
    {
        int id = 0;
        String method_name = Method.getText();
        Error.setText("");
        try
        {
            ResultSet rs = stmt.executeQuery("select max(MethodId) from PaymentMethod");
            while(rs.next())
            id  = rs.getInt(1)+1;

            PreparedStatement ps =  con.prepareStatement("INSERT INTO PaymentMethod VALUES (?,?)");
            ps.setInt(1, id);
            ps.setString(2, method_name);
            ps.executeUpdate();

            System.out.println("Payment Method Added !!");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
            root = loader.load();	
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("Home.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
            // Error.setText("Please pick a User name !!");
        }
        catch(Exception e)
        { 
            System.out.println(e);
        }  
    }
    public void back(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("More.fxml"));
        root = loader.load();	
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
