//~--- non-JDK imports --------------------------------------------------------


import CDate.CDate;
import CDate.CDateException;
import DBCONNECT.DataBaseConnector;
import DBCONNECT.TRS;

import Utils.DnldURL;
import Utils.LoggerNB;

//~--- JDK imports ------------------------------------------------------------

import java.io.*;

import java.sql.*;

//~--- classes ----------------------------------------------------------------

public class ReadFromYahoo extends DnldURL {

    // actually it is read local file in yahoo format
    public ReadFromYahoo(BufferedReader reader) {
        super(reader);
    }

    public ReadFromYahoo(String url) {
        super(url);
    }

    /*
     http://finance.yahoo.com/q/hp?s=ELAL.TA
            http://ichart.finance.yahoo.com/table.csv?s=ELAL.TA&d=10&e=14&f=2007&g=d&a=5&b=12&c=2003&ignore=.csv
     */
    /**
     * Method ReadFromYahoo
     */
    public ReadFromYahoo(String symbol, String market, CDate start,
                         CDate end) {

        // first maner
        // http://ichart.finance.yahoo.com/table.csv?s=TEVA.TA&a=07&b=12&c=2002&d=09&e=24&f=2005&g=d&ignore=.csv
        // a = month-1,b=day,c=year
        // e=month-1..
        // second maner read dayly
        // http://finance.yahoo.com/d/quotes.csv?s=ELRN.TA&f=sl1d1t1c1ohgv&e=.csv
        // ret string in cvs file
        // name.market,lasttrade,"date","time",change%,open,hi,lo,volume
        // ELRN.TA,4743.00,"11/14/2005","10:50am",-15.00,4745.00,4817.00,4716.00,59208

        //http://ichart.finance.yahoo.com/table.csv?s=LPMA.TA&d=1&e=25&f=2008&g=d&a=7&b=12&c=2002&ignore=.csv
        url = "http://ichart.finance.yahoo.com/table.csv?s=";
        url += symbol + "." + market;
        int dl=start.getMonthCalendar(),
                e=start.getDay(),
                a=end.getMonthCalendar(),
                b=end.getDay();
        url += "&a=" + (dl<10?"0"+dl:dl) + "&b=" + (e<10?"0"+e:e) + "&c="+ /*2003;*/(start.getYear()) ;
        url += "&g=d";
        url += "&d=" + (a<10?"0"+a:a) + "&e=" + (b<10?"0"+b:b) + "&f=" + end.getYear();
        url += "&ignore=.csv";
        //http://ichart.finance.yahoo.com/table.csv?s=ELAL.TA&d=4&e=12&f=2008&g=d&a=5&b=12&c=2003&ignore=.csv

        //http://ichart.finance.yahoo.com/table.csv?s=BEZQ.TA&a=07&b=12&c=2002&d=04&e=15&f=2011&g=d&ignore=.csv
        //"http://ichart.finance.yahoo.com/table.csv?s=ASHO.TA&a=04&b=13&c=2011&g=d&d=04&e=15&f=2011&ignore=.csv"
        if(LoggerNB.debuging){System.out.println("Update string " + url);}
        create();
        open();
    }

    //~--- methods ------------------------------------------------------------

    public boolean checktitleformat(String title, String delimiter) {

        
        String titlesplited[] = title.split(delimiter);
        String fmt[]          = {
            "Date", "Open", "High", "Low", "Close", "Volume", "Adj Close"
        };

        if (titlesplited.length < fmt.length) {
            return false;
        }

        for (int i = 0; i < fmt.length; i++) {
            if (!fmt[i].equalsIgnoreCase(titlesplited[i])) {
                return false;
            }
        }

        return true;
    }

    /**
     * Method read
     * if no factor specified then used  scalefactor 1.0f
     */
    public void read(DataBaseConnector data, int table /* ,CDate last */,
                     float scalefactor) {
        if (!status) {
            return;
        }

        TRS            RS = new TRS(data.con);
        PreparedStatement pstmt;
        String            s = "";
        String            splited[];

        // String sqlstr="INSERT INTO "+table+"(DAY,OPEN,HI,LO,CLOSE,AMOUNT) VALUES(?,?,?,?,?,?)";
        try {

            // first read title
            if ((s = d.readLine()) == null) {
                return;
            }

            /*
             * if(!checktitleformat(s,","))
             *       if(!checktitleformat(s," "))
             *       {
             *          if(LoggerNB.DEBUG){System.out.println("Cant update from source - Format inconsistence");}
             *       return;
             *       }
             */

            // pstmt = data.con.prepareStatement(sqlstr);
            // read data to end of file
            CDate ddd = new CDate();

            while ((s = d.readLine()) != null) {
                splited = s.split(",");

                try {
                    //ddd.parse(splited[0]);
                    ddd.parseDYinterchanged(splited[0]);
                } catch (CDateException e) {
                    if(LoggerNB.debuging){System.out.println("fmt err " + e);}

                    try {
                        //ddd.parse(splited[0], '/');
                        ddd.parse(splited[0],'/',true);
                    } catch (CDateException e2) {
                        if(LoggerNB.debuging){System.out.println("fmt err " + e2);}
                    }
                }

                java.sql.Date sqldate = new java.sql.Date(ddd.getDateMilis());

                // pstmt.setDate(1, sqldate);
                // for(int i=1;i<splited.length&&i<6;i++)
                // pstmt.setFloat(i+1, Float.parseFloat(splited[i]));
                String sql2 = "SELECT DAY FROM [" + table + "] WHERE DAY="
                              + sqldate;

                RS.execSQL(sql2);

                if (!RS.rs.next()) {

                    // pstmt.executeUpdate();
                    GenericTableLoaders.insert1TradeDataRow(table,
                            sqldate,
                            Float.parseFloat(splited[1]) * scalefactor,
                            Float.parseFloat(splited[2]) * scalefactor,
                            Float.parseFloat(splited[3]) * scalefactor,
                            Float.parseFloat(splited[4]) * scalefactor,
                            Float.parseFloat(splited[5]),
                            "D");
                }

                RS.reuse();
            }
        } catch (SQLException sqle) {
            if(LoggerNB.debuging){System.out.println("322 99 SQLException happened. " + sqle);
            sqle.printStackTrace();}
        } catch (IOException ioe) {
            if(LoggerNB.debuging){System.out.println("1 2 IOException happened. " + ioe);}
        } finally {
            RS.close();
        }
    }
    public void CleanAfter() {
        System.out.println("Not yet implemented");
    }
    /*
    public static void main(String argv[])
    {
        //test for nasdak
        String stikker[]={"LVS","SCSO","GOOG","NVDA","ENTR","INTC","AAPL","SNDK","AONE","WDC","GME","TEVA","NICE"};
        for(int i=0;i<stikker.length;i++)
        {
        String tmp="http://charting.nasdaq.com/ext/charts.dll?2-1-14-0-0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0-5120-03NA000000"+stikker[i]+"-&SF:4|5-WD=539-HT=395--XXCL-";
     ReadFromYahoo r=new ReadFromYahoo(tmp);
     r.create();
     r.open();
     r.SaveLocaly("c:\\ttt_"+stikker[i]+".xls");
        }
    }*/
}


//~ Formatted by Jindent --- http://www.jindent.com
