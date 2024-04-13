package expensetracker;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

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
import javafx.scene.Node;

public class ExpenseViewController implements Initializable
{

    @FXML
    private ListView<transaction> listView;

    private ObservableList<transaction> transactionsObservableList;

    Statement stmt;
    Connection con;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        transactionsObservableList = FXCollections.observableArrayList();
        try{   
            Class.forName("oracle.jdbc.driver.OracleDriver");  
            con=DriverManager.getConnection(  
            "jdbc:oracle:thin:@localhost:1521:xe","system","Jayan2005");  
            stmt = con.createStatement();  

            ResultSet rs = stmt.executeQuery("select expense_date,description,categoryname,amount from PersonalExpenses,ExpenseCategory where PersonalExpenses.categoryid = ExpenseCategory.categoryid and userid = "+AppGlobal.CurrentUserId);
            while(rs.next())
            {
                transactionsObservableList.add(new transaction(rs.getDate(1), rs.getString(2), rs.getString(3), rs.getDouble(4)));
            } 
        }
        catch(Exception e){ System.out.println(e);}
        
        listView.setItems(transactionsObservableList);
        listView.setCellFactory(transactionsListView -> new TransactionCellController());
    }
    public void Add(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPersonalExpense.fxml"));
        root = loader.load();	
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void Group(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Group.fxml"));
        root = loader.load();	
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void Overview(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("overview.fxml"));
        root = loader.load();	
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
