package expensetracker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Groups 
{
    Statement stmt;
    Connection con;
    
    public String name;
    public String member;
    public String amount;

    public Groups(String name)
    {
        this.name = name;
        try 
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system",AppGlobal.password);  
            stmt=con.createStatement(); 

            ResultSet rs = stmt.executeQuery("select TOTALMEMBERS,TOTAL_EXPENSE from Groups where GROUPNAME = '"+name+"'"); 
            while (rs.next()) 
            {
                member = rs.getInt(1)+" Members";
                amount = "-₹ "+rs.getInt(2);
            }
            
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }  
    }
    
}
