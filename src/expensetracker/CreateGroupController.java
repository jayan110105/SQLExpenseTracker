package expensetracker;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateGroupController implements Initializable
{
    Statement stmt;
    Connection con;

    @FXML
    private TextField GroupName;

    @FXML
    private TextField Description;

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
    public void Create(ActionEvent event) throws IOException
    {
        int id = 0;
        String  group_name = GroupName.getText();
        String desc = Description.getText();
        try
        {
            ResultSet rs = stmt.executeQuery("select max(GroupId) from Groups");
            while(rs.next())
            id  = rs.getInt(1)+1;

            PreparedStatement ps =  con.prepareStatement("INSERT INTO Groups VALUES (?,?,?)");
            ps.setInt(1, id);
            ps.setString(2, group_name);
            ps.setString(3, desc);
            ps.executeUpdate();

            PreparedStatement ps2 =  con.prepareStatement("INSERT INTO GroupMember VALUES (?,?,?)");
            ps2.setInt(1, id);
            ps2.setInt(2, AppGlobal.CurrentUserId);
            ps2.setInt(3, 1);
            ps2.executeUpdate();
            
            System.out.println("Group Created !!");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Expenses.fxml"));
            root = loader.load();	
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
        root = loader.load();	
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
