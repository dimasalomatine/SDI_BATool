//~--- JDK imports ------------------------------------------------------------

import Utils.MySimleInputByCombo;
import java.awt.*;

import javax.swing.*;
import javax.swing.JPanel;

//~--- classes ----------------------------------------------------------------

class JSPanel extends JPanel {
    String[] labels = {
        "Date:", "Open:", "High:", "Low:", "Close:", "Vol:"
    };
    JLabel[] l      = new JLabel[labels.length];

    //~--- constructors -------------------------------------------------------

    public JSPanel() {
        setLayout(new FlowLayout());
        StokGraph.lblName.setForeground(Color.blue);
        StokGraph.lblName.setFont(new Font("SansSerif", Font.ITALIC, 14));
        add(StokGraph.lblName);

        for (int i = 0; i < labels.length; i++) {
            JLabel t = new JLabel(labels[i]);

            add(t);
            l[i] = new JLabel("0");
            add(l[i]);
        }

        Object tn[][] = {
            { "Dayly", "0" }, { "Weekly", "1" }, { "Monthly", "2" },
            { "Year", "3" }
        };

        StokGraph.ViewType = new MySimleInputByCombo(null, tn);
        add(StokGraph.ViewType);

        Object tn1[][] = {
            { "Vertical", "0" }, { "Opens", "1" }, { "Closes", "2" },
            { "Averages", "3" }, { "Japan", "4" }
        };

        StokGraph.GraphType = new MySimleInputByCombo(null, tn1);
        add(StokGraph.GraphType);

        Object tn2[][] = {
            { "All Entries", "0" }, { "Last 2 Years", "1" },
            { "Last Year", "2" }, { "Last 6 Months", "3" },
            { "Last 3 Months", "4" }, { "Last Month", "5" }
        };

        StokGraph.GraphTimeType = new MySimleInputByCombo(null, tn2);
        add(StokGraph.GraphTimeType);
    }

    //~--- methods ------------------------------------------------------------

    public void setdatatoview(double[] ar) {}
}


//~ Formatted by Jindent --- http://www.jindent.com
