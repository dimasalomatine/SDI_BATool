
import CDate.CDate;
import DBCONNECT.TRS;
import Utils.LoggerNB;
import Utils.MySimleInputByCombo;
import Utils.TDoc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.DefaultCellEditor;
import javax.swing.table.TableColumn;

class UserFolderTable extends SQL_DG implements SQL_DG_OPS
{
    public UserFolderTable ()
    {
        super (new String[]{"ID","Name","Since","Status"}, true, new boolean[]{false,true,false,true});
        fetchdata(BursaAnalizer_Frame.getWellcomeScreenInstance().getCurentUserID());	
    }
    MySimleInputByCombo cbind;//combo for folder status types
    
    void initComboxes (Connection con)
    {
        cbind = new MySimleInputByCombo (null, con, "[FOLDER_STATUS]", "STATUS", "ID");
    }
    public void initTableControlPanel (){
    }
    public void onNewBut (){
    }
    public void onUpdateBut (){
    }
    public void onDeleteBut (){
    }
    public void saveAll (int record)
    {
    	 
    }
    public void saveAll ()
    {
        int i, id;
        boolean upd;
        Connection con = BursaAnalizer_Frame.getConnectToDB(BursaAnalizer_Frame.DBID_BASE).con;
        //Connection con=((msdb_connect)(BursaAnalizer_Frame.base)).con;
        String sqlstr = "UPDATE MENAYA_BASE SET UPDATE = ? WHERE MENAYA_ID = ?";
        try
        {
            PreparedStatement pstmt = con.prepareStatement(sqlstr);
            for (i=0; i<atd.size(); i++)
            {
                if (((TDoc) atd.get(i)).changed == true)
                {
                    id = (Integer.valueOf((((TDoc) atd.get(i)).o[0]).toString())).intValue();
                    upd = (Boolean.valueOf((((TDoc) atd.get(i)).o[6]).toString())).booleanValue();
                    pstmt.setBoolean(1, upd);
                    pstmt.setInt(2, id);
                    pstmt.executeUpdate();
                    //if i here to not save twise then set changed to false
                    ((TDoc) atd.get(i)).changed = false;
                }
            }
            pstmt.close ();
        } catch (SQLException ioe)
        { 
            if (LoggerNB.debuging){                System.out.println("1 100 SQLException happened. "+ioe);    }
        }  
    }
    public void fetchdata (){
    }
     public void fetchdata (String filters[]){}
    int tituid=0;
    public int getuserid(){return tituid;}
    public void fetchdata (int uid)
    {	
        tituid=uid;
        if(uid<0){atd.clear();return;}
        Connection con = BursaAnalizer_Frame.getConnectToDB(BursaAnalizer_Frame.DBID_USER).con;
        initComboxes(con);
        Integer ti = new Integer(0);
        TDoc t = null;
        TRS RS = new TRS(con);
        try {
            RS.execSQL("SELECT * FROM [FOLDER] WHERE UID="+uid);
            while (RS.rs.next())
            {
                t = new TDoc (4);
                t.o[0] = new Integer (RS.rs.getInt("ID"));
                //t.o[1]=new String(RS.rs.getString("NAME"));
                t.o[1]=RS.rs.getString("NAME");
                ti = new Integer (RS.rs.getInt("FSID"));
                //t.o[3]=new String(cbind.getVc1byVc2(ti.toString()));
                t.o[3]=cbind.getVc1byVc2(ti.toString());
                CDate ddd;
                try
                {
                    ddd = new CDate (RS.rs.getDate("SINCE"));
                    //t.o[2]=new String(ddd.toString());
                    t.o[2]=ddd.toString();
                } catch (Exception e)
                {
                 t.o[2]="";	
                }
                atd.add(t);
            }
        } catch (SQLException e)
        {
            if (LoggerNB.debuging){                System.out.println("Error20: " + e);}
        } 
        finally
        {
        	RS.close();
     		    RS=null;
        }
        initAdvancedTableParams();
    }
    public void initAdvancedTableParams ()
    {
        //advanced setings for some columns
        TableColumn tcg;
        hideColumnN(0);
        //tcg=table.getColumn(columnnames[0]);
        //tcg.setMaxWidth(60);
        //tcg.setWidth(60);
        //tcg.setResizable(false);
        tcg=table.getColumn(columnnames[1]);
        tcg.setMaxWidth(220);
        tcg.setWidth(220);
        tcg.setResizable(true);
        tcg=table.getColumn(columnnames[2]);
        tcg.setMaxWidth(80);
        tcg.setWidth(80);
        tcg.setResizable(false);
        tcg=table.getColumn(columnnames[3]);
        tcg.setMaxWidth(100);
        tcg.setWidth(100);
        tcg.setResizable(false);
        tcg.setCellEditor(new DefaultCellEditor (cbind));
       revalidate();
    }
}