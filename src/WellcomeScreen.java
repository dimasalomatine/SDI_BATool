import DBCONNECT.Msdb_connect;
import DBCONNECT.Mysql_connect;
import DBCONNECT.DataBaseConnector;
import DBCONNECT.TRS;
import DBCONNECT.mssql_connect;
import Utils.LoggerNB;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/*
 * WellcomeScreen.java
 *
 * Created on 2 ������ 2006, 09:38
 */

/**
 *
 * @author  Student
 */
public class WellcomeScreen extends javax.swing.JPanel {
    
    static private int curentuserid=758;
  static public int getCurentUserID(){return curentuserid;}
static public void setCurentUserID(int cui){ curentuserid=cui;}
static public void resetCurentUserID(){ curentuserid=758;}
  
   private static String ServerUsername = null;
  private static String ServerPassword = null;
  
  public  String getUsername() {
    return ServerUsername;
  }
   public  void setUsername(String un) {
     ServerUsername=un;
     user.setText(un);
  }
   public  void setAdminUsername(String un) {
     //ServerUsername=un;
     this.user_admin.setText(un);
  }
  public  String getPassword() {
    return ServerPassword;
  }
  public  void setPassword(String up) {
    ServerPassword=up;
    pass.setText(up);
  }
  public  void setAdminPassword(String up) {
    ServerPassword=up;
    pass_admin.setText(up);
  }
  
  private static String dataSource_sys = null;
  private static String dataSource_base = null;
  private static String dataSource_data = null;
  
  private boolean logedstatus=false;
  
    /** Creates new form WellcomeScreen */
    public WellcomeScreen() {
        initComponents();
        user.setText(ServerUsername);
        pass.setText(ServerPassword);
    }

    private String getAdminPassword() {
     //   return new String(this.pass_admin.getPassword());
            String L_passa=new String(pass_admin.getPassword());
        return L_passa;
    }

    private String getAdminUsername() {
        return this.user_admin.getText();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        user = new javax.swing.JTextField();
        login = new javax.swing.JButton();
        pass = new javax.swing.JPasswordField();
        jPanel2 = new javax.swing.JPanel();
        logAsAdmin = new javax.swing.JButton();
        user_admin = new javax.swing.JTextField();
        pass_admin = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        msg = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jname = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jaddr = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jtel = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jfax = new javax.swing.JTextField();
        jmail = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jlname = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jcity = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jmob = new javax.swing.JTextField();
        jinfoupdate = new javax.swing.JToggleButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1024, 610));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Password(min 6 chrs):");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel2.setText("Login(min 4 chrs):");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        user.setText("anonym");
        user.setMaximumSize(new java.awt.Dimension(6, 60));
        user.setMinimumSize(new java.awt.Dimension(6, 100));
        user.setPreferredSize(new java.awt.Dimension(6, 100));
        user.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                userFocusLost(evt);
            }
        });
        jPanel1.add(user, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 130, 20));

        login.setIcon(new javax.swing.ImageIcon("C:\\SDI_BATool\\images\\id_card.png")); // NOI18N
        login.setText("Login");
        login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginActionPerformed(evt);
            }
        });
        jPanel1.add(login, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, -1, -1));

        pass.setText("12345678");
        pass.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                passFocusLost(evt);
            }
        });
        jPanel1.add(pass, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, 130, -1));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 310, -1));

        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logAsAdmin.setIcon(new javax.swing.ImageIcon("C:\\SDI_BATool\\images\\address_book.png")); // NOI18N
        logAsAdmin.setText("LogAsAdmin");
        logAsAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logAsAdminActionPerformed(evt);
            }
        });
        jPanel2.add(logAsAdmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, -1, -1));

        user_admin.setText("anonym");
        user_admin.setMaximumSize(new java.awt.Dimension(20, 100));
        user_admin.setMinimumSize(new java.awt.Dimension(20, 100));
        user_admin.setPreferredSize(new java.awt.Dimension(20, 100));
        user_admin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                user_adminFocusLost(evt);
            }
        });
        jPanel2.add(user_admin, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 130, 20));

        pass_admin.setText("12345678");
        pass_admin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                pass_adminFocusLost(evt);
            }
        });
        jPanel2.add(pass_admin, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, 130, -1));

        jLabel4.setText("Admin");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel5.setText("Password");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 310, 120));

        msg.setFont(new java.awt.Font("Tahoma", 3, 14));
        msg.setForeground(new java.awt.Color(0, 0, 153));
        add(msg, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, 300, 20));

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setText("F.Name:");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jnameActionPerformed(evt);
            }
        });
        jPanel3.add(jname, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 170, -1));

        jLabel7.setText("Address:");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jaddr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jaddrActionPerformed(evt);
            }
        });
        jPanel3.add(jaddr, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 40, 170, -1));

        jLabel8.setText("Tel:");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        jtel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtelActionPerformed(evt);
            }
        });
        jPanel3.add(jtel, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 170, -1));

        jLabel9.setText("Fax:");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        jLabel10.setText("E-Mail:");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        jfax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jfaxActionPerformed(evt);
            }
        });
        jPanel3.add(jfax, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 100, 170, -1));

        jmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmailActionPerformed(evt);
            }
        });
        jPanel3.add(jmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 130, 170, -1));

        jLabel15.setText("L.Name");
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, -1, -1));

        jlname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jlnameActionPerformed(evt);
            }
        });
        jPanel3.add(jlname, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 10, 160, -1));

        jLabel16.setText("City:");
        jPanel3.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 40, -1, -1));

        jcity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcityActionPerformed(evt);
            }
        });
        jPanel3.add(jcity, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 40, 160, -1));

        jLabel17.setText("Mob:");
        jPanel3.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 70, -1, -1));

        jmob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmobActionPerformed(evt);
            }
        });
        jPanel3.add(jmob, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 70, 160, -1));

        jinfoupdate.setText("Update");
        jinfoupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jinfoupdateActionPerformed(evt);
            }
        });
        jPanel3.add(jinfoupdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 110, -1, -1));

        add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 170, -1, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon("C:\\SDI_BATool\\images\\logo1.jpg")); // NOI18N
        jLabel3.setOpaque(true);
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("�� ������ ������ ������� �������  �2009 ");
        add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(555, 10, 320, -1));

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("����� ����� ������� ����� �");
        add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 470, -1, -1));

        jLabel13.setText("ImpactSoft");
        add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 470, -1, -1));

        jLabel14.setIcon(new javax.swing.ImageIcon("C:\\SDI_BATool\\images\\logoimpact.jpg")); // NOI18N
        jLabel14.setOpaque(true);
        add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 500, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginActionPerformed
     if(logedstatus)
     {
      disconnectFromDBS();
      pass.setEnabled(true);
      user.setEnabled(true);
      login.setText("Logon");
      msg.setText("Disconected mode");
     }else
     {
        SDI_Properties p=BursaAnalizer_Frame.getInstance().p;
        String servertype=p.getProperty("serverdriver.type");
        setUsername(user.getText());
        String L_pass=new String(pass.getPassword());
        setPassword(L_pass);
        System.out.println(L_pass+" "+new String(pass.getPassword()));
        if(logIninto(servertype))
        BursaAnalizer_Frame.getUserFolderInstance().sv.fetchdata(getCurentUserID());
        else
            BursaAnalizer_Frame.getUserFolderInstance().sv.fetchdata(-1);
     }
     
    }//GEN-LAST:event_loginActionPerformed

    private void passFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passFocusLost
         String ps=new String(pass.getPassword());
        if(ps.length()<4)
        {
            JOptionPane.showMessageDialog(this,
         	 						   "password ? :)",
         	 								"must be at least 4 characters !!!!",
         	 								JOptionPane.INFORMATION_MESSAGE);
            pass.grabFocus();
        }
    }//GEN-LAST:event_passFocusLost

    private void userFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_userFocusLost
        if(user.getText().length()<4)
        {
            JOptionPane.showMessageDialog(this,
         	 						   "user name ? :)",
         	 								"must be at least 4 characters !!!!",
         	 								JOptionPane.INFORMATION_MESSAGE);
            user.grabFocus();
        }
    }//GEN-LAST:event_userFocusLost

    private void logAsAdminActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_logAsAdminActionPerformed
    {//GEN-HEADEREND:event_logAsAdminActionPerformed
      if(logedstatus)
     {
      disconnectFromDBS();
      pass.setEnabled(true);
      user.setEnabled(true);
      msg.setText("Disconected mode");
     }else
     {
        SDI_Properties p=BursaAnalizer_Frame.getInstance().p;
        String servertype=p.getProperty("serverdriver.type");
        setUsername(user.getText());
        setAdminUsername(user_admin.getText());
        String L_passa=new String(pass_admin.getPassword());
        setAdminPassword(L_passa);
        if(logAsAdminIninto(servertype))
        BursaAnalizer_Frame.getUserFolderInstance().sv.fetchdata(getCurentUserID());
        else
            BursaAnalizer_Frame.getUserFolderInstance().sv.fetchdata(-1);
     }
    }//GEN-LAST:event_logAsAdminActionPerformed

    private void pass_adminFocusLost (java.awt.event.FocusEvent evt)//GEN-FIRST:event_pass_adminFocusLost
    {//GEN-HEADEREND:event_pass_adminFocusLost
      String ps=new String(pass_admin.getPassword());
        if(ps.length()<4)
        {
            JOptionPane.showMessageDialog(this,
         	 						   "password ? :)",
         	 								"must be at least 4 characters !!!!",
         	 								JOptionPane.INFORMATION_MESSAGE);
            pass_admin.grabFocus();
        }
}//GEN-LAST:event_pass_adminFocusLost

    private void user_adminFocusLost (java.awt.event.FocusEvent evt)//GEN-FIRST:event_user_adminFocusLost
    {//GEN-HEADEREND:event_user_adminFocusLost
       if(user_admin.getText().length()<4)
        {
            JOptionPane.showMessageDialog(this,
         	 						   "user name ? :)",
         	 								"must be at least 4 characters !!!!",
         	 								JOptionPane.INFORMATION_MESSAGE);
            user_admin.grabFocus();
        }
}//GEN-LAST:event_user_adminFocusLost

    private void jtelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtelActionPerformed
        Connection tcon=BursaAnalizer_Frame.getConnectToDB(BursaAnalizer_Frame.DBID_USER).con;
       TRS RS=new TRS(tcon);
       String tsql="UPDATE users set  tel='"+jtel.getText()+"' where userid="+curentuserid;
       RS.execSQL(tsql);
    }//GEN-LAST:event_jtelActionPerformed

    private void jnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jnameActionPerformed
       Connection tcon=BursaAnalizer_Frame.getConnectToDB(BursaAnalizer_Frame.DBID_USER).con;
       TRS RS=new TRS(tcon);
       String tsql="UPDATE users set  fn='"+jname.getText()+"' where userid="+curentuserid;
       RS.execSQL(tsql);
    }//GEN-LAST:event_jnameActionPerformed

    private void jaddrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jaddrActionPerformed
        Connection tcon=BursaAnalizer_Frame.getConnectToDB(BursaAnalizer_Frame.DBID_USER).con;
       TRS RS=new TRS(tcon);
       String tsql="UPDATE users set  addr='"+jaddr.getText()+"' where userid="+curentuserid;
       RS.execSQL(tsql);
    }//GEN-LAST:event_jaddrActionPerformed

    private void jfaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jfaxActionPerformed
       Connection tcon=BursaAnalizer_Frame.getConnectToDB(BursaAnalizer_Frame.DBID_USER).con;
       TRS RS=new TRS(tcon);
       String tsql="UPDATE users set  fax='"+jfax.getText()+"' where userid="+curentuserid;
       RS.execSQL(tsql);
    }//GEN-LAST:event_jfaxActionPerformed

    private void jmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmailActionPerformed
        Connection tcon=BursaAnalizer_Frame.getConnectToDB(BursaAnalizer_Frame.DBID_USER).con;
       TRS RS=new TRS(tcon);
       String tsql="UPDATE users set  mail='"+jmail.getText()+"' where userid="+curentuserid;
       RS.execSQL(tsql);
    }//GEN-LAST:event_jmailActionPerformed

    private void jlnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jlnameActionPerformed
        Connection tcon=BursaAnalizer_Frame.getConnectToDB(BursaAnalizer_Frame.DBID_USER).con;
       TRS RS=new TRS(tcon);
       String tsql="UPDATE users set  ln='"+jlname.getText()+"' where userid="+curentuserid;
       RS.execSQL(tsql);
    }//GEN-LAST:event_jlnameActionPerformed

    private void jcityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcityActionPerformed
       Connection tcon=BursaAnalizer_Frame.getConnectToDB(BursaAnalizer_Frame.DBID_USER).con;
       TRS RS=new TRS(tcon);
       String tsql="UPDATE users set  city='"+jcity.getText()+"' where userid="+curentuserid;
       RS.execSQL(tsql);
    }//GEN-LAST:event_jcityActionPerformed

    private void jmobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmobActionPerformed
        Connection tcon=BursaAnalizer_Frame.getConnectToDB(BursaAnalizer_Frame.DBID_USER).con;
       TRS RS=new TRS(tcon);
       String tsql="UPDATE users set  mob='"+jmob.getText()+"' where userid="+curentuserid;
       RS.execSQL(tsql);
    }//GEN-LAST:event_jmobActionPerformed

    private void jinfoupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jinfoupdateActionPerformed
        jnameActionPerformed(null);
        jlnameActionPerformed(null);
        jfaxActionPerformed(null);
        jtelActionPerformed(null);
        jmobActionPerformed(null);
        jmailActionPerformed(null);
        jcityActionPerformed(null);
        jaddrActionPerformed(null);
    }//GEN-LAST:event_jinfoupdateActionPerformed

    public void initinfo()
    {
                   this.jname.setText("");
                   this.jlname.setText("");
                    this.jaddr.setText("");
                    this.jcity.setText("");
                    this.jtel.setText("");
                    this.jmob.setText("");
                    this.jfax.setText("");
                    this.jmail.setText("");
       Connection tcon=BursaAnalizer_Frame.getConnectToDB(BursaAnalizer_Frame.DBID_USER).con;
       TRS RS=new TRS(tcon);
       String tsql="SELECT isnull(FN,'') fn,isnull(LN,'') ln,isnull(addr,'') addr,isnull(city,'') city,isnull(tel,'') tel,isnull(mob,'') mob,isnull(fax,'') fax,isnull(mail,'') mail";
       tsql+=" from users where userid="+curentuserid;
       RS.execSQL(tsql);
       System.out.println("Init Info");
       if(RS.rs!=null)
       {
            try {
                if (RS.rs.next()) {
                    this.jname.setText(RS.rs.getString("fn"));
                    this.jlname.setText(RS.rs.getString("ln"));
                    this.jaddr.setText(RS.rs.getString("addr"));
                    this.jcity.setText(RS.rs.getString("city"));
                    this.jtel.setText(RS.rs.getString("tel"));
                    this.jmob.setText(RS.rs.getString("mob"));
                    this.jfax.setText(RS.rs.getString("fax"));
                    this.jmail.setText(RS.rs.getString("mail"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(WellcomeScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
    }
    void setAutoLogin(String auto,String servertype) {
        if(auto.equalsIgnoreCase("true"))logIninto(servertype);
    }
    public boolean logIninto(String servertype)
    {
        int errlevel=0;
        SDI_Properties p=BursaAnalizer_Frame.getInstance().p;
          System.out.println ("Connecting to databases");
          DataBaseConnector users,base,data;
          try{
                 if(servertype.equals("msdb"))
                 {
                dataSource_sys = p.getProperty("datasource.systema");
                if (dataSource_sys == null)throw new Exception ();
                    dataSource_base = p.getProperty("datasource.base");
                if (dataSource_base == null)throw new Exception ();
                 dataSource_data = p.getProperty("datasource.data");
                if (dataSource_data == null)throw new Exception ();
                 }
                 if(servertype.equals("mssql"))
                 {
                dataSource_sys = p.getProperty("server.SERVER");
                if (dataSource_sys == null)throw new Exception ();
                    dataSource_base = p.getProperty("server.SERVER");
                if (dataSource_base == null)throw new Exception ();
                 dataSource_data = p.getProperty("server.SERVER");
                if (dataSource_data == null)throw new Exception ();
                 }
            }catch(Exception e)
            {
                if(LoggerNB.debuging){System.out.println("Err level 1"+e);}
            }
        String tpass;
        String tlog;
      if(servertype.equals("msdb"))
      {
      	String datapath=p.getProperty("datasource.datapath");
        users=new Msdb_connect(datapath+dataSource_sys+".mdb",getUsername(),getPassword());
        if(!((Msdb_connect)users).connect())errlevel++;
        TRS tmprs=new TRS(((Msdb_connect)users).con);
        setCurentUserID(tmprs.rowExistID("SELECT * FROM USERS WHERE USER='"+getUsername()+"' AND PASS='"+getPassword()+"'","USERID"));
        BursaAnalizer_Frame.setConnectToDB(BursaAnalizer_Frame.DBID_USER,users);
    	
    	base=new Msdb_connect(datapath+dataSource_base+".mdb");
    	if(!((Msdb_connect)base).connect())errlevel++;
         BursaAnalizer_Frame.setConnectToDB(BursaAnalizer_Frame.DBID_BASE,base);
         
    	data=new Msdb_connect(datapath+dataSource_data+".mdb");
    	if(!((Msdb_connect)data).connect())errlevel++;
        BursaAnalizer_Frame.setConnectToDB(BursaAnalizer_Frame.DBID_DATA,data);
      }else if(servertype.equals("mysql"))
      {
        boolean serverreqpass =
            Boolean.parseBoolean(BursaAnalizer_Frame.p.getProperty("server.reqpass"));
        tlog=getUsername();
        tpass=getPassword();
        if(serverreqpass==true)
        {
         tpass=JOptionPane.showInputDialog(null);
        }
      	int tport=Integer.parseInt(p.getProperty("server.port"));
      	String thost=p.getProperty("server.host");
      	users=new Mysql_connect(dataSource_sys,tlog,tpass);
      	((Mysql_connect)users).setPort(tport);
      	((Mysql_connect)users).setHost(thost);
    	if(!((Mysql_connect)users).connect())errlevel++;
        /*
         TRS tmprs=new TRS(((Msdb_connect)users).con);
        setCurentUserID(tmprs.rowExistID("SELECT * FROM USERS WHERE USER='"+getUsername()+"' AND PASS='"+getPassword()+"'","USERID"));
        
         */
         BursaAnalizer_Frame.setConnectToDB(BursaAnalizer_Frame.DBID_USER,users);
    	base=new Mysql_connect(dataSource_base,tlog,tpass);
    	((Mysql_connect)base).setPort(tport);
    	((Mysql_connect)base).setHost(thost);
    	if(!((Mysql_connect)base).connect())errlevel++;
        BursaAnalizer_Frame.setConnectToDB(BursaAnalizer_Frame.DBID_BASE,base);
    	data=new Mysql_connect(dataSource_data,tlog,tpass);
    	((Mysql_connect)data).setPort(tport);
    	((Mysql_connect)data).setHost(thost);
    	if(!((Mysql_connect)data).connect())errlevel++;
        BursaAnalizer_Frame.setConnectToDB(BursaAnalizer_Frame.DBID_DATA,data);
      }else if(servertype.equals("mssql"))
      {    
        tlog=getUsername();
        tpass=getPassword();
      	int tport=Integer.parseInt(p.getProperty("server.mssqlport"));
      	String thost=p.getProperty("server.mssqlhost");
        String tu=p.getProperty("server.mssqlusername");
        String tp=p.getProperty("server.mssqlpassword");
        String tdriver=p.getProperty("server.mssqldriver");
        String tsqlinstance=p.getProperty("server.mssqlinstance");
        System.out.println("driver:"+tdriver+"\ninstance:"+tsqlinstance);
      	users=new mssql_connect(dataSource_sys,tu,tp,tdriver,tsqlinstance.trim());
      	((mssql_connect)users).setPort(tport);
      	((mssql_connect)users).setHost(thost);
    	if(!((mssql_connect)users).connect())errlevel++;
        TRS tmprs=new TRS(((mssql_connect)users).con);
        setCurentUserID(tmprs.rowExistID("SELECT * FROM USERS WHERE [USER]='"+getUsername()+"' AND PASS='"+getPassword()+"'","USERID"));
         BursaAnalizer_Frame.setConnectToDB(BursaAnalizer_Frame.DBID_USER,users);
    	base=new mssql_connect(dataSource_base,tu,tp,tdriver,tsqlinstance.trim());
    	((mssql_connect)base).setPort(tport);
    	((mssql_connect)base).setHost(thost);
    	if(!((mssql_connect)base).connect())errlevel++;
        BursaAnalizer_Frame.setConnectToDB(BursaAnalizer_Frame.DBID_BASE,base);
    	data=new mssql_connect(dataSource_data,tu,tp,tdriver,tsqlinstance.trim());
    	((mssql_connect)data).setPort(tport);
    	((mssql_connect)data).setHost(thost);
    	if(!((mssql_connect)data).connect())errlevel++;
        BursaAnalizer_Frame.setConnectToDB(BursaAnalizer_Frame.DBID_DATA,data);
      }
      else
      {
      	System.out.println("Unknown datasource type "+servertype);
      	System.out.println("Currently supported msdb access and mysql");
        errlevel=4;
      }
       if(errlevel==0)
          {
              login.setText("Logout");
              String timgdir=BursaAnalizer_Frame.getImgDir();
              login.setIcon(new javax.swing.ImageIcon(timgdir+"login.gif"));
              this.user.setEnabled(false);
              this.pass.setEnabled(false);
              logedstatus=true;
              msg.setText("Connected as "+getUsername());
          }
          else 
          {
              login.setText("Logon");
              String timgdir=BursaAnalizer_Frame.getImgDir();
              login.setIcon(new javax.swing.ImageIcon(timgdir+"smiles//locked.gif"));
              this.login.setEnabled(true);
              this.pass.setEnabled(true);
              msg.setText("Disconected mode");
          }
          initinfo();
          return logedstatus;
    }
    
    public boolean logAsAdminIninto(String servertype)
    {
        int errlevel=0;
          SDI_Properties p=BursaAnalizer_Frame.getInstance().p;
          System.out.println ("Connecting to databases");
          DataBaseConnector users,base,data;
          try{
              if(servertype.equals("msdb"))
              {
                dataSource_sys = p.getProperty("datasource.systema");
                if (dataSource_sys == null)throw new Exception ();
                    dataSource_base = p.getProperty("datasource.base");
                if (dataSource_base == null)throw new Exception ();
                 dataSource_data = p.getProperty("datasource.data");
                if (dataSource_data == null)throw new Exception ();
              }
              if(servertype.equals("mssql"))
                 {
                dataSource_sys = p.getProperty("server.SERVER");
                if (dataSource_sys == null)throw new Exception ();
                    dataSource_base = p.getProperty("server.SERVER");
                if (dataSource_base == null)throw new Exception ();
                 dataSource_data = p.getProperty("server.SERVER");
                if (dataSource_data == null)throw new Exception ();
                 }
              
            }catch(Exception e)
            {
                if(LoggerNB.debuging){System.out.println("Err level 1"+e);}
            }
        String tpass;
        String tlog;
      if(servertype.equals("msdb"))
      {
      	String datapath=p.getProperty("datasource.datapath");
        users=new Msdb_connect(datapath+dataSource_sys+".mdb",getAdminUsername(),getAdminPassword());
        if(!((Msdb_connect)users).connect())errlevel++;
        //TODO log as Admin to Users Folder
        TRS tmprs=new TRS(((Msdb_connect)users).con);
        //check if admin is valid login
        if(tmprs.rowExistID("SELECT * FROM USERS WHERE USER='"+getAdminUsername()+"' AND PASS='"+getAdminPassword()+"'","USERID")<=0)
        {
           //message
            logedstatus=false;
            JOptionPane.showMessageDialog(this,
         	                          "user/admin/pasword ? :)",
         	 			"one of them probably uncorect !!!!",
         	 			JOptionPane.INFORMATION_MESSAGE);
            return logedstatus;
        }
        //here check if curent admin permited to view this user data
        String users_admins=tmprs.getSpecValue("SELECT top 1 * FROM USERS WHERE USER='"+getUsername()+"'","groupspermited");
        String users_adminsgroup=tmprs.getSpecValue("SELECT top 1 * FROM USERS WHERE USER='"+getAdminUsername()+"'","groupid");
        boolean permited=false;
        String tss[]=users_admins.split(" ");
        for(int tui=0;tui<tss.length;tui++)
        {
         if(users_adminsgroup.equalsIgnoreCase(tss[tui]))
         {
          permited=true;
          break;
         }
        }
        if(permited)
        {
        //and then log as user
        setCurentUserID(tmprs.rowExistID("SELECT * FROM USERS WHERE USER='"+getUsername()+"'","USERID"));
        BursaAnalizer_Frame.setConnectToDB(BursaAnalizer_Frame.DBID_USER,users);
    	
    	base=new Msdb_connect(datapath+dataSource_base+".mdb");
    	if(!((Msdb_connect)base).connect())errlevel++;
         BursaAnalizer_Frame.setConnectToDB(BursaAnalizer_Frame.DBID_BASE,base);
         
    	data=new Msdb_connect(datapath+dataSource_data+".mdb");
    	if(!((Msdb_connect)data).connect())errlevel++;
        BursaAnalizer_Frame.setConnectToDB(BursaAnalizer_Frame.DBID_DATA,data);
        }
      }
        //mysql login
      else if(servertype.equals("mysql"))
      {
          //TODO log to db as admin using
        boolean serverreqpass =
            Boolean.parseBoolean(BursaAnalizer_Frame.p.getProperty("server.reqpass"));
        tlog=getUsername();
        tpass=getPassword();
        if(serverreqpass==true)
        {
         tpass=JOptionPane.showInputDialog(null);
        }
      	int tport=Integer.parseInt(p.getProperty("server.port"));
      	String thost=p.getProperty("server.host");
      	users=new Mysql_connect(dataSource_sys,tlog,tpass);
      	((Mysql_connect)users).setPort(tport);
      	((Mysql_connect)users).setHost(thost);
    	if(!((Mysql_connect)users).connect())errlevel++;
         BursaAnalizer_Frame.setConnectToDB(BursaAnalizer_Frame.DBID_USER,users);
    	base=new Mysql_connect(dataSource_base,tlog,tpass);
    	((Mysql_connect)base).setPort(tport);
    	((Mysql_connect)base).setHost(thost);
    	if(!((Mysql_connect)base).connect())errlevel++;
        BursaAnalizer_Frame.setConnectToDB(BursaAnalizer_Frame.DBID_BASE,base);
    	data=new Mysql_connect(dataSource_data,tlog,tpass);
    	((Mysql_connect)data).setPort(tport);
    	((Mysql_connect)data).setHost(thost);
    	if(!((Mysql_connect)data).connect())errlevel++;
        BursaAnalizer_Frame.setConnectToDB(BursaAnalizer_Frame.DBID_DATA,data);
      }
        //mssql login
      else if(servertype.equals("mssql"))
      {
        String sau=p.getProperty("server.mssqlusername");
        String sap=p.getProperty("server.mssqlpassword");
        int tport=Integer.parseInt(p.getProperty("server.mssqlport"));
      	String thost=p.getProperty("server.mssqlhost");
        String tdriver=p.getProperty("server.mssqldriver");
        String tsqlinstance=p.getProperty("server.mssqlinstance");
        System.out.println("instance:"+tsqlinstance+"\ndriver:"+tdriver);
        users=new mssql_connect(dataSource_sys,sau,sap,tdriver,tsqlinstance.trim());
        ((mssql_connect)users).setPort(tport);
      	((mssql_connect)users).setHost(thost);
        if(!((mssql_connect)users).connect())errlevel++;
        //TODO log as Admin to Users Folder
        TRS tmprs=new TRS(((mssql_connect)users).con);
        //check if admin is valid login
        String strcheck="SELECT * FROM USERS WHERE [USER]='"+getAdminUsername()+"' AND PASS='"+getAdminPassword()+"'";
        int checkret=tmprs.rowExistID(strcheck,"USERID");
        System.out.println(strcheck+" ret"+checkret);
        if(checkret<=0)
        {
           //message
            logedstatus=false;
            JOptionPane.showMessageDialog(this,
         	                          "user/admin/pasword ? :)",
         	 			"one of them probably uncorect !!!!",
         	 			JOptionPane.INFORMATION_MESSAGE);
            return logedstatus;
        }
        //here check if curent admin permited to view this user data
        String users_admins=tmprs.getSpecValue("SELECT top 1 * FROM USERS WHERE [USER]='"+getUsername()+"'","groupspermited");
        String users_adminsgroup=tmprs.getSpecValue("SELECT top 1 * FROM USERS WHERE [USER]='"+getAdminUsername()+"'","groupid");
        boolean permited=false;
        String tss[]=users_admins.split(" ");
        for(int tui=0;tui<tss.length;tui++)
        {
         if(users_adminsgroup.equalsIgnoreCase(tss[tui]))
         {
          permited=true;
          break;
         }
        }
        if(permited)
        {
            System.out.println("permited access");
        //and then log as user
        setCurentUserID(tmprs.rowExistID("SELECT * FROM USERS WHERE [USER]='"+getUsername()+"'","USERID"));
        BursaAnalizer_Frame.setConnectToDB(BursaAnalizer_Frame.DBID_USER,users);
    	
    	base=new mssql_connect(dataSource_base,sau,sap,tdriver,tsqlinstance.trim());
         ((mssql_connect)base).setPort(tport);
      	((mssql_connect)base).setHost(thost);
    	if(!((mssql_connect)base).connect())errlevel++;
         BursaAnalizer_Frame.setConnectToDB(BursaAnalizer_Frame.DBID_BASE,base);
         
    	data=new mssql_connect(dataSource_data,tdriver,tsqlinstance.trim());
         ((mssql_connect)data).setPort(tport);
      	((mssql_connect)data).setHost(thost);
    	if(!((mssql_connect)data).connect())errlevel++;
        BursaAnalizer_Frame.setConnectToDB(BursaAnalizer_Frame.DBID_DATA,data);
        }
      }
      else
      {
      	System.out.println("Unknown datasource type "+servertype);
      	System.out.println("Currently supported msdb access and mysql");
        errlevel=4;
      }
       if(errlevel==0)
          {
              login.setText("Logout");
              String timgdir=BursaAnalizer_Frame.getImgDir();
              login.setIcon(new javax.swing.ImageIcon(timgdir+"login.gif"));
              this.user.setEnabled(false);
              this.pass.setEnabled(false);
              logedstatus=true;
              msg.setText("Connected as "+getAdminUsername()+" into "+getUsername()+" account...");
          }
          else 
          {
              login.setText("Logon");
              String timgdir=BursaAnalizer_Frame.getImgDir();
              login.setIcon(new javax.swing.ImageIcon(timgdir+"smiles//locked.gif"));
              this.login.setEnabled(true);
              this.pass.setEnabled(true);
              msg.setText("Disconected mode");
          }
         initinfo();
          return logedstatus;
    }

    public void disconnectFromDBS() {
        System.out.println ("DisConnecting from databases");
       resetCurentUserID();
       //here also drop curent loaded personal pages
       BursaAnalizer_Frame.getUserFolderInstance().sv.fetchdata(-1);
       //disconect
        BursaAnalizer_Frame.getConnectToDB(BursaAnalizer_Frame.DBID_USER).disConnect();
    	BursaAnalizer_Frame.getConnectToDB(BursaAnalizer_Frame.DBID_DATA).disConnect();
    	BursaAnalizer_Frame.getConnectToDB(BursaAnalizer_Frame.DBID_BASE).disConnect();
        logedstatus=false;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
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
    private javax.swing.JTextField jaddr;
    private javax.swing.JTextField jcity;
    private javax.swing.JTextField jfax;
    private javax.swing.JToggleButton jinfoupdate;
    private javax.swing.JTextField jlname;
    private javax.swing.JTextField jmail;
    private javax.swing.JTextField jmob;
    private javax.swing.JTextField jname;
    private javax.swing.JTextField jtel;
    private javax.swing.JButton logAsAdmin;
    private javax.swing.JButton login;
    private javax.swing.JLabel msg;
    private javax.swing.JPasswordField pass;
    private javax.swing.JPasswordField pass_admin;
    private javax.swing.JTextField user;
    private javax.swing.JTextField user_admin;
    // End of variables declaration//GEN-END:variables
    
}
