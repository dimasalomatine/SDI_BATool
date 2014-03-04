/*
 * DataMinimalNNSetEX.java
 *
 * Created on January 6, 2007, 3:15 PM
 */

package NN2HLBP;

import Utils.MySympleValVerifier;

/**
 *
 * @author  Dmitry
 */
public class DataMinimalNNSetEX extends javax.swing.JPanel {
    
    /** Creates new form DataMinimalNNSetEX */
    public DataMinimalNNSetEX() {
        initComponents();
    }
    //gets exact sequence data used in Test_2H_2.calculateInAndOutData
    public double[]getData()
    {
     double[]ret=new double[7];
     double[]internal=dataMinimalNNSet1.getData();
     int i;
     for(i=0;i<internal.length;i++)ret[i]=internal[i];
     ret[i++]=Double.parseDouble(evt.getText());
     ret[i]=Double.parseDouble(exevt.getText());
     return ret;
    }
    public void setData(double[]  d)
    {
     
      dataMinimalNNSet1.setData(d);
   
    }
    public void setHidenOnChangeData(double[] d)
    {
     dataMinimalNNSet1.setHidenOnChangeData(d);
    }
     public double[] getHidenOnChangeData()
    {
     return dataMinimalNNSet1.getHidenData();
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        exevt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        evt = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        dataMinimalNNSet1 = new myCForms.DataMinimalNNSet();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        exevt.setText("0.001");
        exevt.setInputVerifier(new MySympleValVerifier(MySympleValVerifier.FLOAT,-100.0f,100.0f));
        add(exevt, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, -1, -1));

        jLabel4.setText("EX EVT");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 20, -1, -1));

        evt.setText("0.001");
        evt.setInputVerifier(new MySympleValVerifier(MySympleValVerifier.FLOAT,-100.0f,100.0f));
        add(evt, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 0, 50, -1));

        jLabel1.setText("EVT");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 0, -1, -1));
        add(dataMinimalNNSet1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 60));
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private myCForms.DataMinimalNNSet dataMinimalNNSet1;
    private javax.swing.JTextField evt;
    private javax.swing.JTextField exevt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables
    
}
