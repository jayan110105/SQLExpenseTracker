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
import javafx.scene.chart.PieChart;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class OverviewController implements Initializable
{
    Statement stmt;
    Connection con;

    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    private PieChart Pie;

    @FXML
    private ListView<Category> listView;

    private ObservableList<Category> CategoryObservableList;

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(); 
        try{   
            Class.forName("oracle.jdbc.driver.OracleDriver");  
            con=DriverManager.getConnection(  
            "jdbc:oracle:thin:@localhost:1521:xe","system",AppGlobal.password);  
            stmt=con.createStatement();  

            ResultSet rs = stmt.executeQuery("select categoryname,sum(amount) from personalexpenses p,expensecategory e where e.categoryid=p.categoryid and userid = "+AppGlobal.CurrentUserId+" group by p.categoryid,categoryname"); 
            while(rs.next())
            {
                pieChartData.add(new PieChart.Data(rs.getString(1), Math.abs(rs.getDouble(2))));
            }

            Pie.setData(pieChartData);
            Pie.setLegendVisible(false);

            Double Total = 1.0;
            rs = stmt.executeQuery("select sum(amount) from personalexpenses where userid="+AppGlobal.CurrentUserId);
            while(rs.next())
            Total = Math.abs(rs.getDouble(1));

            CategoryObservableList = FXCollections.observableArrayList();

            rs = stmt.executeQuery("select categoryname,sum(amount) from personalexpenses p,expensecategory e where e.categoryid=p.categoryid and userid = "+AppGlobal.CurrentUserId+" group by p.categoryid,categoryname");
            while(rs.next())
            {
                CategoryObservableList.add(new Category(rs.getString(1), String.format("%.2f", (Math.abs(rs.getDouble(2))/Total)*100)+"%",Math.abs(rs.getDouble(2))));
            } 
        }
        catch(Exception e){ System.out.println(e);} 

        listView.setItems(CategoryObservableList);
        listView.setCellFactory(categoryListView -> new CategoryCellController());
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
    public void group(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Group.fxml"));
        root = loader.load();	
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void more(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("More.fxml"));
        root = loader.load();	
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
