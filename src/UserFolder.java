
import CDate.CDate;
import DBCONNECT.TRS;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class UserFolder extends JPanel
implements ActionListener       // Listener
{
	
	static private int curentfolderid=-1;
    static public int getCurentFolderID(){return curentfolderid;}
   static public void setCurentFolderID(int cfi){curentfolderid=cfi;}
    static public void resetCurentFolderID(){curentfolderid=-1;}
    private simulation simp=null;
	
	// Names of buttons menus and menuitems
	static class Name
  		{
    		public static final String ViewFollow  = "Follow";
    		public static final String Deals  = "Deals";
    		public static final String Merge  = "Merge projects";
    		public static final String Add  = "Create";
    		public static final String Del  = "Delete";
                public static final String Sim="Simulate";
    	}
    JButton ViewFollow;
    JButton Deals;
    JButton Merge;
    JButton Add;
    JButton Del;
    JButton Sim;
    UserFolderTable sv;
    
	/**
	 * Method StoksList
	 *
	 */
	public UserFolder() 
	{
	
		String timgdir=BursaAnalizer_Frame.getImgDir();
		ViewFollow=new JButton(Name.ViewFollow,new ImageIcon(timgdir+"data_find.png"));	
		Deals=new JButton(Name.Deals,new ImageIcon( timgdir+"info.png"));	
		Merge=new JButton(Name.Merge,new ImageIcon(timgdir+"data_edit.png"));	
		Add=new JButton(Name.Add,new ImageIcon( timgdir+"index_add.png"));	
		Del=new JButton(Name.Del,new ImageIcon( timgdir+"index_add.png"));	
		Sim=new JButton(Name.Sim,new ImageIcon(timgdir+"sim_run"));
		setLayout(new BorderLayout(5,5));
		
		sv=new UserFolderTable();
		add(sv,BorderLayout.CENTER);
		
		JPanel cbp=new JPanel();
		JPanel cbp1=new JPanel();
    	       JPanel cbpdiv2=new JPanel();
    
		cbpdiv2.setLayout(new GridLayout(1,2));
		cbpdiv2.setBorder(BorderFactory.createRaisedBevelBorder());
		
		cbp.setLayout(new GridLayout(3,1));
		cbp.setBorder(BorderFactory.createLineBorder(Color.green,2));
		cbp.add(Merge);
		cbp.add(Add);
		cbp.add(Del);
		
		cbpdiv2.add(cbp);
		cbp1.setLayout(new GridLayout(2,1));
		cbp1.setBorder(BorderFactory.createLineBorder(Color.blue,2));
		cbp1.add(ViewFollow);
		cbp1.add(Deals);
                cbp1.add(Sim);
		cbpdiv2.add(cbp1);
		
		ViewFollow.addActionListener(this);
		Merge.addActionListener(this);
		Add.addActionListener(this);
		Del.addActionListener(this);
		Deals.addActionListener(this);
                Sim.addActionListener(this);
		add(cbpdiv2,BorderLayout.SOUTH);
		add(new JLabel("User's Folder"),BorderLayout.NORTH);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		String command = ae.getActionCommand();
		int sr=sv.table.getSelectedRow();
		if(command.equals(Name.ViewFollow))
		{
        	 if(sr>-1)
         	{
          	curentfolderid=Integer.parseInt(sv.datamodel.getValueAt(sr,0).toString());
          	BursaAnalizer_Frame.getFollowInstance().initOnData();
			BursaAnalizer_Frame.switchToTab("Follow");
         	}else
         	{
         		JOptionPane.showMessageDialog(this,
         	 							  "<HTML><center><font size=16 color=red>But nothing is selected !!!!",
         	 							  "Select ? :)",
         	 								JOptionPane.INFORMATION_MESSAGE);
         	}
		}
		if(command.equals(Name.Deals))
		{
        	 if(sr>-1)
         	{
          	curentfolderid=Integer.parseInt(sv.datamodel.getValueAt(sr,0).toString());
          	BursaAnalizer_Frame.getDealScreenInstance().initOnData();
			BursaAnalizer_Frame.switchToTab("Deal");
         	}else
         	{
         		JOptionPane.showMessageDialog(this,
         	 							  "<HTML><center><font size=16 color=red>But nothing is selected !!!!",
         	 							  "Select ? :)",
         	 								JOptionPane.INFORMATION_MESSAGE);
         	}
		}
                if(command.equalsIgnoreCase(Name.Sim))
                {if(sr>-1)
         	{
          	curentfolderid=Integer.parseInt(sv.datamodel.getValueAt(sr,0).toString());
          	      if(simp!=null)
                      simp.dispose();
                      simp=new simulation(curentfolderid);
                        
         	}else
         	{
         		JOptionPane.showMessageDialog(this,
         	 							  "<HTML><center><font size=16 color=red>But nothing is selected !!!!",
         	 							  "Select ? :)",
         	 								JOptionPane.INFORMATION_MESSAGE);
         	}   
                }
                else if(command.equals(Name.Add))
        {
         makenewdeals();				
        }
                else if(command.equalsIgnoreCase(Name.Del))
        {
            if(sr>-1)
            {
              deleteallaccordingtoproject(sr);
            }
                else
         	{
         		JOptionPane.showMessageDialog(this,
         	 							  "<HTML><center><font size=16 color=red>But nothing is selected !!!!",
         	 							  "Select ? :)",
         	 								JOptionPane.INFORMATION_MESSAGE);
         	}
        }
        else if(command.equals(Name.Merge))
        {
         mergeprojects();
        }      
	}

    private void deleteallaccordingtoproject(int sr) {
        if(sr<0)return;
        int tcproj=Integer.parseInt(sv.datamodel.getValueAt(sr,0).toString());
        String  tpname=sv.datamodel.getValueAt(sr,1).toString();
        if(JOptionPane.showConfirmDialog(this,
         	 							"<HTML><p><center>During this operation <br>Project "+tpname+" will be completely removed !!!<br><font size=16 color red>also from Deals!!!!",
         	 							"Important !!!",
         	 							JOptionPane.YES_NO_OPTION)==JOptionPane.NO_OPTION)return;
        TRS RS=new TRS(BursaAnalizer_Frame.getConnectToDB(BursaAnalizer_Frame.DBID_USER).con);
        //delete first from operations
           String sqlstr="delete from operations o join deal d on (o.dealid=d.dealid)";
           sqlstr+=" join folder f on(f.id=d.folderid)";
           sqlstr+=" where  f.id="+tcproj;
           RS.execSQL(sqlstr);
           sqlstr="delete from deal d join folderf on (f.id=d.folderid)";
           sqlstr+=" where  f.id="+tcproj;
           RS.execSQL(sqlstr);
           sqlstr = "delete from FOLDER where ID="+tcproj;
	   RS.execSQL(sqlstr);
           sv.fetchdata(-1);
           int cuid=BursaAnalizer_Frame.getWellcomeScreenInstance().getCurentUserID();
           sv.fetchdata(cuid);
           sv.table.revalidate();
        
    }

    private void makenewdeals() {
         	JOptionPane.showMessageDialog(this,
         	 						   "New Deals Entrty ? ",
         	 								"Just provide uniq name and LETS GO !!!!",
         	 								JOptionPane.INFORMATION_MESSAGE);
         if(JOptionPane.showConfirmDialog(this,
         	 							"<HTML><p><center>During this operation <br>New project will be added !!!!",
         	 							"Important !!!",
         	 							JOptionPane.YES_NO_OPTION)==JOptionPane.NO_OPTION)return;
           TRS RS=new TRS(BursaAnalizer_Frame.getConnectToDB(BursaAnalizer_Frame.DBID_USER).con);
           boolean uniq=false;
           String nepn="";
           int cuid=BursaAnalizer_Frame.getWellcomeScreenInstance().getCurentUserID();
         while(!uniq)//don't leave until not uniq name provided or cancel pressed
         {
         	if((nepn=JOptionPane.showInputDialog("Enter 'New Project' name"))==null)return;
	 				if(nepn.length()>0)
	 				 if(!RS.rowExist("SELECT * from folder WHERE UID="+cuid+" and NAME='"+nepn+"'"))
	 				 uniq=true;				
         }
           RS.reuse();
           CDate d=new CDate();
          String sc= new Character((char)39).toString();     
	   String sqlstr = "insert into FOLDER(NAME,UID,SINCE) values('"+nepn+"',"+cuid+","+sc+d.getYear()+"-"+d.getMonth()+"-"+d.getDay()+sc+")";
	   RS.execSQL(sqlstr);
           
           sv.fetchdata(-1);
           sv.fetchdata(cuid);
           sv.table.revalidate();
    }

    private void mergeprojects() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
	
	
}

