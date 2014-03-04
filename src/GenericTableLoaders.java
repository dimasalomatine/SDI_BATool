

import CDate.CDate;

import DBCONNECT.TRS;
import DBOPERATIONS.GenericTableOperations;
import Utils.LoggerNB;


//~--- JDK imports ------------------------------------------------------------

import java.sql.*;

//~--- classes ----------------------------------------------------------------

public class GenericTableLoaders extends GenericTableOperations{
    
    /*
     * Method insertone - inserts only one row ib db uses real app connection
     */
    static public void insert1TradeDataRow(int table, java.sql.Date sqldate,
            float open, float hi, float lo, float close, float amount,String datatype) {
        String sqlstr = "INSERT INTO [" + table
                        + "] (DAY,[OPEN],HI,LO,[CLOSE],AMOUNT,[DT]) VALUES(?,?,?,?,?,?,?)";
        Connection con = BursaAnalizer_Frame.getConnectToDB(
                             BursaAnalizer_Frame.DBID_DATA).con;

        try {
             insert1TradeDataRowToDB(con,sqlstr,sqldate,open,hi,lo,close,amount,datatype);
        } catch (SQLException ioe) {
            if(LoggerNB.debuging){System.out.println("1 992 SQLException happened. " + ioe);}
        }
    }
    
    // this two functions loading trade data from db by id of stok=tablename
    static public void loadTradeDataTableToOut(int fieldcount, int id,
            java.util.List Out) {
        Connection con = BursaAnalizer_Frame.getConnectToDB(
                             BursaAnalizer_Frame.DBID_DATA).con;
 
        if (id < 0) {
            if(LoggerNB.debuging){System.out.println("Error in 20: try to get Stock id: " + id);}

            return;
        }
        
        loadTradeDataTableToOutByOutSideCon(con,fieldcount,id,Out);
    }

    /*
     * Method removeduplicates - remove duplicate rows from db
     */
    public void remdupfromTradeDataTable(int table) {
        System.out.println("Removing duplicates");

        String     s   = "";
        Connection con = BursaAnalizer_Frame.getConnectToDB(
                             BursaAnalizer_Frame.DBID_DATA).con;
        TRS RS  = new TRS(con);
        TRS RS1 = new TRS(con);

        try {
            String sql2 =
                "SELECT DAY,OPEN,CLOSE,HI,LO,AMOUNT,Count(DAY) as DAYCNT";

            sql2 += " FROM [" + table+"]";
            sql2+=" WHERE DT='D' ";
            sql2 += " GROUP BY DAY,OPEN,CLOSE,HI,LO,AMOUNT";
            sql2 += " HAVING Count(DAY)>1";
            RS.execSQL(sql2);

            while (RS.rs.next()) {

                // read the data that have duplicate entry
                CDate d = new CDate();

                d.setDate(RS.rs.getDate("DAY"));

                java.sql.Date sqldate = new java.sql.Date(d.getDateMilis());
                float         open    = RS.rs.getFloat("OPEN");
                float         hi      = RS.rs.getFloat("HI");
                float         lo      = RS.rs.getFloat("LO");
                float         close   = RS.rs.getFloat("CLOSE");
                float         amount  = RS.rs.getFloat("AMOUNT");

                // remove all entry with this data
                String sql3 = "DELETE FROM [" + table + "] WHERE DAY=" + sqldate +" AND DT='D' ";

                RS1.execSQL(sql3);

                // insert only one
                insert1TradeDataRow(table, sqldate, open, hi, lo, close,
                                    amount,"D");
            }
        } catch (SQLException ioe) {
            if(LoggerNB.debuging){System.out.println("1 991 SQLException happened. " + ioe);}
        } finally {
            RS.close();
            RS1.close();
        }
    }
}


