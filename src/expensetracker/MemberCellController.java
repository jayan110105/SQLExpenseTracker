package expensetracker;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

public class MemberCellController extends ListCell<Members>
{
    private FXMLLoader mLLoader;

    @FXML
    private Label Group_Member;

    @FXML
    private AnchorPane Cell;
    
    @Override
    protected void updateItem(Members member, boolean empty) 
    {
        super.updateItem(member, empty);

        if(empty || member == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("GroupMemberCell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            Group_Member.setText(member.name);

            setText(null);
            setGraphic(Cell);
        }
    }
}
