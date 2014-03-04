//~--- JDK imports ------------------------------------------------------------

import Utils.LoggerNB;
import java.io.*;

import java.util.Properties;

//~--- classes ----------------------------------------------------------------

public class SDI_Properties extends Properties {
    String propfilename = "c:\\\\SDI_BATool\\srv.prop";    // default file

    //~--- constructors -------------------------------------------------------

    /**
     * Method SDI_Properties
     *
     *
     */
    public SDI_Properties() {

        // TODO: Add your code here
    }

    public SDI_Properties(String fn) {
        propfilename = fn;
    }

    //~--- methods ------------------------------------------------------------

    void initpropertiesfile(String filename) {

        // p.load(new FileInputStream(propfilename));
        System.out.println("Reading settings");

        if (filename != null) {
            propfilename = filename;
        }

        try {
        load(new FileInputStream(propfilename));
    } catch (IOException e) {
        if(LoggerNB.debuging){System.out.println("Errr39:" + e);}
    }

        //jarr version file read
        /*InputStream is = ClassLoader.getSystemResourceAsStream(propfilename);
        try {
            load(is);
        } catch (IOException e) {
            if(LoggerNB.debuging){System.out.println("Errr39:" + e);}
        }*/
    }

    void savepropertiesfile() {
        System.out.println("Saving settings");

        try {
            FileOutputStream cfn = new FileOutputStream(propfilename);

            store(cfn, "");
        } catch (IOException e) {
            if(LoggerNB.debuging){System.out.println("Errr39:" + e);}
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
