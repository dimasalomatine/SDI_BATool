import Utils.TDoc;
import java.beans.BeanDescriptor;
import java.util.ArrayList;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.*;
/*
 * SQL_DG_NB.java
 *
 * Created on March 31, 2007, 8:30 PM
 */

/**
 *
 * @author  Dmitry
 */
public class SQL_DG_NB extends javax.swing.JPanel
{
    boolean        firstcolumnvisible = true;
    String[]       columnnames        = { "#", "VALUE" };
    boolean[]      columneditable     = { false, false };
    java.util.List atd                = new ArrayList();
    TableModel datamodel;
    /**
     * Creates new form SQL_DG_NB
     */
    public SQL_DG_NB ()
    {
        initComponents ();
        buildtable();
    }
     public SQL_DG_NB(String[] colnames, boolean firstcolvisible,
                      boolean[] coleditable) {
        initComponents ();
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
    }
      public SQL_DG_NB(java.util.List tatd, String[] colnames,
                      boolean firstcolvisible, boolean[] coleditable) {
       initComponents ();
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
    }
    public BeanDescriptor getBeanDescriptor() {
    BeanDescriptor desc = new BeanDescriptor(SQL_DG_NB.class);
    desc.setValue("containerDelegate", "getInnerPane");
    return desc;
}
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
        table.setModel (datamodel);
        
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
    public JTable getTable(){return table;}
    public TableModel getTableModel(){return datamodel;}
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String []
            {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(table);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 654, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
    
}
