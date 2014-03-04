/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package LocalUtils;

import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class common {

	public static boolean cmptxt(String s1, String s2) {
		if (s1==null) s1 = "";
		if (s2==null) s2 = "";
		return s1!=null&&s1.toLowerCase().equals(s2.toLowerCase());
	}

	public static String changeExtension(String fn,String ext) {
		int p = fn.lastIndexOf(".");
		if (p==-1) return fn + "." + ext; else {
			return fn.substring(0,p+1) + ext;
		}
	}

	public static String clearExtension(String fn) {
		int p = fn.lastIndexOf(".");
		if (p==-1) return fn; else {
			return fn.substring(0,p);
		}
	}

	public static String extractFileExt(String fn) {
		int p = fn.lastIndexOf(".");
		if (p==-1) return ""; else
			return fn.substring(p+1);
	}

	public static String extractFileName(String fn) {
		int p = fn.lastIndexOf("\\");
		if (p==-1) return fn; else
			return fn.substring(p+1);
	}

	public static String extractFilePath(String fn) {
		int p = fn.lastIndexOf("\\");
		if (p==-1) return fn; else
			return fn.substring(0,p+1);
	}

	public static boolean fileExists(String fn) {
		File fl = new File(fn);
		return fl.exists();
	}

	public static void copyFile(String fromFileName, String toFileName)
    throws IOException {
		File fromFile = new File(fromFileName);
		File toFile = new File(toFileName);

		if (!fromFile.exists())
			throw new IOException("FileCopy: " + "no such source file: "
					+ fromFileName);
		if (!fromFile.isFile())
			throw new IOException("FileCopy: " + "can't copy directory: "
					+ fromFileName);
		if (!fromFile.canRead())
			throw new IOException("FileCopy: " + "source file is unreadable: "
					+ fromFileName);

		if (toFile.isDirectory())
			toFile = new File(toFile, fromFile.getName());

		if (toFile.exists()) {
			if (!toFile.canWrite())
				throw new IOException("FileCopy: "
						+ "destination file is unwriteable: " + toFileName);
		} else {
			String parent = toFile.getParent();
			if (parent == null)
				parent = System.getProperty("user.dir");
			File dir = new File(parent);
			if (!dir.exists())
				throw new IOException("FileCopy: "
						+ "destination directory doesn't exist: " + parent);
			if (dir.isFile())
				throw new IOException("FileCopy: "
						+ "destination is not a directory: " + parent);
			if (!dir.canWrite())
				throw new IOException("FileCopy: "
						+ "destination directory is unwriteable: " + parent);
		}

		FileInputStream from = null;
		FileOutputStream to = null;
		try {
			from = new FileInputStream(fromFile);
			to = new FileOutputStream(toFile);
			byte[] buffer = new byte[4096];
			int bytesRead;

			while ((bytesRead = from.read(buffer)) != -1)
				to.write(buffer, 0, bytesRead); // write
		} finally {
			if (from != null)
				try {
					from.close();
				} catch (IOException e) {
					common.DebugingLine("common.copyFile <EXCEPTION> message="+e.toString());
				}
				if (to != null)
					try {
						to.close();
					} catch (IOException e) {
						common.DebugingLine("common.copyFile <EXCEPTION> message="+e.toString());
					}
		}
	}


	public static void DebugingLine(String s) {
		setDebugingLine(s,"messages");
	}

	public static void Logged(String s) {
		setDebugingLine(s,"events");
	}

	public static void setDebugingLine(String s, String subDir)
	{
		try {
			Calendar cl = new GregorianCalendar();
			Date d = new Date();
			cl.setTimeInMillis(d.getTime());

			String dt = "";
			String v;
			dt = String.valueOf(cl.get(GregorianCalendar.YEAR)).substring(2);
			v = String.valueOf(cl.get(GregorianCalendar.MONTH)+1);
			if (v.length()==1) v = "0" + v;
			dt += v;
			v = String.valueOf(cl.get(GregorianCalendar.DAY_OF_MONTH));
			if (v.length()==1) v = "0" + v;
			dt += v;
			v = String.valueOf(cl.get(GregorianCalendar.HOUR_OF_DAY));
			if (v.length()==1) v = "0" + v;
			dt += " "+v;
			v = String.valueOf(cl.get(GregorianCalendar.MINUTE));
			if (v.length()==1) v = "0" + v;
			dt += v;
			v = String.valueOf(cl.get(GregorianCalendar.SECOND));
			if (v.length()==1) v = "0" + v;
			dt += v;
			v = String.valueOf(cl.get(GregorianCalendar.MILLISECOND));
			while (v.length()<3) v = "0" + v;
			dt += " "+v;

			s = s.replaceAll("\r\n"," ");
			s = s.replaceAll("\r"," ");
			s = s.replaceAll("\n"," ");
			s = s.replaceAll("\t"," ");
			s = "xml2gp--" + dt + ">" + s;

			String fn = String.valueOf(cl.get(GregorianCalendar.YEAR));
			v = String.valueOf(cl.get(GregorianCalendar.MONTH)+1);
			if (v.length()==1) v = "0" + v;
			fn += v;

			String appworkingdir=System.getProperty("user.dir");
			String fp = appworkingdir+"\\LogFiles\\"+subDir+"\\current.log";

			File f = new File(fp);
			if (f.exists()) {
				if (f.length()>1000000) {
					String fp1 = appworkingdir+"\\LogFiles\\"+subDir+"\\"+fn+" "+dt.substring(4,11).replaceAll(" ","")+".log";
					File f1 = new File(fp1);
					f.renameTo(f1);
				}
			}

			FileWriter fr = new FileWriter(fp,true);
			for (int i=0;i<s.length();i++) {
				fr.append(s.charAt(i));
			}
			fr.append('\r');
			fr.append('\n');
			fr.close();
		} catch (IOException e) {}
	}

	public static String QuotedStr(String s) {
		if (s==null) s = "";
		String r = "";
		for (int i = 0;i<s.length();i++) {
			r = r + s.charAt(i);
			if (s.charAt(i)=='\'') {
				r = r + "'";
			}
		}
		return "'" + r + "'";
	}

	public static String ExtractQuotedStr(String s) {
		if (common.cmptxt(s,"")) return "";
		if (s.charAt(0)!='\'') return s; else {
			s = s.substring(1,s.length()-1);
			return s.replaceAll("''","'");
		}
	}

	public static String GetRecId() {
		Calendar cl = new GregorianCalendar();
		Date d = new Date();
		cl.setTimeInMillis(d.getTime());
		return
			String.valueOf(cl.get(GregorianCalendar.DAY_OF_MONTH))+
			String.valueOf(cl.get(GregorianCalendar.MONTH))+
			String.valueOf(cl.get(GregorianCalendar.YEAR))+
			String.valueOf(cl.get(GregorianCalendar.SECOND))+
			String.valueOf(cl.get(GregorianCalendar.MINUTE))+
			String.valueOf(cl.get(GregorianCalendar.HOUR_OF_DAY));
	}

}
