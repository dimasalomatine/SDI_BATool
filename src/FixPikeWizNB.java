
import tikunimbemasad.EliminateDuplicateRows;
import tikunimbemasad.TikunNetunYahoo2PikeFixing;

/*
 * FixPikeWizNB.java
 *
 * Created on May 13, 2008, 9:07 PM
 */

/**
 
 @author  Dmitry
 */
public class FixPikeWizNB extends javax.swing.JFrame
{
    private AppSettingsNB parent=null;
    private TikunNetunYahoo2PikeFixing ty=null;
    EliminateDuplicateRows de=null;
    /** Creates new form FixPikeWizNB */
    public FixPikeWizNB (AppSettingsNB parent)
    {
        this.parent=parent;
        initComponents ();
    }

    private void RunStopDuplicates() {
        if(this.RunDuplicates.getText().equalsIgnoreCase("run"))
        {
        if(de==null)de=new EliminateDuplicateRows(BursaAnalizer_Frame.getConnectToDB(BursaAnalizer_Frame.DBID_BASE).con,
                                                  BursaAnalizer_Frame.getConnectToDB(BursaAnalizer_Frame.DBID_DATA).con);
   
        else
        {
         de.dostop();
         de.clear();
        }
        this.RunDuplicates.setText("Stop");
        de.setprogress(this.jProgressBar1);
        de.dorun();
        }else
        {
        this.RunDuplicates.setText("Run");
         de.dostop();
        }
       
    }
    
        private void RunStopPikes() {
        if(this.RunPikes.getText().equalsIgnoreCase("run"))
        {
        if(ty==null) ty=new TikunNetunYahoo2PikeFixing(BursaAnalizer_Frame.getConnectToDB(BursaAnalizer_Frame.DBID_BASE),
                                                       BursaAnalizer_Frame.getConnectToDB(BursaAnalizer_Frame.DBID_DATA), 
                                                       true,true);
        else{
            ty.dostop();
         ty.clear();
        }
        this.RunPikes.setText("Stop");
        ty.dorun();
        }else
        {
        this.RunPikes.setText("Run");
         ty.dostop();
        }
       
    }
    
    /** This method is called from within the constructor to
     initialize the form.
     WARNING: Do NOT modify this code. The content of this method is
     always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        RunDuplicates = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jProgressBar2 = new javax.swing.JProgressBar();
        RunPikes = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        BACK = new javax.swing.JButton();
        NEXT = new javax.swing.JButton();
        CANCEL = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 51), 5, true), "Fix Data Pikes Wizard"));

        jTextArea2.setBackground(new java.awt.Color(0, 0, 0));
        jTextArea2.setColumns(20);
        jTextArea2.setEditable(false);
        jTextArea2.setFont(new java.awt.Font("Tahoma", 3, 18));
        jTextArea2.setForeground(new java.awt.Color(0, 255, 204));
        jTextArea2.setRows(5);
        jTextArea2.setText("Welcome to the PikeFix Wizzard !!!\nThis wizard will help you analize entire DB storage data \nsequences,detecting strange data pikes or corruption \nand eliminate this!");
        jTextArea2.setAutoscrolls(false);
        jTextArea2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 51), 1, true));
        jTextArea2.setFocusable(false);
        jScrollPane2.setViewportView(jTextArea2);

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 536, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(37, 37, 37)
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(195, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Welcome", jPanel1);

        RunDuplicates.setText("Run");
        RunDuplicates.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RunDuplicatesActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(53, 53, 53)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jProgressBar1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE))
                .addContainerGap(54, Short.MAX_VALUE))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(259, Short.MAX_VALUE)
                .add(RunDuplicates)
                .add(246, 246, 246))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 197, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(27, 27, 27)
                .add(jProgressBar1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 46, Short.MAX_VALUE)
                .add(RunDuplicates)
                .add(28, 28, 28))
        );

        jTabbedPane1.addTab("Duplicates", jPanel2);

        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jScrollPane3.setViewportView(jTextArea3);

        RunPikes.setText("Run");
        RunPikes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RunPikesActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .add(57, 57, 57)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(jProgressBar2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
                        .addContainerGap())
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(jScrollPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
                        .add(50, 50, 50))))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(263, Short.MAX_VALUE)
                .add(RunPikes)
                .add(242, 242, 242))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .add(24, 24, 24)
                .add(jScrollPane3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 197, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(26, 26, 26)
                .add(jProgressBar2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(RunPikes)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Pikes", jPanel3);

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 556, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 346, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Fix", jPanel4);

        BACK.setText("BACK");

        NEXT.setText("NEXT");

        CANCEL.setText("Cancel");
        CANCEL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CANCELActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(40, 40, 40)
                .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 579, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap(323, Short.MAX_VALUE)
                .add(BACK)
                .add(29, 29, 29)
                .add(NEXT)
                .add(33, 33, 33)
                .add(CANCEL)
                .add(82, 82, 82))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(45, 45, 45)
                .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 406, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(28, 28, 28)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(BACK)
                    .add(NEXT)
                    .add(CANCEL))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CANCELActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_CANCELActionPerformed
    {//GEN-HEADEREND:event_CANCELActionPerformed
        this.dispose();
        if(parent!=null)parent.fixpikswiz=null;
    }//GEN-LAST:event_CANCELActionPerformed

    private void RunDuplicatesActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_RunDuplicatesActionPerformed
    {//GEN-HEADEREND:event_RunDuplicatesActionPerformed
        
        RunStopDuplicates();
}//GEN-LAST:event_RunDuplicatesActionPerformed

private void RunPikesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RunPikesActionPerformed
  RunStopPikes();
}//GEN-LAST:event_RunPikesActionPerformed
    
    /**
     @param args the command line arguments
     */
    public static void main (String args[])
    {
        java.awt.EventQueue.invokeLater (new Runnable ()
        {
            public void run ()
            {
                new FixPikeWizNB (null).setVisible (true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BACK;
    private javax.swing.JButton CANCEL;
    private javax.swing.JButton NEXT;
    private javax.swing.JButton RunDuplicates;
    private javax.swing.JButton RunPikes;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JProgressBar jProgressBar2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    // End of variables declaration//GEN-END:variables
    
}
