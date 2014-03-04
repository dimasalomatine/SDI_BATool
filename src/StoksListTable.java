import CDate.CDate;
import DBCONNECT.TRS;
import Utils.MySimleInputByCombo;
import Utils.LoggerNB;
import Utils.TDoc;
import java.awt.Color;
import java.awt.Dimension;
import java.beans.BeanDescriptor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
/*
 * StoksListTable.java
 *
 * Created on March 31, 2007, 11:49 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Dmitry
 */
public class StoksListTable extends SQL_DG_NB 
	implements SQL_DG_OPS,TableContrlolPanelNBInterface
	{
	 	public StoksListTable()
	 	{
	 	 super(new String[]{"#","Company","SYMBOL","Name","Market","Industry","Update","Since","TYPE"},
	 	 		true,
	 	 		new boolean[]{false,false,false,false,false,false,true,false,false});	
	 	 fetchdata(null);
	 	 initTableControlPanel();
	 	}
    @Override
             public BeanDescriptor getBeanDescriptor() {
    BeanDescriptor desc = new BeanDescriptor(StoksListTable.class);
    desc.setValue("containerDelegate", "getInnerPane");
    return desc;
}
	TableControlPanelNB tcp;
	
	public void onNewBut(){
            StokRowEditor nrep=new StokRowEditor(!StokRowEditor.EDITFLAG);
            //TODO add on accepted edit or new
	}
	public void onUpdateBut(){
            JTable t=getTable();
            TableModel tm=getTableModel();
            int sr=t.getSelectedRow();
		if(sr>-1)
         	{
                 StokRowEditor nrep=new StokRowEditor(StokRowEditor.EDITFLAG);
                 int menayaid=Integer.parseInt(tm.getValueAt(sr,0).toString());
                 String s=(String) tm.getValueAt(sr,2);
                 String sn=(String) tm.getValueAt(sr,3);
                 String cn=(String) tm.getValueAt(sr,1);
                 String mn=(String) tm.getValueAt(sr,4);
                 String in=(String) tm.getValueAt(sr,5);
                 String tn=(String) tm.getValueAt(sr,8);
                 boolean uf= ((Boolean) tm.getValueAt(sr,6)).booleanValue();
                 nrep.setMenayaID(menayaid);
                 nrep.setStokSymbol(s);
                 nrep.setStok(sn);
                 nrep.setCompany(cn);
                 nrep.setMarket(mn);
                 nrep.setIndustry(in);
                 nrep.setType(tn);
                 nrep.setUpdate(uf);
                 nrep.setTM(sr,tm,this);
                }else JOptionPane.showMessageDialog(this,
         	 						   "Update ? :)",
         	 								"But nothing is selected !!!!",
         	 								JOptionPane.INFORMATION_MESSAGE);
	}
	public void onDeleteBut(){
                int sr=getTable().getSelectedRow();
		int menayaid;
		if(sr>-1)
         	{
                    menayaid=Integer.parseInt(getTableModel().getValueAt(sr,0).toString());
                    if(JOptionPane.showConfirmDialog(this,
         	 							"<HTML><p><center>During this operation <br>Refferenced data and relevant tables will be totaly lost<br> so make sure You want it !!!!",
         	 							"Atention!!! Important !!!",
         	 							JOptionPane.YES_NO_OPTION)==JOptionPane.NO_OPTION)return;
                    //here delete table of this stok
                    Connection con=BursaAnalizer_Frame.getConnectToDB(BursaAnalizer_Frame.DBID_DATA).con;
	            String str="DROP TABLE ["+menayaid+"]";
                    TRS RS=new TRS(con);
                    RS.execSQL(str);
                    //delete row in stok list
                    con=BursaAnalizer_Frame.getConnectToDB(BursaAnalizer_Frame.DBID_BASE).con;
                    str="delete from MENAYA_BASE where MENAYA_ID="+menayaid;
                     RS=new TRS(con);
                     RS.execSQL(str);
                    //also in current view
                    atd.remove(sr);
                    revalidate(); 
                }else JOptionPane.showMessageDialog(this,
         	 						   "Delete ? :)",
         	 								"But nothing is selected !!!!",
         	 								JOptionPane.INFORMATION_MESSAGE);
        
	}
	public void saveAll()
	{
		checkuncheck(0,atd.size(),true,false);
	}
    @Override
	public void saveAll(int record)
	{
		checkuncheck(record,record+1,true,false);
	}
	MySimleInputByCombo cbind;//combo for industry types
	MySimleInputByCombo cbmkt;//combo for market types
	MySimleInputByCombo cbcomp;//combo for company types
        MySimleInputByCombo cbSType;//combo for stock types
		
	void initComboxes(Connection con)
	{
	 cbind=new MySimleInputByCombo(null,
	 								con,
	 								"[INDUSTRY_T]",
	 								"INDUSTRY",
	 								"ID");
	 cbmkt=new MySimleInputByCombo(null,
	 								con,
	 								"[MARKET_T]",
	 								"MARKET",
	 								"ID");
	 cbcomp=new MySimleInputByCombo(null,
	 								con,
	 								"[COMPANY_T]",
	 								"COMPANY",
	 								"ID");
         cbSType=new MySimleInputByCombo(null,
	 								con,
	 								"[STOCKTYPE_T]",
	 								"STOCKTYPE",
	 								"ID");
        }	
	public void fetchdata(int cr)
	{
	if(cr==0)
	 {
	 	atd.clear();
	 	return;
	 }
	}
         public void fetchdata ()
         {
           fetchdata(null);
         }
        public String filterbymarket="";
        public String filterbysymbol="";
        public String filterbydesc="";
        public String filterbyupdatable="";
	public void fetchdata(String filters[])
	{	
	 fetchdata(0);
         /*no conecter test
         int test=1;
         if(test==1)return;
          */
	 Connection con=BursaAnalizer_Frame.getConnectToDB(BursaAnalizer_Frame.DBID_BASE).con;
	 initComboxes(con);
	 Integer ti=new Integer(0);
	 TDoc t=null;
	 TRS RS=new TRS(con);
	 CDate ddd;
        if(filters!=null) {filterbymarket=filters[0];filterbysymbol=filters[1];filterbydesc=filters[2];filterbyupdatable=filters[3];}
         String filter="";
         if(!filterbymarket.isEmpty()||!filterbysymbol.isEmpty()||!filterbydesc.isEmpty())
             filter =" where ";
        if(!filterbymarket.isEmpty()){filter+="  [MARKET_T].MARKET = '"+filterbymarket+"'";}
         else 
             if (!filterbysymbol.isEmpty()) {
             filter+= "  [MENAYA_BASE].NAME like '%" + filterbysymbol + "%'";
         }
         else if (!filterbydesc.isEmpty()) {
             filter+= "  [INDUSTRY_T].INDUSTRY like '%" + filterbydesc + "%'";
         }

         if(!filterbyupdatable.isEmpty()){
          if(filter.isEmpty())filter=" where ";
          else filter+=" and ";
          filter+="isnull([MENAYA_BASE].[UPDATE],'')=1";
         }

	 try {
                 String query="SELECT [MENAYA_BASE].MENAYA_ID,[MENAYA_BASE].COMPANY_ID,[MENAYA_BASE].SYMBOL," +
                          "[MENAYA_BASE].NAME,[MENAYA_BASE].MARKET_ID,[MENAYA_BASE].INDUSTRY_ID," +
                          "[MENAYA_BASE].[UPDATE],[MENAYA_BASE].SINCE,[MENAYA_BASE].STOCKTYPE_ID," +
                          "[MENAYA_BASE].MFRISK,[MENAYA_BASE].MFEXPE"+
                          " FROM [MENAYA_BASE]  " +
                          "  join [MARKET_T] on( [MENAYA_BASE].MARKET_ID = [MARKET_T].ID)"+
                          " left join [INDUSTRY_T] on ([INDUSTRY_T].id=[MENAYA_BASE].INDUSTRY_ID)"+
                          (!filter.isEmpty()? filter:"")+
                          " ORDER BY [MENAYA_BASE].NAME ASC";
	 	  RS.execSQL(query);
	 	while(RS.rs.next())
     		{
     		 t=new TDoc(9);
     		 t.o[0]=new Integer(RS.rs.getInt("MENAYA_ID"));
     		 ti=new Integer(RS.rs.getInt("COMPANY_ID"));
     		 t.o[1]=cbcomp.getVc1byVc2(ti.toString());
     		 t.o[2]=RS.rs.getString("SYMBOL");
                 if(RS.rs.wasNull())t.o[2]="";
     		 t.o[3]=RS.rs.getString("NAME");
                 if(RS.rs.wasNull())t.o[3]="";
     		 ti=new Integer(RS.rs.getString("MARKET_ID"));
     		 t.o[4]=cbmkt.getVc1byVc2(ti.toString());
     		 ti=new Integer(RS.rs.getInt("INDUSTRY_ID"));
     		 t.o[5]=cbind.getVc1byVc2(ti.toString());
                 t.o[6]=RS.rs.getBoolean("UPDATE");
                 
     		 try
     		 {
     		 	ddd=new CDate(RS.rs.getDate("SINCE"));
     		    t.o[7]=ddd.toString();
     		 }catch(Exception e)
     		 {
     		  t.o[7]="";	
     		 }
                 ti=new Integer(RS.rs.getInt("STOCKTYPE_ID"));
     		 t.o[8]=cbSType.getVc1byVc2(ti.toString());
     		 atd.add(t);
     		}
         }
         catch (SQLException e) 
            {
            if(LoggerNB.debuging){System.out.println("Error20: " + e);}
        	}
        	finally
        	{
        		RS.close();
     		    RS=null;
        	}
        	initAdvancedTableParams();

            TableModel tm=getTableModel();
            int mrc=tm.getRowCount();
              JTable t2=getTable();
        Dimension preferredSize = t2.getPreferredSize();

                  preferredSize.height=16*mrc+20;
                  t2.setPreferredSize(preferredSize);
	}
	public void initAdvancedTableParams()
		{
                    JTable t=getTable();
			TableColumn tcg=t.getColumn(columnnames[5]);
		//tcg.setMaxWidth(200);
		//tcg.setWidth(128);
		//tcg.setResizable(false);
                hideColumnN(0);
		tcg.setCellEditor(new DefaultCellEditor(cbind));
		tcg=t.getColumn(columnnames[1]);
		tcg.setCellEditor(new DefaultCellEditor(cbcomp));
		tcg=t.getColumn(columnnames[4]);
		tcg.setMaxWidth(60);
		tcg.setWidth(60);
		tcg.setResizable(false);
		tcg.setCellEditor(new DefaultCellEditor(cbmkt));
		tcg=t.getColumn(columnnames[0]);
		tcg.setMaxWidth(60);
		tcg.setWidth(40);
		tcg.setResizable(false);
		tcg=t.getColumn(columnnames[6]);
		tcg.setMaxWidth(50);
		tcg.setWidth(50);
		tcg.setResizable(false);
		tcg=t.getColumn(columnnames[7]);
		tcg.setMaxWidth(70);
		tcg.setWidth(70);
		tcg.setResizable(false);
		DefaultTableCellRenderer coloredcelbydateupdated= new DefaultTableCellRenderer()
		{
			public void setValue(Object value)
			{
				setForeground(Color.blue);
				setBackground(Color.white);
				setToolTipText("Valid data point :)");
				if(value instanceof String)
				{
					String tv=value.toString();
					if(tv.length()>5)
					{
						CDate its=new CDate(tv);
						CDate now=new CDate();
						long diff=now.diff(its);
						if(diff>=7){setForeground(Color.green);setToolTipText("It is more then 7 days old");}
						if(diff>=14){setForeground(Color.red);setToolTipText("It is more then two weeks old");}
						setText(its.toString());
					}else {
							setForeground(Color.black);
							setBackground(Color.red);
							setToolTipText("This data point missed on local mashine or it is not exist on remote.Probably you can disable it or even remove.");
							setText("miss");
						  }
				}
			}
		};
		tcg.setCellRenderer(coloredcelbydateupdated);
			}

    public void initTableControlPanel ()
    {
        //throw new UnsupportedOperationException("Not yet implemented");
    }
    public void checkuncheck(int start,int end,boolean useself,boolean state)
	{
	    int i,id;
            boolean upd;
            Connection con=BursaAnalizer_Frame.getConnectToDB(BursaAnalizer_Frame.DBID_BASE).con;
            String sqlstr="UPDATE MENAYA_BASE SET [UPDATE]=? WHERE [MENAYA_ID]=?";
            try
            {
		PreparedStatement pstmt = con.prepareStatement(sqlstr);
		for(i=start;i<end;i++)
		{
                	 if(useself&&((TDoc) atd.get(i)).changed)
			 {
                        	id=(Integer.valueOf((((TDoc) atd.get(i)).o[0]).toString())).intValue();
				upd=(Boolean.valueOf((((TDoc) atd.get(i)).o[6]).toString())).booleanValue();
				pstmt.setBoolean(1, upd);
   				pstmt.setInt(2, id);
				pstmt.executeUpdate();
				//if i here to not save twise then set changed to false
				((TDoc) atd.get(i)).changed=false;
			 }else
                            {
                                id=(Integer.valueOf((((TDoc) atd.get(i)).o[0]).toString())).intValue();
				upd=(Boolean.valueOf((((TDoc) atd.get(i)).o[6]).toString())).booleanValue();
				pstmt.setBoolean(1, state);
   				pstmt.setInt(2, id);
				pstmt.executeUpdate();	
                            }
                }
             pstmt.close ();
            }catch (SQLException ioe) 
		{ 
                	if(LoggerNB.debuging){System.out.println("1 100 SQLException happened. "+ioe);  }
      		} 	
	}
 }
