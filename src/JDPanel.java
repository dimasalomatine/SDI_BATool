import CDate.CDate;
import Utils.TDoc;
import Utils.Utils;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.MouseListener;

class JDPanel extends JPanel
        implements MouseListener
	{
                JDPanelAdvTools adtp=null;
		int datediffs[]=null;
                MinMax MM=new MinMax();
		JDPanel(JDPanelAdvTools tadtp)
		{
                    adtp=tadtp;
                    adtp.setControlls(this);
                    addMouseListener(this);
                    MM.initMinMax();
		}
		public void setdatatodraw(double []data)
		{
			repaint();
		}
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
                        StokGraph sg=BursaAnalizer_Frame.getStockGraphInstance ();
			if(sg.isEnabledTrade())drawTrade(g);
		}

    private void drawSRLine(Graphics g) {
        StokGraph sg=BursaAnalizer_Frame.getStockGraphInstance ();
        if(sg.dataSRLine==null)return;
	
              int ts=StokGraph.dataTrade.size();
            int xx=getWidth()-begingraffromx-10;
            double r=(double)xx/(double)ts;
        int txs=(int)((ts-sg.getRSLPERIOD())*r)+begingraffromx;
        int txe=(int)(ts*r)+begingraffromx;
              g.setColor(Color.red);
        int tt=graphStartsAtY-(int)((sg.dataSRLine[0]-MM.mm[0][0])*rateystock);                     
         g.drawLine(txs,tt,txe,tt);	
         g.setColor(sg.dataSRLcolor);
         tt=graphStartsAtY-(int)((sg.dataSRLine[1]-MM.mm[0][0])*rateystock);                     
         g.drawLine(txs,tt,txe,tt);
         tt=graphStartsAtY-(int)((sg.dataSRLine[2]-MM.mm[0][0])*rateystock);                     
         g.drawLine(txs,tt,txe,tt);
         tt=graphStartsAtY-(int)((sg.dataSRLine[3]-MM.mm[0][0])*rateystock);                     
         g.drawLine(txs,tt,txe,tt);
         tt=graphStartsAtY-(int)((sg.dataSRLine[4]-MM.mm[0][0])*rateystock);                     
         g.drawLine(txs,tt,txe,tt);
			
    }
		private void drawTrade(Graphics g)
		{
                        StokGraph sg=BursaAnalizer_Frame.getStockGraphInstance ();
                        if(StokGraph.dataTrade==null)return;
			MM.detectminmaxOHLCA(StokGraph.dataTrade,MMMode.RESET);//init and reset
			MM.detectminmaxOHLCA(StokGraph.dataTrade,MMMode.DETECTMMPRICE);//calc open close hi lo min max bounds
			MM.detectminmaxOHLCA(StokGraph.dataTrade,MMMode.DETECTMMVOLUME);//calc volume min max bounds
                        
			calculatedatediff_dayly();
			calculateRatex();
			drawGrid(g);
			drawDayly(g);
			//here draw volumes
			drawVolume(g);
			//here draw rsi,,,,,,
			drawRSI(g);
			//here draw MACD,,,,,,
			drawMACD(g);
			//here draw MA short and long
                        
                        drawMAVERAGES(g,sg.getDataMA1(), sg.getMAVG_1_PERIOD(), sg.isEnabledMA1(), sg.getDataMA1Color());
                        drawMAVERAGES(g,sg.getDataMA2(), sg.getMAVG_2_PERIOD(), sg.isEnabledMA2(), sg.getDataMA2Color());
                        //here draw the percent rolers boilinger etc
                        if(sg.enabledSRLine)drawSRLine(g);
                        //if any in queue
                        drawListofClicks(g);
		}
		int begingraffromx=60;
		int graphStartsAtY=0;
		int rsiStartsAtY=0;
		int macdStartsAtY=0;
		int macdscalelimit=50;
		int volumeStartsAtY=0;
		static final float RATEXC=1.0f;
		float ratex=RATEXC;
		private void calculateRatex()
		{
			int sumd=0;
			if(StokGraph.dataTrade==null)return;
			for(int i=0;i<datediffs.length;i++)sumd+=datediffs[i];
			if(sumd!=0)
			ratex=((float)(getWidth()-10-begingraffromx))/(float)sumd;
			else ratex=RATEXC;
		}
		private void drawGrid(Graphics g)
		{
		    int mx=getWidth(),my=getHeight();
		    graphStartsAtY=my*3/4-10;
		    volumeStartsAtY=my*4/5-10;
		    rsiStartsAtY=my-20;
		    macdStartsAtY=my-20;
			g.setColor(Color.white);
			g.fillRect(0,0,mx,my);
			g.setColor(Color.gray);
			g.drawLine(begingraffromx,10,begingraffromx,my-20);
			//draw horizontal low line
			g.setFont(new Font("SansSerif",Font.ITALIC,10));
			g.setColor(Color.red);
                        StokGraph sg=BursaAnalizer_Frame.getStockGraphInstance ();
			if(sg.isEnabledRSI()&&sg.isEnabledMACD())
			{
				macdStartsAtY=rsiStartsAtY-100;//volumeStartsAtY;//-30;
				volumeStartsAtY=macdStartsAtY-50;
				graphStartsAtY=volumeStartsAtY-100;
			         int mx2=getWidth();
				drawGeneric1Scale(g,"MACD",0,macdStartsAtY,macdscalelimit,10,-2.f,1.f);
                                g.setColor(Color.yellow);
				g.drawLine(begingraffromx,macdStartsAtY-macdscalelimit/2+5,mx2-10,macdStartsAtY-macdscalelimit/2+5);
				drawGeneric1Scale(g,"RSI",0,rsiStartsAtY,100,10,0.f,10.f);
				g.setColor(Color.yellow);
				g.drawLine(begingraffromx,rsiStartsAtY-30,mx2-10,rsiStartsAtY-30);	
				g.drawLine(begingraffromx,rsiStartsAtY-70,mx2-10,rsiStartsAtY-70);
			}else
			if(sg.isEnabledRSI())
			{
				volumeStartsAtY=rsiStartsAtY-100;
				graphStartsAtY=volumeStartsAtY-100;
				drawGeneric1Scale(g,"RSI",0,rsiStartsAtY,100,10,0.f,10.f);
				int mx2=getWidth();
				g.setColor(Color.yellow);
				g.drawLine(begingraffromx,rsiStartsAtY-30,mx2-10,rsiStartsAtY-30);	
				g.drawLine(begingraffromx,rsiStartsAtY-70,mx2-10,rsiStartsAtY-70);
			}
			else if(sg.isEnabledMACD())
			{
				volumeStartsAtY=macdStartsAtY-50;
				graphStartsAtY=volumeStartsAtY-100;
				drawGeneric1Scale(g,"MACD",0,macdStartsAtY,macdscalelimit,10,-2.f,1.f);
                                g.setColor(Color.yellow);
                                int mx2=getWidth();
				g.drawLine(begingraffromx,macdStartsAtY-macdscalelimit/2+5,mx2-10,macdStartsAtY-macdscalelimit/2+5);
			}else 
			{
			 graphStartsAtY=volumeStartsAtY-30;
			 volumeStartsAtY=rsiStartsAtY;	
			}
                        drawGeneric1Scale(g,"VOLUME",30,volumeStartsAtY,100.f,25.f,(float)MM.mm[1][0],(float)((MM.mm[1][1]-MM.mm[1][0])/4.f));
			g.setColor(Color.gray);
			g.drawLine(begingraffromx,graphStartsAtY,mx-10,graphStartsAtY);
			g.drawLine(begingraffromx,volumeStartsAtY,mx-10,volumeStartsAtY);
                        drawGeneric1Scale(g,"PRICE",25,graphStartsAtY,graphStartsAtY-10.f,(graphStartsAtY-10.f)/20.f,(float)(MM.mm[0][0]*100.f),(float)((MM.mm[0][1]-MM.mm[0][0])*100.f/20.f));
		}
		/*
                 *limit is a heigth of scale 
                 *ofset is divider size
                 *scalestart is number that scale starts
                 *scaleoffset is number that actualy divider
                 */
		void drawGeneric1Scale(Graphics g,String title,int titlex,float startsatY,float limit,float offset,float scalestart,float scaleoffset)
		{
                        g.setFont(new Font("SansSerif",Font.ITALIC+Font.BOLD,12));
			g.setColor(Color.blue);
                        g.drawString(title,begingraffromx+5,(int)startsatY-5);
			g.setColor(Color.gray);
                        g.setFont(new Font("SansSerif",Font.BOLD,12));
			g.drawLine(begingraffromx,(int)startsatY,getWidth()-10,(int)startsatY);		
                        float scalestarttodraw;
			for(float i=startsatY;i>startsatY-limit;i-=offset,scalestart+=scaleoffset)
			{
                          g.setColor(Color.gray);
			 g.drawLine(begingraffromx-2,(int)i,begingraffromx+2,(int)i);	
                         g.setColor(Color.red);
                         scalestarttodraw=scalestart;
                         if(scalestarttodraw/1000000.f>=1.f)
                         {
                           scalestarttodraw=scalestarttodraw/1000000.f;
                           g.drawString(String.format("%6.2fM",scalestarttodraw),begingraffromx-25-titlex,(int)i);
                         }
                         else if(scalestarttodraw/1000.f>=10.f)
                         {
                           scalestarttodraw=scalestarttodraw/1000.f;
                           g.drawString(String.format("%6.2fK",scalestarttodraw),begingraffromx-25-titlex,(int)i);
                         }else		
                         {
                         g.drawString(String.format("%5.2f",scalestarttodraw),begingraffromx-25-titlex,(int)i);	
                         }
			}	
		}
		
		/*
                    t.o[0] - 	CDate
                    t.o[1] -	OPEN
                    t.o[2] -	HI
                    t.o[3] -	LOW
                    t.o[4]	-	CLOSE
                    t.o[5]	-	AMOUNT
                    t.o[6]	-	percent of change according previous close
		 **/
                
		private void calculatedatediff_dayly()
		{
			if(StokGraph.dataTrade==null||StokGraph.dataTrade.size()<=0)return;
			datediffs=new int[StokGraph.dataTrade.size()];
			int diff=0;
			CDate tdc,tdp;
			int i=0;
			tdp=new CDate((String)(((TDoc) StokGraph.dataTrade.get(i)).o[0]));
			for(i=1;i<StokGraph.dataTrade.size();i++)
			{
				tdc=new CDate((String)(((TDoc) StokGraph.dataTrade.get(i)).o[0]));
				datediffs[diff++]=(int)tdc.diff(tdp);
				tdp=tdc;
			}
		}
		private void drawtimescale(Graphics g)
		{
			g.setFont(new Font("SansSerif",Font.TRUETYPE_FONT,12));
			g.setColor(Color.black);
			float diff=0.f;
			int m=0,tc=0;
			int downpage=getHeight()-10;
			for(int i=0;i<StokGraph.dataTrade.size();i++)
			{
				CDate tdc=new CDate((String)(((TDoc) StokGraph.dataTrade.get(i)).o[0]));
				String ts=tdc.toStringMonthYear_Short();
				if(tc<datediffs.length)diff+=datediffs[tc++];
				if(i==0)
				{
				 g.drawString(ts,begingraffromx-25,downpage);	
				 m=tdc.getMonth();
				}
				else
				{
					if(m!=tdc.getMonth())
					{
					 g.drawString(ts,(int)(diff*ratex)+begingraffromx-25,downpage);	
				 	 m=tdc.getMonth();	
					}
				}	
			}
		}
		float xofset=0.0f;
		double rateystock=1.0f;
		double rateyamount=1.0f;
		private void drawDayly(Graphics g)
		{
			if(StokGraph.dataTrade==null)return;
			drawtimescale(g);
			Float p;
			float yc,yo;
			int xs=0,tc=0,i,j;
			
			if((MM.mm[0][1]-MM.mm[0][0])!=0.0f)
			rateystock=(double)graphStartsAtY/(MM.mm[0][1]-MM.mm[0][0]);
			else rateystock=1.0f;
			
			double min=100000.0f,max=-100000.f;
			int prevx=0, prevy=0;
                        boolean showpredicredvalues=true;
			for(i=0;i<StokGraph.dataTrade.size();i++)
			{
                                String datatype=(String)((TDoc) StokGraph.dataTrade.get(i)).o[7];
                                if(datatype==null)datatype="d";
                                if((datatype.equalsIgnoreCase("i")
                                   ||datatype.equalsIgnoreCase("v")
                                   ||datatype.equalsIgnoreCase("p")
                                   ||datatype.equalsIgnoreCase("z")) && !showpredicredvalues)continue;
				//get close value
				p=(Float)(((TDoc) StokGraph.dataTrade.get(i)).o[4]);
				yc=p.floatValue();
				
				min=100000.0f;
				max=-100000.f;
				for(j=1;j<=4;j++)
				{
					p=(Float)(((TDoc) StokGraph.dataTrade.get(i)).o[j]);
					min=Utils.min(p.floatValue(),min);
					max=Utils.max(p.floatValue(),max);
				}
				min=min-MM.mm[0][0];
				max=max-MM.mm[0][0];
				int typeofgraph=StokGraph.GraphType.getSelectedIndex();
				switch(typeofgraph)
				{
					case 1://open
					//get open value
					p=(Float)(((TDoc) StokGraph.dataTrade.get(i)).o[1]);
					yo=p.floatValue();
					if(i==0)
					{
						prevx=(int)(xs*ratex)+begingraffromx;
						prevy=graphStartsAtY-(int)((yo-MM.mm[0][0])*rateystock);
					}else
					{
						drawLine(g,
							  (int)(xs*ratex)+begingraffromx,
							  graphStartsAtY-(int)((yo-MM.mm[0][0])*rateystock),
							  prevx,prevy,
                                                          datatype);
						prevx=(int)(xs*ratex)+begingraffromx;
						prevy=graphStartsAtY-(int)((yo-MM.mm[0][0])*rateystock);
					}
					break;
					case 2://close
					if(i==0)
					{
						prevx=(int)(xs*ratex)+begingraffromx;
						prevy=graphStartsAtY-(int)((yc-MM.mm[0][0])*rateystock);
					}else
					{
						drawLine(g,
							  (int)(xs*ratex)+begingraffromx,
							  graphStartsAtY-(int)((yc-MM.mm[0][0])*rateystock),
							  prevx,prevy,
                                                          datatype);
						prevx=(int)(xs*ratex)+begingraffromx;
						prevy=graphStartsAtY-(int)((yc-MM.mm[0][0])*rateystock);
					}
					break;
					case 3://average open close high low
					//get open value
					float tavg=0;
					p=(Float)(((TDoc) StokGraph.dataTrade.get(i)).o[1]);
					tavg+=p.floatValue();
					//get hi value
					p=(Float)(((TDoc) StokGraph.dataTrade.get(i)).o[2]);
					tavg+=p.floatValue();
					//get low value
					p=(Float)(((TDoc) StokGraph.dataTrade.get(i)).o[3]);
					tavg+=p.floatValue();
					tavg+=yc;
					tavg/=4.0f;
					if(i==0)
					{
						prevx=(int)(xs*ratex)+begingraffromx;
						prevy=graphStartsAtY-(int)((tavg-MM.mm[0][0])*rateystock);
					}else
					{
						drawLine(g,
							  (int)(xs*ratex)+begingraffromx,
							  graphStartsAtY-(int)((tavg-MM.mm[0][0])*rateystock),
							  prevx,prevy,
                                                          datatype
                                                          );
						prevx=(int)(xs*ratex)+begingraffromx;
						prevy=graphStartsAtY-(int)((tavg-MM.mm[0][0])*rateystock);
					}
					break;
					case 4://japanese
					p=(Float)(((TDoc) StokGraph.dataTrade.get(i)).o[1]);
					yo=p.floatValue();
					//get hi value
					float yh,yl;
					p=(Float)(((TDoc) StokGraph.dataTrade.get(i)).o[2]);
					yh=p.floatValue();
					//get low value
					p=(Float)(((TDoc) StokGraph.dataTrade.get(i)).o[3]);
					yl=p.floatValue();
					drawCandle(g,
								(int)(xs*ratex)+begingraffromx,
								graphStartsAtY-(int)((yo-MM.mm[0][0])*rateystock),
								graphStartsAtY-(int)((yc-MM.mm[0][0])*rateystock),
								graphStartsAtY-(int)((yh-MM.mm[0][0])*rateystock),
								graphStartsAtY-(int)((yl-MM.mm[0][0])*rateystock),
                                                                datatype);
					break;
					default://vertical is 0
					p=(Float)(((TDoc) StokGraph.dataTrade.get(i)).o[1]);
				    yo=p.floatValue();
					drawFull(g,
						 	(int)(xs*ratex)+begingraffromx,
						 	graphStartsAtY-(int)(min*rateystock),
						 	graphStartsAtY-(int)(max*rateystock),
						 	graphStartsAtY-(int)((yc-MM.mm[0][0])*rateystock),
						 	graphStartsAtY-(int)((yo-MM.mm[0][0])*rateystock),
                                                        datatype);
					break;
				};
				if(tc<datediffs.length)xs+=datediffs[tc++];	
			}
		}
		private void drawFull(Graphics g,int wherex,int low,int high,int close,int open,String datatype)
		{
                    StokGraph sg=BursaAnalizer_Frame.getStockGraphInstance ();
			if(datatype.equalsIgnoreCase("d"))g.setColor(sg.dataTradeColor);
                        else
                        {
                            g.setColor(Color.red);
                        g.drawString(datatype, wherex+2, high+8);
                    }
			g.drawLine(wherex,low,wherex,high);	
			g.drawLine(wherex,close,wherex+1,close);	
			g.drawLine(wherex-1,open,wherex,open);	
		}
		private void drawPoint(Graphics g,int wherex,int point)
		{
                    StokGraph sg=BursaAnalizer_Frame.getStockGraphInstance ();
			g.setColor(sg.dataTradeColor);
			g.drawRect(wherex,point,1,-1);	
		}
		private void drawLine(Graphics g,int wherex,int point,int wherexprev,int pointprev,String datatype)
		{
                        StokGraph sg=BursaAnalizer_Frame.getStockGraphInstance ();
			if(datatype.equalsIgnoreCase("d"))g.setColor(sg.dataTradeColor);
                        else
                        {
                            g.setColor(Color.red);
                        g.drawString(datatype, wherex+2, point+8);
                    }
			g.drawLine(wherexprev,pointprev,wherex,point);	
		}
		private void drawCandle(Graphics g,int wherex,int open,int close,int hi,int low,String datatype)
		{
                    StokGraph sg=BursaAnalizer_Frame.getStockGraphInstance ();
			if(datatype.equalsIgnoreCase("d"))g.setColor(sg.dataTradeColor);
                         else
                        {
                            g.setColor(Color.red);
                        g.drawString(datatype, wherex+2, hi+8);
                    }
			g.drawLine(wherex,hi,wherex,low);	
			int from=(open>=close)?close:open;
			int to=(close<=open)?open:close;
			g.drawRect(wherex-3,from,6,to-from);
			if(close<open)g.setColor(Color.black);
			else g.setColor(Color.white);
			g.fillRect(wherex-2,from-1,4,to-from-2);
		}
		private void drawVolume(Graphics g)
		{
			if(StokGraph.dataTrade==null)return;
			g.setColor(Color.pink);
			Float p;
			float ya,yprev=0;
			int xs=0;
			
			if((MM.mm[1][1]-MM.mm[1][0])!=0.0f)
			rateyamount=100.f/(MM.mm[1][1]-MM.mm[1][0]);
			else rateyamount=1.0f;
                        g.setXORMode(Color.blue);
			for(int i=0,tc=0;i<StokGraph.dataTrade.size();i++,tc++)
			{
				//get amount value
				p=(Float)(((TDoc) StokGraph.dataTrade.get(i)).o[5]);
				ya=p.floatValue();
				if(i==0)yprev=ya;
				if(ya>=yprev)g.setColor(Color.green);
				else g.setColor(Color.pink);
                                
				g.drawRect((int)(xs*ratex)+begingraffromx,volumeStartsAtY-(int)((ya-MM.mm[1][0])*rateyamount),
							(int)(1*ratex),(int)((ya-MM.mm[1][0])*rateyamount));
				g.fillRect((int)(xs*ratex)+begingraffromx,volumeStartsAtY-(int)((ya-MM.mm[1][0])*rateyamount),
							(int)(1*ratex),(int)((ya-MM.mm[1][0])*rateyamount));
				yprev=ya;
				if(tc<datediffs.length)xs+=datediffs[tc];
			}	
                        g.setPaintMode();
		}
		int invertscreen(int y,int sp)
		{
			return sp-y;
		}
		private void drawMACD(Graphics g)
		{
                    StokGraph sg=BursaAnalizer_Frame.getStockGraphInstance ();
			if(sg.isEnabledMACD()==false||sg.dataMACD==null)return;
			int i,j,ys,ye;
			double curent1,last1,
			        curent2,last2;
			int tosum=(datediffs.length-(StokGraph.dataMACD.length/2));
			int lastx=0,curx;
			for(j=0;j<tosum&&j<datediffs.length;j++)lastx+=datediffs[j];
			
			 //detect min max bounds to use in ratio calculations
			 double tmin=100000.f,tmax=-100000.f;
			 for(i=StokGraph.dataMACD.length-1;i>=1;i=i-2)
			 {
			 	curent1=StokGraph.dataMACD[i];
			 	curent2=StokGraph.dataMACD[i-1];
				tmin=Utils.min(curent1,tmin);
				tmax=Utils.max(curent1,tmax);
				tmin=Utils.min(curent2,tmin);
				tmax=Utils.max(curent2,tmax);
			}
			//calculate ratio
			double rateymacd;
			if((tmax-tmin)!=0.0f)
			rateymacd=macdscalelimit/(tmax-tmin);
			else rateymacd=1.0f;
			 //draw data points
			 last1=StokGraph.dataMACD[0];
			 last2=StokGraph.dataMACD[1];
			 curx=lastx;
			for(i=2;i<StokGraph.dataMACD.length&&j<datediffs.length;i=i+2,j++)
			{
			 curent1=StokGraph.dataMACD[i];
			 curent2=StokGraph.dataMACD[i+1];
			 if(j<datediffs.length)curx=lastx+datediffs[j];
			 g.setColor(sg.getDataMACD1Color());
			 ys=invertscreen((int)(rateymacd*(last1-tmin)),macdStartsAtY);
			 ye=invertscreen((int)(rateymacd*(curent1-tmin)),macdStartsAtY);
			 g.drawLine((int)(lastx*ratex)+begingraffromx,ys,(int)(curx*ratex)+begingraffromx,ye);	
			 g.setColor(sg.getDataMACD2Color());
			 ys=invertscreen((int)(rateymacd*(last2-tmin)),macdStartsAtY);
			 ye=invertscreen((int)(rateymacd*(curent2-tmin)),macdStartsAtY);
			 g.drawLine((int)(lastx*ratex)+begingraffromx,ys,(int)(curx*ratex)+begingraffromx,ye);	
			 last1=curent1;
			 last2=curent2;
			 lastx=curx;
			}	
		}
		private void drawRSI(Graphics g)
		{
                    StokGraph sg=BursaAnalizer_Frame.getStockGraphInstance ();
			if(sg.isEnabledRSI()==false||StokGraph.dataRSI==null||StokGraph.dataRSI.length==0)return;
			int i,j,ys,ye;
			double curent,last;
			int lastx=0,curx=0;
			for(j=0;j<sg.getRSIPERIOD()&&j<datediffs.length;j++)lastx+=datediffs[j];
			last=StokGraph.dataRSI[0];
			for(i=1;i<StokGraph.dataRSI.length;i++,j++)
			{
			 if(j<datediffs.length)curx=lastx+datediffs[j];
			 curent=StokGraph.dataRSI[i];
			 //<=30 buye 
			 //>=70 sale
			 if(curent<=30.0f)g.setColor(Color.green);
			 else if(curent>=70.0f)g.setColor(Color.red);
			 else g.setColor(sg.getDataRSIColor());
			 ys=invertscreen((int)last,rsiStartsAtY);
			 ye=invertscreen((int)curent,rsiStartsAtY);
			 g.drawLine((int)(lastx*ratex)+begingraffromx,ys,(int)(curx*ratex)+begingraffromx,ye);	
			 last=curent;
			 lastx=curx;
			}
		}
                
                private void drawMAVERAGES(Graphics g,double data[],int period,boolean enabled,Color c)
                {
                        int i,j,ys,ye;
			double curent,last;
			int lastx=0,curx=0;
			if(enabled==true&&data!=null)
			{
			for(j=0;j<period&&j<datediffs.length;j++)lastx+=datediffs[j];
			g.setColor(c);
                        last=data[0];
			for(i=1;i<data.length;i++,j++)
			{
                                if(j<datediffs.length)curx=lastx+datediffs[j];
				curent=data[i];
                                ys=graphStartsAtY-(int)((last-MM.mm[0][0])*rateystock);
                                ye=graphStartsAtY-(int)((curent-MM.mm[0][0])*rateystock);
				g.drawLine((int)(lastx*ratex)+begingraffromx,ys,(int)(curx*ratex)+begingraffromx,ye);
                                last=curent;
                                lastx=curx;
			}	
			}
                }
		

    void drawListofClicks(Graphics g)
    {
        String tstr;
        int tx1=0,ty1 = 0,tx2 = 0,ty2 = 0;
     for(int i=0;i<pointsclickedin4.size();i++)
     {
        tx1=Integer.parseInt((((TDoc) pointsclickedin4.get(i)).o[0]).toString());
        ty1=Integer.parseInt((((TDoc) pointsclickedin4.get(i)).o[1]).toString());
        tx2=Integer.parseInt((((TDoc) pointsclickedin4.get(i)).o[2]).toString());
        ty2=Integer.parseInt((((TDoc) pointsclickedin4.get(i)).o[3]).toString());
        tstr=""+Float.parseFloat((((TDoc) pointsclickedin4.get(i)).o[4]).toString())
               +((TDoc) pointsclickedin4.get(i)).o[5];
        g.setColor(Color.gray);
        g.drawLine(tx1,ty1,tx2,ty2);
        g.setColor(Color.red);
        g.setFont(new Font("Arial",Font.TRUETYPE_FONT,10));
        g.drawString(tstr,tx1,ty1);
     }
    }
    java.util.List pointsclickedin4  = new ArrayList();
    boolean isfirstklick=true;                
    int firstClickX=0,firstClickY=0;
    int secondClickX=0,secondClickY=0;
    public void mouseClicked(MouseEvent e) {
        if(isfirstklick)
        {
        firstClickX=e.getX();
        firstClickY=e.getY();
        isfirstklick=false;
        }else
        {
            secondClickX=e.getX();
            secondClickY=e.getY();
            if(secondClickX<firstClickX)
            {
             int t=firstClickX;
             firstClickX=secondClickX;
             secondClickX=t;
             if(secondClickY>firstClickY)
            {
             t=firstClickY;
             firstClickY=secondClickY;
             secondClickY=t;
            }
            }
            isfirstklick=true;
            TDoc tempval=new TDoc(6);
            tempval.o[0]=firstClickX;
            tempval.o[1]=firstClickY;
            tempval.o[2]=secondClickX;
            tempval.o[3]=secondClickY;
            //tempval.o[4]=getpercentsbetweenclicks();
            //tempval.o[5]="(%)";
            //or in points
            tempval.o[4]=getpointsbetweenclicks();
            tempval.o[5]="(pt.)";
            pointsclickedin4.add(tempval);
            //System.out.println("start(x,y)="+firstClickX+","+firstClickY+" end(x,y)="+secondClickX+","+secondClickY);
            repaint();
        }
    }

    private float getpercentsbetweenclicks()
    {
      float dx=secondClickX-firstClickX;
      float dy=firstClickX-secondClickX;
      return (float)Math.atan2(dx,dy);//theta
    }
    private float getpointsbetweenclicks()
    {
      float dy=firstClickY-secondClickY;
      return (float)(dy*rateystock);
    }
    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }


    public void mouseReleased(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }
	}
