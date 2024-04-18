package expensetracker;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Node;

public class GroupCellController extends ListCell<Groups>
{
    private FXMLLoader mLLoader;

    @FXML
    private Label Amount;

    @FXML
    private AnchorPane Cell;

    @FXML
    private Label Group_Name;

    @FXML
    private Label no_member;


    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @Override
    protected void updateItem(Groups group, boolean empty) 
    {
        super.updateItem(group, empty);

        if(empty || group == null) {
            setText(null);
            setGraphic(null);
        } 
        else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("GroupCell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } 
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Group_Name.setText(group.name); 
            no_member.setText(group.member);
            Amount.setText(group.amount);
 
            setText(null);
            setGraphic(Cell);

            this.setOnMouseClicked(event -> 
            {
                // Call your function here with the item data
                try 
                {
                    myFunction(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void myFunction(MouseEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GroupMembers.fxml"));
        root = loader.load();	
        GroupMemberController controller = loader.getController();
        controller.setName(Group_Name.getText());
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
