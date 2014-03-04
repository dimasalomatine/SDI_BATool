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
public class TikunNetunNoAmount extends Thread {
    private static final long sleeptime = 10;

    //~--- fields -------------------------------------------------------------
    private boolean              runing         = false;
    private DataBaseConnector basecon;
    private DataBaseConnector datacon;

    //~--- constructors -------------------------------------------------------

    /** Creates a new instance of TikunNetunYahoo1 */
    public TikunNetunNoAmount(DataBaseConnector base,
                            DataBaseConnector data) {
        basecon        = base;
        datacon        = data;
       
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
        
            LoggerNB.getLogger().log("Delete [incorect stok row] by amount zero",LoggerNB.INFORMATIVE);
        
        TRS            r1 = new TRS(basecon.con);
       TRS            r2 = new TRS(basecon.con);
        String            m_ID;
        PreparedStatement pstmt;
    
        int               count = 0;
        
        try {
            r1.execSQL("SELECT MENAYA_ID from MENAYA_BASE");

            while (runing && r1.rs.next()) {
                
                try {
                    m_ID = r1.rs.getString("MENAYA_ID");
                  
                     LoggerNB.getLogger().log("Delete [incorect stok row] "+m_ID+" by amount zero",LoggerNB.INFORMATIVE);
                        String stmttoexe="DELETE FROM  ["
                                + m_ID + "] WHERE [AMOUNT]=0";
                        pstmt = datacon.con.prepareStatement(stmttoexe);
                        pstmt.executeUpdate();
                        pstmt.close ();
                        count++;
                   
                } catch (SQLException e) {
                    //if(LoggerNB.debuging){
                        System.err.println("Err " + e);
                   // }
                }

                try {
                    sleep(sleeptime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }   
        }catch (SQLException e) {
                    if(LoggerNB.debuging){System.err.println("Err " + e);}
                }
         
        LoggerNB.getLogger().log(
            "Proccessed [incorect stok row] by amount zero  - total deleted "
            + count + " entities", LoggerNB.INFORMATIVE);
        dostop();
    }
}


//~ Formatted by Jindent --- http://www.jindent.com

