//~--- non-JDK imports --------------------------------------------------------

import DBCONNECT.TRS;
import Utils.LoggerNB;

import Utils.TDoc;
import Utils.SimpleHelp;

//~--- JDK imports ------------------------------------------------------------

import java.awt.*;
import java.awt.event.*;

import java.sql.*;

import javax.swing.*;

//~--- classes ----------------------------------------------------------------

public class Company extends JPanel implements ActionListener    // Listener
{
    JButton      Delete;
    JButton      Edit;
    JButton      Home;
    JButton      Info;
    JButton      New;
    JButton      Tree;
    CompanyTable ct;

    //~--- constructors -------------------------------------------------------

    /**
     * Method Company
     *
     */
    public Company() {
        String timgdir = BursaAnalizer_Frame.getImgDir();

        Home = new JButton(Name.Home,
                           new ImageIcon(timgdir + "data_table.png"));
        Info = new JButton(Name.Info, new ImageIcon(timgdir + "info.png"));
        Tree = new JButton(Name.Tree, new ImageIcon(timgdir + "exchange.png"));
        setLayout(new BorderLayout(5, 5));
        ct = new CompanyTable();
        add(ct, BorderLayout.CENTER);

        JPanel cbp     = new JPanel();
        JPanel cbp1    = new JPanel();
        JPanel cbpdiv2 = new JPanel();

        cbpdiv2.setLayout(new GridLayout(1, 2));
        cbpdiv2.setBorder(BorderFactory.createRaisedBevelBorder());
        cbp.setLayout(new GridLayout(3, 1));
        cbp.setBorder(BorderFactory.createRaisedBevelBorder());
        addAndRegisterButtonInGridLayout(cbp, Home);
        addAndRegisterButtonInGridLayout(cbp, Info);
        addAndRegisterButtonInGridLayout(cbp, Tree);
        cbpdiv2.add(cbp);
        cbpdiv2.add(cbp1);
        add(cbpdiv2, BorderLayout.SOUTH);
    }

    //~--- methods ------------------------------------------------------------

    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        int    sr      = ct.table.getSelectedRow();

        if (command.equals(Name.Info)) {
            if (sr > -1) {
                int compid = Integer.parseInt(ct.datamodel.getValueAt(sr,
                                 0).toString());

                showinfo(compid);
            } else {
                ct.selectionmessageerr(null);
            }
        }

        /*
         * if(command.equals(Name.Load))
         * {
         * int sr=table.getSelectedRow();
         * if(sr>-1)
         * {
         * String prjid=new String(datamodel.getValueAt(sr,0).toString());
         * mysimpleoption so=new mysimpleoption(this,"Add to curent scene?","You are going to load the Project");
         * world.Load(prjid,db,so.useraction==1?false:true);
         * parent.curentprojectid=Integer.parseInt(datamodel.getValueAt(sr,-1).toString());
         * }else
         * {
         * JOptionPane.showMessageDialog(this,
         *                                                         "<HTML><center><font size=16 color=red>But nothing is selected !!!!",
         *                                                         "Load Project ? :)",
         *                                                               JOptionPane.INFORMATION_MESSAGE);
         * }
         * }
         * else if(command.equals(Name.New))
         * {
         * makenewproject();
         * }
         * else if(command.equals(Name.Delete))
         * {
         * deleteproject();
         * }
         */
    }

    private void addAndRegisterButtonInGridLayout(JPanel to, JButton b) {
        to.add(b);
        b.addActionListener(this);
    }

    String buildaddpagestring(int cid) {

        // example http://finance.walla.co.il/ts.cgi?tsscript=company&path=&target=comp&ric=RABN.TA
        // example from tase elal http://www.tase.co.il/mantispage.cgi?sn=quotes&type=security&sec=01087824
        Connection con = BursaAnalizer_Frame.getConnectToDB(
                             BursaAnalizer_Frame.DBID_BASE).con;

        // String ret="http://finance.walla.co.il/ts.cgi?tsscript=company&path=&target=comp&ric=";
        String ret =
            "http://www.tase.co.il/mantispage.cgi?sn=quotes&type=security&sec=";
        TRS RS = new TRS(con);

        try {
            RS.execSQL("SELECT * FROM MENAYA_BASE WHERE COMPANY_ID=" + cid);

            if (RS.rs.next()) {

                // Integer tm=new Integer(RS.rs.getInt("MARKET_ID"));
                // ret+=RS.rs.getString("SYMBOL");
                ret += RS.rs.getString("TASEID");
                System.out.println(ret);

                /*
                 * RS.execSQL("SELECT * FROM MARKET_T WHERE ID="+tm.intValue());
                 * if(RS.rs.next())
                 * {
                 *       ret+="."+RS.rs.getString("MARKET");
                 * }
                 */
            }
        } catch (SQLException e) {
            if(LoggerNB.debuging){System.out.println("Error203: " + e);}
        } finally {
            RS.close();
            RS = null;
        }

        return ret;
    }

    /*
     * private void makenewproject()
     * {
     *        boolean uniq=false;
     * String nepn="";
     * TRS RS=null;
     * String sqlstr="";
     * TDoc t=null;
     *       JOptionPane.showMessageDialog(this,
     *                                                          "New Project ? :)",
     *                                                                       "Just provide uniq name and LETSGO !!!!",
     *                                                                       JOptionPane.INFORMATION_MESSAGE);
     * if(JOptionPane.showConfirmDialog(this,
     *                                                               "<HTML><p><center>During this operation <br>Current project will be unloaded so make sure You have Saved it !!!!",
     *                                                               "Important !!!",
     *                                                               JOptionPane.YES_NO_OPTION)==JOptionPane.NO_OPTION)return;
     * RS=new TRS(db.con);
     * while(!uniq)//don't leave until not uniq name provided or cancel pressed
     * {
     *       if((nepn=JOptionPane.showInputDialog("Enter 'New Project' name"))==null)return;
     *                               if(nepn.length()>0)
     *                                if(!RS.RowExist("SELECT * from PROJECT WHERE NAME='"+nepn+"'"))
     *                                uniq=true;
     * }
     * JOptionPane.showMessageDialog(this,
     *                                                               "<HTML><center>Current project will be unloaded<br>and new will be set up as current !!!!",
     *                                                               "Important !!!",
     *                                                               JOptionPane.INFORMATION_MESSAGE);
     * // if we got to here so make new row and insert into table and also in swing table
     * try {
     *                       RS.reuse();
     *                       sqlstr = new String("insert into PROJECT(NAME,COMPLETED) values('"+nepn+"',false)");
     *                       RS.ExecSQL(sqlstr);
     *                       RS.reuse();
     *                       RS.ExecSQL("SELECT * from PROJECT WHERE NAME='"+nepn+"'");
     *                       if(RS.rs.next())//only one row must be returned
     *               {
     *           t=new TDoc(4);
     *               t.o[0]=new Integer(RS.rs.getInt("PROJECTID"));
     *               t.o[1]=new String(RS.rs.getString("NAME"));
     *               t.o[2]=new String(RS.rs.getDate("DATE").toString());
     *               t.o[3]=new Boolean(RS.rs.getBoolean("COMPLETED"));
     *               atd.add(t);
     *               world.Clear();//if any loaded
     *               parent.curentprojectid=((Integer)t.o[0]).intValue();
     *               }
     *       }catch (SQLException e)
     *       {
     *       if(W3D_C.debug){System.out.println("Error23: " + e);}
     *               }
     *               finally
     *       {
     *                       RS.close();
     *               RS=null;
     *               table.revalidate();
     *       }
     * }
     * private void deleteproject()
     * {
     *       String sqlstr="";
     *       int sr=table.getSelectedRow();
     * if(sr>-1)
     * {
     *         mysimpleoption so=new mysimpleoption(this,
     *                                          "Delete project?",
     *                                          "<HTML><center>You are going to delete :<br><font color=red>the Project and the references </font><br><font color=green>but not the object data !!!");
     * if(so.useraction==0)
     * {
     *       if(parent.curentprojectid==Integer.parseInt(datamodel.getValueAt(sr,-1).toString()))
     *       {
     *        JOptionPane.showMessageDialog(this,
     *                                                                       "'"+datamodel.getValueAt(sr,0).toString()+"' - This is curent scene :(",
     *                                                                       "I going to unload it too !!!!",
     *                                                                       JOptionPane.INFORMATION_MESSAGE);
     *        world.Clear();
     *        parent.curentprojectid=-1;
     *       }
     *       //here delete project object then project
     *       //here remove it from db and table model
     *
     *               int pid=Integer.parseInt(datamodel.getValueAt(sr,-1).toString());
     *               sqlstr = new String("delete from Project_Object where ProjectID="+pid);
     *               TRS RS=new TRS(db.con);
     *                       RS.ExecSQL(sqlstr);
     *                       sqlstr = new String("delete from Project where ProjectID="+pid);
     *                       RS.reuse();
     *                       RS.ExecSQL(sqlstr);
     *                       RS.close();
     *                       RS=null;
     *
     *               atd.remove(sr);
     *               table.revalidate();
     *
     * }
     * }
     * }
     */
    public void initOnData() {
        initOnData(1);
    }

    public void initOnData(int companyid) {
        showinfo(companyid);
    }

    public void showinfo(int companyid) {
        String windowName        = "About ";
        String helpIndexFileName =
            "C:\\\\SDI_BATool\\templates\\defabout.html";
        String text = "Not found";
        int    i    = 0;

        while (i < ct.atd.size()) {
            if (companyid
                    == Integer.parseInt(ct.datamodel.getValueAt(i,
                        0).toString())) {
                windowName += ct.datamodel.getValueAt(i, 1).toString();
                text       =
                    "<a href=" + ct.datamodel.getValueAt(i, 2).toString()
                    + ">go to home page</a>"
                    + "<br>Profit/Loss<br><table><td><tr>1</tr><tr>2</tr></td>";

                break;
            }

            i++;
        }

        text +=
            "<br><a href="
            + buildaddpagestring(Integer.parseInt(ct.datamodel.getValueAt(i,
                0).toString())) + ">go to extra info page</a>";

        SimpleHelp sh = new SimpleHelp(windowName, helpIndexFileName);

        sh.help.setText("<HTML><BODY>" + text + "</BODY></HTML>");
        sh.setVisible(true);
    }

    //~--- inner classes ------------------------------------------------------


    // Names of buttons menus and menuitems
    static class Name {
        public static final String Home   = "Home";
        public static final String Info   = "Info";
        public static final String Tree   = "Tree";
        public static final String New    = "New";
        public static final String Edit   = "Edit";
        public static final String Delete = "Delete";
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
