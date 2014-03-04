//~--- non-JDK imports --------------------------------------------------------

import DBCONNECT.TRS;
import Utils.MySimleInputByCombo;
import Utils.LoggerNB;

import Utils.TDoc;

//~--- JDK imports ------------------------------------------------------------

import java.awt.*;
import java.awt.event.*;

import java.sql.*;

import javax.swing.*;
import javax.swing.table.*;

//~--- classes ----------------------------------------------------------------

public class Follow extends JPanel implements ActionListener    // Listener
{
    JButton     DeleteFromFollow;
    JButton     Graph;
    JButton     PinToDeal;
    JButton     ViewCompanyInfo;
    JButton     ViewTradeData;
    FollowTable st;

    //~--- constructors -------------------------------------------------------

    /**
     * Method StoksList
     *
     */
    public Follow() {
        String timgdir = BursaAnalizer_Frame.getImgDir();

        ViewTradeData = new JButton(Name.ViewTradeData,
                                    new ImageIcon(timgdir + "data_table.png"));
        ViewCompanyInfo = new JButton(Name.ViewCompanyInfo,
                                      new ImageIcon(timgdir
                                          + "data_view.png"));
        Graph = new JButton(Name.Graph,
                            new ImageIcon(timgdir + "exchange.png"));
        PinToDeal = new JButton(Name.PinToDeal,
                                new ImageIcon(timgdir + "index_add.png"));
        DeleteFromFollow = new JButton(Name.DeleteFromFollow,
                                       new ImageIcon(timgdir
                                           + "index_add.png"));
        setLayout(new BorderLayout(5, 5));
        st = new FollowTable();
        add(st, BorderLayout.CENTER);

        JPanel cbp     = new JPanel();
        JPanel cbp1    = new JPanel();
        JPanel cbpdiv2 = new JPanel();

        cbpdiv2.setLayout(new GridLayout(1, 2));
        cbpdiv2.setBorder(BorderFactory.createRaisedBevelBorder());
        cbp.setLayout(new GridLayout(3, 1));
        cbp.setBorder(BorderFactory.createRaisedBevelBorder());
        cbp.add(ViewTradeData);
        cbp.add(ViewCompanyInfo);
        cbp.add(Graph);
        cbpdiv2.add(cbp);
        cbp1.setLayout(new GridLayout(2, 1));
        cbp1.setBorder(BorderFactory.createRaisedBevelBorder());
        cbp1.add(PinToDeal);
        cbp1.add(DeleteFromFollow);
        cbpdiv2.add(cbp1);
        ViewTradeData.addActionListener(this);
        ViewCompanyInfo.addActionListener(this);
        Graph.addActionListener(this);
        PinToDeal.addActionListener(this);
        DeleteFromFollow.addActionListener(this);
        add(cbpdiv2, BorderLayout.SOUTH);
    }

    //~--- methods ------------------------------------------------------------

    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        int    sr      = st.table.getSelectedRow();

        if (command.equals(Name.Graph)) {

            // BursaAnalizer_Frame.sg.setDataPoints(lblName.getText(),atd);
            BursaAnalizer_Frame.switchToTab("Graph");
        } else if (command.equals(Name.ViewTradeData)) {
            if (sr > -1) {
                int menayaid = Integer.parseInt(st.datamodel.getValueAt(sr,
                                   0).toString());

                BursaAnalizer_Frame.getTradeDataListInstance().initOnData(
                    menayaid);
                BursaAnalizer_Frame.switchToTab("Data");
            } else {
                st.selectionmessageerr(null);
            }
        }
    }

    public void initOnData() {
        initOnData(UserFolder.getCurentFolderID());
    }

    public void initOnData(int folder) {
        st.fetchdata(0);
        st.fetchdata(folder);
        st.table.revalidate();
    }

    //~--- inner classes ------------------------------------------------------

    // Names of buttons menus and menuitems
    static class Name {
        public static final String ViewTradeData    = "Trade";
        public static final String ViewCompanyInfo  = "Company";
        public static final String PinToDeal        = "Pin to Deals";
        public static final String Graph            = "Graph";
        public static final String DeleteFromFollow = "Delete";
    }

}


//~ Formatted by Jindent --- http://www.jindent.com
