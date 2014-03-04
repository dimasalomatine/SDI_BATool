/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tikunimbemasad;

//~--- non-JDK imports --------------------------------------------------------

import DBCONNECT.DataBaseConnector;
import DBCONNECT.TRS;
import Utils.LoggerNB;

//~--- JDK imports ------------------------------------------------------------

import java.sql.*;

//~--- classes ----------------------------------------------------------------

/**
 *
 * @author Student
 */
public class TikunNetunYahoo2PikeFixing extends Thread {
    private static final long sleeptime = 10;

    //~--- fields -------------------------------------------------------------

    private boolean              updateordelete = true;
    private boolean              dateequorgreater = false;
    private boolean              runing         = false;
    private DataBaseConnector basecon;
    private DataBaseConnector datacon;

    //~--- constructors -------------------------------------------------------

    /** Creates a new instance of TikunNetunYahoo1 */
    public TikunNetunYahoo2PikeFixing(DataBaseConnector base,
                            DataBaseConnector data,
                            boolean updel,
                            boolean wereop) {
        basecon        = base;
        datacon        = data;
        updateordelete = updel;
        dateequorgreater=wereop;
    }

    //~--- methods ------------------------------------------------------------

    public void dorun() {
        if (runing) {
            dostop();
        }

        runing = true;
        start();
    }

    public void dostop() {
        runing = false;
    }
    public void clear()
    {
    
    }

    //TODO write more correct code also for > update
    @Override
    public void run() {
        if(updateordelete)
            LoggerNB.getLogger().log("Updating [incorect stok row] by acceptance day",LoggerNB.INFORMATIVE);
        else LoggerNB.getLogger().log("Deleting [incorect stok row] by acceptance day",LoggerNB.INFORMATIVE);

        TRS            r1 = new TRS(basecon.con);
        TRS            r2 = new TRS(datacon.con);
        //TRS            tempex = new TRS(datacon.con);
        String            m_ID, sqltoproceed;
        PreparedStatement pstmt;
        float             o, c, h, l;
        int               count = 0;
        
        try {
            r1.execSQL("SELECT MENAYA_ID from MENAYA_BASE");

            while (runing && r1.rs.next()) {
                /*
                try {
                    m_ID = r1.rs.getString("MENAYA_ID");

                    if (updateordelete) {

                        // first get old values
                        // java.sql.Date tdts=new java.sql.Date(operateondate.getDateMilis());
                        r2.execSQL("SELECT * FROM " + m_ID + " WHERE DAY="
                                   + operateondate.toStringDelimited('/'));

                        if (r2.rs.next()) {
                            System.out.println("found "
                                               + operateondate.toString());
                            o = r2.rs.getFloat("OPEN");
                            c = r2.rs.getFloat("CLOSE");
                            h = r2.rs.getFloat("HI");
                            l = r2.rs.getFloat("LO");

                            // pstmt = datacon.con.prepareStatement("UPDATE "+m_ID+" SET [OPEN]=?,[CLOSE]=?,[HI]=?,[LO]=? WHERE [DAY]=?");
                            pstmt = datacon.con.prepareStatement(
                                "UPDATE  " + m_ID
                                + "  SET  OPEN =? , CLOSE = ? , HI = ? ,  LO = ?  WHERE  DAY ="
                                + operateondate.toString());
                            pstmt.setFloat(1, o / 100.f);
                            pstmt.setFloat(2, c / 100.f);
                            pstmt.setFloat(3, h / 100.f);
                            pstmt.setFloat(4, l / 100.f);

                            // pstmt.setDate(5, new java.sql.Date(operateondate.getDateMilis()));
                            pstmt.executeUpdate();
                            pstmt.close ();
                            count++;
                            System.out.println("changed "
                                               + operateondate.toString());
 
                        }

                        r2.reuse();
                    } else {
                        String stmttoexe="DELETE FROM  "
                                + m_ID + " WHERE [DAY]";
                        if(dateequorgreater)stmttoexe+=">?";
                        else stmttoexe+="=?";
                        pstmt = datacon.con.prepareStatement(stmttoexe);
                         pstmt.setDate(
                            1, new java.sql.Date(
                                operateondate.getDateMilis()));
                        pstmt.executeUpdate();
                        pstmt.close ();
                        count++;
                    }
                } catch (SQLException e) {
                    if(LoggerNB.debuging){System.err.println("Err " + e);}
                }

                try {
                    sleep(sleeptime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            r1.reuse();
                 */
        }
            /*catch (SQLException e) {
           if(LoggerNB.debuging){ System.err.println("Err " + e);}
        }*/

            
        LoggerNB.getLogger().log(
            "Proccessed [incorect stok row] by acceptance day - total updated "
            + count + " entities", LoggerNB.INFORMATIVE);
        dostop();
    }catch (SQLException e) {
           if(LoggerNB.debuging){ System.err.println("Err " + e);}
}
    }
}


//~ Formatted by Jindent --- http://www.jindent.com

