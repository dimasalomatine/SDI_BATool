import CDate.CDate;
import DBCONNECT.DataBaseConnector;
import DBCONNECT.TRS;
import Utils.LoggerNB;
import java.awt.Color;
import java.beans.BeanDescriptor;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
/*
 * TradeDataListTable.java
 *
 * Created on March 31, 2007, 9:44 PM
 *
 */

/**
 *
 * @author Dmitry
 */
public class TradeDataListTable extends SQL_DG_NB
            implements SQL_DG_OPS ,TableContrlolPanelNBInterface{
        JLabel lblName,lblSymbol;
        private int curentloadedstock;
        //~--- constructors ---------------------------------------------------

        public TradeDataListTable() {
            super(new String[] {
                "Date", "Open", "Hi", "Low", "Close", "Volume", "Diff(%)"
            }, true, new boolean[] {
                false, false, false, false, false, false, false
            });
            
        }
         public BeanDescriptor getBeanDescriptor() {
    BeanDescriptor desc = new BeanDescriptor(TradeDataListTable.class);
    desc.setValue("containerDelegate", "getInnerPane");
    return desc;
}
        public void setFatherLabels(JLabel lblName,JLabel lblSymbol)
        {
         this.lblName=lblName;
         this.lblSymbol=lblSymbol;
        }
        //~--- methods --------------------------------------------------------

        public void saveAll() {}

    @Override
        public void saveAll(int record) {}

        public void fetchdata (String filters[]){fetchdata(0);}
        public void fetchdata() {
            fetchdata(0);
        }

        public void fetchdata(int id) {
            if (id == 0) {
                atd.clear();

                return;
            }

            Connection con = BursaAnalizer_Frame.getConnectToDB(
                                 BursaAnalizer_Frame.DBID_BASE).con;
            TRS RS = new TRS(con);

            try {
                RS.execSQL(
                    "SELECT [SYMBOL],[NAME] FROM MENAYA_BASE WHERE MENAYA_ID="
                    + id);

                if (RS.rs.next())
                {
                    lblName.setText(RS.rs.getString("NAME"));
                    lblSymbol.setText(RS.rs.getString("SYMBOL"));
                }

                GenericTableLoaders.loadTradeDataTableToOut(8, id, atd);
            } catch (SQLException e) {
                if(LoggerNB.debuging){System.out.println("Error20: " + e);}
            } finally {
                RS.close();
                RS = null;
            }
            curentloadedstock=id;
            // calculate diff %
            GenericTableLoaders.calculateDiffARtoPreviousA(atd,4,6,true);
            initAdvancedTableParams();
        }

        public void initAdvancedTableParams() {

            // advanced setings for some columns
            TableColumn tcg = getTable ().getColumn(columnnames[5]);

            tcg.setMaxWidth(100);
            tcg.setWidth(100);
            tcg.setResizable(false);
            tcg = getTable ().getColumn(columnnames[6]);
            tcg.setMaxWidth(50);
            tcg.setWidth(50);
            tcg.setResizable(false);

            DefaultTableCellRenderer coloredcelbychange =
                new DefaultTableCellRenderer() {
                public void setValue(Object value) {
                    if (value instanceof Float) {
                        setForeground(Color.blue);

                        Float tf = (Float) value;

                        if (tf.floatValue() > 0.0f) {
                            setForeground(Color.green);
                        } else if (tf.floatValue() < 0.0f) {
                            setForeground(Color.red);
                        }

                        setText("" + tf);
                    }
                }
            };

            tcg.setCellRenderer(coloredcelbychange);
            tcg = getTable ().getColumn(columnnames[1]);
            tcg.setMaxWidth(80);
            tcg.setWidth(80);
            tcg.setResizable(false);
            tcg = getTable ().getColumn(columnnames[2]);
            tcg.setMaxWidth(80);
            tcg.setWidth(80);
            tcg.setResizable(false);
            tcg = getTable ().getColumn(columnnames[3]);
            tcg.setMaxWidth(80);
            tcg.setWidth(80);
            tcg.setResizable(false);
            tcg = getTable ().getColumn(columnnames[4]);
            tcg.setMaxWidth(80);
            tcg.setWidth(80);
            tcg.setResizable(false);
            tcg = getTable ().getColumn(columnnames[0]);
            tcg.setMaxWidth(80);
            tcg.setWidth(80);
            tcg.setResizable(false);
        }

    public void initTableControlPanel ()
    {
    }

    //TODO implement it
        public void onDeleteBut() 
        {
            int sr = getTable ().getSelectedRow();
            if (sr > -1)
            {
            CDate curseldate=new CDate(getTableModel ().getValueAt(sr, 0).toString());
            PreparedStatement pstmt;
            
            DataBaseConnector data=BursaAnalizer_Frame.getConnectToDB(
                                 BursaAnalizer_Frame.DBID_DATA);
            try{
             pstmt = data.con.prepareStatement("DELETE FROM  ["
                                + curentloadedstock + "] WHERE [DAY]=?");
             pstmt.setDate(1, new java.sql.Date(curseldate.getDateMilis()));
             pstmt.executeUpdate();
             pstmt.close ();
             //refetch data
             fetchdata();
             fetchdata(curentloadedstock);
             repaint ();
             } 
            catch (SQLException e) 
            {
                if(LoggerNB.debuging){System.err.println("Err " + e);}
            }
            }
        }

        TradeDataRowEditDlg tdef=null;
        public void onNewBut() {
            if(tdef!=null){tdef.dispose();tdef=null;}
             tdef =
                new TradeDataRowEditDlg(null,true,!TradeDataRowEdit.EDITFLAG,curentloadedstock);
        }

        public void onUpdateBut() {
            int sr = getTable ().getSelectedRow();

            if (sr > -1) {
                 if(tdef!=null){tdef.dispose();tdef=null;}
                 tdef =
                    new TradeDataRowEditDlg(null,false,TradeDataRowEdit.EDITFLAG,curentloadedstock);

                tdef.setDate(getTableModel ().getValueAt(sr, 0).toString());
                tdef.setOpen(getTableModel ().getValueAt(sr, 1).toString());
                tdef.setClose(getTableModel ().getValueAt(sr, 2).toString());
                tdef.setHi(getTableModel ().getValueAt(sr, 3).toString());
                tdef.setLow(getTableModel ().getValueAt(sr, 4).toString());
                tdef.setVolume(getTableModel ().getValueAt(sr, 5).toString());

               /* if (tdef.accepted)
                {
                ;
                }*/
            }
        }

        
    }
