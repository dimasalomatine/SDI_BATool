
import DBCONNECT.TRS;
import Utils.LoggerNB;
import Utils.MySimleInputByCombo;
import Utils.TDoc;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.DefaultCellEditor;
import javax.swing.table.TableColumn;



class FollowTable extends SQL_DG implements SQL_DG_OPS {
    MySimleInputByCombo cbfolowtype;    // combo for folow types
    MySimleInputByCombo menayaname;

    //~--- constructors ---------------------------------------------------

    public FollowTable () {
        super (new String[]{
                "MENAYAID", "NAME", "TYPE", "ENABLED", "MINLIMIT", "MAXLIMIT",
                "VOL(MIN)", "VOL(MAX)", "RSI", "MINRSI", "MAXRSI", "AVG(1)",
                "MIN(avg1)", "MAX(avg1)", "AVG(2)", "MIN(avg2)", "MAX(avg2)",
                "AVG(3)", "MIN(avg3)", "MAX(avg3)"
            }, true, new boolean[]{
                false, false, true, true, true, true, true, true, true, true,
                true, true, true, true, true, true, true, true, true, true
            });
        fetchdata(0);
    }

    //~--- methods --------------------------------------------------------

    public void saveAll () {}

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
        initComboxes(con);

        TDoc t = null;
        TRS  RS = new TRS(con);

        try {
            RS.execSQL("SELECT * FROM FOLOW WHERE FOLDERID=" + folder);

            while (RS.rs.next()) {
                t = new TDoc (20);

                Integer ti = new Integer(RS.rs.getInt("MENAYAID"));

                t.o[0] = ti;
                t.o[1] = menayaname.getVc1byVc2(ti.toString());
                ti = new Integer (RS.rs.getInt("TYPE"));
                t.o[2] = cbfolowtype.getVc1byVc2(ti.toString());

                // t.o[2]=new String(cbfolowtype.getVc1byVc2(ti.toString()));
                //t.o[3]  = new Boolean(RS.rs.getBoolean("ENABLED"));
                t.o[3]  = RS.rs.getBoolean("ENABLED");
                t.o[4] = new Float (RS.rs.getFloat("MINLIMIT"));
                t.o[5] = new Float (RS.rs.getFloat("MAXLIMIT"));
                t.o[6] = new Integer (RS.rs.getInt("MINAMOUNT"));
                t.o[7] = new Integer (RS.rs.getInt("MAXAMOUNT"));
                t.o[8] = new Integer (RS.rs.getInt("RSIC"));
                t.o[9] = new Float (RS.rs.getFloat("MINRSI"));
                t.o[10] = new Float (RS.rs.getFloat("MAXRSI"));
                t.o[11] = new Integer (RS.rs.getInt("AVG1"));
                t.o[12] = new Float (RS.rs.getFloat("AVG1_MIN"));
                t.o[13] = new Float (RS.rs.getFloat("AVG1_MAX"));
                t.o[14] = new Integer (RS.rs.getInt("AVG2"));
                t.o[15] = new Float (RS.rs.getFloat("AVG2_MIN"));
                t.o[16] = new Float (RS.rs.getFloat("AVG2_MAX"));
                t.o[17] = new Integer (RS.rs.getInt("AVG3"));
                t.o[18] = new Float (RS.rs.getFloat("AVG3_MIN"));
                t.o[19] = new Float (RS.rs.getFloat("AVG3_MAX"));
                atd.add(t);
            }
        }  catch (SQLException e) {
            if (LoggerNB.debuging){                System.out.println("Error20: " + e);}
        }  finally  {
            RS.close();
            RS = null;
        }

        initAdvancedTableParams();
    }

    public void initAdvancedTableParams () {

        // advanced setings for some columns
        TableColumn tcg;

        // tcg = table.getColumn(columnnames[0]);
        hideColumnN(0);

        // tcg.setMaxWidth(60);
        // tcg.setWidth(60);
        // tcg.setResizable(false);
        tcg = table.getColumn(columnnames[1]);
        tcg.setMaxWidth(200);
        tcg.setWidth(200);
        tcg.setResizable(true);
        tcg.setCellEditor(new DefaultCellEditor (menayaname));
        tcg = table.getColumn(columnnames[2]);
        tcg.setMaxWidth(150);
        tcg.setWidth(150);
        tcg.setResizable(false);
        tcg.setCellEditor(new DefaultCellEditor (cbfolowtype));
        tcg = table.getColumn(columnnames[3]);
        tcg.setMaxWidth(70);
        tcg.setWidth(70);
        tcg.setResizable(false);

        for (int i = 4; i < columnnames.length; i++) {
            tcg = table.getColumn(columnnames[i]);
            tcg.setMaxWidth(70);
            tcg.setWidth(70);
            tcg.setResizable(false);
        }
    }

    void initComboxes (Connection con) {
        cbfolowtype = new MySimleInputByCombo (null, con, "GTYPE", "NAME", "ID");

        Connection con2 = BursaAnalizer_Frame.getConnectToDB(
                                  BursaAnalizer_Frame.DBID_BASE).con;

        menayaname = new MySimleInputByCombo (null, con2, "MENAYA_BASE", "NAME", "MENAYA_ID");
    }

    public void initTableControlPanel () {}

    public void onDeleteBut () {}

    public void onNewBut () {}

    public void onUpdateBut () {}
}