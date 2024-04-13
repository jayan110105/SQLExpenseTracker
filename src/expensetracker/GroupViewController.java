package expensetracker;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.scene.Node;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class GroupViewController implements Initializable
{
    @FXML
    private ListView<Groups> listView;

    private ObservableList<Groups> GroupsObservableList;

    Statement stmt;
    Connection con;

    private Stage stage;
    private Scene scene;
    private Parent root;


    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        GroupsObservableList = FXCollections.observableArrayList();
        try{   
            Class.forName("oracle.jdbc.driver.OracleDriver");  
            con=DriverManager.getConnection(  
            "jdbc:oracle:thin:@localhost:1521:xe","system","Jayan2005");  
            stmt = con.createStatement();  

            ResultSet rs = stmt.executeQuery("select groupname from groupmember,groups where userid="+AppGlobal.CurrentUserId+" and groupmember.groupid = groups.groupid");
            while(rs.next())
            {
                GroupsObservableList.add(new Groups(rs.getString(1)));
            } 
        }
        catch(Exception e){ System.out.println(e);}
        
        listView.setItems(GroupsObservableList);
        listView.setCellFactory(GroupListView -> new GroupCellController());
    }

    public void add(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateGroup.fxml"));
        root = loader.load();	
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void expenses(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Expenses.fxml"));
        root = loader.load();	
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void overview(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("overview.fxml"));
        root = loader.load();	
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
}
