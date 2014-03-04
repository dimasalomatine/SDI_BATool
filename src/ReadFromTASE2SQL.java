
import CDate.CDate;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import DBCONNECT.DataBaseConnector;
import DBCONNECT.TRS;
import Utils.LoggerNB;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dmitry
 */
public class ReadFromTASE2SQL {
    private ReadFromTASE p;
    public ReadFromTASE2SQL(ReadFromTASE tp)
    {
    p=tp;
    }
void ReadAndUpdateIndex(String filename,DataBaseConnector data) {
        String s = "";
        String tn;
        String tn2;
        Object ret[];
        try
            {
            p.InitFileToRead(filename);
            // first read junk title
            char []buf=new char[80];
            //if ((s = p.d.readLine()) == null)return;  
            if (p.d.read(buf, 0, 80)<=0)return;  
            
            // read data to end of file
            //while ((s = p.d.readLine()) != null) 
            while (p.d.read(buf, 0, 80)>0) 
            {
                // TACT file 
                // 2 bytes rec type+8 bytes num+ 10 bytes NAME+15 bytes FULNAME
                // +2 bytes IL+more butt it is junk for us
                //TODO also detect last line that no contains relevant info so skip it
                byte []a=new byte[80];
                for(int i=0;i<buf.length;i++)a[i]=(byte)buf[i];
                //tn=String.copyValueOf(s.toCharArray(),2,10);
                tn=String.valueOf(buf, 2, 10);
                //tn2=String.copyValueOf(s.toCharArray(),10,20);
                tn2=String.valueOf(buf, 10, 20);
                ret=getTableIDbyName(data,tn,tn2);
                updateDBAccordingtoState(data,ret,tn,tn2);
                 
            }
        } catch (IOException ioe)
        {
            if(LoggerNB.debuging){System.out.println("1 2 IOException happened. " + ioe);}
        }
    }
    public void ReadTACT(String filename,DataBaseConnector data, int table,CDate now) {
        /*
        String s = "";
        String []s3=null;
        String tn;
        int tablei;
        try
            {
            p.InitFileToRead(filename);
            // first read junk title
            if ((s = p.d.readLine()) == null) {
                return;
            }  
            
            // read data to end of file
            while ((s = p.d.readLine()) != null) 
            {
                // TACT file 
                // 2 bytes rec type+8 bytes num+ 10 bytes NAME+15 bytes FULNAME
                // +2 bytes IL+8+sym+name
                s3 = s.split(" ");
                //10 bytes + NAME
                tn=String.copyValueOf(s3[0].toCharArray(),10,s3[0].toCharArray().length);
                if((tablei=getTableIDbyName(tn))<0)continue;
                java.sql.Date sqldate = new java.sql.Date(now.getDateMilis());
                
                String sql2 = "SELECT DAY FROM " + table + " WHERE DAY="
                              + sqldate;
                ResultSet r1 = data.execSQL(sql2);

                if (!r1.next()) 
                {
                    String sqlstr ="INSERT INTO ? (DAY,OPEN,HI,LO,CLOSE,AMOUNT) VALUES(?,?,?,?,?,?)";
                    PreparedStatement pstmt = data.con.prepareStatement(sqlstr);
                    pstmt.setInt (1,table);
                    pstmt.setDate(2, sqldate);
                    for (int i = 1; (i < s3.length) && (i < 6); i++) 
                        {
                            pstmt.setFloat(i + 2, Float.parseFloat(s3[i]));
                        }
                    pstmt.executeUpdate();
                    pstmt.close ();
                }
            }            
         }catch (SQLException ioe) 
        {
            if(LoggerNB.debuging){System.out.println("1 99 SQLException happened. " + ioe);}
        }
        catch (IOException ioe)
        {
            if(LoggerNB.debuging){System.out.println("1 2 IOException happened. " + ioe);}
        }
         */
    }
 
    private Object[] getTableIDbyName(DataBaseConnector data,String tn,String tn2) {
        Object ret[]={new Integer(-1),new Integer(-1)};
        try
        {
        String sql2 = "SELECT MENAYA_ID,SYMBOL,TASEID FROM MENAYA_BASE WHERE SYMBOL="
                              + tn2+" OR TASEID="+tn;
                ResultSet r1 = data.execSQL(sql2);

                if (!r1.next()) 
                {
                 ret[1]=r1.getInt("MENAYA_ID");
                 String sym=r1.getString("SYMBOL");
                 int taseid=r1.getInt("TASEID");
                 if(!tn2.equalsIgnoreCase(sym))ret[0]=new Integer(-2);
                 if(taseid!=Integer.parseInt(tn))ret[0]=new Integer(-3);
                }
            }            
         catch (SQLException ioe) 
        {
            if(LoggerNB.debuging){System.out.println("1 99 SQLException happened. " + ioe);}
        }
       return ret;
    }

    private void updateDBAccordingtoState(DataBaseConnector data, Object[] ret, String tn, String tn2) {
        try
        {
        if(((Integer)ret[0])==-1)
                {
                 // not exist so create record and then create table
                    
                 PreparedStatement pstmt =
                    data.con.prepareStatement("INSERT INTO MENAYA_BASE "+
                                                "(SYMBOL,TASEID) VALUES(?,?)");

                pstmt.setString(1, tn2);
                pstmt.setString(2, tn);
                pstmt.executeUpdate();
                pstmt.close ();
                //now get table id and create table in data db
                ret=getTableIDbyName(data,tn,tn2);
                String sqlst=DataBaseUpdater.getCreateTableString(((Integer)ret[1]).toString());
                TRS r1 = new TRS(
                        BursaAnalizer_Frame.getConnectToDB(
                        BursaAnalizer_Frame.DBID_DATA).con);
                r1.createTable(sqlst);
                }else if(((Integer)ret[0])==-2)
                {
                 // found but symbol is not correct
                }else if(((Integer)ret[0])==-3)
                {
                 // found but taseid is not correct
                }
        }catch (SQLException ex) {
            Logger.getLogger(ReadFromTASE2SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
