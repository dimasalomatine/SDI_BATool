//~--- non-JDK imports --------------------------------------------------------

import CDate.CDate;
import Utils.LoggerNB;

//~--- classes ----------------------------------------------------------------

public class TradeData {
    static final String del = "|";

    //~--- fields -------------------------------------------------------------

    public float open      = 0;
    public float low       = 0;
    public float hi        = 0;
    public float close     = 0;
    public float vol       = 0;
    public CDate timepoint = new CDate();

    //~--- constructors -------------------------------------------------------

    /**
     * Method TradeData
     *
     *
     */
    public TradeData() {

        // TODO: Add your code here
    }

    /**
     * Method TradeData
     *
     *
     */
    public TradeData(String data) {
        parse(data);
    }

    //~--- methods ------------------------------------------------------------

    /**
     * Method parse
     *
     *
     * @param data
     *
     * @return
     *
     */
    public boolean parse(String data) {
        try {
            String st[] = data.split(del);

            timepoint.parse(st[0]);
            open  = Float.parseFloat(st[1]);
            hi    = Float.parseFloat(st[2]);
            low   = Float.parseFloat(st[3]);
            close = Float.parseFloat(st[4]);
            vol   = Float.parseFloat(st[5]);
        } catch (Exception e) {
            if(LoggerNB.debuging){System.out.println("Err 236" + e);}

            return false;
        }

        return true;
    }

    /**
     * Method toString
     *
     *
     * @return
     *
     */
    @Override
    public String toString() {
        String st = timepoint.toString() + del + open + del + hi + del + low
                    + del + close + del + vol;

        return st;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
