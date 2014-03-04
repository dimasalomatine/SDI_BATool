package LocalUtils;

//~--- JDK imports ------------------------------------------------------------

import Utils.*;
import java.io.*;

//import java.lang.StringBuffer;
import java.util.*;
import java.util.zip.*;

//~--- classes ----------------------------------------------------------------

public class BasicZIP {
    public boolean CreateZIP(String zipfilename, String[] filenames) {

        // These are the files to include in the ZIP file
        // String[] filenames = new String[]{"filename1", "filename2"};
        boolean status = true;

        // Create a buffer for reading the files
        byte[] buf = new byte[1024];
        int    len;

        try {

            // Create the ZIP file
            ZipOutputStream out =
                new ZipOutputStream(new FileOutputStream(zipfilename));

            // Compress the files
            for (int i = 0; i < filenames.length; i++) {
                FileInputStream in = new FileInputStream(filenames[i]);

                // Add ZIP entry to output stream.
                out.putNextEntry(new ZipEntry(filenames[i]));

                // Transfer bytes from the file to the ZIP file
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }

                // Complete the entry
                out.closeEntry();
                in.close();
            }

            // Complete the ZIP file
            out.close();
        } catch (IOException e) {
            if(LoggerNB.debuging){System.out.println("ZIP ERR" + e);}
            status = false;
        }

        return status;
    }

   public  boolean GetFileAs(String zipfilename, String filename, String asfilename) {
        boolean status = true;

        try {

            // Open the ZIP file
            ZipInputStream in =
                new ZipInputStream(new FileInputStream(zipfilename));

            // Get the first entry
            ZipEntry entry;
            boolean  found = false;

            do {
                entry = in.getNextEntry();

                if (filename.equals(entry.getName())) {
                    found = true;
                }
            } while ((found == false) && (in.available() == 1));

            if (found) {

                // Open the output file
                OutputStream out = new FileOutputStream(asfilename);

                // Transfer bytes from the ZIP file to the output file
                byte[] buf = new byte[1024];
                int    len;

                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }

                // Close the streams
                out.close();
            }

            in.close();
        } catch (IOException e) {
            if(LoggerNB.debuging){System.out.println("ZIP ERR" + e);}
            status = false;
        }

        return status;
    }

   public boolean GetFileAs(String zipfilename, String filename, StringBuffer out) {
        boolean status = true;

        try {

            // Open the ZIP file
            ZipInputStream in =
                new ZipInputStream(new FileInputStream(zipfilename));

            // Get the first entry
            ZipEntry entry;
            boolean  found = false;

            do {
                entry = in.getNextEntry();

                if (filename.equals(entry.getName())) {
                    found = true;
                }
            } while ((found == false) && (in.available() == 1));

            if (found) {

                // Transfer bytes from the ZIP file to the output file
                byte[] buf = new byte[1024];
                int    len;

                while ((len = in.read(buf)) > 0) {
                    out.append(buf);
                }
            }

            // Close the streams
            in.close();
        } catch (IOException e) {
            if(LoggerNB.debuging){System.out.println("ZIP ERR" + e);}
            status = false;
        }

        return status;
    }

   public java.util.List listContent(String zipfilename) {
        java.util.List list = new ArrayList();

        try {

            // Open the ZIP file
            ZipFile zf = new ZipFile(zipfilename);

            // Enumerate each entry
            Enumeration entries = zf.entries();

            while (entries.hasMoreElements()) {

                // Get the entry name
                String zipEntryName =
                    ((ZipEntry) entries.nextElement()).getName();

                list.add(zipEntryName);
                LoggerNB.getLogger().log("opening ZIPENTRY: " + zipEntryName,
                                          LoggerNB.INFORMATIVE);
            }
        } catch (IOException e) {
            if(LoggerNB.debuging){System.out.println("ZIP ERR" + e);}
        }

        return list;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
