package expensetracker;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.*;  

public class LogInController implements Initializable
{
    @FXML
    private TextField usr;

    @FXML
    private PasswordField pass;

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
            "jdbc:oracle:thin:@localhost:1521:xe","system","Jayan2005");  
            stmt=con.createStatement();  
        }
        catch(Exception e){ System.out.println(e);}  

    }

    

    public void LogIn(ActionEvent event) throws IOException
    {
        Error.setText("");
        String user_name = usr.getText();
        String password = pass.getText();
        int id = 0;
        //System.out.println(user_name+", "+password);
        try
        {
            ResultSet rs = stmt.executeQuery("select userid from UserTable where Username='"+user_name+"' and Password='"+password+"'"); 
            while(rs.next())
            id  = rs.getInt(1);
            if(id == 0)
            {
                Error.setText("Invalid User Name or Password !!");
            }
            else
            {
                AppGlobal.CurrentUserId = id;
                //FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Expenses.fxml"));
                root = loader.load();	
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            System.out.println(id+" "+user_name);
        }
        catch(Exception e){ System.out.println(e);}  
    }
    public void back(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("expensetracker.fxml"));
        root = loader.load();	
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
