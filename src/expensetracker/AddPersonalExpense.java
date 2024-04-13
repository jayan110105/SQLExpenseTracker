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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDate;

public class AddPersonalExpense implements Initializable
{
    Statement stmt;
    Connection con;

    @FXML
    private ChoiceBox<String> Category;

    @FXML
    private TextField Description;

    @FXML
    private DatePicker ExpenseDate;

    @FXML
    private TextField Amount;

    @FXML
    private Label Error;

    @FXML
    private ChoiceBox<String> Method;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        Category.getSelectionModel().select("Category");
        Method.getSelectionModel().select("Method");
        try{   
            Class.forName("oracle.jdbc.driver.OracleDriver");  
            con=DriverManager.getConnection(  
            "jdbc:oracle:thin:@localhost:1521:xe","system","Jayan2005");  
            stmt=con.createStatement();  

            ResultSet rs = stmt.executeQuery("select CategoryName from ExpenseCategory"); 
            while(rs.next())
            Category.getItems().add(rs.getString(1));

            rs = stmt.executeQuery("select MethodName from PaymentMethod"); 
            while(rs.next())
            Method.getItems().add(rs.getString(1));
        }
        catch(Exception e){ System.out.println(e);}  
    }
    public void Add(ActionEvent event) throws IOException
    {
        int meth_id = 0;
        int id = 0;
        int cat_id = 0;
        String  category_name = Category.getValue();
        String desc = Description.getText();
        LocalDate expense_date = ExpenseDate.getValue();
        Error.setText("");
        try
        {
            Double amnt = Double.parseDouble(Amount.getText());
            ResultSet rs = stmt.executeQuery("select max(ExpenseId) from PersonalExpenses");
            while(rs.next())
            id  = rs.getInt(1)+1;

            rs = stmt.executeQuery("select CategoryId from ExpenseCategory where CategoryName = '"+category_name+"'"); 
            while(rs.next())
            cat_id  = rs.getInt(1);

            rs = stmt.executeQuery("select MethodId from PaymentMethod where MethodName = '"+Method.getValue()+"'"); 
            while(rs.next())
            meth_id  = rs.getInt(1);

            PreparedStatement ps =  con.prepareStatement("INSERT INTO PersonalExpenses VALUES (?,?,?,?,?,?,?)");
            ps.setInt(1, id);
            ps.setInt(2, AppGlobal.CurrentUserId);
            ps.setDouble(3, amnt);
            ps.setInt(4, cat_id);
            ps.setString(5, desc);
            ps.setDate(6, Date.valueOf(expense_date));
            ps.setInt(7, meth_id);
            ps.executeUpdate();

            System.out.println("Expense Added !!");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Expenses.fxml"));
            root = loader.load();	
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
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
            System.out.println(e);
        }  
    }
    public void back(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Expenses.fxml"));
        root = loader.load();	
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
