//~--- non-JDK imports --------------------------------------------------------

import CDate.CDate;
import DBCONNECT.TRS;
import Utils.DnldURL;
import Utils.LoggerNB;
import LocalUtils.ThreadBasic;
import java.awt.Color;

//~--- JDK imports ------------------------------------------------------------

import java.sql.*;

/*
 * DataBaseUpdater.java
 *
 * Created on October 21, 2006, 11:54 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

//~--- classes ----------------------------------------------------------------
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Dmitry
 */
public class DataBaseUpdater extends ThreadBasic {
    BursaAnalizer_Frame mfrm = null;

    //~--- constructors -------------------------------------------------------

    /** Creates a new instance of DataBaseUpdater */
    public DataBaseUpdater(BursaAnalizer_Frame tmfrm) {
        super("DataBaseUpdater");
        this.mfrm = tmfrm;
        dorun();
    }

    //~--- methods ------------------------------------------------------------

    boolean checkConstinguesyTASE(int m_ID, String m_name, String m_market,
                                  CDate begin, CDate now,Map mapindexing) {
        LoggerNB.getLogger().log("Updating from TASE.co.il...",
                                  LoggerNB.INFORMATIVE_NC);

        
        
        DnldURL f = new ReadFromTASE(m_name, m_market, begin, now,mapindexing);

        ((ReadFromTASE)f).read(BursaAnalizer_Frame.getConnectToDB(BursaAnalizer_Frame.DBID_DATA), m_ID,begin, now);    // ,begin);
        f.close();

        return f.status;
    }

    boolean checkConstinguesyYAHOO(int m_ID, String m_name, String m_market,
                                   CDate begin, CDate now) {
        LoggerNB.getLogger().log("Updating from Yahoo.com...",
                                  LoggerNB.INFORMATIVE_NC);

        DnldURL f = new ReadFromYahoo(m_name, m_market, begin, now);

        // ((ReadFromYahoo)f).removeduplicates(m_ID);
        float scalefactor =
            BursaAnalizer_Frame.getAppSettingsInstance().getYahooScaleFactor();

        ((ReadFromYahoo) f).read(
            BursaAnalizer_Frame.getConnectToDB(BursaAnalizer_Frame.DBID_DATA), m_ID,
            scalefactor);    // ,begin);
        f.close();

        return f.status;
    }

    void checkUpdatedStatus() {
        int total=1,curent=0;
        LoggerNB.getLogger().log("Updating [UPDATED SINCE] status",
                                  LoggerNB.INFORMATIVE);
        LoggerNB.getLogger().caprg.setVisible(true);
            LoggerNB.getLogger().actiontxtlbl.setText("Action: Update Status");
            LoggerNB.getLogger().actiontxtlbl.setForeground(Color.red);
            LoggerNB.getLogger().caprg.setMinimum(0);
            LoggerNB.getLogger().caprg.setMaximum(total);
            LoggerNB.getLogger().caprg.setValue(curent);

        TRS r1 = new TRS(
                        BursaAnalizer_Frame.getConnectToDB(
                        BursaAnalizer_Frame.DBID_BASE).con);
        TRS r2 = new TRS(
                        BursaAnalizer_Frame.getConnectToDB(
                        BursaAnalizer_Frame.DBID_DATA).con);
        String            m_ID, tsqllastdayok, upsqlstr;
        PreparedStatement pstmt;
        Connection        con =
            BursaAnalizer_Frame.getConnectToDB(BursaAnalizer_Frame.DBID_BASE).con;

        try {
            //r1.execSQL("SELECT * from MENAYA_BASE  WHERE UPDATE=true");
             r1.execSQL("SELECT * from MENAYA_BASE  WHERE [UPDATE]=1");
            pstmt = con.prepareStatement(
                "UPDATE MENAYA_BASE SET [SINCE]=? WHERE [MENAYA_ID]=?");

            while (r1.rs.next()) {
                m_ID = r1.rs.getString("MENAYA_ID");

                // detect last exist record for this symbol
                tsqllastdayok = "SELECT DAY as lastok FROM [" + m_ID
                                + "] ORDER BY DAY DESC";
                r2.execSQL(tsqllastdayok);

                if (r2.rs.next()) {
                    CDate         lok     = new CDate(r2.rs.getDate("lastok"));
                    java.sql.Date sqldate =
                        new java.sql.Date(lok.getDateMilis());

                    // here update since
                    pstmt.setDate(1, sqldate);
                    pstmt.setInt(2, Integer.parseInt(m_ID));
                    pstmt.executeUpdate();
                }
                LoggerNB.getLogger().caprg.setMaximum(total++);
                LoggerNB.getLogger().caprg.setValue(curent++);
            }
            pstmt.close ();
            r1.close ();
            r2.close ();
        } catch (SQLException e) {
            System.err.println("Err " + e);
        }
        LoggerNB.getLogger().actiontxtlbl.setText("Action: Updating Status Finished");
        LoggerNB.getLogger().actiontxtlbl.setForeground(Color.green);
        LoggerNB.getLogger().caprg.setVisible(false);
        
        mfrm.updateCurentOpenViews();
    }

    // it is actually static void checkAccordingToTables()
    public void run() {
        String m_name   = "";
        String m_ID     = "";
        String m_market = "";
        String tcs      = "";
        TRS rs       = new TRS(
                              BursaAnalizer_Frame.getConnectToDB(
                        BursaAnalizer_Frame.DBID_BASE).con);
        TRS rs_2 = new TRS(
                          BursaAnalizer_Frame.getConnectToDB(
                        BursaAnalizer_Frame.DBID_BASE).con);
        TRS r1 = new TRS(
                        BursaAnalizer_Frame.getConnectToDB(
                        BursaAnalizer_Frame.DBID_DATA).con);
        int total     = 0,
            completed = 0,
            skiped    = 0;
        Map mapindexing = new HashMap();    // hash table
        try {
            //rs.execSQL(    "SELECT COUNT(*) as total from MENAYA_BASE WHERE UPDATE=true");
            rs.execSQL(    "SELECT COUNT(*) as total from MENAYA_BASE WHERE [UPDATE]=1");

            if (rs.rs.next()) {
                total = rs.rs.getInt("total");
            }
            LoggerNB.getLogger().caprg.setVisible(true);
            LoggerNB.getLogger().actiontxtlbl.setText("Action: Updating DB");
            LoggerNB.getLogger().actiontxtlbl.setForeground(Color.red);
            LoggerNB.getLogger().caprg.setMinimum(0);
            LoggerNB.getLogger().caprg.setMaximum(total);
            LoggerNB.getLogger().caprg.setValue(0);
            LoggerNB.getLogger().log("Total " + total, LoggerNB.INFORMATIVE);
            
            String otherstringupdate="";
            boolean yahooatupdate=Boolean.parseBoolean(BursaAnalizer_Frame.p.getProperty("update.USEYAHOO","false"));
            boolean taseupdate=Boolean.parseBoolean(BursaAnalizer_Frame.p.getProperty("update.USETASE","true"));
            boolean otherupdate=Boolean.parseBoolean(BursaAnalizer_Frame.p.getProperty("update.USEOTHER","false"));
            otherstringupdate=BursaAnalizer_Frame.p.getProperty("update.USEOTHER.MODULE","none");
            int updatecase=(taseupdate?1:(yahooatupdate?2:3));
         
            switch(updatecase)
            {
                case 1://TASE
                    //TODO updateIndexfromTASE();
                    break;
                case 2://YAHOO
                    //TODO updateIndexfromYAHOO();
                    break;
                case 3://CUSTOM CLASS LOAD MODULE
                    break;
            }
            
            rs.reuse();
            //rs.execSQL("SELECT * from MENAYA_BASE WHERE UPDATE=true");
            rs.execSQL("SELECT * from MENAYA_BASE WHERE [UPDATE]=1");

            while (isRuning() && rs.rs.next()) {
                m_ID   = rs.rs.getString("MENAYA_ID");
                m_name = rs.rs.getString("SYMBOL").trim();

                int m_market_id = rs.rs.getInt("MARKET_ID");

                rs_2.execSQL("SELECT * from MARKET_T WHERE ID=" + m_market_id);

                if (rs_2.rs.next()) {
                    m_market = rs_2.rs.getString("MARKET");
                } else {
                    m_market = "TA";
                }
                tcs=getCreateTableString(m_ID);
       
                // alter once
                // tcs="alter table "+m_ID+" alter DAY datetime PRYMARY KEY";
                // tcs="alter table "+m_ID+" alter DAY datetime PRYMARY KEY(DAY) ";
                // tcs="alter table "+m_ID+" add PRYMARY KEY(DAY) ";
                try
                {
                r1.createTable(tcs);
                r1.reuse();
                }catch (SQLException e) {
            if(LoggerNB.debuging){System.err.println("Table [ " + m_ID+"] Already Exists");}
        } 

                // update data from internet
                // detect last exist record for this symbol
                r1.execSQL("SELECT DAY as lastok FROM [" + m_ID
                           + "] ORDER BY DAY DESC");

                CDate   begin = new CDate(1, 1, 2003);
                CDate   now   = new CDate();
                boolean flag  = true;

                if (r1.rs.next()) {
                    flag = false;
                    begin.setDate(r1.rs.getDate("lastok"));
                    begin.roll(1);

                    if (begin.less(now)) {
                        flag = true;
                    }
                    if(LoggerNB.debuging){
                    System.out.println("Are stok needs in update from date:" + begin.toString());}
                }

                r1.reuse();

                if (flag) {
                    if(LoggerNB.debuging){
                    System.out.println("Update this stok begins from date:" + begin.toString());}
                    completed++;
                    boolean utflag=true;
                    switch(updatecase)
                    {
                        case 1://TASE
                        if(!checkConstinguesyTASE(Integer.parseInt(m_ID), 
                                                m_name, m_market,
                                               begin, now,mapindexing))utflag=false;
                        break;
                        case 2://YAHOO
                        if(!m_market.equals("TA")){ utflag=false;}
                        else{
                        if (!checkConstinguesyYAHOO(Integer.parseInt(m_ID),
                                                m_name, m_market, begin,
                                                now)) utflag=false;}
                        break;
                        case 3://CUSTOM CLASS LOAD MODULE
                        break;
            }
                if (utflag==false)
                    {
                        skiped++;
                        completed--;
                    }
                }
                LoggerNB.getLogger().caprg.setValue(completed);
                if(LoggerNB.debuging){
                System.out.print(
                    "\r                                            ");
                System.out.print("\r"+String.format("Completed:%6.2f %% Skiped:%6.2f%%", completed * 100.0f / total,skiped * 100.0f / total));
               }
            }
            LoggerNB.getLogger().actiontxtlbl.setText("Action: Updating Finished");
            LoggerNB.getLogger().actiontxtlbl.setForeground(Color.green);
            LoggerNB.getLogger().caprg.setVisible(false);
            LoggerNB.getLogger().log("Completed:"
                                      + completed * 100.0f / total
                                      + "% Skiped:" + skiped * 100.0f / total
                                      + "%", LoggerNB.INFORMATIVE);
        } catch (SQLException e) {
            if(LoggerNB.debuging){System.err.println("Err " + e);}
        } finally {
            rs.close();
            r1.close();
            rs_2.close();
        }
        //do my post updates stuff
        checkUpdatedStatus();
        createSofisticatedIndexes();
        dostop();
    }

    boolean updateIndexfromTASE() {
        //get action status label 
        String oldlabel=LoggerNB.getLogger().actiontxtlbl.getText();
        Color oldlabelc=LoggerNB.getLogger().actiontxtlbl.getForeground();
        LoggerNB.getLogger().actiontxtlbl.setText("Action: Updating INDEXES(TASE)");
        LoggerNB.getLogger().actiontxtlbl.setForeground(Color.blue);
        //TODO here implement index update  tase call
        this.dosleep();
        //restore action label status
        LoggerNB.getLogger().actiontxtlbl.setText(oldlabel);
        LoggerNB.getLogger().actiontxtlbl.setForeground(oldlabelc);
        //TODO here implement clean after update
        oldlabel=LoggerNB.getLogger().actiontxtlbl.getText();
        oldlabelc=LoggerNB.getLogger().actiontxtlbl.getForeground();
        LoggerNB.getLogger().actiontxtlbl.setText("Action: CLEAN FS LOCAL");
        LoggerNB.getLogger().actiontxtlbl.setForeground(Color.blue);
        DnldURL f = new ReadFromTASE("", "", null, null,null);
        this.dosleep();
        ((ReadFromTASE)f).CleanAfter();
        //restore action label status
        LoggerNB.getLogger().actiontxtlbl.setText(oldlabel);
        LoggerNB.getLogger().actiontxtlbl.setForeground(oldlabelc);
        return true;
    }

    boolean updateIndexfromYAHOO() {
        //get action status label 
        String oldlabel=LoggerNB.getLogger().actiontxtlbl.getText();
        Color oldlabelc=LoggerNB.getLogger().actiontxtlbl.getForeground();
        LoggerNB.getLogger().actiontxtlbl.setText("Action: Updating INDEXES(YAHOO)");
        LoggerNB.getLogger().actiontxtlbl.setForeground(Color.blue);
        //TODO here implement index update  tase call
        this.dosleep();
        // IndexFromYahoo idx=new IndexFromYahoo(0,true);
        // idx.read((Msdb_connect)base,"MENAYA_BASE");
        // idx.close();
        // if(idx.status==true)return;
        //TODO here implement clean after update
        DnldURL f = new ReadFromYahoo("", "", null, null);
        ((ReadFromYahoo)f).CleanAfter();
        //restore action label status
        LoggerNB.getLogger().actiontxtlbl.setText(oldlabel);
        LoggerNB.getLogger().actiontxtlbl.setForeground(oldlabelc);
        return true;
    }

    private void createSofisticatedIndexes ()
    {
        SofisticatedIndex indexta100253070=new SofisticatedIndex(mfrm);
        CDate di=new CDate("01-01-2001"),
                end=new CDate();
        for(;di.less (end);di.roll(1))
        {
        indexta100253070.calculate (di,0,100,1446);
        indexta100253070.calculate (di,0,25,1447);
        indexta100253070.calculate (di,100,130,1440);
        indexta100253070.calculate (di,100,175,1441);
        }
    }

    public static String getCreateTableString(String tID) {
        String ret = "create table [" + tID
                      + "] ( DAY datetime null,OPEN float null,CLOSE float null,HI float null,LO float null,AMOUNT float null)";
        return ret;              
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
