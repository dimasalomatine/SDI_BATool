//~--- non-JDK imports --------------------------------------------------------

import Utils.DnldURL;
import LocalUtils.BasicZIP;
import Utils.LoggerNB;
import Utils.MySimpleBrowser;

//~--- JDK imports ------------------------------------------------------------

import java.awt.*;
import java.awt.event.*;

import java.io.*;

import javax.swing.*;
import javax.swing.JPanel;

//~--- classes ----------------------------------------------------------------

public class EXporters extends JPanel implements ActionListener {
    TaseDataFactory tdf = null;
    JButton         Add;
    JButton         CYahoo;
    JButton         Deals;
    JButton         Del;
    JButton         Edit;
    JButton         ViewFollow;
    JTextArea       progressview;
    JScrollPane     sp;

    //~--- constructors -------------------------------------------------------

    /**
     * Method EXporters
     */
    public EXporters() {
        String timgdir = "../images/";

        ViewFollow = new JButton(Name.ViewFollow,
                                 new ImageIcon(timgdir + "data_table.png"));
        Deals = new JButton(Name.Deals,
                            new ImageIcon(timgdir + "index_add.png"));
        Edit = new JButton(Name.Edit,
                           new ImageIcon(timgdir + "data_view.png"));
        Add    = new JButton(Name.Add,
                             new ImageIcon(timgdir + "index_add.png"));
        Del    = new JButton(Name.Del,
                             new ImageIcon(timgdir + "index_add.png"));
        CYahoo = new JButton(Name.CustomYahoo,
                             new ImageIcon(timgdir + "index_add.png"));

        // setBounds(TFSPX,TFSPY,TFDX,TFDY);
        setLayout(new BorderLayout(5, 5));
        progressview = new JTextArea();
        sp           = new JScrollPane(progressview);
        add(sp, BorderLayout.CENTER);

        JPanel cbp     = new JPanel();
        JPanel cbp1    = new JPanel();
        JPanel cbpdiv2 = new JPanel();

        cbpdiv2.setLayout(new GridLayout(1, 2));
        cbpdiv2.setBorder(BorderFactory.createRaisedBevelBorder());
        cbp.setLayout(new GridLayout(3, 1));
        cbp.setBorder(BorderFactory.createLineBorder(Color.green, 2));
        cbp.add(Edit);
        cbp.add(Add);
        cbp.add(Del);
        cbpdiv2.add(cbp);
        cbp1.setLayout(new GridLayout(3, 1));
        cbp1.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
        cbp1.add(ViewFollow);
        cbp1.add(Deals);
        cbp1.add(CYahoo);
        cbpdiv2.add(cbp1);
        ViewFollow.addActionListener(this);
        Edit.addActionListener(this);
        Add.addActionListener(this);
        Del.addActionListener(this);
        Deals.addActionListener(this);
        CYahoo.addActionListener(this);
        add(cbpdiv2, BorderLayout.SOUTH);
        add(new JLabel("Developer's SIDE"), BorderLayout.NORTH);
    }

    //~--- methods ------------------------------------------------------------

    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();

        // int sr=sv.table.getSelectedRow();
        String dir = "C:\\SDI_BATool\\advinfo\\TASE\\";

        if (command.equals(Name.ViewFollow)) {
            BasicZIP    bz          = new BasicZIP();
            String         zipfilename = "20060111.zip";
            java.util.List tl          = bz.listContent(dir + zipfilename);

            for (int i = 0; i < tl.size(); i++) {
                progressview.append("\n" + (String) tl.get(i));
            }
        } else if (command.equals(Name.Deals)) {
            String          sec = "LISTOFSECURITESDATAEXAMPLE08031201.tas.txt";
            taseindexreader tr  = new taseindexreader();

            tr.read(dir + sec);
        } else if (command.equals(Name.Edit)) {
            if (tdf != null) {
                tdf.setVisible(true);
            } else {
                tdf = new TaseDataFactory();
            }
        } else if (command.equals(Name.CustomYahoo)) {
            exportFromSingleFileYahooed();
        }
    }

    private void exportFromSingleFileYahooed() {
        MySimpleBrowser sb =
            new MySimpleBrowser(BursaAnalizer_Frame.getInstance(),
                                "Select the file to import", "table.csv",
                                false, true, new String[] { "csv" });

        if (sb.file == null) {
            return;
        }

        JOptionPane
            .showMessageDialog(
                null, "<HTML><center><font size=3 color=blue>" + sb.directory
                + "\\" + sb.file
                + "<br><font size=4 color=red>File will be imported!!!", "Import", JOptionPane
                    .INFORMATION_MESSAGE);

        MySimpleInput si =
            new MySimpleInput(
                null, "select menaya",
                BursaAnalizer_Frame.getConnectToDB(
                        BursaAnalizer_Frame.DBID_BASE).con, "MENAYA_BASE",
                        "NAME", "MENAYA_ID");
        int menayaid = Integer.parseInt(si.selectedValuesecond.toString());

        try {
            BufferedReader br = new BufferedReader(new FileReader(sb.directory
                                    + "\\" + sb.file));
            ReadFromYahoo yr = new ReadFromYahoo(br);

            yr.read(
                BursaAnalizer_Frame.getConnectToDB(
                    BursaAnalizer_Frame.DBID_DATA), menayaid);
        } catch (IOException e) {
            if(LoggerNB.debuging){System.out.println(e);}
        }
    }

    //~--- inner classes ------------------------------------------------------

    // Names of buttons menus and menuitems
    static class Name {
        public static final String ViewFollow  = "EXPORT TASE INDEXES";
        public static final String Edit        = "Edit";
        public static final String Del         = "Delete";
        public static final String Deals       = "Deals";
        public static final String CustomYahoo = "Custom Yahoo";
        public static final String Add         = "Create";
    }


    class taseindexreader extends DnldURL {
        public taseindexreader() {}

        public taseindexreader(String url) {
            super(url);
        }

        //~--- methods --------------------------------------------------------

        public void read()    // msdb_connect data,int table,CDate last)
        {
            String s = "";

            if (!status) {
                return;
            }

            try {

                // String sqlstr="INSERT INTO "+table+"(DAY,OPEN,HI,LO,CLOSE,AMOUNT) VALUES(?,?,?,?,?,?)";
                // PreparedStatement pstmt = data.con.prepareStatement(sqlstr);
                // first read title
                if ((s = d.readLine()) == null) {
                    return;
                }

                // read data to end of file
                while ((s = d.readLine()) != null) {
                    progressview.append("\n" + s);
                }    // String s3[]=s.split(",");

                {}

                // java.sql.Date sqldate=new java.sql.Date(ddd.getDateMilis());
                // pstmt.setDate(1, sqldate);
                // for(int i=1;i<s3.length&&i<6;i++)pstmt.setFloat(i+1, Float.parseFloat(s3[i]));
                // String sql2="SELECT DAY FROM "+table+" WHERE DAY="+sqldate;
                // ResultSet r1=((msdb_connect)data).execSQL(sql2);
                // if(!r1.next())pstmt.executeUpdate();
            }

//          catch (SQLException ioe) 
            // {
            // System.out.println("1 99 SQLException happened. "+ioe);
            // }
            catch (IOException ioe) {
                if(LoggerNB.debuging){System.out.println("1 2 IOException happened. " + ioe);}
            }
        }

        public void read(String url) {
            try {
                BufferedReader in = new BufferedReader(new FileReader(url));
                String         str;

                while ((str = in.readLine()) != null) {
                    progressview.append("\n" + str);
                }

                in.close();
            } catch (IOException e) {}
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
