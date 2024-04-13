package expensetracker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddGroupExpense implements Initializable
{
    Statement stmt;
    Statement stmt2;
    Connection con;

    @FXML
    private ChoiceBox<String> GroupMember;

    @FXML
    private ChoiceBox<String> Category;

    @FXML
    private TextField Amount;

    @FXML
    private TextField Description;

    @FXML
    private DatePicker ExpenseDate;

    @FXML
    private Label Error;

    private Stage stage;
    private Scene scene;
    private Parent root;

    String  group_name;

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        Category.getSelectionModel().select("Category");
        GroupMember.getSelectionModel().select("Paid by");

        try{   
            Class.forName("oracle.jdbc.driver.OracleDriver");  
            con=DriverManager.getConnection(  
            "jdbc:oracle:thin:@localhost:1521:xe","system",AppGlobal.password);  
            stmt=con.createStatement(); 
            stmt2=con.createStatement(); 

            ResultSet rs = stmt.executeQuery("select CategoryName from ExpenseCategory"); 
            while(rs.next())
            Category.getItems().add(rs.getString(1));
        }
        catch(Exception e){ System.out.println(e);}  
    }

    public void setGroup(String name)
    {
        group_name = name;

        try
        {
            ResultSet rs = stmt.executeQuery("select username from usertable u,groupmember gm,groups g where gm.groupid = g.groupid and groupname='"+group_name+"' and u.userid = gm.userid"); 
            while(rs.next())
            {
                GroupMember.getItems().add(rs.getString(1));
            }
        }
        catch(Exception e){ System.out.println(e);}  
    }

    public void Add(ActionEvent event) throws IOException
    {
        int id = 0;
        int pid = 0;
        int gid = 0;
        int uid = 0;
        int mid = 0;
        int cat_id = 0;
        int no_members = 0;
        int uid2 = 0;
        String name = "";
        String gdesc =""; 
        Date exDate = Date.valueOf(java.time.LocalDate.now());
        String  category_name = Category.getValue();
        String desc = Description.getText();
        LocalDate expense_date = ExpenseDate.getValue();
        Error.setText("");
        try
        {
            Double amnt = Double.parseDouble(Amount.getText());

            ResultSet rs = stmt.executeQuery("select max(ExpenseId) from GroupExpenses");
            while(rs.next())
            id  = rs.getInt(1)+1;

            rs = stmt.executeQuery("select GroupId from Groups where GroupName = '"+group_name+"'");
            while(rs.next())
            gid  = rs.getInt(1);

            rs = stmt.executeQuery("select UserID from UserTable where Username = '"+GroupMember.getValue()+"'");
            while(rs.next())
            uid  = rs.getInt(1);

            rs = stmt.executeQuery("select MemberId from GroupMember where GroupId = "+gid+" and UserId = "+uid);
            while(rs.next())
            mid  = rs.getInt(1);

            rs = stmt.executeQuery("select CategoryId from ExpenseCategory where CategoryName = '"+category_name+"'"); 
            while(rs.next())
            cat_id  = rs.getInt(1);



            PreparedStatement ps =  con.prepareStatement("INSERT INTO GroupExpenses VALUES (?,?,?,?,?,?,?)");
            ps.setInt(1, id);
            ps.setInt(2, gid);
            ps.setInt(3, mid);
            ps.setInt(4, cat_id);
            ps.setDouble(5, amnt);
            ps.setString(6, desc);
            ps.setDate(7, Date.valueOf(expense_date));
            ps.executeUpdate();

            System.out.println("Group Expense Added !!");

            rs = stmt.executeQuery("select max(memberid) from groupmember where groupid = "+gid); 
            while(rs.next())
            no_members  = rs.getInt(1);

            rs = stmt.executeQuery("select userid from groupmember where groupid = "+gid); 
            while(rs.next())
            {
                
                uid2  = rs.getInt(1);
                if(uid2 != uid)
                {
                    ResultSet rs2 = stmt2.executeQuery("select max(ExpenseId) from PersonalExpenses");
                    while(rs2.next())
                    pid  = rs2.getInt(1)+1;

                    rs2 = stmt2.executeQuery("select CategoryId from GroupExpenses where EXPENSEID = "+id); 
                    while(rs2.next())
                    cat_id  = rs2.getInt(1);

                    rs2 = stmt2.executeQuery("select USERNAME from usertable where USERID = "+uid); 
                    while(rs2.next())
                    name  = rs2.getString(1);

                    rs2 = stmt2.executeQuery("select DESCRIPTION,expensedate from GroupExpenses where EXPENSEID = "+id); 
                    while(rs2.next())
                    {
                        gdesc  = rs2.getString(1);
                        exDate = rs2.getDate(2);
                    }

                    PreparedStatement ps2 =  con.prepareStatement("INSERT INTO PersonalExpenses VALUES (?,?,?,?,?,?,?)");
                    ps2.setInt(1, pid);
                    ps2.setInt(2, uid2);
                    ps2.setDouble(3, amnt/no_members);
                    ps2.setInt(4, cat_id);
                    ps2.setString(5, "You owe "+name+" for "+gdesc);
                    ps2.setDate(6, exDate);
                    ps2.setNull(7, Types.INTEGER);
                    ps2.executeUpdate();
                }
                else
                {
                    ResultSet rs2 = stmt2.executeQuery("select max(ExpenseId) from PersonalExpenses");
                    while(rs2.next())
                    pid  = rs2.getInt(1)+1;

                    rs2 = stmt2.executeQuery("select CategoryId from GroupExpenses where EXPENSEID = "+id); 
                    while(rs2.next())
                    cat_id  = rs2.getInt(1);

                    rs2 = stmt2.executeQuery("select DESCRIPTION,expensedate from GroupExpenses where EXPENSEID = "+id); 
                    while(rs2.next())
                    {
                        gdesc  = rs2.getString(1);
                        exDate = rs2.getDate(2);
                    }

                    PreparedStatement ps2 =  con.prepareStatement("INSERT INTO PersonalExpenses VALUES (?,?,?,?,?,?,?)");
                    ps2.setInt(1, pid);
                    ps2.setInt(2, uid);
                    ps2.setDouble(3, -1*(amnt-(amnt/no_members)));
                    ps2.setInt(4, cat_id);
                    ps2.setString(5, "You paid "+amnt+" for "+gdesc);
                    ps2.setDate(6, exDate);
                    ps2.setNull(7, Types.INTEGER);
                    ps2.executeUpdate();
                }
            }

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
        catch(java.lang.NumberFormatException e)
        { 
            Error.setText("Enter a number for Amount");
        } 
        catch(Exception e)
        { 
            e.printStackTrace();
            System.out.println(e);
        }  
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
