package expensetracker;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

public class TransactionCellController extends ListCell<transaction>
{

    @FXML
    private Label AmountPos;
    @FXML
    private Label AmountNeg;

    @FXML
    private Label DateLbl;

    @FXML
    private Label Description;

    @FXML
    private Label category;

    @FXML
    private AnchorPane Cell;

    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(transaction Transaction, boolean empty) 
    {
        super.updateItem(Transaction, empty);

        if(empty || Transaction == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("TransactionCell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
            String formattedDate = formatter.format(Transaction.transaction_date);
            DateLbl.setText(formattedDate);
            Description.setText(Transaction.Description);
            category.setText(Transaction.Category);
            if(Transaction.Amount<0)
            {
                AmountPos.setText("₹"+String.valueOf(Math.abs(Transaction.Amount)));
                AmountNeg.setText("");
            }
            else
            {
                AmountNeg.setText("-₹"+String.valueOf(Transaction.Amount));
                AmountPos.setText("");
            }
            //Amount.setText(String.valueOf(Transaction.Amount));


            setText(null);
            setGraphic(Cell);
        }
    }

}
