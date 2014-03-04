import CDate.CDate;
import NN2HLBP.Test_2H_2_GUI_NBF;
import Utils.MySimleInputByCombo;
import Utils.TDoc;
import Utils.ValidatedJTextField;
import Utils.Utils;
import TechnicalTools.MA2;
import TechnicalTools.MACD;
import TechnicalTools.RSI;
import TechnicalTools.ResistanseSupportLine;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

import java.awt.*;
import java.awt.event.*;

import java.util.*;

public class StokGraph extends JPanel
implements ActionListener,ItemListener
{
     private int RSIPERIOD=14;	
	private JCheckBox showRSI=new JCheckBox("RSI "+getRSIPERIOD());
	JSlider rsicbs=new JSlider(3,28, getRSIPERIOD());
	
     private int MAVG_1_PERIOD=7;
     private int MAVG_2_PERIOD=51;
	private JCheckBox showMA=new JCheckBox("MA");
	JCheckBox macb1=new JCheckBox(""+getMAVG_1_PERIOD());
	JCheckBox macb2=new JCheckBox(""+getMAVG_2_PERIOD());
	JSlider macb1s=new JSlider(2,21, getMAVG_1_PERIOD());
	JSlider macb2s=new JSlider(getMAVG_1_PERIOD()+1,210, getMAVG_2_PERIOD());
	
     private int MACDHIGHPERIOD=26;
     private int MACDLOWPERIOD=12;
     private int MACDMAPERIOD=9;
	JCheckBox macdcb=new JCheckBox("MACD");
	
	JCheckBox boilengercb=new JCheckBox("Bolinger");
	
	JCheckBox showRegressionLine=new JCheckBox("Regression Line");
	ValidatedJTextField forecastYonX=new ValidatedJTextField("0.0","Xch",ValidatedJTextField.FLOAT);
	JCheckBox showDPLine=new JCheckBox("Veiershtrasse");
	
	JCheckBox momewntumcb=new JCheckBox("Momentum detect");
	JRadioButton momentdetectpercent = new JRadioButton("(%)");
	JRadioButton momentdetectdif = new JRadioButton("(Pts.)");
	ButtonGroup groupmomentdetect = new ButtonGroup();
   
	ValidatedJTextField momentumdiff=new ValidatedJTextField("0.0","Mch",ValidatedJTextField.FLOAT);
	
	JCheckBox showCompareLine=new JCheckBox("Compare to");
	MySimleInputByCombo CompareNames;//combo to select to what compare
	
	static MySimleInputByCombo ViewType;//combo to select to what compare
	static MySimleInputByCombo GraphType;//combo to select how to represent the data in graph candles or points or...
	static MySimleInputByCombo GraphTimeType;//combo to select how to represent the data in graph candles or points or...
        
        JButton runAI=new JButton("AI");
        JButton runCandleEngine=new JButton("JCandleE");
        
        
	JPanel cp=new JPanel();
        JDPanelAdvTools adtp=new JDPanelAdvTools();
	JDPanel dp=new JDPanel(adtp);
	
	static JLabel lblName=new JLabel("");
	
	JSPanel sp= new JSPanel();
	/*
	 *This is data pointer
	 **/
	static java.util.List dataTradeAll=null;//content all data
	static java.util.List dataTrade=null;//on this partially list working graphs
        static Color dataTradeColor=Color.blue;
        private boolean enabledTrade=true;
        public boolean enabledSRLine=false;
        public double[] dataSRLine=null;
        Color dataSRLcolor=Color.green;
        private int rsldays=2;
        JCheckBox SRLine=new JCheckBox("SRL");
        JSlider srlperiod=new JSlider(2,365, getRSLPERIOD());

	/**
	 * Method StokGraph
	 */
	public StokGraph()
	{
		setLayout(new BorderLayout(5,5));
		initcontrolpanel();
		add(dp,BorderLayout.CENTER);
		add(cp,BorderLayout.SOUTH);
		
		add(sp,BorderLayout.NORTH);
		
		ViewType.addItemListener(this);
		GraphType.addItemListener(this);
		GraphTimeType.addItemListener(this);
		
		macb1s.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e)
			{
				setMAVG_1_PERIOD(((JSlider)e.getSource()).getValue());
				macb1.setText(""+getMAVG_1_PERIOD());
				if(isEnabledMA1())
				{
					setDataMA1(MA2.computeMA(dataTrade,getMAVG_1_PERIOD(), 0,4));
					dp.repaint();
				}
			}
			});
		macb2s.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e)
			{
				setMAVG_2_PERIOD(((JSlider)e.getSource()).getValue());
				macb2.setText(""+getMAVG_2_PERIOD());
				if(isEnabledMA2())
				{
					setDataMA2(MA2.computeMA(dataTrade,getMAVG_2_PERIOD(), 0,4));
					dp.repaint();
				}
			}
			});
		
		rsicbs.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e)
			{
				setRSIPERIOD(((JSlider)e.getSource()).getValue());
				getShowRSI().setText("RSI "+getRSIPERIOD());
				if(isEnabledRSI())
				{
					dataRSI=RSI.computeRSI(dataTrade,getRSIPERIOD(), 4);
				    dp.repaint();
				  }
			}
			});
                        srlperiod.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e)
			{
				setRSLPERIOD(((JSlider)e.getSource()).getValue());
				if(isEnabledSLR())
				{
					 ResistanseSupportLine rsl=new ResistanseSupportLine(rsldays);
                                        rsl.run(dataTrade);
                                        dataSRLine=rsl.getRSL();
					dp.repaint();
				}
			}
			});
	}	
	void initcontrolpanel()
	{
		cp.setLayout(new GridLayout(1,2));
		JPanel ap=new JPanel(new GridLayout(2,1));
		ap.add(initAveragesBasedToolsPanel());
		ap.add(adtp);
		JPanel ap2=new JPanel(new GridLayout(2,1));
		ap2.add(initRegressionForecastToolPanel());
		ap2.add(initMyAdvToolPanel());
		cp.add(ap);
		cp.add(ap2);
	}

    
	private JPanel initAveragesBasedToolsPanel()
	{
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder title = BorderFactory.createTitledBorder(loweredetched, "Averages based");
		title.setTitleJustification(TitledBorder.LEFT);

		JPanel ap01=new JPanel(new GridLayout(2,2));
		JPanel ap01a=new JPanel(new GridLayout(1,2));
		JPanel ap01b=new JPanel(new GridLayout(1,2));
		JPanel ap01c=new JPanel(new GridLayout(1,2));
		ap01.setBorder(title);
		ap01a.add(macb1);
		macb1.addItemListener(this);
		macb1.setSelected(true);
		macb1s.setForeground(getDataMA1Color());
		ap01a.add(macb1s);
		ap01b.add(macb2);
		macb2.addItemListener(this);
		macb2.setSelected(true);
		macb2s.setForeground(getDataMA2Color());
		ap01b.add(macb2s);
		ap01.add(ap01a);
		ap01.add(ap01b);
		
		macdcb.addItemListener(this);
		macdcb.setSelected(true);
		ap01.add(macdcb);
		ap01c.add(getShowRSI());
		getShowRSI().addItemListener(this);
		getShowRSI().setSelected(true);
		ap01c.add(rsicbs);
		ap01.add(ap01c);
		return ap01;
	}
	private JPanel initRegressionForecastToolPanel()
	{
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder title = BorderFactory.createTitledBorder(loweredetched, "Regression based");
		title.setTitleJustification(TitledBorder.LEFT);
		JPanel ap3=new JPanel(new GridLayout(2,2));
		ap3.setBorder(title);
		ap3.add(showCompareLine);
		showCompareLine.setSelected(false);
		showCompareLine.addItemListener(this);
		Object cn[][]={{"None","0"},
					   {"TA25","1"},
					   {"TA100","2"},
					   {"BANKS","3"},
					   {"TELTECH","4"},
					   {"Madad Le zarhan","5"},
					   {"NSDK","6"},
					   {"WS","7"}
					   };
		CompareNames=new MySimleInputByCombo(null,cn);
		CompareNames.setEnabled(false);
		ap3.add(CompareNames);
		ap3.add(showRegressionLine);
		showRegressionLine.addItemListener(this);
		showRegressionLine.setSelected(false);
		showRegressionLine.setEnabled(false);
		JPanel ap301=new JPanel(new GridLayout(1,2));
		ap301.add(new JLabel("Forecast Y on X:"));
		ap301.add(forecastYonX);
		forecastYonX.addActionListener(this);
		forecastYonX.setEnabled(false);
		ap3.add(ap301);
		return ap3;
	}
	private JPanel initMyAdvToolPanel()
	{
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder title = BorderFactory.createTitledBorder(loweredetched, "Advanced methods");
		title.setTitleJustification(TitledBorder.LEFT);

		JPanel ap01=new JPanel(new GridLayout(2,1));
		JPanel ap01a1=new JPanel(new GridLayout(1,2));
		JPanel ap01a1a=new JPanel(new GridLayout(1,3));
		JPanel ap01a1b=new JPanel(new GridLayout(1,3));
		ap01.setBorder(title);
		ap01a1b.add(showDPLine);
		ap01a1b.add(runCandleEngine);
                runCandleEngine.addActionListener(this);
                ap01a1b.add(runAI);
                runAI.addActionListener(this);
		boilengercb.addItemListener(this);
		boilengercb.setSelected(false);
		ap01a1b.add(boilengercb);
                SRLine.addItemListener(this);
		SRLine.setSelected(false);
		ap01a1b.add(SRLine);
                srlperiod.setForeground(getSRLColor());
		ap01a1b.add(srlperiod);
		ap01.add(ap01a1b);
		showDPLine.addItemListener(this);
		showDPLine.setSelected(false);
		momewntumcb.addItemListener(this);
		momewntumcb.setSelected(false);
		momewntumcb.addActionListener(this);
		momentumdiff.setEnabled(false);
		ap01a1a.add(momewntumcb);
		ap01a1a.add(momentumdiff);
		groupmomentdetect.add(momentdetectdif);
    	       groupmomentdetect.add(momentdetectpercent);
    	      momentdetectdif.addActionListener(this);
    	     momentdetectpercent.addActionListener(this);
    	     momentdetectpercent.setMnemonic(KeyEvent.VK_P);
    	     momentdetectdif.setSelected(true);
		momentdetectdif.setMnemonic(KeyEvent.VK_P);
    	momentdetectpercent.setEnabled(false);
    	momentdetectdif.setEnabled(false);
    	ap01a1.add(momentdetectdif);
    	ap01a1.add(momentdetectpercent);
    	ap01a1a.add(ap01a1);
    	ap01.add(ap01a1a);
		return ap01;
	}
	public void actionPerformed(ActionEvent ae)
	{
		String command = ae.getActionCommand();
        if(command.equals("Xch"))
        {
        	System.out.println("Xch changed "+forecastYonX.getText());

        }else if(command.equals("Mch"))
        {
        	System.out.println("Mch changed "+momentumdiff.getText());

        }else if(command.equals("AI"))
        {
        	Test_2H_2_GUI_NBF AI_panel = new Test_2H_2_GUI_NBF(false,
                    BursaAnalizer_Frame.getConnectToDB(BursaAnalizer_Frame.DBID_BASE),
                    BursaAnalizer_Frame.getUserFolderInstance().sv.getuserid());
            AI_panel.setdbc(BursaAnalizer_Frame.getConnectToDB(BursaAnalizer_Frame.DBID_BASE));
                AI_panel.setSymbolAndLock(lblName.getText());
                AI_panel.setDatalist2out(dataTrade);
        }else if(command.equals("JCandleE"))
        {
        	System.out.println("JCandleEngine not implemented yet ");
        }
            
        dp.repaint();
     }
     public void itemStateChanged(ItemEvent e) 
     {
    	Object source = e.getItemSelectable();
    	boolean stat=true;
		if (e.getStateChange() == ItemEvent.DESELECTED)stat=false;
    	if (source == getShowRSI()) 
    	{
        	setEnabledRSI(stat);
        	rsicbs.setEnabled(stat);
        	if(stat)dataRSI=RSI.computeRSI(dataTrade,getRSIPERIOD(), 4);
    	} else if (source == momewntumcb) 
    	{
        	setEnabledMomentum(stat);
        	momentdetectdif.setEnabled(stat);
        	momentdetectpercent.setEnabled(stat);
        	momentumdiff.setEnabled(stat);
    	} else if (source == showDPLine) 
    	{
        	setEnabledDP(stat);
    	}else if (source == showRegressionLine) 
    	{
        	setEnabledRegression(stat);
        	forecastYonX.setEnabled(stat);
    	} else if (source == showCompareLine) 
    	{
        	CompareNames.setEnabled(stat);
        	showRegressionLine.setEnabled(stat);
        	setEnabledCompare(stat);
    	}else if (source == macb1) 
    	{
       		macb1s.setEnabled(stat);
      		setEnabledMA1(stat);
                if(stat)setDataMA1(MA2.computeMA(dataTrade,getMAVG_1_PERIOD(), 0,4));
    	}else if (source == macb2) 
    	{
       		macb2s.setEnabled(stat);
      		setEnabledMA2(stat);
                if(stat)setDataMA2(MA2.computeMA(dataTrade,getMAVG_2_PERIOD(), 0,4));
    	}else if (source == macdcb) 
    	{
        	setEnabledMACD(stat);
        	if(stat)dataMACD=MACD.computeMACD(dataTrade,getMACDHIGHPERIOD(), getMACDLOWPERIOD(), getMACDMAPERIOD(), 0,4);
    	}else if (source == boilengercb) 
    	{
        	setEnabledBOILINGER(stat);
                
    	}else if (source == SRLine) 
    	{
        	setEnabledSRLine(stat);
                if(stat)
                {
                    ResistanseSupportLine rsl=new ResistanseSupportLine(rsldays);
                    rsl.run(dataTrade);
                    dataSRLine=rsl.getRSL();
                     dp.repaint();     
                }
    	}
        else if(source==ViewType)
    	{
    		safecopydata();
                if(isEnabledRSI())dataRSI=RSI.computeRSI(dataTrade,getRSIPERIOD(), 4);
        	if(isEnabledMACD())dataMACD=MACD.computeMACD(dataTrade,getMACDHIGHPERIOD(), getMACDLOWPERIOD(), getMACDMAPERIOD(), 0,4);
                if(isEnabledMA1())setDataMA1(MA2.computeMA(dataTrade,getMAVG_1_PERIOD(), 0,4));
                if(isEnabledMA2())setDataMA2(MA2.computeMA(dataTrade,getMAVG_2_PERIOD(), 0,4));
                if(this.isEnabledSLR())
                {
                    ResistanseSupportLine rsl=new ResistanseSupportLine(rsldays);
                    rsl.run(dataTrade);
                    dataSRLine=rsl.getRSL();
                    
                }
    	}else if(source==GraphTimeType)
    	{
    		safecopydata();
                if(isEnabledRSI())dataRSI=RSI.computeRSI(dataTrade,getRSIPERIOD(), 4);
        	if(isEnabledMACD())dataMACD=MACD.computeMACD(dataTrade,getMACDHIGHPERIOD(), getMACDLOWPERIOD(), getMACDMAPERIOD(), 0,4);
                if(isEnabledMA1())setDataMA1(MA2.computeMA(dataTrade,getMAVG_1_PERIOD(), 0,4));
                if(isEnabledMA2())setDataMA2(MA2.computeMA(dataTrade,getMAVG_2_PERIOD(), 0,4));
                if(this.isEnabledSLR())
                {
                    ResistanseSupportLine rsl=new ResistanseSupportLine(rsldays);
                    rsl.run(dataTrade);
                    dataSRLine=rsl.getRSL();
                    
                }
    	}else if(source==GraphType)
    	{
    		safecopydata(); 
    	}
    	dp.repaint();
}
	public void setDataPoints(String name,java.util.List atd)
	{
		lblName.setText(name);
		dataTradeAll=atd;
		safecopydata();
		rebuild();
	}
	private void safecopydata()
	{
		//make only copy of data we need
		int typeoftrade=ViewType.getSelectedIndex();
		TDoc t=null,lastgood=null;
		CDate d2,tempd;
		if(dataTrade!=null)dataTrade.clear();
		if(dataTradeAll.size()==0)return;
		dataTrade = new ArrayList();
		boolean first=true;
		//get type of time resolution and copy data only from this start point
    	CDate beginfrom=new CDate();
    	int typeoftimeview=GraphTimeType.getSelectedIndex();
    	int rollto=-1;
    	switch(typeoftimeview)
    	{
    		case 0:
    		rollto*=7300;//20 years
    		break;
			case 1://Last 2 Years
			rollto*=730;
			break;
			case 2://Last Year
			rollto*=365;
			break;
			case 3://Last 6 Months
			rollto*=186;
			break;
			case 4://Last 3 Months
			rollto*=92;
			break;
			case 5://Last Month
			rollto*=31;
			break;
			default:
			rollto*=14;
			break;
    	};
    	beginfrom.roll(rollto);
		//detect last good and push in reversed order then reverse
		for(int i=dataTradeAll.size()-1;i>=0;i--)
		{
			t=(TDoc)dataTradeAll.get(i);
     		tempd=new CDate((String)(t.o[0]));
     		if(beginfrom.greater(tempd))continue;
			switch(typeoftrade)
			{
				case 1://Weekly
				if(first)
     			{
     				lastgood=t;
     				dataTrade.add(t);
     				first=false;
     			}else
     			{
     				d2=new CDate((String)(lastgood.o[0]));
					if(tempd.getWeekOfYear()!=d2.getWeekOfYear())
					{
						dataTrade.add(t);
						lastgood=t;		
					}
				}
				break;
				case 2://Monthly
				if(first)
     			{
     				lastgood=t;
     				dataTrade.add(t);
     				first=false;
     			}else
     			{
     				d2=new CDate((String)(lastgood.o[0]));
					if(tempd.getMonth()!=d2.getMonth())
					{
						dataTrade.add(t);
						lastgood=t;
					}
				}
				break;
				case 3://Yearly
				if(first)
     			{
     				lastgood=t;
     				first=false;
     				dataTrade.add(t);
     			}else
     			{
     				d2=new CDate((String)(lastgood.o[0]));
					if(tempd.getYear()!=d2.getYear())
					{
						dataTrade.add(t);
						lastgood=t;
					}
				}
				break;
				default://Dayly or unknoun
				dataTrade.add(t);
				break;
			};	
		}
		//here reverse data to right direction from past to future
		Utils.reverselist(dataTrade);
	}
	private void rebuild()
	{
		getShowRSI().setSelected(false);
		macb1.setSelected(false);
		macb2.setSelected(false);
		macdcb.setSelected(false);
		boilengercb.setSelected(false);
		showRegressionLine.setSelected(false);
		showDPLine.setSelected(false);
		showCompareLine.setSelected(false);
		//here calc ma 1-3 etc
		//
		dp.repaint();
	}
    
    double dataBOILDOWN[]=null;
    double dataBOILUP[]=null;
    Color dataBOILINGERColor=Color.gray;
    private boolean enabledBOILINGER=false;
    
     private Color dataMA1Color=Color.red;
     private Color dataMA2Color=Color.green;
     private double dataMA1[]=null;
     private boolean enabledMA1=false;
    private double dataMA2[]=null;
     private boolean enabledMA2=false;
    
    static double dataRSI[]=null;
     private Color dataRSIColor=Color.black;
     private boolean enabledRSI=false;
    
    static double dataMACD[]=null;
     private Color dataMACD1Color=Color.black;
     private Color dataMACD2Color=Color.blue;
     private boolean enabledMACD=false;
    
    double dataRegression[]=null;
    private Color dataRegressionColor=Color.green;
    private boolean enabledRegression=false;
    
    double dataDP[]=null;
    private Color dataDPColor=Color.black;
    private boolean enabledDP=false;
    
    double dataCompare[]=null;
    private Color dataCompareColor=Color.red;
    private boolean enabledCompare=false;
    	
    private boolean enabledMomentum=false;

    public boolean isEnabledTrade ()
    {
        return enabledTrade;
    }

    public void setEnabledTrade (boolean enabledTrade)
    {
        this.enabledTrade = enabledTrade;
    }

    public double[] getDataMA1 ()
    {
        return dataMA1;
    }

    public void setDataMA1 (double[] dataMA1)
    {
        this.dataMA1 = dataMA1;
    }

    public double[] getDataMA2 ()
    {
        return dataMA2;
    }

    public void setDataMA2 (double[] dataMA2)
    {
        this.dataMA2 = dataMA2;
    }

    public int getRSIPERIOD ()
    {
        return RSIPERIOD;
    }

    public void setRSIPERIOD (int RSIPERIOD)
    {
        this.RSIPERIOD = RSIPERIOD;
    }
    
     public int getRSLPERIOD ()
    {
        return this.rsldays;
    }

    public void setRSLPERIOD (int RSLPERIOD)
    {
        this.rsldays = RSLPERIOD;
    }

    public JCheckBox getShowRSI ()
    {
        return showRSI;
    }

    public void setShowRSI (JCheckBox showRSI)
    {
        this.showRSI = showRSI;
    }

    public int getMAVG_1_PERIOD ()
    {
        return MAVG_1_PERIOD;
    }

    public void setMAVG_1_PERIOD (int MAVG_1_PERIOD)
    {
        this.MAVG_1_PERIOD = MAVG_1_PERIOD;
    }

    public int getMAVG_2_PERIOD ()
    {
        return MAVG_2_PERIOD;
    }

    public void setMAVG_2_PERIOD (int MAVG_2_PERIOD)
    {
        this.MAVG_2_PERIOD = MAVG_2_PERIOD;
    }

    public JCheckBox getShowMA ()
    {
        return showMA;
    }

    public int getMACDHIGHPERIOD ()
    {
        return MACDHIGHPERIOD;
    }

    public void setMACDHIGHPERIOD (int MACDHIGHPERIOD)
    {
        this.MACDHIGHPERIOD = MACDHIGHPERIOD;
    }

    public int getMACDLOWPERIOD ()
    {
        return MACDLOWPERIOD;
    }

    public void setMACDLOWPERIOD (int MACDLOWPERIOD)
    {
        this.MACDLOWPERIOD = MACDLOWPERIOD;
    }

    public int getMACDMAPERIOD ()
    {
        return MACDMAPERIOD;
    }

    public void setMACDMAPERIOD (int MACDMAPERIOD)
    {
        this.MACDMAPERIOD = MACDMAPERIOD;
    }

    public boolean isEnabledBOILINGER ()
    {
        return enabledBOILINGER;
    }

    public void setEnabledBOILINGER (boolean enabledBOILINGER)
    {
        this.enabledBOILINGER = enabledBOILINGER;
    }

    public Color getDataMA1Color ()
    {
        return dataMA1Color;
    }

    public void setDataMA1Color (Color dataMA1Color)
    {
        this.dataMA1Color = dataMA1Color;
    }

    public Color getDataMA2Color ()
    {
        return dataMA2Color;
    }

    public void setDataMA2Color (Color dataMA2Color)
    {
        this.dataMA2Color = dataMA2Color;
    }

    public Color getSRLColor() {
        return this.dataSRLcolor;
    }
    public void  setSRLColor(Color dataSRLColor) {
         this.dataSRLcolor=dataSRLColor;
    }
    public boolean isEnabledMA1 ()
    {
        return enabledMA1;
    }

    public void setEnabledMA1 (boolean enabledMA1)
    {
        this.enabledMA1 = enabledMA1;
    }

    public boolean isEnabledMA2 ()
    {
        return enabledMA2;
    }

    public void setEnabledMA2 (boolean enabledMA2)
    {
        this.enabledMA2 = enabledMA2;
    }

    public Color getDataRSIColor ()
    {
        return dataRSIColor;
    }

    public void setDataRSIColor (Color dataRSIColor)
    {
        this.dataRSIColor = dataRSIColor;
    }

    public boolean isEnabledRSI ()
    {
        return enabledRSI;
    }

    public void setEnabledRSI (boolean enabledRSI)
    {
        this.enabledRSI = enabledRSI;
    }

    public Color getDataMACD1Color ()
    {
        return dataMACD1Color;
    }

    public void setDataMACD1Color (Color dataMACD1Color)
    {
        this.dataMACD1Color = dataMACD1Color;
    }

    public Color getDataMACD2Color ()
    {
        return dataMACD2Color;
    }

    public void setDataMACD2Color (Color dataMACD2Color)
    {
        this.dataMACD2Color = dataMACD2Color;
    }

    public boolean isEnabledMACD ()
    {
        return enabledMACD;
    }

    public void setEnabledMACD (boolean enabledMACD)
    {
        this.enabledMACD = enabledMACD;
    }

    public Color getDataRegressionColor ()
    {
        return dataRegressionColor;
    }

    public void setDataRegressionColor (Color dataRegressionColor)
    {
        this.dataRegressionColor = dataRegressionColor;
    }

    public boolean isEnabledRegression ()
    {
        return enabledRegression;
    }

    public void setEnabledRegression (boolean enabledRegression)
    {
        this.enabledRegression = enabledRegression;
    }

    public Color getDataDPColor ()
    {
        return dataDPColor;
    }

    public void setDataDPColor (Color dataDPColor)
    {
        this.dataDPColor = dataDPColor;
    }

    public boolean isEnabledDP ()
    {
        return enabledDP;
    }

    public void setEnabledDP (boolean enabledDP)
    {
        this.enabledDP = enabledDP;
    }

    public Color getDataCompareColor ()
    {
        return dataCompareColor;
    }

    public void setDataCompareColor (Color dataCompareColor)
    {
        this.dataCompareColor = dataCompareColor;
    }

    public boolean isEnabledCompare ()
    {
        return enabledCompare;
    }

    public void setEnabledCompare (boolean enabledCompare)
    {
        this.enabledCompare = enabledCompare;
    }

    public boolean isEnabledMomentum ()
    {
        return enabledMomentum;
    }

    public void setEnabledMomentum (boolean enabledMomentum)
    {
        this.enabledMomentum = enabledMomentum;
    }
    public void addPredicted(TDoc t,String ref)
    {
        t.oadv=new String[1];
        t.oadv[0]=new String(ref);
        dataTrade.add(t);
    }

    private void setEnabledSRLine(boolean stat) {
        this.enabledSRLine = stat;
    }
    public boolean isEnabledSLR()
    {
        return enabledSRLine;
    }
}
