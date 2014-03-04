//~--- non-JDK imports --------------------------------------------------------



import DBCONNECT.TRS;
import Utils.TDoc;

//~--- JDK imports ------------------------------------------------------------

import java.awt.*;
import java.awt.event.*;

import java.sql.*;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;

public class DealScreen extends JPanel implements ActionListener    // Listener
{
    JButton       View, New, Delete;
    DealInfoTable dit;
    DealTable     dt;
    
     JTextArea totalqtty = new JTextArea("0");
JTextArea totalsum = new JTextArea("0");
    

    //~--- constructors -------------------------------------------------------

    /**
     * Method DealScreen
     *
     *
     */
    public DealScreen() {
        String timgdir = BursaAnalizer_Frame.getImgDir();

        View   = new JButton("View", new ImageIcon(timgdir + "info.png"));
        New    = new JButton("New", new ImageIcon(timgdir + "index_add.png"));
        Delete = new JButton("Delete",
                             new ImageIcon(timgdir + "index_delete.png"));
        setLayout(new BorderLayout(5, 5));
        dt  = new DealTable();
        dit = new DealInfoTable();

        JPanel cbp0 = new JPanel();

        cbp0.setLayout(new GridLayout(1, 2));
        cbp0.add(dt);
        cbp0.add(dit);
        add(cbp0, BorderLayout.CENTER);

        JPanel cbp     = new JPanel();
        JPanel cbp1    = new JPanel();
        JPanel cbpdiv2 = new JPanel();

        cbpdiv2.setLayout(new GridLayout(1, 2));
        cbpdiv2.setBorder(BorderFactory.createRaisedBevelBorder());
        cbp.setLayout(new GridLayout(2, 3));
        cbp.setBorder(BorderFactory.createRaisedBevelBorder());
        addAndRegisterButtonInGridLayout(cbp, View);
        addAndRegisterButtonInGridLayout(cbp, New);
        addAndRegisterButtonInGridLayout(cbp, Delete);
         JButton tb1temp1   = new JButton("Export");
         addAndRegisterButtonInGridLayout(cbp, tb1temp1);
         JButton tb1temp2  = new JButton("Export2");
          addAndRegisterButtonInGridLayout(cbp, tb1temp2);
        cbpdiv2.add(cbp);
        cbp1.setLayout(new GridLayout(2, 3));
        cbp1.setBorder(BorderFactory.createRaisedBevelBorder());
        cbp1.add(new JLabel("Curent QTTY :"));
        cbp1.add(totalqtty);
        cbp1.add(new JLabel("Curent SUM:"));
        cbp1.add(totalsum);
        
        cbpdiv2.add(cbp1);
        add(cbpdiv2, BorderLayout.SOUTH);
    }

    //~--- methods ------------------------------------------------------------

    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        int    sr1     = dt.table.getSelectedRow();
        int sr2 ;
        sr2 = dit.table.getSelectedRow();
        if (command.equals("View")) {
            if (sr1 >= 0) {
                dit.fetchdata(0);
                if (dt.atd.size() > 0) {
                    int tid = (Integer.valueOf(
                                  (((TDoc) dt.atd.get(
                                      sr1)).o[0]).toString())).intValue();
                    dit.fetchdata(tid);
                }
            }
        }
        if (command.equals("New"))
        {         
            if (sr1>= 0) {
                dit.fetchdata(0);
                if (dt.atd.size() > 0) {
                    int tid = (Integer.valueOf(
                                  (((TDoc) dt.atd.get(
                                      sr1)).o[0]).toString())).intValue();
                    String  ms = (String) ((TDoc) dt.atd.get(sr1)).o[2];
                    addDeal a=new addDeal(tid,ms);
                    dit.fetchdata(tid);
                }      
            }else
            {
                //completly new deal
                int curentfolderid=UserFolder.getCurentFolderID();
                 	JOptionPane.showMessageDialog(this,
         	 						   "New deal completely ? ",
         	 								"Just provide uniq symbol and LETS GO !!!!",
         	 								JOptionPane.INFORMATION_MESSAGE);
         if(JOptionPane.showConfirmDialog(this,
         	 							"<HTML><p><center>During this operation <br>New deal will be added !!!!",
         	 							"Important !!!",
         	 							JOptionPane.YES_NO_OPTION)==JOptionPane.NO_OPTION)return;
             addDeal a=new addDeal(0,curentfolderid,true);
             int rvid=a.dealid;
             
                    initOnData(UserFolder.getCurentFolderID());
            }
        }
             if (command.equals("Delete"))
        {
            if (sr2>= 0) {
    
                if (dt.atd.size() > 0) {
                    int tid = (Integer.valueOf(
                                  (((TDoc) dt.atd.get(
                                      sr1)).o[0]).toString())).intValue();
                    dit.onDeleteBut();
                      dit.fetchdata(0);
                    dit.fetchdata(tid);
            }
        }
             }
         if (command.equals("Export"))
        {
            exporttoexcell1();
             }
         recalculateall();
    }

    private void addAndRegisterButtonInGridLayout(JPanel to, JButton b) {
        to.add(b);
        b.addActionListener(this);
    }

    public void initOnData() {
        initOnData(UserFolder.getCurentFolderID());
    }

    public void initOnData(int folder) {
        dt.fetchdata(0);
        dt.fetchdata(folder);
        dt.table.revalidate();
        dit.fetchdata(0);
        recalculateall();
    }

    private void exporttoexcell1()
    {
        //TODO complete the doh for tf_

Connection con = BursaAnalizer_Frame.getConnectToDB(BursaAnalizer_Frame.DBID_USER).con;
           TRS  RS = new TRS(con);
   int loc_curentfolderid=UserFolder.getCurentFolderID();
        String prepare="if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[tf_"+loc_curentfolderid+"]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)"+
"drop table [dbo].[tf_"+loc_curentfolderid+"]";
 RS.execSQL(prepare);
prepare="select * into tf_"+loc_curentfolderid+
" from "+
"(";
        for(int rowid=0;rowid<dt.atd.size();rowid++)
        {
             int tableid = (Integer.valueOf(
                                  (((TDoc) dt.atd.get(rowid)).o[1]).toString())).intValue();
        prepare+=" select [close],'"+tableid+"' tid,day from ["+tableid+"] "+
                                "where day=(select max(day) from ["+tableid+"])";

        if(rowid<dt.atd.size()-1)prepare+=" union ";
        }
prepare+=") t";
System.out.println(prepare);
  RS.execSQL(prepare);
  
  prepare="select m.menaya_id,m.taseid,m.[name],o.price buye,tf_"+loc_curentfolderid+".[close] lastclose,tf_"+loc_curentfolderid+".day,m.LASTPREDICT"+
" from operations o join deal d "+
" on(d.dealid=o.dealid)"+
" join menaya_base m "+
" on(d.menayaid=m.menaya_id)"+
" join tf_"+loc_curentfolderid+
" on(tf_"+loc_curentfolderid+".tid=m.menaya_id)"+
" where d.folderid="+loc_curentfolderid+
" order by  1 desc";
 System.out.println(prepare);
    }

    private void recalculateall() {
       dit.table.revalidate();
       int s=dit.atd.size();
       float sum=0;
       int ct=0;
       for (int i=0;i<s;i++)
       {
          TDoc t= (TDoc)dit.atd.get(i);
          String formulaid=(String) t.o[7];
          String optypestr=(String) t.o[1];
          String formula="";
           Connection con = BursaAnalizer_Frame.getConnectToDB(
                                 BursaAnalizer_Frame.DBID_USER).con;
            TRS  RS = new TRS(con);
             RS.execSQL("SELECT top 1 FORMULA FROM [FORMULA_T] WHERE FORMULAID=" + formulaid);
            try {
                while (RS.rs.next()) {
                    formula = RS.rs.getString("FORMULA");
                }
            } catch (SQLException ex) {
                Logger.getLogger(DealScreen.class.getName()).log(Level.SEVERE, null, ex);
                formula="ret=price*qtty";
            }
          String price=((Float) t.o[3]).toString();
          String origprice=((Float) t.o[6]).toString();
          String qtty=((Integer) t.o[2]).toString();
          String ret=doonescript(formula,Float.parseFloat(price),Float.parseFloat(qtty),Float.parseFloat(origprice));
           t.o[5]=ret;
           sum+=Float.parseFloat(ret);
           if (optypestr.matches("DIVIDENT")==false)ct+=Integer.parseInt(qtty);
       }
       this.totalqtty.setText(Integer.toString(ct));
       this.totalsum.setText(String.format("%1$.2f", sum));
    }
    
    private String  doonescript(String script,float price,float qtty,float orig)
    {
       ScriptEngineManager manager = new ScriptEngineManager();
    ScriptEngine engine = manager.getEngineByName("javascript");
    String ret="0.0";
    try {
         engine.put("ret", "0.0");
         engine.put("price", Float.toString(price));
         engine.put("orig", Float.toString(orig));
         engine.put("qtty", Float.toString(qtty));
         engine.eval(script);
        ret=engine.get("ret").toString();
    } catch (ScriptException e) 
    {
      System.err.println(e);
    }
    return ret;
    }

}


//~ Formatted by Jindent --- http://www.jindent.com
