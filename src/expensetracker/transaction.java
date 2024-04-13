package expensetracker;

import java.sql.Date;

public class transaction 
{
    public Date transaction_date ;
    public String Description;
    public String Category;
    public Double Amount;

    public transaction(Date transaction_date, String Description, String Category, Double Amount)
    {
        this.transaction_date = transaction_date;
        this.Description = Description;
        this.Category = Category;
        this.Amount = Amount;
    }
}
