//~--- non-JDK imports --------------------------------------------------------

import CDate.CDate;
import DBCONNECT.DataBaseConnector;
import DBCONNECT.TRS;
import Utils.LoggerNB;

//~--- JDK imports ------------------------------------------------------------

import java.awt.*;
import java.awt.event.*;

import java.sql.*;

import javax.swing.*;
import javax.swing.event.*;

//~--- classes ----------------------------------------------------------------

public class BursaAnalizer_Frame extends JFrame {
    public static final int                   DBID_USER  = 1;
    public static final int                   DBID_DATA  = 3;
    public static final int                   DBID_BASE  = 2;
    private static String              dSusername = null;
    private static String              dSpassword = null;
    static final public SDI_Properties p          = new SDI_Properties();
    //private static final int           TFSPY      = 10;

    // some constants
    /*
    private static final int TFSPX = 10;     // window starts position
    private static final int TFDY  = 600;
    private static final int TFDX  = 800;    // window dimensions
     **/
    private static String    tl[]  = {
        "Wellcome", "Stocks", "Data", "Company", "User folder", "Follow",
        "Deal", "Graph", "Money-Detector", "Maintenance","Stock Risk","PredictionsHist"
    };
    static String              timgdir = "../images/";
    private static JTabbedPane tabs    = new JTabbedPane(JTabbedPane.TOP);    // JTabbedPane.RIGHT);
    static LoggerNB                       logger         =
        new LoggerNB(false, false, "errlog.errs");
    static final int                       CAN_UPD_REC    = 2;
    static final int                       CAN_DROP_TABLE = 5;
    static final int                       CAN_DEL_REC    = 1;
    static final int                       CAN_CR_TABLE   = 3;
    static final int                       CAN_ALT_TABLE  = 4;
    static final int                       CAN_ADD_REC    = 0;
    static private AppSettingsNB           apps;
    static private DataBaseConnector    base;
    static private Company                 cl;
    static private DataBaseConnector    data;
    static private DealScreen              ds;
    static private BursaAnalizer_Frame firstlayerinstance;
    static private Follow                  fl;
    static private StokGraph               sg;
    static private StockListNB               sl;
    static private TradeDataListNB           td;
    static private UserFolder              uf;
    static private DataBaseConnector    users;
    static private WellcomeScreen          ws;
    static private stockRisk sr;
    static private MoneyDetector mdes;
    static private PredictionsHist phist;
    static private String uiInterLang;
    static public String getUIInterLan(){return uiInterLang;}
    static public void setUIInterLan(String lang)
    {
        uiInterLang=lang;
        p.setProperty("UILANG", uiInterLang);
    }
    
    //~--- constructors -------------------------------------------------------

    public BursaAnalizer_Frame() {
        firstlayerinstance = this;

        Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();

        setBounds(0, 0, ss.width, ss.height-20);

        init2s();
        init();
    }

    //~--- methods ------------------------------------------------------------

    //this is database updater threaded version
    static DataBaseUpdater dbtu=null;
    
    static void check_constinguesy(boolean initialstate) {
        boolean tb = (Boolean.valueOf(
                         BursaAnalizer_Frame.p.getProperty(
                             "update.auto", "false"))).booleanValue();

        if (initialstate || tb) {
            LoggerNB.getLogger().log("app self check! wait...",
                                      LoggerNB.INFORMATIVE);
            if(dbtu==null)dbtu= new DataBaseUpdater(firstlayerinstance);
            else dbtu.dorun();
        }
    }
    
    public void init() {
        tabs.addTab(tl[0], ws);
        sl = new StockListNB();
        tabs.addTab(tl[1], sl);
        td = new TradeDataListNB();
        tabs.addTab(tl[2], td);
        cl = new Company();
        tabs.addTab(tl[3], cl);
        uf = new UserFolder();
        tabs.addTab(tl[4], uf);
        fl = new Follow();
        tabs.addTab(tl[5], fl);
        ds = new DealScreen();
        tabs.addTab(tl[6], ds);
        
        sg = new StokGraph();
        tabs.addTab(tl[7], sg);
         mdes=new MoneyDetector ();
        tabs.addTab(tl[8], mdes);
        apps = new AppSettingsNB();
        tabs.addTab(tl[9], apps);
        sr=new stockRisk();
        tabs.addTab(tl[10],sr);
        phist=new PredictionsHist();
        tabs.add(tl[11], phist);
        if(LoggerNB.DEBUG==LoggerNB.DEBUG_DEVELOPER)
        {
        tabs.addTab("AQSQL", new AdvancedOptions());
        tabs.addTab("TESTS", new SDI_TEST_UNIT());
        tabs.addTab("DEVELOPER", new EXporters());
        }
        tabs.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {

                //
            }
        });
        showLogger((Boolean.valueOf(p.getProperty("logger.auto",
                "false"))).booleanValue());
        getContentPane().add(tabs);
        mySetTabsPlacement((Boolean.valueOf(p.getProperty("tabs.RT",
                "true"))).booleanValue());
    }

    public void init2s() {
        p.initpropertiesfile(null);
        uiInterLang=p.getProperty("UILANG", "LANGen");
        timgdir = p.getProperty("app.imagsetdir", timgdir);
        ws      = new WellcomeScreen();
        ws.setUsername(p.getProperty("server.username", "anonym"));
        ws.setPassword(p.getProperty("server.password", "123456"));

        // same user and pass for server and for user database
        String servertype = p.getProperty("serverdriver.type");

        ws.setAutoLogin(p.getProperty("server.autologin", "false"),
                        servertype);
        check_constinguesy(false);
    }

    public void mySetTabsPlacement(boolean stat) {
        if (stat) {
            tabs.setTabPlacement(JTabbedPane.RIGHT);
        } else {
            tabs.setTabPlacement(JTabbedPane.TOP);
        }

        getContentPane().validate();
    }

    @Override
    public void processWindowEvent(WindowEvent we) {
        if (we.getID() == WindowEvent.WINDOW_CLOSING) {
            windowClosed();
        }
    }

    public void showLogger(boolean stat) {
        if (!stat) {
            getContentPane().remove(logger);
        } else {
            getContentPane().add(BorderLayout.SOUTH, logger);
        }
        getContentPane().validate();
    }

    public static void switchToTab(int idx) {
        tabs.setSelectedIndex(idx);
    }

    public static void switchToTab(String tname) {
        for (int i = 0; i < tl.length; i++) {
            if (tname.equals(tl[i])) {
                switchToTab(i);
                return;
            }
        }
    }

    /**
     * Shutdown procedure when run as an application.
     */
    protected void windowClosed() {
        ws.disconnectFromDBS();
        p.savepropertiesfile();

        // Exit application.
        System.exit(0);
    }
    public void callToShootDown()
    {
     windowClosed();
    }
    //~--- get methods --------------------------------------------------------

    public static AppSettingsNB getAppSettingsInstance() {
        return apps;
    }

    public static Company getCompanyInstance() {
        return cl;
    }

    static public DataBaseConnector getConnectToDB(int DBID) {
        if (DBID == DBID_USER) {
            return users;
        }

        if (DBID == DBID_BASE) {
            return base;
        }

        if (DBID == DBID_DATA) {
            return data;
        }

        return null;
    }

    static public boolean[] getCurentUserPrivilegies() {
        TRS  rs    = new TRS(users.con);
        int     tci   = ws.getCurentUserID(), tsi;
        boolean ret[] = new boolean[6];
        
        //TODO implement allert on not registered users
        allertNotGeneralyRegisteredCustomer();
        try {
            rs.execSQL("SELECT USER_STATUS from USERS WHERE USERID=" + tci);

            if (rs.rs.next()) {
                tsi = rs.rs.getInt("USER_STATUS");
                rs.reuse();
                rs.execSQL("SELECT * FROM USER_STATUS WHERE STATUSID=" + tsi);

                if (rs.rs.next()) {
                    ret[CAN_ADD_REC]    = rs.rs.getBoolean("CAN_ADD_REC");
                    ret[CAN_DEL_REC]    = rs.rs.getBoolean("CAN_DEL_REC");
                    ret[CAN_UPD_REC]    = rs.rs.getBoolean("CAN_UPD_REC");
                    ret[CAN_CR_TABLE]   = rs.rs.getBoolean("CAN_CR_TBL");
                    ret[CAN_ALT_TABLE]  = rs.rs.getBoolean("CAN_ALT_TBL");
                    ret[CAN_DROP_TABLE] = rs.rs.getBoolean("CAN_DROP_TBL");
                }
            }
        } catch (SQLException e) {
            System.err.println("Err " + e);
        } finally {
            rs.close();
        }

        return ret;
    }

    public static DealScreen getDealScreenInstance() {
        return ds;
    }

     static public MoneyDetector getMoneyDetectorInstance ()
    {
        return mdes;
    }
    public static Follow getFollowInstance() {
        return fl;
    }

    static public String getImgDir() {
        return timgdir;
    }

    static public BursaAnalizer_Frame getInstance() {
        return firstlayerinstance;
    }

    public static StokGraph getStockGraphInstance() {
        return sg;
    }

    public static StockListNB getStoksListInstance() {
        return sl;
    }
    
    public static stockRisk getStoksRiskInstance() {
        return sr;
    }

    public static TradeDataListNB getTradeDataListInstance() {
        return td;
    }

    public static UserFolder getUserFolderInstance() {
        return uf;
    }

    public static WellcomeScreen getWellcomeScreenInstance() {
        return ws;
    }

    //~--- set methods --------------------------------------------------------

    static public void setConnectToDB(int DBID, DataBaseConnector tcon) {
        if (DBID == DBID_USER) {
            users = tcon;
        } else if (DBID == DBID_BASE) {
            base = tcon;
        } else if (DBID == DBID_DATA) {
            data = tcon;
        }
    }

    private static void allertNotGeneralyRegisteredCustomer() {
        String lastvoted=p.getProperty("ONserver.voted", "now()");
        boolean itisoktovote=false;
        if(lastvoted.equalsIgnoreCase("now()"))
        {
          itisoktovote=true;
        }else
        {
            CDate lv=new CDate(lastvoted);
            CDate today=new CDate();
            if(today.diff(lv)>30)itisoktovote=true;
        }
        if(itisoktovote)
        {
        GeneralyRU gr=new GeneralyRU();
        gr.setVisible(true);
        }
        }

    void updateCurentOpenViews() {
        //update curent stok view
        sl.sltp.slt.fetchdata(null);
        //update curent data view if loaded
        td.reInitOnData();
        //and also update grapg if it ising data
        sg.setDataPoints(td.lblSymbol.getText(),td.getTradeDataList());
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
