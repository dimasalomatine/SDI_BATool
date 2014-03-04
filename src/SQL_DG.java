//~--- non-JDK imports --------------------------------------------------------

import Utils.TDoc;

//~--- JDK imports ------------------------------------------------------------

import java.awt.*;

import java.util.*;

import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.table.*;

//~--- classes ----------------------------------------------------------------

public class SQL_DG extends JPanel {
    boolean        firstcolumnvisible = true;
    String[]       columnnames        = { "#", "VALUE" };
    boolean[]      columneditable     = { false, false };
    java.util.List atd                = new ArrayList();
    TableModel     datamodel;
    JScrollPane    sp;
    JTable         table;

    //~--- constructors -------------------------------------------------------

    /**
     * Method SQL_DG
     */
    public SQL_DG() {

//      setBounds(TFSPX,TFSPY,TFDX,TFDY);
        setLayout(new BorderLayout(5, 5));
        buildtable();
        table.revalidate();
        add(sp, BorderLayout.CENTER);
    }

    public SQL_DG(String[] colnames, boolean firstcolvisible,
                      boolean[] coleditable) {

//      setBounds(TFSPX,TFSPY,TFDX,TFDY);
        setLayout(new BorderLayout(5, 5));
        columnnames        = new String[colnames.length];
        firstcolumnvisible = firstcolvisible;
        columneditable     = new boolean[coleditable.length];

        int i;

        for (i = 0; i < colnames.length; i++) {

            // columnnames[i]=new String(colnames[i]);
            columnnames[i]    = colnames[i];
            columneditable[i] = coleditable[i];
        }

        buildtable();
        table.revalidate();
        add(sp, BorderLayout.CENTER);
    }

    public SQL_DG(java.util.List tatd, String[] colnames,
                      boolean firstcolvisible, boolean[] coleditable) {

//      setBounds(TFSPX,TFSPY,TFDX,TFDY);
        setLayout(new BorderLayout(5, 5));
        columnnames        = new String[colnames.length];
        firstcolumnvisible = firstcolvisible;
        columneditable     = new boolean[coleditable.length];

        int i;

        for (i = 0; i < colnames.length; i++) {

            // columnnames[i]=new String(colnames[i]);
            columnnames[i]    = colnames[i];
            columneditable[i] = coleditable[i];
        }

        atd = tatd;
        buildtable();
        table.revalidate();
        add(sp, BorderLayout.CENTER);
    }

    //~--- methods ------------------------------------------------------------

    public void saveAll(int record) {

        // must be implemented in interface
    }

    public void buildtable() {
        datamodel = new AbstractTableModel() {
            public int getColumnCount() {
                return columnnames.length;
            }
            public int getRowCount() {
                return atd.size();
            }
            public Object getValueAt(int row, int col) {
                return ((TDoc) atd.get(row)).o[col];
            }
            public String getColumnName(int col) {
                return columnnames[col];
            }
            public Class getColumnClass(int col) {
                return getValueAt(0, col).getClass();
            }
            public void setValueAt(Object o, int r, int c) {
                ((TDoc) atd.get(r)).o[c]    = o;
                ((TDoc) atd.get(r)).changed = true;
                saveAll(r);
            }
            public boolean isCellEditable(int r, int c) {
                return columneditable[c];
            }
        };
        table = new JTable(datamodel);
        sp    = new JScrollPane(table);

        // advanced setings for default columns
        setColumnAdvSet(table.getColumn(columnnames[0]));
        setColumnAdvSet(table.getColumn(columnnames[1]));

        /*
         * setColumnAdvSet(table.getColumn(columnnames[0]),
         *                               100,50,
         *                               false,
         *                               new DefaultCellEditor(cbind));
         */
    }

    public void hideColumnN(int columnnumumber) {
        TableColumn tcg = table.getColumn(columnnames[columnnumumber]);

        tcg.setPreferredWidth(0);
        tcg.setMinWidth(0);
        tcg.setMaxWidth(0);
    }

    public void selectionmessageerr(String msg) {
        String message =
            "<HTML><center><font size=16 color=red>But nothing is selected !!!!";

        if (msg != null) {
            message = msg;
        }

        JOptionPane.showMessageDialog(this, message, "Select ? :)",
                                      JOptionPane.INFORMATION_MESSAGE);
    }

    //~--- set methods --------------------------------------------------------

    public void setColumnAdvSet(TableColumn tc) {
        setColumnAdvSet(tc, 100, 100, true, null);
    }

    public void setColumnAdvSet(TableColumn tc, int mw, int w, boolean r,
                                DefaultCellEditor ce) {
        tc.setMaxWidth(mw);

        if (w == 0) {
            tc.setWidth(mw);
        } else {
            tc.setWidth(w);
        }

        tc.setResizable(r);

        if (ce != null) {
            tc.setCellEditor(ce);
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
