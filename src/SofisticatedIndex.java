import CDate.CDate;
/*
 * SofisticatedIndex.java
 *
 * Created on March 3, 2007, 1:15 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Dmitry
 */
public class SofisticatedIndex
{
    private int top;
    private int low;
    private BursaAnalizer_Frame mfrm = null;
    private int id;
    private CDate day;
    /** Creates a new instance of SofisticatedIndex */
    public SofisticatedIndex (BursaAnalizer_Frame tfrm)
    {
        mfrm=tfrm;
    }
    public void calculate(CDate tday,int ttop,int tlow,int tid)
    {
     day=new CDate(tday);
     top=ttop;
        low=tlow;
        id=tid;
     
        /*
        String m_name   = "";
        String m_ID     = "";
        String m_market = "";
        String tcs      = "";
        SDITRS rs       = new SDITRS(
                              mfrm.getConnectToDB(
                                  BursaAnalizer_Frame.DBID_BASE).con);
        SDITRS rs_2 = new SDITRS(
                          mfrm.getConnectToDB(
                              BursaAnalizer_Frame.DBID_BASE).con);
        SDITRS r1 = new SDITRS(
                        mfrm.getConnectToDB(
                            BursaAnalizer_Frame.DBID_DATA).con);
        int total     = 0,
            completed = 0,
            skiped    = 0;

        try {
            rs.execSQL(
                "SELECT COUNT(*) as total from MENAYA_BASE WHERE UPDATE=true");

            if (rs.rs.next()) {
                total = rs.rs.getInt("total");
            }
            SDILoggerNB.getLogger().caprg.setVisible(true);
            SDILoggerNB.getLogger().actiontxtlbl.setText("Action: Updating DB");
            SDILoggerNB.getLogger().actiontxtlbl.setForeground(Color.red);
            SDILoggerNB.getLogger().caprg.setMinimum(0);
            SDILoggerNB.getLogger().caprg.setMaximum(total);
            SDILoggerNB.getLogger().caprg.setValue(0);
            SDILoggerNB.getLogger().Log("Total " + total, SDILoggerNB.INFORMATIVE);
            
            if (!updateIndexfromTASE()) {
                updateIndexfromYAHOO();
            }
            
            rs.reuse();
            rs.execSQL("SELECT * from MENAYA_BASE WHERE UPDATE=true");

            while (isRuning() && rs.rs.next()) {
                m_ID   = rs.rs.getString("MENAYA_ID");
                m_name = rs.rs.getString("SYMBOL");

                int m_market_id = rs.rs.getInt("MARKET_ID");

                rs_2.execSQL("SELECT * from MARKET_T WHERE ID=" + m_market_id);

                if (rs_2.rs.next()) {
                    m_market = rs_2.rs.getString("MARKET");
                } else {
                    m_market = "TA";
                }

                tcs = "create table " + m_ID
                      + " ( DAY datetime,OPEN float,CLOSE float,HI float,LO float,AMOUNT float)";

                // alter once
                // tcs="alter table "+m_ID+" alter DAY datetime PRYMARY KEY";
                // tcs="alter table "+m_ID+" alter DAY datetime PRYMARY KEY(DAY) ";
                // tcs="alter table "+m_ID+" add PRYMARY KEY(DAY) ";
                r1.createTable(tcs);
                r1.reuse();

                // update data from internet
                // detect last exist record for this symbol
                r1.execSQL("SELECT DAY as lastok FROM " + m_ID
                           + " ORDER BY DAY DESC");

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
                    if(SDILoggerNB.debuging){
                    System.out.println("Are stok needs in update from date:" + begin.toString());}
                }

                r1.reuse();

                if (flag) {
                    if(SDILoggerNB.debuging){
                    System.out.println("Update this stok begins from date:" + begin.toString());}
                    completed++;

                    // if(!checkConstinguesyTASE())
                    if (!checkConstinguesyYAHOO(Integer.parseInt(m_ID),
                                                m_name, m_market, begin,
                                                now)) {
                        skiped++;
                        completed--;
                    }
                }
                SDILoggerNB.getLogger().caprg.setValue(completed);
                if(SDILoggerNB.debuging){
                System.out.print(
                    "\r                                            ");
                System.out.print("\rCompleted:" + completed * 100.0f / total
                                 + "% Skiped:" + skiped * 100.0f / total
                                 + "%");}
            }
            SDILoggerNB.getLogger().actiontxtlbl.setText("Action: Updating Finished");
            SDILoggerNB.getLogger().actiontxtlbl.setForeground(Color.green);
            SDILoggerNB.getLogger().caprg.setVisible(false);
            SDILoggerNB.getLogger().Log("Completed:"
                                      + completed * 100.0f / total
                                      + "% Skiped:" + skiped * 100.0f / total
                                      + "%", SDILoggerNB.INFORMATIVE);
        } catch (SQLException e) {
            if(SDILoggerNB.debuging){System.err.println("Err " + e);}
        } finally {
            rs.close();
            r1.close();
            rs_2.close();
        }

        checkUpdatedStatus();
         */
    }
    
}
