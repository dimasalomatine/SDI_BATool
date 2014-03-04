
import DBCONNECT.TRS;
import Utils.LoggerNB;
import Utils.TDoc;
import java.sql.Connection;
import java.sql.SQLException;


//~--- inner classes ------------------------------------------------------

class CompanyTable extends SQL_DG implements SQL_DG_OPS {
    public CompanyTable () {
        super (new String[]{ "ID", "Company", "Home" }, true, new boolean[]{ false,
                                  true, true });
        fetchdata();
    }

    //~--- methods --------------------------------------------------------

    public void saveAll () {}

    public void saveAll (int record) {}

    public void fetchdata () {
        Connection con = BursaAnalizer_Frame.getConnectToDB(
                                 BursaAnalizer_Frame.DBID_BASE).con;
        TDoc t = null;
        TRS  RS = new TRS(con);

        try {
            RS.execSQL("SELECT * FROM COMPANY_T");

            while (RS.rs.next()) {
                t = new TDoc (3);
                t.o[0] = new Integer (RS.rs.getInt("ID"));
                t.o[1] = RS.rs.getString("COMPANY");
                t.o[2] = RS.rs.getString("HOME");
                atd.add(t);
            }
        }  catch (SQLException e) {
            if (LoggerNB.debuging){                System.out.println("Error20: " + e);}
        }  finally {
            RS.close();
            RS = null;
        }

        initAdvancedTableParams();
    }

    public void fetchdata (int r) {}

    public void fetchdata (String filters[]){}

    public void initAdvancedTableParams () {
        hideColumnN(0);
    }

    public void initTableControlPanel () {}

    public void onDeleteBut () {}

    public void onNewBut () {}

    public void onUpdateBut () {}
}