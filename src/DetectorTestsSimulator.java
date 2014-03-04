/*
 * DetectorTests.java
 *
 * Created on October 18, 2006, 12:47 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

//~--- classes ----------------------------------------------------------------

/**
 *
 * @author Dmitry
 */

/**
 * this is class that contain basic test functions
  * vol,rsi,close up/down change on some perion relevant to percent
 */

import Utils.TDoc;

import TechnicalTools.RSI;
import TechnicalTools.ResistanseSupportLine;
public class DetectorTestsSimulator {



    // local intensive used vars---//
    String                eventmsg     = "";
    TDoc               lastdetected = null;
    float                 pdiff        = 0.0f;

    // ----------------------------//
    Float                 plast, pfirst;

    //~--- constructors -------------------------------------------------------

    /** Creates a new instance of DetectorTests */
    public DetectorTestsSimulator(TDoc               lastdetected) {
      this.lastdetected=lastdetected;
    }

    //~--- methods ------------------------------------------------------------

    public void testCloseChangeDown(java.util.List ttradedata, int menayaid,String menayaSymbol,
                                    
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

           
            eventmsg          = "Last " + closechdownperiod
                                + " days DOWN change";
            lastdetected.o[8] = eventmsg;
            lastdetected.o[9] = "" + pdiff;
            

            //
        }
    }

    public void testCloseChangeUp(java.util.List ttradedata, int menayaid,String menayaSymbol,
                                   int closechupperiod,
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

           
            eventmsg          = "Last " + closechupperiod + " days UP change";
            lastdetected.o[8] = eventmsg;
            lastdetected.o[9] = "" + pdiff;
            

            //
        }
    }

    public void testRSI_I(java.util.List ttradedata, int menayaid,String menayaSymbol,
                          int rsiperiod, float rsimin,
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
            
            eventmsg          = "RSI IN RANGE (I)";
            lastdetected.o[6] = eventmsg;
            lastdetected.o[7] = "" + dataRSI[dataRSI.length - 1];

         

            //
        }
    }

    public void testRSI_II(java.util.List ttradedata, int menayaid,String menayaSymbol,
                           int rsiperiod, float rsimin,
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
           
            eventmsg          = "RSI IN RANGE (II)";
            lastdetected.o[6] = eventmsg;
            lastdetected.o[7] = "" + dataRSI[dataRSI.length - 1];

          
        }
    }

    public void testVolumeChangeDown(java.util.List ttradedata, int menayaid,String menayaSymbol,
                                     int volchdownperiod,
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

            
            eventmsg          = "Last avg " + volchdownperiod
                                + " days DOWN change";
            lastdetected.o[10] = eventmsg;
            lastdetected.o[11] = "" + pdiff;
           

            //
        }
    }

    public void testVolumeChangeUp(java.util.List ttradedata, int menayaid,String menayaSymbol,
                                  int volchupperiod,
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

            
            eventmsg          = "Last avg " + volchupperiod
                                + " days UP change";
            lastdetected.o[10] = eventmsg;
            lastdetected.o[11] = "" + pdiff;
           
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
           
            eventmsg          = "RRR";
            lastdetected.o[12] = eventmsg;
            lastdetected.o[13] = "" +rrrrr;
            }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
