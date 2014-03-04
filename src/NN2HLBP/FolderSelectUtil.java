package NN2HLBP;


import DBCONNECT.DataBaseConnector;
import Utils.MySimleInputByCombo;
import Utils.MySimpleBrowser;
import java.sql.Connection;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FolderSelectUtil.java
 *
 * Created on 07/05/2009, 17:53:32
 */

/**
 *
 * @author Dmitry
 */
public class FolderSelectUtil extends javax.swing.JFrame {

    Connection con=null;
    int uid=0;
    String pathtoXLSFileaccess="c:\\\\SDI_BATOOL\\";
    Test_2H_2_GUI_NBF parent;
    /** Creates new form FolderSelectUtil */
    public FolderSelectUtil(DataBaseConnector dbc,int uid,Test_2H_2_GUI_NBF tparent) {
        this.con=dbc.con;
        parent=tparent;
this.uid=uid;
        initComponents();

         MySimpleBrowser sb =
                    new MySimpleBrowser(this,
                                        "Select the Excel file",System.getProperty("user.dir"), false,
                                        true, new String[] { "xls" });

                if ((sb.file == null))return;

                pathtoXLSFileaccess=sb.file.substring (0,sb.file.indexOf (".",1));


     this.setVisible(true);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButtonClose = new javax.swing.JButton();
        jButtonOk = new javax.swing.JButton();
        jComboBoxfolder = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                thiswindowclosing1(evt);
            }
        });

        jButtonClose.setText("Close");

        jButtonOk.setText("Ok");
        jButtonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOkActionPerformed(evt);
            }
        });

        jComboBoxfolder.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        MySimleInputByCombo cbind = new MySimleInputByCombo ();
        cbind.buildfromsource(null, con, " [FOLDER]", "NAME", "ID", " where uid="+uid);
        this.jComboBoxfolder=cbind;

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonOk)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonClose))
                    .addComponent(jComboBoxfolder, 0, 258, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jComboBoxfolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonOk)
                    .addComponent(jButtonClose))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void thiswindowclosing1(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_thiswindowclosing1
     doclose();
    }//GEN-LAST:event_thiswindowclosing1

    private void doclose()
    {
       this.dispose();
        if(parent!=null)parent.fs=null;
    }
    private void jButtonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOkActionPerformed
        if(parent!=null)
        {
         parent.pathtoXLSFileaccess=pathtoXLSFileaccess;
         parent.cfolderid=Integer.parseInt( ((MySimleInputByCombo) jComboBoxfolder).getVc2bySelected());
        }
        doclose();
    }//GEN-LAST:event_jButtonOkActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FolderSelectUtil(null,0,null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonOk;
    private javax.swing.JComboBox jComboBoxfolder;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables

}