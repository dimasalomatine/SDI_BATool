
import CDate.CDate;
import DBCONNECT.TRS;
import Utils.LoggerNB;
import Utils.MySimleInputByCombo;
import Utils.TDoc;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.table.TableColumn;


//~--- inner classes ------------------------------------------------------

class DealInfoTable extends SQL_DG implements SQL_DG_OPS {
    int dealid;
    MySimleInputByCombo formula;    // combo for folow types
    MySimleInputByCombo optype;     // combo for folow types

    //~--- constructors ---------------------------------------------------

    public DealInfoTable () {
        super (new String[]{
                "Date", "Operation", "Qtty", "Price", "FORMULA", "Total","OriginalP"
            }, true, new boolean[]{
                false, true, true, true, true, false,false
            });
        fetchdata(0);
    }

    //~--- methods --------------------------------------------------------

    public void saveAll () {}

    public void saveAll (int record) {}

    public void fetchdata () {}
     public void fetchdata (String filters[]){}

    public void fetchdata (int deal) {
        dealid = deal;

        if (deal == 0) {
            atd.clear();

            return;
        }

        Connection con = BursaAnalizer_Frame.getConnectToDB(
                                 BursaAnalizer_Frame.DBID_USER).con;

        initComboxes(con);

        TDoc t = null;
        TRS  RS = new TRS(con);
        Integer ti;
        Integer tf;

        try {
            RS.execSQL("SELECT * FROM OPERATIONS WHERE DEALID=" + deal+" order by DATE");

            while (RS.rs.next()) {
                t = new TDoc (9);

                // t.o[0]=new Integer(RS.rs.getInt("DEALID"));
                ti = new Integer (RS.rs.getInt("OP_TYPE"));
                t.o[1] = optype.getVc1byVc2(ti.toString());
                t.o[2] = new Integer (RS.rs.getInt("QTTY"));
                t.o[3] = new Float (RS.rs.getFloat("PRICE"));
                t.o[6] = new Float (RS.rs.getFloat("orig"));
                tf = new Integer (RS.rs.getInt("FORMULAID"));
                t.o[4] = formula.getVc1byVc2(tf.toString());
                t.o[5] = "0.0";
                t.o[7]=tf.toString();
                t.o[8] = new Integer (RS.rs.getInt("counter"));
                CDate ddd;

                try {
                    ddd = new CDate (RS.rs.getDate("DATE"));
                    t.o[0] = ddd.toString();
                }  catch (Exception e) {
                    t.o[0] = "";
                }

                atd.add(t);
            }
        }  catch (SQLException e) {
            if (LoggerNB.debuging){                System.out.println("Error20: " + e);}
        }   finally {
            RS.close();
            RS = null;
        }

        initAdvancedTableParams();
    }

    public void initAdvancedTableParams () {

        // advanced setings for some columns
        TableColumn tcg;

        tcg = table.getColumn(columnnames[0]);
        tcg.setMaxWidth(60);
        tcg.setWidth(60);
        tcg.setResizable(false);
        tcg = table.getColumn(columnnames[1]);
        tcg.setMaxWidth(110);
        tcg.setWidth(110);
        tcg.setResizable(false);
        tcg.setCellEditor(new DefaultCellEditor (optype));
        tcg = table.getColumn(columnnames[2]);
        tcg.setMaxWidth(50);
        tcg.setWidth(50);
        tcg.setResizable(true);
        tcg = table.getColumn(columnnames[3]);
        tcg.setMaxWidth(50);
        tcg.setWidth(50);
        tcg.setResizable(true);
        tcg = table.getColumn(columnnames[4]);
        tcg.setMaxWidth(120);
        tcg.setWidth(120);
        tcg.setResizable(false);
        tcg.setCellEditor(new DefaultCellEditor (formula));
        tcg = table.getColumn(columnnames[5]);
        tcg.setMaxWidth(70);
        tcg.setWidth(70);
        tcg.setResizable(true);
        tcg = table.getColumn(columnnames[6]);
        tcg.setMaxWidth(60);
        tcg.setWidth(50);
        tcg.setResizable(true);
    }

    void initComboxes (Connection con) {
        optype = new MySimleInputByCombo (null, con, "OPTYPES_T", "VAL", "OP_TYPE_ID");
        formula = new MySimleInputByCombo (null, con, "FORMULA_T", "NAME", "FORMULAID");
    }

    public void initTableControlPanel () {}

    public void onDeleteBut () {
    int sr2 = table.getSelectedRow();
    if(sr2>=0)
    {
    TDoc t=(TDoc)atd.get(sr2);
     Connection con = BursaAnalizer_Frame.getConnectToDB(
                                 BursaAnalizer_Frame.DBID_USER).con;
     Integer c=(Integer)t.o[8];
     TRS  RS = new TRS(con);
      RS.execSQL("DELETE FROM OPERATIONS WHERE counter=" + c.intValue());
      
    }
    }

    public void onNewBut () {}

    public void onUpdateBut () {}
}