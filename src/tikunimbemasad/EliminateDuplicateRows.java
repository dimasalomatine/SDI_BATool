/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tikunimbemasad;
import CDate.CDate;
import DBCONNECT.*;
import Utils.LoggerNB;
import Utils.TDoc;
import java.sql.*;
import java.util.*;
import javax.swing.JProgressBar;

/**
 *
 * @author Dmitry
 */
public class EliminateDuplicateRows  extends Thread {
public EliminateDuplicateRows(Connection tbase,Connection tcon)
{
 this.con=tcon;
 this.base=tbase;
}
    private static final long sleeptime = 10;
    private boolean              runing         = false;
    private boolean lock=false;
    private int table=0;
    private java.util.List Out=new ArrayList();
    private Connection con=null;
    private Connection base=null;
    private JProgressBar jProgressBar1=null;
    public void setprogress(JProgressBar jProgressBar1) {
        this.jProgressBar1=jProgressBar1;
    }

    private void preInitTable(int ttable)
    {
        if(lock)return;
          this.table=ttable;
          lock=true;
          TRS RS = new TRS(con);
          
          //String sql="ALTER TABLE "+table+" ADD URID INT UNSIGNED NOT NULL AUTO_INCREMENT;";
          //String sql ="ALTER TABLE ["+table+"] ADD URID identity primary key(URID)";
         // String sql ="ALTER TABLE ["+table+"] ADD URID COUNTER";
          String sql ="ALTER TABLE ["+table+"] ADD URID int identity";
          
          RS.execSQL(sql);
          RS.close();
    }
    public void postCleanAll()
    {
     if(lock)return;
     String sql="ALTER TABLE ["+table+"] DROP COLUMN URID;";
     TRS RS = new TRS(con);
     RS.execSQL(sql);
     RS.close();
    }
    public int getDuplicatesCount()
    {
     if(lock)return 0;//not eliminated yet
     int cnt=0;
     for(int i=0;i<Out.size();i++)
     {
      TDoc t=(TDoc)Out.get(i);
      if(((Boolean)t.o[2]).booleanValue()==true)cnt++;
     }
     return cnt;
    }
    public java.util.List getOut()
    {
     if(lock)return null;//not eliminated yet
     return Out;
    }
    public void eliminateDuplicates()
    {
     if(!lock)return;
     //here do al
     int i=0;
     if(Out.isEmpty())return;
     TDoc start=(TDoc)Out.get(i);
     for(i++;i<Out.size();i++)
     {
      TDoc curent=(TDoc)Out.get(i); 
      if(((String)start.o[0]).equalsIgnoreCase(((String)curent.o[0])))
      {
       curent.o[2] = new Boolean(true);
      }else
      {
       start=curent;
      }
     }
     //clear state
     lock=false;
    }

    private void removeDuplicates()
    {
     //here do al
     for(int i=0;i<Out.size();i++)
     {
      TDoc curent=(TDoc)Out.get(i); 
      if(((Boolean)curent.o[2]).booleanValue()==true &&
          ((Boolean)curent.o[3]).booleanValue()==false)
      {
       String sql="DELETE FROM ["+table+"] WHERE URID="+((Integer)curent.o[1]).intValue();
       TRS RS = new TRS(con);
       RS.execSQL(sql);
       RS.close();
       curent.o[3] = new Boolean(true);//for clean state ok
      }
     }
    }
    
    // this two functions loading trade data from db by id of stok=tablename
     public void loadTradeDataTableToOutByOutSideCon( )
    {
        if (!lock || table < 0) {
            if(LoggerNB.debuging){System.out.println("Error in 20: try to get Stock id: " + table);}
            return;
        }
        Out.clear();
        TRS RS = new TRS(con);
        try {
            RS.execSQL("SELECT DAY,URID FROM [" + table + "] ORDER BY DAY ASC");
            loadTradeDataTableFromReadyRSToOut(RS);
        } catch (SQLException e) {
            if(LoggerNB.debuging){System.out.println("Error20: " + e);}
        } finally {
           if(RS!=null) RS.close();
           // RS = null;
        }
    }
    
    // this two functions loading trade data from db by id of stok=tablename
     private void loadTradeDataTableFromReadyRSToOut(TRS RS)
            throws SQLException
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
                add1TradeDataRowToOut(tday,RS.rs.getInt("URID"));
            }
    }
    
     private void add1TradeDataRowToOut(String day,int urid) {
        TDoc t = new TDoc(4);
        t.o[0] = day;
        t.o[1] = new Integer(urid);
        t.o[2] = new Boolean(false);
        t.o[3] = new Boolean(false);//for clean state
        Out.add(t);
    }
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
        
         
        
        LoggerNB.getLogger().log("Deleting [duplicate stok row] by acceptance day",LoggerNB.INFORMATIVE);

        TRS            r1 = new TRS(base);
        String            m_ID, sqltoproceed;
        int               count = 0,maxc=0;
        try
        {
            r1.execSQL("SELECT count(*)  from MENAYA_BASE");
            if(r1.rs.next())maxc=r1.rs.getInt(1);
            if(jProgressBar1!=null)
            {
            jProgressBar1.setMaximum(maxc);
            jProgressBar1.setMinimum(0);
            jProgressBar1.setValue(0);
            }
            r1.execSQL("SELECT MENAYA_ID from MENAYA_BASE");
            while (runing && r1.rs.next()) 
            {
                try {
                   // m_ID = r1.rs.getString("MENAYA_ID");
                    int tiid = r1.rs.getInt("MENAYA_ID");
                    //int tiid=Integer.getInteger(m_ID);
                    //here do stuff
                    //
                    TRS            r2temp = new TRS(base);
                      String sqltemp ="ALTER TABLE ["+tiid+"] ADD DT varchar(1) NULL";
                      r2temp.execSQL(sqltemp);
                       sqltemp ="update ["+tiid+"] set DT='D' ";
                      r2temp.execSQL(sqltemp);
                      r2temp.close();
                     //
                    preInitTable(tiid);
                    loadTradeDataTableToOutByOutSideCon();
                    eliminateDuplicates();
                    removeDuplicates();
                    postCleanAll();
                    count++;
                    if(jProgressBar1!=null)jProgressBar1.setValue(count);
                    
                    //-------------
                    }catch (SQLException e) {
                                                if(LoggerNB.debuging){ System.err.println("Err " + e);}
                                            }
                try {
                    sleep(sleeptime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }catch (SQLException e) {
                                  if(LoggerNB.debuging){ System.err.println("Err " + e);}
                                 }
        
        LoggerNB.getLogger().log(
            "Completed [duplicate stok row] by acceptance day - total updated "
            + count + " entities", LoggerNB.INFORMATIVE);
        dostop();
    
    }
}
