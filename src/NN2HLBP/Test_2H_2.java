/*
 * Test_2H_2.java
 *
 * Created on November 26, 2006, 10:36 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package NN2HLBP;


import CDate.CDate;
import DBCONNECT.DataBaseConnector;
import DBCONNECT.mssql_connect;
import DBOPERATIONS.GenericTableOperations;
import Utils.LoggerNB;
import Utils.TDoc;
import StatisticBase.STA;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 *
 * @author Dmitry
 */
public class Test_2H_2 extends NNTestRecal{
    
    private double[][] in = null;
    private double[][] out = null;
    //private double[][] testDATA = null;
    
    public Neural_2HDV nn=null;
    private static String neuropath="c://SDI_BATool/data/AI/";
    private String filetosave="test.neural";
    
    private static final String []examptionnames=new String[]{"ABS_SUM","MEAN","VARIANCE(SIGMA)","STD_DEV"};
    public static final boolean[] usetest=new boolean[]{true,true,true,true};
    private static final int TESTIDSTART=0;
    public static final int ABS_SUM=TESTIDSTART;
    public static final int MEAN=1;
    public static final int VARIANCE=2;
    public static final int STD_DEV=3;
    private static final int TESTIDEND=STD_DEV;
    private int doitbyexamption=ABS_SUM;
    
    private long learncycle=320000L;//1500;
    
    public static final long MAX_LEARN_CYCLE=Long.MAX_VALUE/2;
    
    private double minErrAcceptedOnNIter=0.2;
    //for each test type I have best err accepted as result of learn
   // private double []goodAcceptedError=new double[]{0.2,0.2,0.2,0.2,0.2};
    
    
    private int numinvals=7;
    private int numoutvals=5;
    private boolean qttystatus=true;
    DataBaseConnector data;

    public void setminErrAcceptedOnNIter(double _minErrAcceptedOnNIter){
    minErrAcceptedOnNIter=_minErrAcceptedOnNIter;
    }
    
    public Test_2H_2(DataBaseConnector tdata,int stokid, int predictondays,boolean use100percenting) {
        this.data=tdata;
        buildInAndOutData( data,stokid,predictondays,use100percenting,null);
        buildAndRunAll(50,90,predictondays,use100percenting,false,false);//auto save false
    }
    public Test_2H_2(DataBaseConnector tdata,int stokid, int predictondays,boolean use100percenting,CDate until) {
         this.data=tdata;
        buildInAndOutData(data,stokid,predictondays,use100percenting,until);
        buildAndRunAll(50,90,predictondays,use100percenting,false,false);//auto save false
    }
    public Test_2H_2(java.util.List Out ,int predictdays,boolean use100percenting) {
        buildInAndOutData(Out,predictdays,use100percenting);
        if(!qttystatus)System.out.println("number of record loaded is ins for prediction");
        else
        buildAndRunAll(50,90,predictdays,use100percenting,false,false);//auto save false
    }
    public Test_2H_2(DataBaseConnector tdata,int hl1size,int hl2size,int predictdays,boolean use100percenting) {
         this.data=tdata;
        buildAndRunAll(hl1size,hl2size,predictdays,use100percenting,false,false);//auto save false
    }
    public Test_2H_2(String filename) {
        setFileName(filename);
        System.out.println("Reload a previously trained NN from disk and re-test:"+filetosave);
        nn = Neural_2HDV.Factory(filetosave);


    }
    
    public void setLearnCicle(long lc) {
        if(lc>0&&lc<MAX_LEARN_CYCLE)learncycle=lc;
    }
    public long getLearnCicle() {
        return learncycle;
    }
    
    
    public void setDoitByExamption(int examption) {
        doitbyexamption=examption;
    }
    public int getDoitByExamption() {
        return doitbyexamption;
    }
    public String getDoitByExamptionString(int examption) {
        if(examption>=0&&examption<4)return examptionnames[examption];
        return "Unknoun";
    }
    public String getDoitByExamptionString() {
        return getDoitByExamptionString(doitbyexamption);
    }
    public void setFileName(String fn){filetosave=getNeuropath()+fn+".neuro";}
    public String getFileName( ){return filetosave;}
    public Test_2H_2() {
        
    }
    
    public double[] aproximateIt(double[]datain) {
        
        return nn.recall(datain);
    }
    public void addIn(double[]datain)
    {
      in=addData(in,datain);
    }
    public void addOut(double[]datain)
    {
     out=addData(out,datain);
    }

    double getInputDataCorelation() {
       //TODO returns input datat corelation
        return 0.0;
    }

    void setusetest(int testid,boolean val) {
        if(testid>=TESTIDSTART&&testid<=TESTIDEND)
            usetest[testid]=val;
        else System.out.println("Trying assign to unknown test type");
    }
    private double[][] addData(double[][]what,double[]datain)
    {
      int i,curentsize=0;
      if(datain==null)return what;
      if(what!=null)
      {
            if(what[0]!=null)
            {
                if(what[0].length!=datain.length)return what;
            }
       curentsize=what.length;
      }
     curentsize++;
     double [][]temp=new double[curentsize][];
     for(i=0;i<curentsize;i++)temp[i]=what[i];
     temp[i]=datain;
     return temp; 
    }
    public double[] getIn(int i)
    {
     return getDataI(in,i);
    }
    public double[] getOut(int i)
    {
     return getDataI(out,i);
    }
    private double[] getDataI(double[][]what,int i)
    {
     if(what!=null&&(i>=0&&i<what.length))return what[i];
     return null;
    }
    public double buildAndRunAll(int numaxhl1,int numaxhl2,int predictondays,boolean use100percenting,boolean autosave,boolean checkchi) {
        if(in==null||out==null)return 100.0;
        if(in.length==0||out.length==0)return 100.0;
        nn = new Neural_2HDV(getNuminvals()*predictondays, numaxhl1, numaxhl2, getNumoutvals());
        runLearner(use100percenting);
        //testDATA=new double[1][];
        //testDATA[0]=in[in.length-1];
        //for(i=0;i<testDATA.length;i++)test_recall(nn, testDATA[i]);
        double errtotal=0.0;
        double []errstatistics=new double[in.length];
        double [][]chi=new double[in.length][];
        int i;
        double got[]=null;
        for(i=0;i<in.length;i++) {
            switch(doitbyexamption) {
                case MEAN:
                     got=test_recall_withERRMEAN(nn, in[i],out[i]);
                    errstatistics[i]=got[0];

                    break;
                case VARIANCE:
                    got=test_recall_withERRVARIANCE(nn, in[i],out[i]);
                     errstatistics[i]=got[0];
                    break;
                case STD_DEV:
                    got=test_recall_withERRSTD_DEV(nn, in[i],out[i]);
                     errstatistics[i]=got[0];
                    break;
                default://ABS_SUM
                    got=test_recall_withERR(nn, in[i],out[i]);
                     errstatistics[i]=got[0];
                    break;
            }
            chi[i]=new double[2];
            chi[i][0]=out[i][0];
            chi[i][1]=got[1];
        }

        try {
            switch(doitbyexamption) {
                case MEAN:
                    errtotal=STA.avg(errstatistics);
                    break;
                case VARIANCE:
                    errtotal=STA.variance(errstatistics,STA.avg(errstatistics));
                    break;
                case STD_DEV:
                    errtotal=STA.std_dev(STA.variance(errstatistics,STA.avg(errstatistics)));
                    break;
                default://ABS_SUM
                    for(i=0;i<errstatistics.length;i++)errtotal+=errstatistics[i];
                    break;
            }
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
        if(checkchi)
        {
          //  for(int ppp=0;ppp<chi.length;ppp++)chi[ppp][1]=Math.abs(errtotal);
        double chiv=STA.chisquare(chi);
        System.out.println(getDoitByExamptionString()+" chi="+chiv);
        System.out.println(getDoitByExamptionString()+" P="+STA.chiP(1, chiv));
        }
        if(LoggerNB.debuging)System.out.println(String.format("The programmed AI error on %d relations is %9.6f",in.length,errtotal));
        if(stattable)
        {
              if(LoggerNB.debuging)
              {
            System.out.println(String.format("Programmed AI %s test error %12.9f  on %d relations hl1 %d hl2 %d",
                    getDoitByExamptionString(),errtotal,in.length,numaxhl1, numaxhl2));
              }
        }
        if(autosave) {
            System.out.println("Saving trained AI NET to later reuse to "+filetosave);
            save();
        }
         nn.besti=numaxhl1;
                    nn.bestj=numaxhl2;
                    nn.besttype=doitbyexamption;
                    nn.resultbest=errtotal;
        return errtotal;
    }
    long runLearner(boolean use100percenting) {
        int i;
        long reti=0;
        boolean hit=false;
        for(i=0;i<in.length&&i<out.length;i++)
            nn.addTrainingExample(in[i], out[i]);
        double error = 0;
        if(use100percenting)minErrAcceptedOnNIter*=100.0;
        for (  reti = 0; reti < learncycle; reti++)
        {
            error += nn.train();
            if (reti > 0 && (reti % 2 == 0))
            {
                error /= 2;
                if(LoggerNB.debuging)System.out.println(String.format("cycle %d error is %9.6f",reti, error));
                if(error<minErrAcceptedOnNIter){hit=true;break;}
                error = 0;
            }
        }
        System.out.println(String.format((hit?"hit on":"gone to max")+" cycle %d error is %9.6f",reti, error));
        return reti;
    }
    public java.util.List buildInAndOutData(DataBaseConnector data,int id,int predictdays,boolean use100percenting,CDate until) {
        java.util.List Out= new ArrayList();
        
        
        //DataBaseConnector    data=new Msdb_connect("C:\\SDI_BATool\\data\\data.mdb");
        //((Msdb_connect)data).connect();
        // DataBaseConnector data;
         if(data==null)
         {
         data=new mssql_connect("batool","sa","sa");
    	((mssql_connect)data).setPort(1433);
    	((mssql_connect)data).setHost("//localhost");
    	((mssql_connect)data).connect();
         }
        GenericTableOperations.loadTradeDataTableToOutByOutSideCon(data.con,13, id, Out);
        //data.disConnect();
        if(until!=null)
        {
         //remove dates wich is greater than until
            for(int i=Out.size()-1;i>=0;i--)
            {
             TDoc t=(TDoc)Out.get(i);
             CDate v=new CDate(t.o[0].toString());
             if(v.greater(until)){Out.remove(t);}
            }
        }
        System.out.println(String.format("On Stok ID=%d Total loaded %d records",id,Out.size()));
        initsomeaddedfields(Out);
        qttystatus=true;
        calculateInAndOutData(Out,predictdays,use100percenting);
        return Out;
    }
    void initsomeaddedfields(java.util.List Out)
    {
     for(int i=0;i<Out.size();i++)
     {
      ((TDoc) Out.get(i)).o[11]=new Float(0.0f);
      ((TDoc) Out.get(i)).o[12]=new Float(0.0f);
     }
    }
    public void buildInAndOutData(java.util.List Out ,int predictdays,boolean use100percenting) {
        //resize Out to feet our needs
        for(int i=0;i<Out.size();i++)
            ((TDoc) Out.get(i)).safeResize(13);
        if(LoggerNB.debuging){System.out.println(String.format("Total loaded %d records",Out.size()));}
        qttystatus=true;
        calculateInAndOutData(Out,predictdays,use100percenting);
    }
    private void calculateInAndOutData(java.util.List Out ,int predictdays,boolean use100percenting) {
        // calculate diffs %
        //GenericTableOperations.calculateDiffARtoPreviousA(Out, 4,6,use100percenting);//diff of close prices
         GenericTableOperations.calculateDiffARtoPreviousA(Out, 2,6,use100percenting);//diff of close prices
        GenericTableOperations.calculateDiffARtoPreviousA(Out, 5,7,use100percenting);//diff of trade amounts
        //GenericTableOperations.calculateDiffAtoB(Out, 4,1,8,use100percenting);//R close to open
        GenericTableOperations.calculateDiffAtoB(Out, 2,1,8,use100percenting);//R close to open
        GenericTableOperations.calculateDiffAtoB(Out, 2,4,9,use100percenting);//R close to high
        GenericTableOperations.calculateDiffAtoB(Out, 2,3,10,use100percenting);//R close to low
        if(Out.size()-predictdays<0){qttystatus=false;return;}
        in=new double[Out.size()-predictdays][];
        out=new double[Out.size()-predictdays][];
        int i,j,k=0;
        //now build the datain
        for(i=predictdays;i<Out.size();i++) {
            //make 1 step back
            //if(i>predictdays)i=i-predictdays+2;
            out[k]=new double[5];
            out[k][0]=((Float) (((TDoc) Out.get(i)).o[6])).floatValue();
            out[k][1]=((Float) (((TDoc) Out.get(i)).o[7])).floatValue();
            out[k][2]=((Float) (((TDoc) Out.get(i)).o[8])).floatValue();
            out[k][3]=((Float) (((TDoc) Out.get(i)).o[9])).floatValue();
            out[k][4]=((Float) (((TDoc) Out.get(i)).o[10])).floatValue();
            //this is a brute consinstency data check
            //diff of trade amounts at 574 data pont
            //i get here some bizare numbers so i fix it manualy
            for(int check=0;check<out[k].length;check++) {
                if(out[k][check]>3.4E38) {
                    out[k][check]=0.0;//mean no change
                }
            }
            in[k]=new double[predictdays*7];
            for(j=0;j<predictdays*7;j+=7) {
                in[k][j]=((Float) (((TDoc) Out.get(i-1-j/7)).o[6])).floatValue();
                in[k][j+1]=((Float) (((TDoc) Out.get(i-1-j/7)).o[7])).floatValue();
                in[k][j+2]=((Float) (((TDoc) Out.get(i-1-j/7)).o[8])).floatValue();
                in[k][j+3]=((Float) (((TDoc) Out.get(i-1-j/7)).o[9])).floatValue();
                in[k][j+4]=((Float) (((TDoc) Out.get(i-1-j/7)).o[10])).floatValue();
                in[k][j+5]=((Float) (((TDoc) Out.get(i-1-j/7)).o[11])).floatValue();
                in[k][j+6]=((Float) (((TDoc) Out.get(i-1-j/7)).o[12])).floatValue();
            }
            if(LoggerNB.debuging) {
                System.out.println(String.format("In layer %d data contains %d basis",k,in[k].length));
                System.out.println(String.format("Out layer %d contains %d expected data",k,out[k].length));
            }
            k++;
        }
        if(LoggerNB.debuging) {
            System.out.println(String.format("In layer size %d predict on %d days data contains %d basis",in.length,predictdays,in[0].length));
            System.out.println(String.format("Out layer size %d contains %d expected data",out.length,out[0].length));
        }
    }
    
    boolean stattable=true;
    public static void main(String[] args) {
        Test_2H_2 ai=new Test_2H_2();
        int predictondays=4;
        boolean use100percenting=false;
        boolean savebesttoserial=false;
        int menayaid=200;//default it is el al table id
        if(args.length>0)menayaid=Integer.parseInt(args[0]);
        if(args.length>1)savebesttoserial=Boolean.parseBoolean(args[1]);
        ai.performancetest("ELAL",menayaid,predictondays,use100percenting,savebesttoserial);
    }
    
    private int perfteststepi=5;
    private int perfteststepj=5;

    public void setPerformanceTestSteping(int a,int b)
    {
     if(a>0&&a<100)perfteststepi=a;
     if(b>0&&b<100)perfteststepj=b;
    }

   public double []resultbest=null;
   public int []besti=null;
   public int []bestj=null;

    public void performancetest(String stockSymbol,int stockid, int predictondays, boolean use100percenting,boolean savebesttoserial)
    {
        double result;
        resultbest=new double[]{100000,100000,100000,100000};
        besti=new int[]{0,0,0,0};
        bestj=new int[]{0,0,0,0};
        buildInAndOutData(data,stockid,predictondays,use100percenting,null);//predict on x days

        if(saveedtfonly)
        {
            saveedtf(getNeuropath()+"stokID_"+stockid+(!stockSymbol.isEmpty()?"_"+stockSymbol:"")+".edtf");
         return;
        }
        int i,j;
        //best found on 50 90 ?
        for(int dis=TESTIDSTART;dis<=TESTIDEND;dis++) {
            if(!usetest[dis])
            {
                  System.out.println("Skeeping test "+getDoitByExamptionString(dis));
                     continue;
            }
            //set type of cumulative error examption
            setDoitByExamption(dis);
            //do all i*j tests for each examption type
            for(i=5;i<100;i+=perfteststepi)
                for(j=5;j<100;j+=perfteststepj) {
                result=buildAndRunAll(i,j,predictondays,use100percenting,false,false);
                if(Math.abs(result)<resultbest[dis]) {
                    resultbest[dis]=Math.abs(result);
                    besti[dis]=i;
                    bestj[dis]=j;
                }
                }
        }
        for(i=TESTIDSTART;i<=TESTIDEND;i++) {
             if(!usetest[i])    continue; 
            System.out.println(String.format("Best examption %s result AI is %12.9f on i=%d j=%d",
                    getDoitByExamptionString(i),resultbest[i],besti[i],bestj[i]));
            if(savebesttoserial) {
                //it is the best i and j for hiden layers
                //auto save to file for later using
                setFileName(getDoitByExamptionString(i)+"_stokID_"+stockid+(!stockSymbol.isEmpty()?"_"+stockSymbol:""));
                buildAndRunAll(besti[i],bestj[i],predictondays,use100percenting,savebesttoserial,true);
                //examples i got on 688 data points el al
                //Best ABS result AI is  2.113992 on i=30 j=70
            }
        }
    }
     public void completeuser(int stokid, int predictondays, int fl,int sl,int examdis,boolean use100percenting,boolean savebest,CDate until) {
        double result;
        buildInAndOutData(data,stokid,predictondays,use100percenting,until);//predict on x days
            setDoitByExamption(examdis);
            result=buildAndRunAll(fl,sl,predictondays,use100percenting,savebest,true);
    }
    public void completeuser(int stokid, int predictondays, int fl,int sl,int examdis,boolean use100percenting,boolean savebest) {
        double result;
        buildInAndOutData(data,stokid,predictondays,use100percenting,null);//predict on x days
            setDoitByExamption(examdis);
            result=buildAndRunAll(fl,sl,predictondays,use100percenting,savebest,true);
    }
     public void completeuser(java.util.List Out, int predictondays, int fl,int sl,int examdis,boolean use100percenting,boolean savebest) {
        double result;
        buildInAndOutData(Out,predictondays,use100percenting);//predict on x days
          if(!qttystatus)System.out.println("number of record loaded is ins for prediction");
        else
        {
            setDoitByExamption(examdis);
            result=buildAndRunAll(fl,sl,predictondays,use100percenting,savebest,true);
        }
    }
    public void save() 
    {
       if(nn!=null) nn.save(filetosave);
    }

    int getInputLayerSize() {
        if(nn!=null)return nn.getInputCount();
        return 0;
    }

    public int getNuminvals ()
    {
        return numinvals;
    }

    public void setNuminvals (int numinvals)
    {
        this.numinvals = numinvals;
    }

    public int getNumoutvals ()
    {
        return numoutvals;
    }

    public void setNumoutvals (int numoutvals)
    {
        this.numoutvals = numoutvals;
    }

    public static String getNeuropath ()
    {
        return neuropath;
    }

    public static void setNeuropath (String aNeuropath)
    {
        neuropath = aNeuropath;
    }

    private boolean saveedtfonly=false;
    void setsaveedtfonly(boolean _saveedtfonly) {
      saveedtfonly=_saveedtfonly;
    }

    private void saveedtf(String __fn) {

          if(in==null||in.length==0)return;
         BufferedOutputStream bufferedOutput = null;

        try {

            //Construct the BufferedOutputStream object
            bufferedOutput = new BufferedOutputStream(new FileOutputStream(__fn));


            //Start writing to the output stream
            bufferedOutput.write(("inlength "+in.length+"\n").getBytes());
            bufferedOutput.write(("inlengthloc "+in[0].length+"\n").getBytes()); //new line, you might want to use \r\n if you're on Windows
            bufferedOutput.write(("outlength "+out.length+"\n").getBytes());
            bufferedOutput.write(("outlengthloc "+out[0].length+"\n").getBytes()); //new line, you might want to use \r\n if you're on Windows

            for(int i=0;i<in.length&&i<out.length;i++)
            {
              bufferedOutput.write("data: ".getBytes());
              for(int j=0;/*j<49*/j<in[i].length;j++)
              bufferedOutput.write((""+in[i][j]+" ").getBytes());
              for(int j=0;/*j<5*/j<out[i].length;j++)
              bufferedOutput.write((""+out[i][j]+" ").getBytes());
              
              bufferedOutput.write("\n".getBytes());
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            //Close the BufferedOutputStream
            try {
                if (bufferedOutput != null) {
                    bufferedOutput.flush();
                    bufferedOutput.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
