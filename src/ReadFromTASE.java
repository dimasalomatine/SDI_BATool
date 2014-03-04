import CDate.*;
import DBCONNECT.DataBaseConnector;

import LocalUtils.BasicZIP;
import Utils.DnldURL;
import Utils.LoggerNB;

//~--- JDK imports ------------------------------------------------------------

import java.io.*;

import java.sql.*;

//~--- classes ----------------------------------------------------------------
import java.util.Calendar;
import java.util.Map;

public class ReadFromTASE extends DnldURL {
    public ReadFromTASE(String url) {
        super(url);
    }
    /**
     * Method ReadFromTASE
     *
     *
     */
    public ReadFromTASE(String symbol, String market, CDate start, CDate end,Map mapindexing) {
        //first check if files of these dates already downloaded from TASE
       if(start!=null&&end!=null) 
           FilesExistsLocalyFromTase(start, end,mapindexing);   
    }
    //~--- methods ------------------------------------------------------------

    /**
     * Method read
     *
     *
     */
    public void read(DataBaseConnector data, int table, CDate begin, CDate now) {
       /* if (!status) {
            return;
        }*/
        try {
            CDate tempd=new CDate(begin);
            while(tempd.less(now))
            {
            // first check if index file exist unpacked for current day
            String index803=GenLocalFileNameN(tempd,803);
            if (!(new File(index803)).exists()) 
            {
                // then unpack it if not from zip 
                String tdir=GenLocalFileNameN(tempd,-100);
                if(!(new File(tdir)).exists())
                {
                    File file = new File(tdir);
                    // Create directory if it does not exist
                    file.mkdir();
                }      
                System.out.println("UNPACK INDEX "+GenLocalFileNameN(tempd,-803)+"from "+GenLocalFileNameN(tempd,-1)+" to "+index803);
                BasicZIP bz=new BasicZIP();
                bz.GetFileAs(GenLocalFileNameN(tempd,-1), GenLocalFileNameN(tempd,-803), index803);
            }
            else
            {
                System.out.println("Found INDEX INDEXING");
            }
            // then open to read and update indexes if any added or changed
            ReadFromTASE2SQL r=new ReadFromTASE2SQL(this);
             r.ReadAndUpdateIndex(index803,data);
             // first check if tact file exist unpacked for current day
             String tact542=GenLocalFileNameN(tempd,542);
            if (!(new File(tact542)).exists()) 
            {
                // then unpack it if not from zip 
                String tdir=GenLocalFileNameN(tempd,-100);
                if(!(new File(tdir)).exists())
                {
                    File file = new File(tdir);
                    // Create directory if it does not exist
                    file.mkdir();
                }      
                System.out.println("UNPACK TACT "+GenLocalFileNameN(tempd,-542)+"from "+GenLocalFileNameN(tempd,-1)+" to "+tact542);
                BasicZIP bz=new BasicZIP();
                bz.GetFileAs(GenLocalFileNameN(tempd,-1), GenLocalFileNameN(tempd,-542), tact542);
            }
            else
            {
                System.out.println("Found TACT WRITING");
            }
            // then open to read and update indexes if any added or changed
             r.ReadTACT(tact542,data,0,tempd);
            //roll to nex date
             tempd.roll(1);
            }
        }catch (Exception ioe2)
        {
            if(LoggerNB.debuging){System.out.println("1 2 IOException happened. " + ioe2);}
        }
    }

    private void DownloadPackFromTASE(String rempath,String tfnd) {
        url = rempath+tfnd;
        System.out.println("Getting file " + url);
        create();
        SaveLocaly(localdatapath+"\\"+tfnd);
    }

    private String localdatapath =
            BursaAnalizer_Frame.p.getProperty("datasource.datapath")+"\\TEMPTASE";
    
    private String rempath="https://www.tase.co.il/FileDistribution/PackTarget/Full";   
    
    private void FilesExistsLocalyFromTase(CDate start, CDate end,Map mapindexing) {
        //first check if exist on disk local
        //PACKAGE GET FROM TASE ALL IN ONE ZIP
        //FORMAT LINKTASE/DIST2/PUBLIC/PACK/YYYYMMDD.ZIP
        //EXAMPLE
        //https://www.tase.co.il/FileDistribution/PackTarget/Full20060112+0.zip
        CDate tempd=new CDate(start);
        String tfnd="";
        if(!(new File(localdatapath)).exists())
        {
                File file = new File(localdatapath);
                // Create directory if it does not exist
                file.mkdir();
        }       
        while(tempd.less(end))
        {
            Integer o=(Integer)mapindexing.get(tempd.toStringTaseFileDate());
            if(o!=null)
            {
             if(o.intValue()==-2)
            {
             tempd.roll(1);
             System.out.println("SKIP NOT EXIST ANY");
             continue;
            }else if(o.intValue()==1)
            {
             tempd.roll(1);
             System.out.println("SKIP ALREADY EXIST SOME");
             continue;
            }
            }
            else if(tempd.dayofweek()==Calendar.SATURDAY)
            {
             mapindexing.put(tempd.toStringTaseFileDate(),new Integer(-1));
             tempd.roll(1);
             System.out.println("SKIP SATURDAY");
             continue;
            }
            
            tfnd=tempd.toStringTaseFileDate()+".zip";
            System.out.println("Looking for "+tfnd);
            if (!(new File(localdatapath+tfnd)).exists()) 
            {
                System.out.println("Not Found localy trying download from TASE");
                 //File or directory does not exist so download it
                DownloadPackFromTASE(rempath,tfnd);
                if (!(new File(localdatapath+tfnd)).exists())
                {
                 mapindexing.put(tempd.toStringTaseFileDate(),new Integer(-2));
                }
                else
                {
                 mapindexing.put(tempd.toStringTaseFileDate(),new Integer(1));
                }
            }else
                {
                 mapindexing.put(tempd.toStringTaseFileDate(),new Integer(1));
                }
            tempd.roll(1);
        }
    }

    private String GenLocalFileNameN(CDate tempd, int i) {
       String tfnd="";
       if(i==-100)
       {
        tfnd=localdatapath+"\\"+tempd.toStringTaseFileDate();
       }
       else if(i==-1)
       {
        tfnd=localdatapath+"\\"+tempd.toStringTaseFileDate()+".zip";
       }
       else if(i==-803)//pure name in zip
       {
        tfnd="0803"+tempd.getDay()+"01.tas";
       }
       else if(i==803)//indexes 2+8+sym+name
       {
        tfnd=localdatapath+"\\"+tempd.toStringTaseFileDate()+"\\0803"+tempd.getDay()+"01.tas";
       }
       else if(i==542)//TACTS
       {
        tfnd=localdatapath+"\\"+tempd.toStringTaseFileDate()+"\\0542"+tempd.getDay()+"01.tas";
       }
       else if(i==-542)//TACTS pure name in zip
       {
        tfnd="0542"+tempd.getDay()+"01.tas";
       }
       return tfnd;
    }

    public void InitFileToRead(String filename) {
        url = filename;
        create();
        open();
    }
 public void CleanAfter() {
        System.out.println("Not yet implemented");
    }
    
}


//~ Formatted by Jindent --- http://www.jindent.com
