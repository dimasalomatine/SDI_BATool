//~--- non-JDK imports --------------------------------------------------------

import Utils.TDoc;

import TechnicalTools.RSI;

/*
 * DetectorTests.java
 *
 * Created on October 18, 2006, 12:47 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

//~--- classes ----------------------------------------------------------------
import TechnicalTools.ResistanseSupportLine;

/**
 *
 * @author Dmitry
 */

/**
 * this is class that contain basic test functions
  * vol,rsi,close up/down change on some perion relevant to percent
 */
public class DetectorTests {

    // local intensive used vars---//
    String                eventmsg     = "";
    TDoc               lastdetected = null;
    float                 pdiff        = 0.0f;

    // ----------------------------//

    private MoneyDetector mdp;
    Float                 plast, pfirst;
    private double lv_epsilon=0.001;

    //~--- constructors -------------------------------------------------------

    /** Creates a new instance of DetectorTests */
    public DetectorTests(MoneyDetector mdpanel) {
        this.mdp = mdpanel;
    }

    //~--- methods ------------------------------------------------------------

    public void testCloseChangeDown(java.util.List ttradedata, int menayaid,
                                    String menayaSymbol,
                                    int closechdownperiod,
                                    float closechdownpercent) {
        if (ttradedata.size() - 1 - closechdownperiod < 0) {
            return;
        }

        plast = (Float) (((TDoc) ttradedata.get(ttradedata.size()
                - 1)).o[4]);
        pfirst = (Float) (((TDoc) ttradedata.get(ttradedata.size() - 1
                - closechdownperiod)).o[4]);
        pdiff = (plast.floatValue() - pfirst.floatValue())
                / pfirst.floatValue();

        if (pdiff <= closechdownpercent / -100.0f) {

            //
            lastdetected      = new TDoc(4);
            lastdetected.o[0] = new Integer(menayaid);
            lastdetected.o[1] = menayaSymbol;
            eventmsg          = "Last " + closechdownperiod
                                + " days DOWN change";
            lastdetected.o[2] = eventmsg;
            lastdetected.o[3] = "" + pdiff;
            mdp.detp.addtoTable(lastdetected);
            mdp.detp.addtotree(menayaSymbol, eventmsg, "" + pdiff);

            //
        }
    }

    public void testCloseChangeUp(java.util.List ttradedata, int menayaid,
                                  String menayaSymbol, int closechupperiod,
                                  float closechuppercent) {
        if (ttradedata.size() - 1 - closechupperiod < 0) {
            return;
        }

        plast = (Float) (((TDoc) ttradedata.get(ttradedata.size()
                - 1)).o[4]);
        pfirst = (Float) (((TDoc) ttradedata.get(ttradedata.size() - 1
                - closechupperiod)).o[4]);
        pdiff = (plast.floatValue() - pfirst.floatValue())
                / pfirst.floatValue();

        if (pdiff >= closechuppercent / 100.0f) {

            //
            lastdetected      = new TDoc(4);
            lastdetected.o[0] = new Integer(menayaid);
            lastdetected.o[1] = menayaSymbol;
            eventmsg          = "Last " + closechupperiod + " days UP change";
            lastdetected.o[2] = eventmsg;
            lastdetected.o[3] = "" + pdiff;
            mdp.detp.addtoTable(lastdetected);
            mdp.detp.addtotree(menayaSymbol, eventmsg, "" + pdiff);

            //
        }
    }

    public void testRSI_I(java.util.List ttradedata, int menayaid,
                          String menayaSymbol, int rsiperiod, float rsimin,
                          float rsimax) {
        double dataRSI[] = RSI.computeRSI(ttradedata, rsiperiod, 4);

        if (dataRSI == null) {
            return;
        }

        if (dataRSI.length == 0) {
            return;
        }

        if ((dataRSI[dataRSI.length - 1] <= rsimin)
                || (dataRSI[dataRSI.length - 1] >= rsimax)) {
            lastdetected      = new TDoc(4);
            lastdetected.o[0] = new Integer(menayaid);
            lastdetected.o[1] = menayaSymbol;
            eventmsg          = "RSI IN RANGE (I)";
            lastdetected.o[2] = eventmsg;
            lastdetected.o[3] = "" + dataRSI[dataRSI.length - 1];
            //
          
            mdp.detp.addtoTable(lastdetected);
            mdp.detp.addtotree(menayaSymbol, eventmsg,"" + dataRSI[dataRSI.length - 1]);
            lastdetected      = new TDoc(4);
            lastdetected.o[0] = new Integer(menayaid);
            lastdetected.o[1] = menayaSymbol;
            eventmsg       = "RSI FLAT";
            lastdetected.o[2] = eventmsg;
            lastdetected.o[3] =isDeadFLATRSI(dataRSI,7);
             mdp.detp.addtoTable(lastdetected);
             mdp.detp.addtotree(menayaSymbol, eventmsg,""+lastdetected.o[3] );
            //
        }
    }

    public void testRSI_II(java.util.List ttradedata, int menayaid,
                           String menayaSymbol, int rsiperiod, float rsimin,
                           float rsimax) {
        double dataRSI[] = RSI.computeRSI(ttradedata, rsiperiod, 4);

        if (dataRSI == null) {
            return;
        }

        if (dataRSI.length == 0) {
            return;
        }

        if ((dataRSI[dataRSI.length - 1] >= rsimin)
                && (dataRSI[dataRSI.length - 1] <= rsimax)) {
            lastdetected      = new TDoc(4);
            lastdetected.o[0] = new Integer(menayaid);
            lastdetected.o[1] = menayaSymbol;
            eventmsg          = "RSI IN RANGE (II)";
            lastdetected.o[2] = eventmsg;
            lastdetected.o[3] = "" + dataRSI[dataRSI.length - 1];
            //
            mdp.detp.addtoTable(lastdetected);
            mdp.detp.addtotree(menayaSymbol, eventmsg,"" + dataRSI[dataRSI.length - 1]);
            //
            lastdetected      = new TDoc(4);
            lastdetected.o[0] = new Integer(menayaid);
            lastdetected.o[1] = menayaSymbol;
            eventmsg          = "RSI FLAT";
            lastdetected.o[2] = eventmsg;
            lastdetected.o[3] =isDeadFLATRSI(dataRSI,7);
            //
            mdp.detp.addtoTable(lastdetected);
            mdp.detp.addtotree(menayaSymbol, eventmsg,"" +lastdetected.o[3] );
        }
    }

    public void testVolumeChangeDown(java.util.List ttradedata, int menayaid,
                                     String menayaSymbol, int volchdownperiod,
                                     float volchdownpercent) {

        // here prepare data for this test on this stock
        // first calculate average for this period and then see if the last meet the limit
        pdiff = 0.0f;

        if (ttradedata.size() < 2) {
            return;
        }

        for (int tj = ttradedata.size() - 2;
                tj > ttradedata.size() - 1 - volchdownperiod; tj--) {
            pdiff +=
                ((Float) (((TDoc) ttradedata.get(tj)).o[5])).floatValue();
        }

        pdiff = pdiff / (float) (volchdownperiod - 1);

        // then detect how last trade day relates to average
        plast = (Float) (((TDoc) ttradedata.get(ttradedata.size()
                - 1)).o[5]);
        pdiff = (plast.floatValue() - pdiff) / pdiff;

        if (pdiff <= volchdownpercent / -100.0f) {

            //
            lastdetected      = new TDoc(4);
            lastdetected.o[0] = new Integer(menayaid);
            lastdetected.o[1] = menayaSymbol;
            eventmsg          = "Last avg " + volchdownperiod
                                + " days DOWN change";
            lastdetected.o[2] = eventmsg;
            lastdetected.o[3] = "" + pdiff;
            mdp.detp.addtoTable(lastdetected);
            mdp.detp.addtotree(menayaSymbol, eventmsg, "" + pdiff);

            //
        }
    }

    public void testVolumeChangeUp(java.util.List ttradedata, int menayaid,
                                   String menayaSymbol, int volchupperiod,
                                   float volchuppercent) {

        // here prepare data for this test on this stock
        // first calculate average for this period and then see if the last meet the limit
        pdiff = 0.0f;

        if (ttradedata.size() < 2) {
            return;
        }

        for (int tj = ttradedata.size() - 2;
                tj > ttradedata.size() - 1 - volchupperiod; tj--) {
            pdiff +=
                ((Float) (((TDoc) ttradedata.get(tj)).o[5])).floatValue();
        }

        pdiff = pdiff / (float) (volchupperiod - 1);

        // then detect how last trade day relates to average
        plast = (Float) (((TDoc) ttradedata.get(ttradedata.size()
                - 1)).o[5]);
        pdiff = (plast.floatValue() - pdiff) / pdiff;

        if (pdiff >= volchuppercent / 100.0f) {

            //
            lastdetected      = new TDoc(4);
            lastdetected.o[0] = new Integer(menayaid);
            lastdetected.o[1] = menayaSymbol;
            eventmsg          = "Last avg " + volchupperiod
                                + " days UP change";
            lastdetected.o[2] = eventmsg;
            lastdetected.o[3] = "" + pdiff;
            mdp.detp.addtoTable(lastdetected);
            mdp.detp.addtotree(menayaSymbol, eventmsg, "" + pdiff);

            //
        }
    }
    public void RRRTest(java.util.List ttradedata, int menayaid,
                          String menayaSymbol, int rperiod, float rrval,float rrrbelt,float minBuyeqtty ) {
            ResistanseSupportLine rsl=new ResistanseSupportLine(rperiod);
                    rsl.run(ttradedata);
           double         dataSRLine[]=rsl.getRSL();
        
           if(ttradedata.size()==0)return;
           float bp=((Float)((TDoc)ttradedata.get(ttradedata.size()-1)).o[4]).floatValue();
           float slp=rrrbelt/100.0f;//stop loss percentage
           float lerp=1.0f+slp;
         
           float c=bp*minBuyeqtty;//buye cost
           float fc=minBuyeqtty*(float)dataSRLine[2]*lerp;//reach cost
           float oslc=( (float)dataSRLine[4]+ (float)dataSRLine[4]*slp)*minBuyeqtty;//cost on stop loss
           float loss=oslc-c;
           float will=fc-c;
           float rrrrr=(loss/minBuyeqtty)/(will/minBuyeqtty);
            
            if(rrrrr<rrval)
            {
            lastdetected      = new TDoc(4);
            lastdetected.o[0] = new Integer(menayaid);
            lastdetected.o[1] = menayaSymbol;
            eventmsg          = "RRR";
            lastdetected.o[2] = eventmsg;
            lastdetected.o[3] = "" +rrrrr;
            mdp.detp.addtoTable(lastdetected);
            mdp.detp.addtotree(menayaSymbol, eventmsg,""+ rrrrr);
            if(rrrrr<0.0f)
            {
            lastdetected      = new TDoc(4);
            lastdetected.o[0] = new Integer(menayaid);
            lastdetected.o[1] = menayaSymbol;
            eventmsg          = "RRR";
            lastdetected.o[2] = eventmsg;
            lastdetected.o[3] = "Rejected";
            mdp.detp.addtoTable(lastdetected);
            mdp.detp.addtotree(menayaSymbol, eventmsg,"Rejected");
            }
            }
    }

    private Object isDeadFLATRSI(double[] dataRSI, int minperiod) {
        for(int i=dataRSI.length-1,k=0;i>=0&&k<minperiod;i--,k++)
        {
          if(i==dataRSI.length-1)continue;
          if(Math.abs(dataRSI[i]-dataRSI[dataRSI.length-1])<=lv_epsilon)return "FLAT";
        }
        return "DIST";
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
