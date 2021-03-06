import DBCONNECT.Mysql_connect;
import DBCONNECT.DataBaseConnector;
import DBCONNECT.mssql_connect;
import Utils.LoggerNB;
import Utils.MySimpleBrowser;
import javax.swing.JOptionPane;
/*
 * DBSettingsNB.java
 *
 * Created on October 18, 2006, 8:19 PM
 */

/**
 *
 * @author  Dmitry
 */
public class DBSettingsNB extends javax.swing.JFrame {
    private boolean lastdbtryok;
    
    /** Creates new form DBSettingsNB */
    public DBSettingsNB() {
        initComponents();
        String servertype =
            BursaAnalizer_Frame.p.getProperty("serverdriver.type");

        if (servertype.equals("msdb")) {
            accessrb.setSelected(true);
            lockfieldsbytypeofdb(false);
        } else if (servertype.equals("mysql")) {
            mysqlrb.setSelected(true);
            lockfieldsbytypeofdb(true);
        } else if (servertype.equals("mssql")) {
            mssql.setSelected(true);
            lockfieldsbytypeofdb(true);
        }
        boolean serverreqpass =
            Boolean.parseBoolean(BursaAnalizer_Frame.p.getProperty("server.reqpass"));
        mysqlpassreqired.setSelected(serverreqpass);
        
    
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        browse = new javax.swing.JButton();
        pathtoaccess = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        base = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        systema = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        data = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        usertomysqldb = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        passtomysqldb = new javax.swing.JPasswordField();
        jLabel7 = new javax.swing.JLabel();
        mysqlhost = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        mysqlport = new javax.swing.JTextField();
        mysqlpassreqired = new javax.swing.JCheckBox();
        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        usertomssqldb = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        passtomssqldb = new javax.swing.JPasswordField();
        jLabel11 = new javax.swing.JLabel();
        mssqlhost = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        mssqlport = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        mssqlserver = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        mysqlrb = new javax.swing.JRadioButton();
        accessrb = new javax.swing.JRadioButton();
        mssql = new javax.swing.JRadioButton();
        test = new javax.swing.JButton();
        ok = new javax.swing.JButton();
        cancel = new javax.swing.JButton();
        defaults = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        browse.setText("Browse");
        browse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseActionPerformed(evt);
            }
        });

        pathtoaccess.setText(BursaAnalizer_Frame.p.getProperty(
            "datasource.datapath").replace("@workingdir@", System.getProperty("user.dir")));

    jLabel1.setText("Path to DB folder");

    jLabel2.setText("Base:");

    base.setText(BursaAnalizer_Frame.p.getProperty(
        "datasource.base"));

jLabel3.setText("Systema:");

systema.setText(BursaAnalizer_Frame.p.getProperty(
    "datasource.systema"));

    jLabel4.setText("Data:");

    data.setText(BursaAnalizer_Frame.p.getProperty(
        "datasource.data"));

org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
jPanel2.setLayout(jPanel2Layout);
jPanel2Layout.setHorizontalGroup(
    jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
    .add(jPanel2Layout.createSequentialGroup()
        .addContainerGap()
        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
            .add(jLabel4)
            .add(jLabel3)
            .add(jLabel2)
            .add(jLabel1))
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                .add(org.jdesktop.layout.GroupLayout.LEADING, data)
                .add(org.jdesktop.layout.GroupLayout.LEADING, systema)
                .add(org.jdesktop.layout.GroupLayout.LEADING, base, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 109, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(jPanel2Layout.createSequentialGroup()
                .add(pathtoaccess, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 332, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(15, 15, 15)
                .add(browse)))
        .addContainerGap(19, Short.MAX_VALUE))
    );
    jPanel2Layout.setVerticalGroup(
        jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        .add(jPanel2Layout.createSequentialGroup()
            .add(36, 36, 36)
            .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(jLabel1)
                .add(pathtoaccess, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(browse))
            .add(45, 45, 45)
            .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(jLabel2)
                .add(base, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(17, 17, 17)
            .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(jLabel3)
                .add(systema, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(15, 15, 15)
            .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(jLabel4)
                .add(data, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(67, Short.MAX_VALUE))
    );

    jTabbedPane1.addTab("MS Access settings", jPanel2);

    jLabel5.setText("User:");

    usertomysqldb.setText(BursaAnalizer_Frame.p.getProperty(
        "server.username"));

jLabel6.setText("Password:");

passtomysqldb.setText(BursaAnalizer_Frame.p.getProperty(
    "server.password"));

    jLabel7.setText("Host:");

    mysqlhost.setText(BursaAnalizer_Frame.p.getProperty("server.host"));

    jLabel8.setText("Port:");

    mysqlport.setText(BursaAnalizer_Frame.p.getProperty("server.port"));

    mysqlpassreqired.setText("Required ACK");
    mysqlpassreqired.addChangeListener(new javax.swing.event.ChangeListener() {
        public void stateChanged(javax.swing.event.ChangeEvent evt) {
            mysqlpassreqiredStateChanged(evt);
        }
    });
    mysqlpassreqired.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            mysqlpassreqiredActionPerformed(evt);
        }
    });

    org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
    jPanel3.setLayout(jPanel3Layout);
    jPanel3Layout.setHorizontalGroup(
        jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        .add(jPanel3Layout.createSequentialGroup()
            .add(17, 17, 17)
            .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                .add(jLabel8)
                .add(jLabel7)
                .add(jLabel6)
                .add(jLabel5))
            .add(12, 12, 12)
            .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                .add(mysqlport)
                .add(mysqlhost)
                .add(passtomysqldb)
                .add(usertomysqldb, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
            .add(mysqlpassreqired)
            .addContainerGap(231, Short.MAX_VALUE))
    );
    jPanel3Layout.setVerticalGroup(
        jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        .add(jPanel3Layout.createSequentialGroup()
            .add(33, 33, 33)
            .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(jLabel5)
                .add(usertomysqldb, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(14, 14, 14)
            .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(jLabel6)
                .add(passtomysqldb, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(mysqlpassreqired))
            .add(27, 27, 27)
            .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                .add(jLabel7)
                .add(mysqlhost, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(23, 23, 23)
            .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(jLabel8)
                .add(mysqlport, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(83, Short.MAX_VALUE))
    );

    jTabbedPane1.addTab("MySQL settings", jPanel3);

    jLabel9.setText("User:");

    usertomssqldb.setText(BursaAnalizer_Frame.p.getProperty(
        "server.mssqlusername"));
usertomssqldb.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        usertomssqldbActionPerformed(evt);
    }
    });

    jLabel10.setText("Password:");

    passtomssqldb.setText(BursaAnalizer_Frame.p.getProperty(
        "server.mssqlpassword"));

jLabel11.setText("Host:");

mssqlhost.setText(BursaAnalizer_Frame.p.getProperty("server.host"));

jLabel12.setText("Port:");

mssqlport.setText(BursaAnalizer_Frame.p.getProperty("server.port"));

jLabel13.setText("Server");

mssqlserver.setText(BursaAnalizer_Frame.p.getProperty("server.SERVER"));
mssqlserver.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        mssqlserverActionPerformed(evt);
    }
    });

    org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanel5);
    jPanel5.setLayout(jPanel5Layout);
    jPanel5Layout.setHorizontalGroup(
        jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        .add(jPanel5Layout.createSequentialGroup()
            .add(17, 17, 17)
            .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                .add(jLabel12)
                .add(jLabel11)
                .add(jLabel10)
                .add(jLabel9))
            .add(12, 12, 12)
            .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                .add(mssqlport)
                .add(mssqlhost)
                .add(passtomssqldb)
                .add(usertomssqldb, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE))
            .add(88, 88, 88)
            .add(jLabel13)
            .add(42, 42, 42)
            .add(mssqlserver, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
            .add(48, 48, 48))
    );
    jPanel5Layout.setVerticalGroup(
        jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        .add(jPanel5Layout.createSequentialGroup()
            .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel5Layout.createSequentialGroup()
                    .add(33, 33, 33)
                    .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel9)
                        .add(usertomssqldb, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(14, 14, 14)
                    .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel10)
                        .add(passtomssqldb, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(27, 27, 27)
                    .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                        .add(jLabel11)
                        .add(mssqlhost, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(23, 23, 23)
                    .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel12)
                        .add(mssqlport, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(jPanel5Layout.createSequentialGroup()
                    .add(43, 43, 43)
                    .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(mssqlserver, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabel13))))
            .addContainerGap(86, Short.MAX_VALUE))
    );

    jTabbedPane1.addTab("MSSQL settings", jPanel5);

    jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Curently used", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(51, 51, 255)));

    buttonGroup1.add(mysqlrb);
    mysqlrb.setText("MySQL");
    mysqlrb.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
    mysqlrb.setMargin(new java.awt.Insets(0, 0, 0, 0));
    mysqlrb.addChangeListener(new javax.swing.event.ChangeListener() {
        public void stateChanged(javax.swing.event.ChangeEvent evt) {
            mysqlrbStateChanged(evt);
        }
    });
    mysqlrb.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            mysqlrbActionPerformed(evt);
        }
    });

    buttonGroup1.add(accessrb);
    accessrb.setSelected(true);
    accessrb.setText("Access");
    accessrb.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
    accessrb.setMargin(new java.awt.Insets(0, 0, 0, 0));
    accessrb.addChangeListener(new javax.swing.event.ChangeListener() {
        public void stateChanged(javax.swing.event.ChangeEvent evt) {
            accessrbStateChanged(evt);
        }
    });

    buttonGroup1.add(mssql);
    mssql.setText("mssql");
    mssql.addChangeListener(new javax.swing.event.ChangeListener() {
        public void stateChanged(javax.swing.event.ChangeEvent evt) {
            mssqlStateChanged(evt);
        }
    });
    mssql.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            mssqlActionPerformed(evt);
        }
    });

    org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
    jPanel4.setLayout(jPanel4Layout);
    jPanel4Layout.setHorizontalGroup(
        jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        .add(jPanel4Layout.createSequentialGroup()
            .addContainerGap()
            .add(mysqlrb)
            .add(26, 26, 26)
            .add(accessrb)
            .add(18, 18, 18)
            .add(mssql)
            .addContainerGap(19, Short.MAX_VALUE))
    );
    jPanel4Layout.setVerticalGroup(
        jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
            .add(mysqlrb)
            .add(accessrb)
            .add(mssql))
    );

    test.setText("Test");
    test.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            testActionPerformed(evt);
        }
    });

    ok.setText("Ok");
    ok.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            okActionPerformed(evt);
        }
    });

    cancel.setText("Cancel");
    cancel.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            cancelActionPerformed(evt);
        }
    });

    defaults.setText("Defaults");
    defaults.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            defaultsActionPerformed(evt);
        }
    });

    org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
        jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        .add(jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jTabbedPane1)
                .add(jPanel1Layout.createSequentialGroup()
                    .add(test)
                    .add(16, 16, 16)
                    .add(ok)
                    .add(17, 17, 17)
                    .add(cancel)
                    .add(22, 22, 22)
                    .add(defaults))
                .add(jPanel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addContainerGap())
    );
    jPanel1Layout.setVerticalGroup(
        jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        .add(jPanel1Layout.createSequentialGroup()
            .add(jPanel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .add(14, 14, 14)
            .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .add(18, 18, 18)
            .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(test)
                .add(ok)
                .add(cancel)
                .add(defaults))
            .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        .add(layout.createSequentialGroup()
            .addContainerGap()
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addContainerGap())
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        .add(layout.createSequentialGroup()
            .addContainerGap()
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addContainerGap())
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void accessrbStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_accessrbStateChanged
    lockfieldsbytypeofdb(!accessrb.isSelected());
    }//GEN-LAST:event_accessrbStateChanged

    private void mysqlrbStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_mysqlrbStateChanged
        lockfieldsbytypeofdb(mysqlrb.isSelected());
    }//GEN-LAST:event_mysqlrbStateChanged

    private void browseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseActionPerformed
    MySimpleBrowser sb =
                    new MySimpleBrowser(this,
                                        "Select the System MDB", pathtoaccess, false,
                                        true, new String[] { "mdb" });

                if ((sb.file == null) || (sb.directory == null)) {
                    return;
                }
                JOptionPane
                    .showMessageDialog(this, "<HTML><center><font size=3 color=blue>"
                               + sb.directory
                               + "<br><font size=4 color=red>directory change!!!", "Set up", JOptionPane
                                   .INFORMATION_MESSAGE);
                pathtoaccess.setText(sb.directory);
                
    }//GEN-LAST:event_browseActionPerformed

    private void defaultsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_defaultsActionPerformed
    if (mysqlrb.isSelected()) 
    {
                    usertomysqldb.setText("bauser");
                    passtomysqldb.setText("none");
                    mysqlport.setText("3306");
                    mysqlhost.setText("localhost");
     } else if (mssql.isSelected()) 
    {
                    usertomssqldb.setText("sa");
                    passtomssqldb.setText("sa");
                    mssqlport.setText("1433");
                    mssqlhost.setText("127.0.0.1");
                    mssqlserver.setText("batool");
     } 
    else if (accessrb.isSelected()) 
    {
                    base.setText("base");
                    systema.setText("systema");
                    data.setText("data");
     }
    }//GEN-LAST:event_defaultsActionPerformed

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
    dispose();
    }//GEN-LAST:event_cancelActionPerformed

    private void testActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testActionPerformed
    lastdbtryok=false;
        if (mysqlrb.isSelected()) {
                    String               thost = mysqlhost.getText();
                    int                  tport =
                        Integer.parseInt(mysqlport.getText());
                    String               tuser = usertomysqldb.getText();
                    String               tpass = new String(passtomysqldb.getPassword());
                    DataBaseConnector temp  = new Mysql_connect("systema");
                    if(mysqlpassreqired.isSelected())
                    {
                        tpass=JOptionPane.showInputDialog(null);
                    }
                    if (((Mysql_connect) temp).setPort(tport) == false
                            || ((Mysql_connect) temp).setHost(thost) == false
                            || (temp.setUser(tuser) == false)
                            || (temp.setPass(tpass) == false)) {
                        LoggerNB.getLogger().log("One of settings wrong",
                                                  LoggerNB.SENSITIVE);
                        lastdbtryok=true;
                    } else if (((Mysql_connect) temp).connect()) {
                        JOptionPane.showMessageDialog(
                            this,
                            "<HTML><center><font size=3 color=blue>Test<br><font size=4 color=red>PASS!!!",
                            "Now Can Use It", JOptionPane.INFORMATION_MESSAGE);
                            lastdbtryok=true;
                    }
                }else if (mssql.isSelected()) {
                    String               thost = mssqlhost.getText();
                    int                  tport =
                        Integer.parseInt(mssqlport.getText());
                    String               tuser = usertomssqldb.getText();
                    String               tpass = new String(passtomssqldb.getPassword());
                    String               tserv = new String(this.mssqlserver.getText());
                    DataBaseConnector temp  = new mssql_connect(tserv,"net.sourceforge.jtds.jdbc.Driver","");
                    
                    if (((mssql_connect) temp).setPort(tport) == false
                            || ((mssql_connect) temp).setHost(thost) == false
                            || (temp.setUser(tuser) == false)
                            || (temp.setPass(tpass) == false)) {
                        LoggerNB.getLogger().log("One of settings wrong",
                                                  LoggerNB.SENSITIVE);
                        lastdbtryok=true;
                    } else if (((mssql_connect) temp).connect()) {
                        JOptionPane.showMessageDialog(
                            this,
                            "<HTML><center><font size=3 color=blue>Test<br><font size=4 color=red>PASS!!!",
                            "Now Can Use It", JOptionPane.INFORMATION_MESSAGE);
                            lastdbtryok=true;
                    }
                }
    /* else if (accessrb.isSelected()) 
                {
                    ;
                 // TODO implement it
                 if(LoggerNB.debuging){System.out.println("Not implemented yet :(");}
                }*/
    ok.setEnabled(lastdbtryok);
    }//GEN-LAST:event_testActionPerformed

    //TODO implement it
    private void okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okActionPerformed
    if(lastdbtryok!=true)return;
        if (mysqlrb.isSelected()) 
        {
            BursaAnalizer_Frame.p.setProperty("serverdriver.type","mysql");
            BursaAnalizer_Frame.p.setProperty("server.username",mysqlhost.getText());
            String ps=new String(passtomysqldb.getPassword());
            BursaAnalizer_Frame.p.setProperty("server.password",ps);
            BursaAnalizer_Frame.p.setProperty("server.host",mysqlhost.getText());
            BursaAnalizer_Frame.p.setProperty("server.port",mysqlport.getText());
            BursaAnalizer_Frame.p.setProperty("server.reqpass",Boolean.toString(mysqlpassreqired.isSelected()));
            
        JOptionPane.showMessageDialog(
                            this,
                            "<HTML><center><font size=3 color=blue>Changing setings to mySQL",
                            "You must restart to take effect", JOptionPane.INFORMATION_MESSAGE);
    }
        else if (mssql.isSelected()) 
        {
            BursaAnalizer_Frame.p.setProperty("serverdriver.type","mssql");
            BursaAnalizer_Frame.p.setProperty("server.mssqlusername",usertomssqldb.getText());
            String ps=new String(passtomssqldb.getPassword());
            BursaAnalizer_Frame.p.setProperty("server.mssqlpassword",ps);
            BursaAnalizer_Frame.p.setProperty("server.host",mssqlhost.getText());
            BursaAnalizer_Frame.p.setProperty("server.port",mssqlport.getText());
            BursaAnalizer_Frame.p.setProperty("server.SERVER",mssqlserver.getText());
            BursaAnalizer_Frame.p.setProperty("server.reqpass","true");
            
        JOptionPane.showMessageDialog(
                            this,
                            "<HTML><center><font size=3 color=blue>Changing setings to MSSQL",
                            "You must restart to take effect", JOptionPane.INFORMATION_MESSAGE);
    }
    else if (accessrb.isSelected()) 
    {
        JOptionPane.showMessageDialog(
                            this,
                            "<HTML><center><font size=3 color=blue>Changing setings to MS Access",
                            "You must restart to take effect", JOptionPane.INFORMATION_MESSAGE);
    }

    dispose();
    }//GEN-LAST:event_okActionPerformed

    private void mysqlpassreqiredActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_mysqlpassreqiredActionPerformed
    {//GEN-HEADEREND:event_mysqlpassreqiredActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_mysqlpassreqiredActionPerformed

    private void mysqlpassreqiredStateChanged (javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_mysqlpassreqiredStateChanged
    {//GEN-HEADEREND:event_mysqlpassreqiredStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_mysqlpassreqiredStateChanged

private void mssqlserverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mssqlserverActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_mssqlserverActionPerformed

private void mysqlrbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mysqlrbActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_mysqlrbActionPerformed

private void mssqlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mssqlActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_mssqlActionPerformed

private void mssqlStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_mssqlStateChanged
lockfieldsbytypeofdb(mssql.isSelected());
}//GEN-LAST:event_mssqlStateChanged

private void usertomssqldbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usertomssqldbActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_usertomssqldbActionPerformed
    
    void lockfieldsbytypeofdb(boolean state) {
        browse.setEnabled(!state);
        pathtoaccess.setEnabled(!state);
        base.setEnabled(!state);
        systema.setEnabled(!state);
        data.setEnabled(!state);
        mysqlport.setEnabled(state);
        mysqlhost.setEnabled(state);
        usertomysqldb.setEnabled(state);
        passtomysqldb.setEnabled(state);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DBSettingsNB().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton accessrb;
    private javax.swing.JTextField base;
    private javax.swing.JButton browse;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton cancel;
    private javax.swing.JTextField data;
    private javax.swing.JButton defaults;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JRadioButton mssql;
    private javax.swing.JTextField mssqlhost;
    private javax.swing.JTextField mssqlport;
    private javax.swing.JTextField mssqlserver;
    private javax.swing.JTextField mysqlhost;
    private javax.swing.JCheckBox mysqlpassreqired;
    private javax.swing.JTextField mysqlport;
    private javax.swing.JRadioButton mysqlrb;
    private javax.swing.JButton ok;
    private javax.swing.JPasswordField passtomssqldb;
    private javax.swing.JPasswordField passtomysqldb;
    private javax.swing.JTextField pathtoaccess;
    private javax.swing.JTextField systema;
    private javax.swing.JButton test;
    private javax.swing.JTextField usertomssqldb;
    private javax.swing.JTextField usertomysqldb;
    // End of variables declaration//GEN-END:variables
    
}
