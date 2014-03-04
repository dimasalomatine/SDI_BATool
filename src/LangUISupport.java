//~--- JDK imports ------------------------------------------------------------

import Utils.LoggerNB;
import java.io.*;

import java.util.*;

//~--- classes ----------------------------------------------------------------

/*
 o read an ini file via an Applet, the Properties class is still useful. Just load the Properties this way :

p.load((new URL(getCodeBase(), "user.ini")).openStream());
An INI file (or Properties) in a JAR can be used this way :

URL url = ClassLoader.getSystemResource("/com/rgagnon/config/system.ini");
  if (url != null) props.load(url.openStream());
 **/
public class LangUISupport extends Properties {
    private /* static final */ String def_file = "eng.lang";

    //~--- constructors -------------------------------------------------------

    /**
     * Method W3D_AllProper
     *
     *
     */
    public LangUISupport() {
        try {
            load(new FileInputStream(def_file));
        } catch (IOException e) {
            if(LoggerNB.debuging){System.out.println("Errr38:" + e);}
        }
    }

    public LangUISupport(String anotherfile) {
        def_file = anotherfile;

        try {
            load(new FileInputStream(def_file));
        } catch (IOException e) {
            if(LoggerNB.debuging){System.out.println("Errr38:" + e);}
        }
    }

    //~--- methods ------------------------------------------------------------

    public void storechanges() {
        try {
            FileOutputStream cfn = new FileOutputStream(def_file);

            store(cfn, "");
        } catch (IOException e) {
            if(LoggerNB.debuging){System.out.println("Errr39:" + e);}
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
