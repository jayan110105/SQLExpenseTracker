package expensetracker;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

public class CategoryCellController extends ListCell<Category>
{
    private FXMLLoader mLLoader;

    @FXML
    private Label Amount;

    @FXML
    private AnchorPane Cell;

    @FXML
    private Label category;

    @FXML
    private Label percent;
    
    @Override
    protected void updateItem(Category categ, boolean empty) 
    {
        super.updateItem(categ, empty);

        if(empty || categ == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("CategoryCell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            Amount.setText("-₹"+categ.amount);
            category.setText(categ.name);
            percent.setText(categ.percent);
            //Amount.setText(String.valueOf(Transaction.Amount));

            setText(null);
            setGraphic(Cell);
        }
    }
}
