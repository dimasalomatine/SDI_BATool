/*
 * GenericTableOperations.java
 *
 * Created on November 27, 2006, 7:20 PM
 *
 */

package DBOPERATIONS;

import CDate.CDate;
import DBCONNECT.*;
import Utils.LoggerNB;
import Utils.TDoc;
import java.sql.*;

/**
 *
 * @author Dmitry
 */
public class GenericTableOperations {
    
    /**
     * Creates a new instance of GenericTableOperations
     */
    public GenericTableOperations() {
    }
    
    // this two functions loading trade data from db by id of stok=tablename
    static public void loadTradeDataTableToOutByOutSideCon(Connection con ,int fieldcount, int id,
            java.util.List Out) {
        TRS RS = new TRS(con);

        if (id < 0) {
            if(LoggerNB.debuging){System.out.println("Error in 20: try to get Stock id: " + id);}

            return;
        }

        try {
            RS.execSQL("SELECT * FROM [" + id + "] ORDER BY DAY ASC");
            loadTradeDataTableFromReadyRSToOut(RS,fieldcount,Out);
            
        } catch (SQLException e) {
            if(LoggerNB.debuging){System.out.println("Error20: " + e);}
        } finally {
            RS.close();
            RS = null;
        }
    }
    
    // this two functions loading trade data from db by id of stok=tablename
    static public void loadTradeDataTableFromReadyRSToOut(TRS RS,int fieldcount,
            java.util.List Out)throws SQLException
    {
            CDate  ddd  = null;
            String tday = "";

            while (RS.rs.next()) {
                try {
                    ddd  = new CDate(RS.rs.getDate("DAY"));
                    tday = ddd.toString();
                } catch (Exception e) {
                    tday = "";
                }

//              amount must be long integer 
                add1TradeDataRowToOut(Out, fieldcount, tday,
                                      RS.rs.getFloat("OPEN"),
                                      RS.rs.getFloat("HI"),
                                      RS.rs.getFloat("LO"),
                                      RS.rs.getFloat("CLOSE"),
                                      RS.rs.getFloat("AMOUNT"),
                                      RS.rs.getString("DT"));
            }
    }
    
    static public void add1TradeDataRowToOut(java.util.List Out,
            int fieldcount, String day, float o, float h, float l, float c,
            float v,String rawdatatype) {
        TDoc t = new TDoc(fieldcount);

        t.o[0] = day;
        t.o[1] = new Float(o);
        t.o[2] = new Float(h);
        t.o[3] = new Float(l);
        t.o[4] = new Float(c);
        t.o[5] = new Float(v);
        t.o[6] = rawdatatype;
        Out.add(t);
    }
    
    /*
     * Method insertone - inserts only one row ib db uses outside connection to db
     */
    static public void insert1TradeDataRowToDB(Connection con, String sqlstr,java.sql.Date sqldate,
            float open, float hi, float lo, float close, float amount,String datatype) throws SQLException
    {
            TRS            RS    = new TRS(con);
            PreparedStatement pstmt = RS.con.prepareStatement(sqlstr);
            pstmt.setDate(1, sqldate);
            pstmt.setFloat(2, open);
            pstmt.setFloat(3, hi);
            pstmt.setFloat(4, lo);
            pstmt.setFloat(5, close);
            pstmt.setFloat(6, amount);
            pstmt.setString(7, datatype);

            pstmt.executeUpdate();
            pstmt.close ();
            RS.close ();
    }
    
    /*
     * Calculate difference in percents and store it
     */
    static public void calculateDiffOld(java.util.List tatd, int difinindex) {
        Float p, c;

        if ((tatd.size() == 0)
                || ((TDoc) tatd.get(0)).o.length <= difinindex) {
            return;
        }

        for (int i = tatd.size() - 1; i > 0; i--) {
            p                                     =
                (Float) (((TDoc) tatd.get(i - 1)).o[4]);
            c                                     =
                (Float) (((TDoc) tatd.get(i)).o[4]);
            ((TDoc) tatd.get(i)).o[difinindex] = new Float(((c.floatValue()
                    - p.floatValue()) / p.floatValue()) * 100.0f);
        }

        ((TDoc) tatd.get(0)).o[difinindex] = new Float(0.0f);
    }
    /*
     * Calculate difference in percents and store it
     *workon it is where close stored =4
     **workon it is where amount stored =5
     */
      static public void calculateDiffARtoPreviousA(java.util.List tatd, int workon,int difinindex,boolean bto100) 
    {
        Float p, c,res;
        float t100=1.0f;
        if(bto100)t100=100.f;
        if ((tatd.size() == 0)
                || ((TDoc) tatd.get(0)).o.length <= difinindex) {
            return;
        }

        for (int i = tatd.size() - 1; i > 0; i--) {
            
            p                                     =
                (Float) (((TDoc) tatd.get(i - 1)).o[workon]);
            c                                     =
                (Float) (((TDoc) tatd.get(i)).o[workon]);
            res = new Float(((c.floatValue()- p.floatValue()) / p.floatValue()) * t100);
            if(res.isNaN())
                ((TDoc) tatd.get(i)).o[difinindex]=new Float(res.MAX_VALUE-1.0f);
            else if(res.isInfinite())
                ((TDoc) tatd.get(i)).o[difinindex]=new Float(res.MAX_VALUE-1.0f);
            else
            ((TDoc) tatd.get(i)).o[difinindex] =res;
        }

        ((TDoc) tatd.get(0)).o[difinindex] = new Float(0.0f);
    }
      
   static public  void calculateDiffAtoB(java.util.List tatd, int workon1,int workon2,int difinindex,boolean bto100) {
        Float p, c,res;
        float t100=1.0f;
        if(bto100)t100=100.f;
        if ((tatd.size() == 0)
                || ((TDoc) tatd.get(0)).o.length <= difinindex) {
            return;
        }

        for (int i=0;i< tatd.size(); i++)
        {
            p                                     =
                (Float) (((TDoc) tatd.get(i)).o[workon1]);
            c                                     =
                (Float) (((TDoc) tatd.get(i)).o[workon2]);
            res = new Float(((c.floatValue()- p.floatValue()) / p.floatValue()) * t100);
            if(res.isNaN())
                ((TDoc) tatd.get(i)).o[difinindex]=new Float(res.MAX_VALUE-1.0f);
            else if(res.isInfinite())
                ((TDoc) tatd.get(i)).o[difinindex]=new Float(res.MAX_VALUE-1.0f);
            else
            ((TDoc) tatd.get(i)).o[difinindex] =res;
        }
    }
}
