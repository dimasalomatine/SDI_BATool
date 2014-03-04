//~--- non-JDK imports --------------------------------------------------------

import DBCONNECT.Msdb_connect;
import Utils.DnldURL;
import Utils.LoggerNB;

//~--- JDK imports ------------------------------------------------------------

import java.io.*;

import java.sql.*;

import java.util.Vector;

//~--- classes ----------------------------------------------------------------

public class IndexFromYahoo extends DnldURL {
    //int    startindex = 0;
    Vector index      = new Vector(50);

    //~--- constructors -------------------------------------------------------

    public IndexFromYahoo(String url) {
        super(url);
        System.out.println("Updating STOK indexes...");
        System.out.println("From " + url);
    }

    /**
     * Method IndexFromYahoo
     *
     *
     */
    public IndexFromYahoo(int start, boolean ainc) {
        System.out.println("Updating STOK indexes...");

        // stoks http://finance.yahoo.com/lookup?s=TA&t=S&m=ALL&r=3&b=0
        // url= new String("http://finance.yahoo.com/lookup?s=TA&t=S&m=ALL&r=3&b="+start);
        url = "file:C://Documents and Settings//Dmitry//Desktop//lookup[1].html";
        System.out.println("From " + url);
        create();
        open();
    }

    //~--- methods ------------------------------------------------------------

    /**
     * Method read - read data from html page parses it and fill in table
     * then call to settotable and updating tables
     *
     *
     */
    public void read(Msdb_connect data, String table) {
        if (!status) {
            return;
        }

        String       s   = "";
        StringBuffer buf = new StringBuffer();

        try {

            // read data to end of file
            while ((s = d.readLine()) != null) {
                if (s.startsWith("#")) {
                    continue;
                }

                buf.append(s);
            }
        } catch (IOException ioe) {
            if(LoggerNB.debuging){System.out.println("1 1 IOException happened.");}
        }

        String total = remall(buf.toString());

        System.out.print(total);

        // settotable(data,table);
    }

    String remall(String s) {
        String tags[] = {
            "<a", "</a>", "<script>", "</script>", "<script ", "</script>",
            "<noscript", "</noscript>", "<!--", "-->"
        };

        for (int i = 0; i < tags.length; i += 2) {
            s = skiptag(s, 0, tags[i], tags[i + 1]);
        }

        // System.out.println("Skiping ADVERTISEMENT...</noscript>");
        return s;
    }

    public void settotable(Msdb_connect data, String table) {
        String s = "";
        IndexFromYahooRecord o;
        int    i, j;

        if (!status) {
            return;
        }

        for (i = 0; i < index.size(); i++) {
            o = (IndexFromYahooRecord) (index.get(i));

            try {
                PreparedStatement pstmt =
                    data.con.prepareStatement(
                        "INSERT INTO " + table
                        + "(SYMBOL,NAME,MARKET) VALUES(?,?,?)");

                pstmt.setString(1, o.symbol);
                pstmt.setString(2, o.desk);
                pstmt.setString(3, o.market);
                pstmt.executeUpdate();
                pstmt.close ();
            } catch (SQLException ioe) {
                if(LoggerNB.debuging){System.out.println("1 1 IOException happened. " + ioe);}
            }
        }
    }

    String skiptag(String s, int begin, String tag, String close) {
        int st  = 0,
            et  = 0,
            st1 = 0;

        if ((st = s.indexOf(tag, begin)) >= 0) {
            st1 = s.indexOf(tag, st + tag.length());

            if (st1 >= 0) {
                s = skiptag(s, st1, tag, close);
            }

            et = s.indexOf(close);
            s  = s.substring(0, st)
                 + s.substring(et + close.length(), s.length());
        }

        return s;
    }

}


//~ Formatted by Jindent --- http://www.jindent.com
