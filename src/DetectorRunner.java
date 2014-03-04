//~--- non-JDK imports --------------------------------------------------------

import DBCONNECT.TRS;
import Utils.TDoc;
import LocalUtils.ThreadBasic;

import TechnicalTools.RSI;

//~--- JDK imports ------------------------------------------------------------

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

//~--- classes ----------------------------------------------------------------
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dmitry
 */
public class DetectorRunner extends ThreadBasic {
    MoneyDetector mdp = null;

    //~--- constructors -------------------------------------------------------

    /**
     * Method DetectorRunner
     */
    public DetectorRunner(MoneyDetector mdpanel) {
        super("DetectorRunner");
        this.mdp = mdpanel;
        dorun();
    }

    public DetectorRunner(MoneyDetector mdpanel, String name) {
        super(name);
        this.mdp = mdpanel;
        dorun();
    }

    //~--- methods ------------------------------------------------------------

    public void dorun() {
        super.dorun();
        mdp.Stop.setEnabled(true);
        mdp.Run.setEnabled(false);
    }

    public void dostop() {
        super.dostop();
        mdp.Stop.setEnabled(false);
        mdp.Run.setEnabled(true);
    }

    public void run() {
        int skiped = 0;

        mdp.detp.clearAll();

        int       i                = 0;
        StockListNB sli              =
            BursaAnalizer_Frame.getStoksListInstance();
        boolean   usersi1          = mdp.tapsetings.findByRSI1.isSelected();
        float     rsi1min          =
            Float.parseFloat(mdp.tapsetings.rsi1minval.getText());
        float     rsi1max          =
            Float.parseFloat(mdp.tapsetings.rsi1maxval.getText());
        int       rsi1period       = mdp.tapsetings.rsicb1s.getValue();
        boolean   usersi2          = mdp.tapsetings.findByRSI2.isSelected();
        float     rsi2min          =
            Float.parseFloat(mdp.tapsetings.rsi2minval.getText());
        float     rsi2max          =
            Float.parseFloat(mdp.tapsetings.rsi2maxval.getText());
        int       rsi2period       = mdp.tapsetings.rsicb2s.getValue();
        double    dataRSI[]        = null;
        boolean   useclosechup     = mdp.tapsetings.usecdchup.isSelected();
        int       closechupperiod  = mdp.tapsetings.cdchsup.getValue();
        float     closechuppercent =
            Float.parseFloat(mdp.tapsetings.closechpup.getText());
        boolean useclosechdown     = mdp.tapsetings.usecdchdown.isSelected();
        int     closechdownperiod  = mdp.tapsetings.cdchsdown.getValue();
        float   closechdownpercent =
            Float.parseFloat(mdp.tapsetings.closechpdown.getText());
        boolean usevolchup     = mdp.tapsetings.usevolchup.isSelected();
        int     volchupperiod  = mdp.tapsetings.volchsup.getValue();
        float   volchuppercent =
            Float.parseFloat(mdp.tapsetings.volumechpup.getText());
        boolean usevolchdown     = mdp.tapsetings.usevolchdown.isSelected();
        int     volchdownperiod  = mdp.tapsetings.volchsdown.getValue();
        float   volchdownpercent =
            Float.parseFloat(mdp.tapsetings.volumechpdown.getText());
        boolean userrrtest=mdp.tapadvsetings.RRTest.isSelected();
        float rrrmaxvalue=Float.parseFloat(mdp.tapadvsetings.RRVal.getText());
         int     rrrperiod = mdp.tapadvsetings.rrrperiod.getValue();
          float rrrbelt=Float.parseFloat(mdp.tapadvsetings.rrrbelt.getText());
        // local intensive used vars
        int     menayaid;
        String  menayaSymbol = "";
        String  eventmsg     = "";
        TDoc lastdetected = null;
        Float   plast, pfirst;
        float   pdiff = 0.0f;

        //
        // set progress bar
        mdp.detp.progress.setMinimum(0);
        mdp.detp.progress.setMaximum(sli.sltp.slt.atd.size());
        mdp.detp.progress.setValue(0);

        DetectorTests dto = new DetectorTests(mdp);

        while (isRuning() && (i < sli.sltp.slt.atd.size())) {

            TDoc totcheck=(TDoc) (sli.sltp.slt.atd.get(i));
            // test only symbol that has update flag or test all if check all enabled
            // otherwise skip and proceed to next stok
            if (!((Boolean
                    .valueOf(totcheck.o[6].toString()))
                        .booleanValue() == true || mdp.detp.runOnlyUpdateable
                        .isSelected())) {
                i++;
                skiped++;
                mdp.detp.progress.setValue(i);

                continue;
            }
           //test for options too
            if(!mdp.detp.runOnOptions.isSelected()&&totcheck.o[8].toString().equalsIgnoreCase("OPTION"))
            {
                i++;
                skiped++;
                mdp.detp.progress.setValue(i);

                continue;
            }
            
            menayaid = Integer.parseInt(
                (((TDoc) sli.sltp.slt.atd.get(i)).o[0]).toString());
            menayaSymbol = (String) ((TDoc) sli.sltp.slt.atd.get(i)).o[2];

            //test if symbol last date not older then some date
            if(olderthanXdays(mdp.detp.notOlderThan.isSelected(),mdp.detp.notolderdays.getValue(),menayaid))
                {
                i++;
                skiped++;
                mdp.detp.progress.setValue(i);

                continue;
            }
             // load data
            java.util.List ttradedata = new ArrayList();

            GenericTableLoaders.loadTradeDataTableToOut(7, menayaid,
                    ttradedata);

            // if(ttradedata.size()==0)assert(true);
            // calculate diff %
            GenericTableLoaders.calculateDiffARtoPreviousA(ttradedata, 4,6,true);

            // here call or do your function tests
            if (useclosechup) {
                dto.testCloseChangeUp(ttradedata, menayaid, menayaSymbol,
                                      closechupperiod, closechuppercent);
            }

            if (useclosechdown) {
                dto.testCloseChangeDown(ttradedata, menayaid, menayaSymbol,
                                        closechdownperiod, closechdownpercent);
            }

            if (usevolchup) {
                dto.testVolumeChangeUp(ttradedata, menayaid, menayaSymbol,
                                       volchupperiod, volchuppercent);
            }

            if (usevolchdown) {
                dto.testVolumeChangeDown(ttradedata, menayaid, menayaSymbol,
                                         volchupperiod, volchuppercent);
            }

            if (usersi1) {
                dto.testRSI_I(ttradedata, menayaid, menayaSymbol, rsi1period,
                              rsi1min, rsi1max);
            }

            if (usersi2) {
                dto.testRSI_II(ttradedata, menayaid, menayaSymbol, rsi2period,
                               rsi2min, rsi2max);
            }
            
            if(userrrtest)
            {
                dto.RRRTest(ttradedata,  menayaid,menayaSymbol, rrrperiod, rrrmaxvalue,rrrbelt,1000.0f ); 
            }
            i++;
            mdp.detp.progress.setValue(i);
            mdp.detp.updateTotalProcessedAndSkiped(i, skiped);
            dosleep();
        }

        if(this.mdp.detp.skipflats.isSelected())
        {
          mdp.detp.removerejecteddata("FLAT");
        }
        if(this.mdp.tapadvsetings.leaveonlyagreed.isSelected())
        {
         mdp.detp.removerejecteddata("Rejected");
        }
        mdp.detp.updateTotalProcessedAndSkiped(i, skiped);
        dostop();
    }

    private boolean olderthanXdays(boolean selected, int value, int menayaid) {
        if(!selected)return false;
         Connection con=BursaAnalizer_Frame.getConnectToDB(BursaAnalizer_Frame.DBID_USER).con;
       TRS rs=new TRS(con);
       String sql="SELECT max(day) from ["+menayaid+"]";
       sql+=" having datediff(dd,max(day),getdate())>"+value;
        ResultSet rst= rs.execSQL(sql);
        try {
            if (rst != null && rst.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DetectorRunner.class.getName()).log(Level.SEVERE, null, ex);
        }
         return false;
    }

    
}


//~ Formatted by Jindent --- http://www.jindent.com
