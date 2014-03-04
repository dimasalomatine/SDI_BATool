//~--- non-JDK imports --------------------------------------------------------

import Utils.ExcelAdapter;
import Utils.ValidatedJTextField;

//~--- JDK imports ------------------------------------------------------------

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;

//~--- classes ----------------------------------------------------------------

public class MoneyDetector extends JPanel implements ActionListener {
    private static JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP);    // JTabbedPane.RIGHT);

    //~--- fields -------------------------------------------------------------

    DetectorRunner                     dr = null;
    JButton                            AddToFollow;
    JButton                            ExportToExcel;
    JButton                            Run;
    JButton                            Stop;
    JButton                            View;
    DetectedEventsTablesPanel          detp;
    FundamentalAnaSettingsDetector fapsetings;
    TechnicalAnaSettingsDetector   tapsetings;
    TechnicalAdvAnaSettingsDetector tapadvsetings;

    //~--- constructors -------------------------------------------------------

    /**
     *     Method MoneyDetector
     */
    public MoneyDetector() {
        setLayout(new BorderLayout(5, 5));
        initcontrolpanel();
    }

    //~--- methods ------------------------------------------------------------

    JPanel InitGridPanelWithTitle(String title, int gx, int gy, Color color) {
        JPanel p = new JPanel();

        p.setLayout(new GridLayout(gx, gy));
        p.setBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createRaisedBevelBorder(), title,
                TitledBorder.CENTER, TitledBorder.TOP,
                new Font("Serif", Font.BOLD | Font.ITALIC, 12), color));

        return p;
    }

    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();

        if (command.equals(Name.Run)) {
            if (dr != null) {
                dr.dostop();
            }

            dr = new DetectorRunner(this);
        } else if (command.equals(Name.Stop)) {
            if (dr != null) {
                dr.dostop();
            }

            dr = null;
        } else if (command.equals(Name.View)) {
            int           menayaid = detp.getIDofStock();
            TradeDataListNB tdli     =
                BursaAnalizer_Frame.getTradeDataListInstance();

            tdli.initOnData(menayaid);
            BursaAnalizer_Frame.getStockGraphInstance().setDataPoints(
                detp.getSymbolofStock(), tdli.getTradeDataList());
            BursaAnalizer_Frame.switchToTab("Graph");
        } else if (command.equals(Name.ExportToExcel)) {
            detp.tableToExcell();
        }
    }

    private void addAndRegisterButtonInGridLayout(JPanel to, JButton b) {
        to.add(b);
        b.addActionListener(this);
    }

    JLabel createandalinglabel(String txt) {
        return createandalinglabel(txt, JLabel.RIGHT);
    }

    JLabel createandalinglabel(String txt, int aling) {
        JLabel tlbl = new JLabel(txt);

        tlbl.setHorizontalAlignment(aling);

        return tlbl;
    }

    void initcontrolpanel() {
        String timgdir = BursaAnalizer_Frame.getImgDir();

        Run  = new JButton(Name.Run, new ImageIcon(timgdir + "execute.gif"));
        Stop = new JButton(Name.Stop, new ImageIcon(timgdir + "stop.gif"));
        View = new JButton(Name.View,
                           new ImageIcon(timgdir + "data_table.png"));
        AddToFollow = new JButton(Name.AddToFollow,
                                  new ImageIcon(timgdir + "data_table.png"));
        ExportToExcel = new JButton(Name.ExportToExcel,
                                    new ImageIcon(timgdir + "data_table.png"));

        // MDPanelLogic logicpanel=new MDPanelLogic();
        // add(logicpanel,BorderLayout.NORTH);
        detp = new DetectedEventsTablesPanel();

        JPanel cbp0 = new JPanel();

        cbp0.setLayout(new GridLayout(1, 2));
        fapsetings = new FundamentalAnaSettingsDetector();
        tapsetings = new TechnicalAnaSettingsDetector();
         tapadvsetings=new TechnicalAdvAnaSettingsDetector();
        tabs.addTab("Technical", tapsetings);
        tabs.addTab("Fundamental I", fapsetings);
      // tabs.addTab("Japaneze Candles", new JPanel());
        tabs.addTab("Markovich Model", tapadvsetings);
        cbp0.add(tabs);
        cbp0.add(detp);
        add(cbp0, BorderLayout.CENTER);

        JPanel cbp     = new JPanel();
        JPanel cbp1    = new JPanel();
        JPanel cbpdiv2 = new JPanel();

        cbpdiv2.setLayout(new GridLayout(1, 2));
        cbpdiv2.setBorder(BorderFactory.createRaisedBevelBorder());
        cbp.setLayout(new GridLayout(1, 2));
        cbp.setBorder(BorderFactory.createRaisedBevelBorder());
        addAndRegisterButtonInGridLayout(cbp, Run);
        addAndRegisterButtonInGridLayout(cbp, Stop);
        Stop.setEnabled(false);
        cbpdiv2.add(cbp);
        cbp1.setLayout(new GridLayout(1, 3));
        cbp1.setBorder(BorderFactory.createRaisedBevelBorder());

        JLabel tlbl = new JLabel("More here:");

        tlbl.setHorizontalAlignment(JLabel.RIGHT);
        cbp1.add(tlbl);
        addAndRegisterButtonInGridLayout(cbp1, View);
        addAndRegisterButtonInGridLayout(cbp1, AddToFollow);
        addAndRegisterButtonInGridLayout(cbp1, ExportToExcel);
        cbpdiv2.add(cbp1);
        add(cbpdiv2, BorderLayout.SOUTH);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame nf = new JFrame();

                nf.add(new MoneyDetector());
                nf.setSize(400, 400);
                nf.setVisible(true);
            }
        });
    }

    //~--- inner classes ------------------------------------------------------

    static class Name {
        public static final String Run           = "Run";
        public static final String Stop          = "Stop";
        public static final String View          = "View";
        public static final String ExportToExcel = "Export To Excel";
        public static final String AddToFollow   = "Add to Follow  List";
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
