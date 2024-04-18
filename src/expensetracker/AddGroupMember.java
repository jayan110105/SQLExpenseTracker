package expensetracker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class AddGroupMember
{
    Statement stmt;
    Connection con;

    @FXML
    private TextField Name;

    @FXML
    private Label Error;

    private Stage stage;
    private Scene scene;
    private Parent root;

    String  group_name;

    public void Add(ActionEvent event) throws IOException
    {
        int gid = 0;
        int mid = 1;
        int uid = 0;
        // String  group_name = Groups.getValue();
        String user_name = Name.getText();
        Error.setText("");
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");  
            con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system",AppGlobal.password);  
            stmt = con.createStatement();  

            ResultSet rs = stmt.executeQuery("select UserId from UserTable where Username='"+user_name+"'");
            while(rs.next())
            uid  = rs.getInt(1);

            if(uid != 0)
            {
                rs = stmt.executeQuery("select GroupId from Groups where GroupName='"+group_name+"'");
                while(rs.next())
                gid  = rs.getInt(1);

                rs = stmt.executeQuery("select max(MemberId) from GroupMember where GroupId = "+gid+" group by GroupId"); 
                while(rs.next())
                mid  = rs.getInt(1)+1;

                System.out.println("INSERT INTO GroupMember VALUES ("+gid+","+uid+","+mid+")");

                PreparedStatement ps =  con.prepareStatement("INSERT INTO GroupMember VALUES (?,?,?)");
                ps.setInt(1, gid);
                ps.setInt(2, uid);
                ps.setInt(3, mid);

                ps.executeUpdate();

                ps =  con.prepareStatement("update groups set TotalMembers = ? where groupid =  ?");
                ps.setInt(1, mid);
                ps.setInt(2, gid);

                ps.executeUpdate();

                System.out.println("Member Added !!");

                FXMLLoader loader = new FXMLLoader(getClass().getResource("GroupMembers.fxml"));
                root = loader.load();	
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                GroupMemberController controller = loader.getController();
                controller.setName(group_name);
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                // Error.setText("Please pick a User name !!");
            }
            else
            {
                Error.setText("User Name doesn't exist !!");
            }
        }
        catch(java.sql.SQLIntegrityConstraintViolationException e)
        { 
            Error.setText("User already in the group !!");
        } 
        catch(Exception e)
        { 
            System.out.println(e);
        }  
    }
    public void setGroup(String name)
    {
        group_name = name;
    }
    public void back(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GroupMembers.fxml"));
        root = loader.load();	
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        GroupMemberController controller = loader.getController();
        controller.setName(group_name);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
