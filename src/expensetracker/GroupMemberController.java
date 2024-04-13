package expensetracker;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.scene.Node;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class GroupMemberController
{
    @FXML
    private Label group_name;

    private ObservableList<Members> MembersObservableList;

    Statement stmt;
    Connection con;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ListView<Members> listView;

    public void setName(String name)
    {
        group_name.setText(name);

        MembersObservableList = FXCollections.observableArrayList();
        try{   
            Class.forName("oracle.jdbc.driver.OracleDriver");  
            con=DriverManager.getConnection(  
            "jdbc:oracle:thin:@localhost:1521:xe","system","Jayan2005");  
            stmt = con.createStatement();  

            ResultSet rs = stmt.executeQuery("select username from usertable u,groupmember g,groups gr where gr.groupid = g.groupid and u.userid = g.userid and groupname = '"+name+"'");
            while(rs.next())
            {
                MembersObservableList.add(new Members(rs.getString(1)));
            } 
        }

        catch(Exception e){ System.out.println(e);}

        listView.setItems(MembersObservableList);
        listView.setCellFactory(MemberListView -> new MemberCellController());
    }

    public void AddGroupMember(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddGroupMembers.fxml"));
		root = loader.load();	
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        AddGroupMember controller = loader.getController();
        controller.setGroup(group_name.getText());
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void AddGroupExpense(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddGroupExpense.fxml"));
		root = loader.load();	
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        AddGroupExpense controller = loader.getController();
        controller.setGroup(group_name.getText());
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void back(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Group.fxml"));
        root = loader.load();	
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

