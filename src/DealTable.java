
import DBCONNECT.TRS;
import Utils.LoggerNB;
import Utils.MySimleInputByCombo;
import Utils.TDoc;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.DefaultCellEditor;
import javax.swing.table.TableColumn;


//~--- inner classes ------------------------------------------------------


class DealTable extends SQL_DG implements SQL_DG_OPS {
    MySimleInputByCombo menayaname;

    //~--- constructors ---------------------------------------------------

    public DealTable () {
        super (new String[]{ "Deal", "MenayaID", "Menaya" }, true, new boolean[]{ false,
                                  false, false });
        fetchdata(0);
    }

    //~--- methods --------------------------------------------------------

    public void saveAll () {}

    @Override
    public void saveAll (int record) {}

    public void fetchdata () {}
     public void fetchdata (String filters[]){}

    public void fetchdata (int folder) {
        if (folder == 0) {
            atd.clear();

            return;
        }

        Connection con = BursaAnalizer_Frame.getConnectToDB(
                                 BursaAnalizer_Frame.DBID_USER).con;

        // Connection con=((msdb_connect)(BursaAnalizer_Frame.users)).con;
        initComboxes();

        TDoc t = null;
        TRS  RS = new TRS(con);

        try {
            RS.execSQL("SELECT * FROM DEAL WHERE FOLDERID=" + folder);

            while (RS.rs.next()) {
                t = new TDoc (3);
                t.o[0] = new Integer (RS.rs.getInt("DEALID"));

                Integer ti = new Integer(RS.rs.getInt("MENAYAID"));

                t.o[1] = ti;
                t.o[2] = menayaname.getVc1byVc2(ti.toString());
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

        hideColumnN(0);
        hideColumnN(1);

        // tcg = table.getColumn(columnnames[0]);
        // tcg.setMaxWidth(60);
        // tcg.setWidth(60);
        // tcg.setResizable(false);
        // tcg = table.getColumn(columnnames[1]);
        // tcg.setMaxWidth(60);
        // tcg.setWidth(60);
        // tcg.setResizable(false);
        tcg = table.getColumn(columnnames[2]);
        tcg.setMaxWidth(200);
        tcg.setWidth(200);
        tcg.setResizable(true);
        tcg.setCellEditor(new DefaultCellEditor (menayaname));
    }

    void initComboxes () {
        Connection con2 = BursaAnalizer_Frame.getConnectToDB(
                                  BursaAnalizer_Frame.DBID_BASE).con;

        menayaname = new MySimleInputByCombo (null, con2, "MENAYA_BASE", "NAME", "MENAYA_ID");
    }

    public void initTableControlPanel () {}

    public void onDeleteBut () {
    int sr=table.getSelectedRow();
    
    }

    public void onNewBut () {}

    public void onUpdateBut () {}
}