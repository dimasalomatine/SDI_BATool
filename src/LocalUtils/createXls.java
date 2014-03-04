package LocalUtils;

import DBCONNECT.DataBaseConnector;
import jp.ne.so_net.ga2.no_ji.jcom.*;
import java.io.*;
import java.util.*;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;


public class createXls {

	public static int adOpenForwardOnly = 0;
	public static int adLockReadOnly = 1;
	public static int adLockUnspecified = -1;
	public static int adCmdFile = 0x00000100;

	public static int xlWorkbookNormal = 0xFFFFEFD1;
	public static int xlNone = -0x102E;
	public static int xlDown = -0x1019;
	public static int xlTop = -0x1040;
	public static int xlRight = -0x1038;
	public static int xlLeft = -0x1023;
	public static int xlLTR = -0x138B;
	public static int xlRTL = -0x138C;


	public static int bkColor = 0xFFCCCC;
	public static int clSilver = 0xC0C0C0;

	private static Object[] DataBorder = {null,null,null,null,false};
	private static Object[] HeaderInterior = {null,null,null,null,null,false};;

	private static IDispatch xlApp;
	private static IDispatch Data;
	private static IDispatch Sum;
	private static IDispatch Count;
	private static DataBaseConnector dbs;

	private static String[] columnLabels;
	private static String fieldSheet;
	private static String fieldSummary;
	private static int fieldSheetIndex;
	private static boolean showAllNumericSum;

	private static Vector<IDispatch> TabSheets = new Vector<IDispatch>();
	private static IDispatch qMain;
	private static Vector<IDispatch> ExcelSheets = new Vector<IDispatch>();
	private static ResultSet rsMain;

	private static Vector<Double> ColumnsWidth = new Vector<Double>();
	private static boolean showFooter = false;

    public void initdbs(DataBaseConnector tdbs)
    {
    this.dbs=tdbs;
    }
	private static Object doMethod(IDispatch v,String desc,String nm,Object[] o) {
		try {
			return v.method(nm,o);
		} catch(Exception e) {
			common.DebugingLine("createXls.doMethod["+desc+"."+nm+"] <EXCEPTION> message="+e.toString());
		}
		return null;
	}

	private static Object doGet(IDispatch v,String desc,String nm,Object[] o) {
		try {
			if (o==null)
				return v.get(nm); else
				return v.get(nm,o);
		} catch(Exception e) {
			common.DebugingLine("createXls.doGet["+desc+"."+nm+"] <EXCEPTION> message="+e.toString());
		}
		return null;
	}

	private static void doPut(IDispatch v,String desc,String nm,Object val) {
		try {
			v.put(nm,val);
		} catch(Exception e) {
			common.DebugingLine("createXls.doPut["+desc+"."+nm+"] <EXCEPTION> message="+e.toString());
		}
	}
	private static String getSheetName(String s) {
		String res = "";
		for (int i=0;i<s.length();i++) {
			char c = s.charAt(i);
			if ((c>='a'&&c<='z')||(c>='A'&&c<='Z')||(c>='א'&&c<='ת')||(c=='-'))
				res += c; else
					res += ' ';
		}
		return res;
	}

	private static String getFileName(String s) {
		String res = "";
		for (int i=0;i<s.length();i++) {
			char c = s.charAt(i);
			if ((c>='0'&&c<='9')||(c>='a'&&c<='z')||(c>='A'&&c<='Z')||(c>='א'&&c<='ת')||(c=='-')||(c=='_')||(c=='.')||(c=='\''))
				res += c; else
					res += ' ';
		}
		return res;
	}

	private static String getSheetNameStr(String s) {
		int p = s.indexOf("!");
		if (p==-1) return "";
		return s.substring(1,p);
	}

	private static Object getFieldValue(String field,String accountno) {
		if (common.cmptxt(accountno,"")) return null;
		int p = field.indexOf(".");
		if (p==-1) return null;
		String table = field.substring(0,p);
		field = field.substring(p+1);
		String expr =
			"select " + field + " from " + table +
			" where ACCOUNTNO = " + common.QuotedStr(accountno);
		try {

			ResultSet rs = dbs.execSQLnoClose(expr);
			if (rs!=null&&rs.next()) {
				return rs.getObject(field);
			}
		} catch(Exception e) {
			common.DebugingLine("createXls.getFieldValue <EXCEPTION> message="+e.toString());
		}

		return null;
	}

	private static String getColumnNameByIndex(int index) {
		String[] cn = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		index -= 1;
		int quotient = index/26;
		if (quotient>0) return getColumnNameByIndex(quotient) + cn[(index%26)]; else
			return cn[index%26];
	}

	private static void setRangeValue(IDispatch rtr, int col, int row, Object val) {
		try {
			String s = getColumnNameByIndex(col)+Integer.toString(row);
			IDispatch r = (IDispatch)doGet(rtr,"rtr","Range", new Object[]{s,s});
			doPut(r,"r","Value",val);
		} catch (Exception e) {
			common.DebugingLine("createXls.setRangeValue <EXCEPTION> message="+e.toString());
		}
	}

	private static void setFieldValue(IDispatch rtr, Object val) {
		try {
			setRangeValue(rtr,1,1,val);
		} catch (Exception e) {
			common.DebugingLine("createXls.setFieldValue <EXCEPTION> message="+e.toString());
		}
	}

	public static void createADOXMLSchema(String[] schema, String FileName) {
		String res =
			"<xml " +
			"xmlns:s='uuid:BDC6E3F0-6DA3-11d1-A2A3-00AA00C14882' " +
			"xmlns:dt='uuid:C2F41010-65B3-11d1-A29F-00AA00C14882' " +
			"xmlns:rs='urn:schemas-microsoft-com:rowset' " +
			"xmlns:z='#RowsetSchema' " +
			">" +
			"<s:Schema id='RowsetSchema'>" +
			"<s:ElementType name='row' content='eltOnly' rs:updatable='true'>";

		for (int i=0;i<schema.length;i++) {
			if (!common.cmptxt(schema[i],"")) {
				String[] attr = schema[i].split("\t");
				if (common.cmptxt(attr[1],"datetime")) {
					res +=
						"<s:AttributeType name='"+attr[0]+"' rs:number='"+Integer.toString(i+1)+"' rs:nullable='true' rs:writeunknown='true'> " +
						"<s:datatype dt:type='dateTime' rs:dbtype='timestamp' dt:maxLength='16' rs:scale='3' rs:precision='23' rs:fixedlength='true'/> " +
						"</s:AttributeType>";
				} else
				if (common.cmptxt(attr[1],"smallint")) {
					res +=
						"<s:AttributeType name='"+attr[0]+"' rs:number='"+Integer.toString(i+1)+"' rs:nullable='true' rs:writeunknown='true'> " +
						"<s:datatype dt:type='i2' dt:maxLength='2' rs:precision='5' rs:fixedlength='true'/> " +
						"</s:AttributeType>";
				} else
				if (common.cmptxt(attr[1],"float")) {
					res +=
						"<s:AttributeType name='"+attr[0]+"' rs:number='"+Integer.toString(i+1)+"' rs:nullable='true' rs:writeunknown='true'> " +
						"<s:datatype dt:type='float' dt:maxLength='8' rs:precision='15' rs:fixedlength='true'/> " +
						"</s:AttributeType>";
				} else
				if (common.cmptxt(attr[1],"int")) {
					res +=
						"<s:AttributeType name='"+attr[0]+"' rs:number='"+Integer.toString(i+1)+"' rs:nullable='true' rs:writeunknown='true'> " +
						"<s:datatype dt:type='int' dt:maxLength='4' rs:precision='10' rs:fixedlength='true'/> " +
						"</s:AttributeType>";
				} else
				if (common.cmptxt(attr[1],"bit")) {
					res +=
						"<s:AttributeType name='"+attr[0]+"' rs:number='"+Integer.toString(i+1)+"' rs:nullable='true' rs:writeunknown='true'> " +
						"<s:datatype dt:type='boolean' dt:maxLength='2' rs:fixedlength='true'/> " +
						"</s:AttributeType>";
				} else
				if (common.cmptxt(attr[1],"text")) {
					res +=
						"<s:AttributeType name='"+attr[0]+"' rs:number='"+Integer.toString(i+1)+"' rs:nullable='true' rs:writeunknown='true'> " +
						"<s:datatype dt:type='string' rs:dbtype='str' dt:maxLength='2147483647' rs:long='true'/> " +
						"</s:AttributeType>";
				} else {
					res +=
						"<s:AttributeType name='"+attr[0]+"' rs:number='"+Integer.toString(i+1)+"' rs:writeunknown='true'> " +
						"<s:datatype dt:type='string' rs:dbtype='str' dt:maxLength='"+attr[2]+"' rs:maybenull='true'/> " +
						"</s:AttributeType>";
				}
			}
		}

		res +=
			"<s:extends type='rs:rowbase'/>" +
			"</s:ElementType>" +
			"</s:Schema>" +
			"</xml>";

		File fl = new File(FileName);
		if (fl.exists()) fl.delete();
		try {
			FileWriter fr = new FileWriter(FileName);
			for (int i=0;i<res.length();i++) {
				fr.append(res.charAt(i));
			}
			fr.close();
		} catch (Exception e) {
			common.DebugingLine("createXls.createADOXMLSchema <EXCEPTION> message="+e.toString());
		}
	}

	private static void addNewRecord(ResultSet resultset, IDispatch recordset) {
		try {
			doMethod(recordset,"recordset","AddNew",null);
			ResultSetMetaData rsmd = resultset.getMetaData();
			for (int i=0;i<rsmd.getColumnCount();i++) {
				String fn = rsmd.getColumnName(i+1);
				IDispatch fld = null;
				try {
					fld = (IDispatch)recordset.get("Fields",new Object[]{fn});
				} catch(Exception e) {}
				if (fld!=null) {
					Object val = null;
					String ct = rsmd.getColumnTypeName(i+1);
					if (common.cmptxt(ct,"text")||common.cmptxt(ct,"varchar")||common.cmptxt(ct,"nvarchar")) {
						byte[] b = resultset.getBytes(fn);
						if (b!=null)
							val = new String(b); else
								val = resultset.getString(fn);
					} else
						val = resultset.getObject(fn);
					if (val==null) val = "";
					doPut(fld,"fld","Value",val);
				}
			}
			doMethod(recordset,"recordset","Update",null);
		} catch (Exception e) {
			common.DebugingLine("createXls.addNewRecord <EXCEPTION> message="+e.toString());
		}
	}

	private static void addRecord(int index, ResultSet rs) {
		IDispatch q = ExcelSheets.get(index);
		addNewRecord(rs,q);
	}

	private static void setBorders(IDispatch dest) {
		if ((Boolean)DataBorder[4]&&dest!=null) {
			try {
				IDispatch borders = (IDispatch)doGet(dest,"dest","Borders",null);
				doPut(borders,"borders","Color",DataBorder[0]);
				doPut(borders,"borders","ColorIndex",DataBorder[1]);
				doPut(borders,"borders","LineStyle",DataBorder[2]);
				doPut(borders,"borders","Weight",DataBorder[3]);
			} catch (Exception e) {
				common.DebugingLine("createXls.setBorders <EXCEPTION> message="+e.toString());
			}
		}
	}

	private static void setInterior(IDispatch header) {
		if ((Boolean)HeaderInterior[5]&&header!=null) {
			try {
				String s = Integer.toHexString((Integer)HeaderInterior[1]);
				s = s+ "";
				IDispatch interior = (IDispatch)doGet(header,"header","Interior",null);
				doPut(interior,"interior","Color",HeaderInterior[0]);
				doPut(interior,"interior","ColorIndex",HeaderInterior[1]);
				doPut(interior,"interior","Pattern",HeaderInterior[2]);
				doPut(interior,"interior","PatternColor",HeaderInterior[3]);
				doPut(interior,"interior","PatternColorIndex",HeaderInterior[4]);
			} catch (Exception e) {
				common.DebugingLine("createXls.setInterior <EXCEPTION> message="+e.toString());
			}
		}
	}

	private static void setFormula(IDispatch nm, IDispatch r) {
		try {
			if (r!=null) {
				IDispatch rtr = (IDispatch)doGet(nm,"nm","RefersToRange",null);
				int cnt = (Integer)doGet(r,"r","Count",null);
				int x = (Integer)doGet(rtr,"rtr","Row",null) - (Integer)doGet(r,"r","Row",null) - cnt;
				int y = (Integer)doGet(rtr,"rtr","Column",null) - (Integer)doGet(r,"r","Column",null);
				String s ="=SUM(R["+Integer.toString(-cnt+1-x)+"]C["+Integer.toString(-y)+"]:R["+Integer.toString(-2-x)+"]C["+Integer.toString(-y)+"])";
				doPut(rtr,"rtr","FormulaR1C1",s);
			}
		} catch(Exception e) {
			common.DebugingLine("createXls.setFormula <EXCEPTION> message="+e.toString());
		}
	}

	private static void setCellValue(IDispatch nm, String s) {
		IDispatch rtr = null;
		try {
			rtr = (IDispatch)doGet(nm,"nm","RefersToRange",null);
			doPut(rtr,"rtr","Value",s);
		} catch(Exception ex) {
			try {
				doPut(rtr,"rtr","Value",getSheetName(s));
			} catch(Exception e) {
				common.DebugingLine("createXls.setFormula <EXCEPTION> message="+e.toString());
			}
		}
	}

	private static void setFooter(int TabIndex, IDispatch q, IDispatch Grid, String[] DisplayFormats, boolean rtl) {
		try {
			try {
				IDispatch xlSheet = TabSheets.get(TabIndex);
				IDispatch xlCells = (IDispatch)doGet(xlSheet,"xlSheet","Cells",null);

				int f = 1;
				if (!showFooter) f = 0;
				int RecordCount = (Integer)doGet(q,"q","RecordCount",null);
				IDispatch Columns = (IDispatch)doGet(Grid,"Grid","Columns",null);
				int cnt = (Integer)doGet(Columns,"Columns","Count",null);
				int gcol = (Integer)doGet(Grid,"Grid","Column",null);
				int grow = (Integer)doGet(Grid,"Grid","Row",null);
				int row = grow+RecordCount+f;
				IDispatch item1 = (IDispatch)doGet(xlCells,"xlCells","Item",new Object[]{grow,gcol});
				IDispatch item2 = (IDispatch)doGet(xlCells,"xlCells","Item",new Object[]{row,gcol+cnt-1});

				Grid = (IDispatch)doGet(xlCells,"xlCells","Range",new Object[]{item1,item2});
				gcol = (Integer)doGet(Grid,"Grid","Column",null);
				grow = (Integer)doGet(Grid,"Grid","Row",null);
				row = grow+RecordCount+f;

				int k = gcol;
				ResultSetMetaData rsmd = rsMain.getMetaData();
				for (int i=0;i<rsmd.getColumnCount();i++) {
					if (!common.cmptxt(rsmd.getColumnName(i+1),fieldSheet)||TabIndex==0) {
						String colType = rsmd.getColumnTypeName(i+1);
						IDispatch column = null;
						try {
							item1 = (IDispatch)doGet(xlCells,"xlCells","Item",new Object[]{grow+1,k});
							item2 = (IDispatch)doGet(xlCells,"xlCells","Item",new Object[]{row-1,k});
							column = (IDispatch)doGet(xlCells,"xlCells","Range",new Object[]{item1,item2});
						} catch(Exception e) {
							common.DebugingLine("createXls.setFooter <EXCEPTION> message="+e.toString());
						}

						String displayFormat = "";
						if (DisplayFormats!=null){
							try {
								if (i<DisplayFormats.length)
									displayFormat = DisplayFormats[i];
							} catch(Exception e) {
								common.DebugingLine("createXls.setFooter <EXCEPTION> message="+e.toString());
							}
						}
						if (common.cmptxt(colType,"datetime")&&common.cmptxt(displayFormat,""))
							displayFormat = "dd/mm/yyyy";

						if (!common.cmptxt(displayFormat,"")&&column!=null) {
							doPut(column,"column","NumberFormat",displayFormat);
						}

						if (
								common.cmptxt(colType,"smallint")||
								common.cmptxt(colType,"float")||
								common.cmptxt(colType,"int")) {
							try {
								if (common.cmptxt(displayFormat,"")&&column!=null) {
									doPut(column,"column","NumberFormat","0.00");
								}

								if (common.cmptxt(rsmd.getColumnName(i+1),fieldSummary)&&RecordCount>0) {
									if (Sum==null||TabIndex>0) {
										if (showFooter) {
											item1 = (IDispatch)doGet(xlCells,"xlCells","Item",new Object[]{row,k});
											item2 = (IDispatch)doGet(xlCells,"xlCells","Item",new Object[]{row,k});
											IDispatch sumrange = (IDispatch)doGet(xlCells,"xlCells","Range",new Object[]{item1,item2});
											String formula = "=SUM(R[-"+Integer.toString(RecordCount)+"]C:R[-1]C)";
											doPut(sumrange,"sumrange","FormulaR1C1",formula);
										}
									} else setFormula(Sum,column);

									if (showAllNumericSum&&showFooter) {
										item1 = (IDispatch)doGet(xlCells,"xlCells","Item",new Object[]{row,k});
										item2 = (IDispatch)doGet(xlCells,"xlCells","Item",new Object[]{row,k});
										IDispatch sumrange = (IDispatch)doGet(xlCells,"xlCells","Range",new Object[]{item1,item2});
										String formula = "=SUM(R[-"+Integer.toString(RecordCount)+"]C:R[-1]C)";
										doPut(sumrange,"sumrange","FormulaR1C1",formula);
									}
								}
							} catch(Exception e) {
								common.DebugingLine("createXls.setFooter <EXCEPTION> message="+e.toString());
							}
						}

						if (column!=null) {
							if (common.cmptxt(colType,"text")) doPut(column,"column","WrapText",true);
							if (i>-1&&i<ColumnsWidth.size()&&TabIndex==0)
								doPut(column,"column","ColumnWidth",ColumnsWidth.get(i)); else {
									IDispatch EntireColumn = (IDispatch)doGet(column,"column","EntireColumn",null);
									doMethod(EntireColumn,"EntireColumn","AutoFit",null);
								}
						}
						k += 1;
					}

					if (Count!=null&&TabIndex==0) setCellValue(Count,Integer.toString(RecordCount));

					IDispatch footer = null;
					if (showFooter) {
						cnt = (Integer)doGet(Columns,"Columns","Count",null);
						item1 = (IDispatch)doGet(xlCells,"xlCells","Item",new Object[]{row,gcol});
						item2 = (IDispatch)doGet(xlCells,"xlCells","Item",new Object[]{row,gcol+cnt-1});
						footer = (IDispatch)doGet(xlCells,"xlCells","Range",new Object[]{item1,item2});
						IDispatch interior = (IDispatch)doGet(footer,"footer","Interior",null);
						doPut(interior,"interior","Color",bkColor);
						IDispatch font = (IDispatch)doGet(footer,"footer","Font",null);
						doPut(font,"font","Bold",true);
					}

					IDispatch borders = (IDispatch)doGet(Grid,"Grid","Borders",null);
					doPut(borders,"borders","Color",clSilver);
					doPut(Grid,"Grid","VerticalAlignment",xlTop);

					if (TabIndex==0) {
						setBorders(Grid);
						if (footer!=null) setInterior(footer);
					}

					if (rtl) {
						doPut(Grid,"Grid","HorizontalAlignment",xlRight);
						doPut(Grid,"Grid","ReadingOrder",xlRTL);
					} else {
						doPut(Grid,"Grid","HorizontalAlignment",xlLeft);
						doPut(Grid,"Grid","ReadingOrder",xlLTR);
					}

					doMethod(xlSheet,"xlSheet","Select",null);
					IDispatch activeWindow = (IDispatch)doGet(xlApp,"xlApp","ActiveWindow",null);
					doPut(activeWindow,"activeWindow","DisplayGridLines",false);
				}


			} catch (Exception e) {
				common.DebugingLine("createXls.setFooter <EXCEPTION> message="+e.toString());
			}
		} finally {
		}
	}

	private static void setHeader(int TabIndex, String[] DisplayFormats, boolean FromTemplate, boolean rtl) {
		try {
			IDispatch q = null;
			if (TabIndex==0) q = qMain; else q = ExcelSheets.get(TabIndex-1);
			IDispatch xlSheet = TabSheets.get(TabIndex);
			IDispatch r = null;
			try {
				IDispatch xlCells = (IDispatch)doGet(xlSheet,"xlSheet","Cells",null);
				if (Data!=null&&TabIndex==0) r = (IDispatch)doGet(Data,"Data","RefersToRange",null); else {
					r = (IDispatch)doGet(xlCells,"xlCells","Range",new Object[]{"A1","A1"});
				}

				int col = (Integer)doGet(r,"r","Column",null);
				int row = (Integer)doGet(r,"r","Row",null);

				int RecordCount = (Integer)doGet(q,"q","RecordCount",null);

				if (TabIndex==0&&FromTemplate) {
					IDispatch fields = (IDispatch)doGet(q,"q","Fields",null);
					int count = (Integer)doGet(fields,"fields","Count",null);
					for (int i=0;i<count;i++) {
						IDispatch item = (IDispatch)doGet(xlCells,"xlCells","Item",new Object[]{1,col+i});
						IDispatch column = (IDispatch)doGet(xlCells,"xlCells","Range",new Object[]{item,item});
						double width = (Double)doGet(column,"column","ColumnWidth",null);
						ColumnsWidth.add(width);
					}
				}

				String c1 = "A"+Integer.toString(row+1);
				String c2 = "Z"+Integer.toString(RecordCount+row+1);
				IDispatch InsertRange = (IDispatch)doGet(xlCells,"xlCells","Range",new Object[]{c1,c2});
				doMethod(InsertRange,"InsertRange","Insert",new Object[]{xlDown});

				IDispatch QueryTables = (IDispatch)doGet(xlSheet,"xlSheet","QueryTables",null);
				IDispatch tbl = (IDispatch)doMethod(QueryTables,"QueryTables","Add",new Object[]{q,r});
				doMethod(tbl,"tbl","Refresh",new Object[]{false});
				IDispatch Grid = (IDispatch)doGet(tbl,"tbl","ResultRange",null);
				doMethod(tbl,"tbl","Delete",null);

				if (rtl) doPut(xlSheet,"xlSheet","DisplayRightToLeft", true); else
					doPut(xlSheet,"xlSheet","DisplayRightToLeft",false);

				int k = col;
				if (columnLabels!=null) {
					try {
						for (int i=0;i<columnLabels.length;i++) {
							if (TabIndex==0||i!=fieldSheetIndex) {
								try {
									setRangeValue(xlCells,k,row,columnLabels[i]);
								} catch (Exception e) {
									setRangeValue(xlCells,k,row,getSheetName(columnLabels[i]));
								}
								k += 1;
							}
						}
					} catch (Exception e) {
						common.DebugingLine("createXls.setHeader <EXCEPTION> message="+e.toString());
					}
				}

				IDispatch item1 = (IDispatch)doGet(xlCells,"xlCells","Item",new Object[]{row,col});
				IDispatch item2 = (IDispatch)doGet(xlCells,"xlCells","Item",new Object[]{row,k-1});
				IDispatch header = (IDispatch)doGet(xlCells,"xlCells","Range",new Object[]{item1,item2});
				IDispatch interior = (IDispatch)doGet(header,"header","Interior",null);
				doPut(interior,"interior","Color",bkColor);

				if (TabIndex==0) setInterior(header);
				setFooter(TabIndex,q,Grid,DisplayFormats,rtl);

			} catch(Exception e) {
				common.DebugingLine("createXls.setHeader <EXCEPTION> message="+e.toString());
			}
		} finally {
		}
	}

	public static String ExportDataSetToExcel(
			String FileName,
			String FieldSheet,
			String FieldSummary,
			ResultSet rs,
			String[] ColumnLabel,
			boolean rtl,
			String Password,
			String Template,
			String[] DisplayFormats,
			boolean ShowFooter,
			boolean ShowAllNumericSum,
			String AccountNo,
			DataBaseConnector db) {

		common.DebugingLine("createXls.ExportDataSetToExcel <ENTER> ");

		TabSheets.clear();
		ExcelSheets.clear();
		ColumnsWidth.clear();

		dbs = db;
		columnLabels = ColumnLabel;
		showFooter = ShowFooter;
		fieldSheet = FieldSheet;
		fieldSummary = FieldSummary;
		showAllNumericSum = ShowAllNumericSum;
		rsMain = rs;

		DataBorder[0] = null;
		DataBorder[1] = null;
		DataBorder[2] = null;
		DataBorder[3] = false;

		HeaderInterior[0] = null;
		HeaderInterior[1] = null;
		HeaderInterior[2] = null;
		HeaderInterior[3] = null;
		HeaderInterior[4] = null;
		HeaderInterior[5] = false;

		xlApp = null;
		Data = null;
		Sum = null;
		Count = null;

		fieldSheetIndex = -1;
		qMain = null;

		IDispatch xlBooks = null;
		IDispatch xlBook = null;
		IDispatch xlSheets = null;
		IDispatch xlActiveSheet = null;
		boolean FromTemplate = false;

		try {
			ReleaseManager rm = new ReleaseManager();
			try {
				xlApp = new IDispatch(rm,"Excel.Application");
				xlBooks = (IDispatch)doGet(xlApp,"xlApp","Workbooks",null);

				String AName = getFileName(common.clearExtension(common.extractFileName(FileName)).trim());
				String s = common.extractFilePath(FileName) + AName;
				if (common.fileExists(Template)) {
					try {
						FileName = s + "." + common.extractFileExt(Template);
						common.DebugingLine("createXls.ExportDataSetToExcel Copy Template to "+FileName);
						common.copyFile(Template,FileName);
						if (common.fileExists(FileName)) {
							doMethod(xlBooks,"xlBooks","Open",new Object[]{FileName});
							xlBook = (IDispatch)doGet(xlApp,"xlApp","ActiveWorkbook",null);
							FromTemplate = true;
							common.DebugingLine("createXls.ExportDataSetToExcel From Template");
						} else {
							common.DebugingLine("createXls.ExportDataSetToExcel <EXCEPTION> message=Copy from Templite is Failed!");
							common.DebugingLine("createXls.ExportDataSetToExcel Without Template");
							FileName = s + ".xls";
							xlBook = (IDispatch)doMethod(xlBooks,"xlBooks","Add",null);
							xlSheets = (IDispatch)doGet(xlApp,"xlApp","Sheets",null);
							int cnt = (Integer)doGet(xlSheets,"xlSheets","Count",null);
							for (int i=2;i<cnt+1;i++) {
								IDispatch xlSheet = (IDispatch)doGet(xlSheets,"xlSheets","Item",new Object[]{i});
								doPut(xlSheet,"xlSheet","Visible",false);
							}
						}
					} catch (Exception e) {
						common.DebugingLine("createXls.ExportDataSetToExcel Without Template");
						FileName = s + ".xls";
						xlBook = (IDispatch)doMethod(xlBooks,"xlBooks","Add",null);
						xlSheets = (IDispatch)doGet(xlApp,"xlApp","Sheets",null);
						int cnt = (Integer)doGet(xlSheets,"xlSheets","Count",null);
						for (int i=2;i<cnt+1;i++) {
							IDispatch xlSheet = (IDispatch)doGet(xlSheets,"xlSheets","Item",new Object[]{i});
							doPut(xlSheet,"xlSheet","Visible",false);
						}
					}
				} else {
					common.DebugingLine("createXls.ExportDataSetToExcel Without Template");
					FileName = s + ".xls";
					xlBook = (IDispatch)doMethod(xlBooks,"xlBooks","Add",null);
					xlSheets = (IDispatch)doGet(xlApp,"xlApp","Sheets",null);
					int cnt = (Integer)doGet(xlSheets,"xlSheets","Count",null);
					for (int i=2;i<cnt+1;i++) {
						IDispatch xlSheet = (IDispatch)doGet(xlSheets,"xlSheets","Item",new Object[]{i});
						doPut(xlSheet,"xlSheet","Visible",false);
					}
				}

				String DataSheet = "";
				String SummmarySheet = "";

				IDispatch xlNames = (IDispatch)doGet(xlBook,"xlBook","Names",null);
				int cnt = (Integer)doGet(xlNames,"xlNames","Count",null);
				for (int i=0;i<cnt;i++) {
					IDispatch nm = (IDispatch)doMethod(xlNames,"xlNames","Item",new Object[]{i+1,null,null});
					s = doGet(nm,"nm","Name",null).toString();
					IDispatch rtr = (IDispatch)doGet(nm,"nm","RefersToRange",null);
					if (common.cmptxt(s,"DATATBL")) {
						Data = nm;

						IDispatch brd = (IDispatch)doGet(rtr,"rtr","Borders",null);
						IDispatch interior = (IDispatch)doGet(rtr,"rtr","Interior",null);

						boolean isBorder = ((Integer)doGet(brd,"brd","LineStyle",null)!=xlNone);
						if (isBorder) {
							DataBorder[0] = doGet(brd,"brd","Color",null);
							DataBorder[1] = doGet(brd,"brd","ColorIndex",null);
							DataBorder[2] = doGet(brd,"brd","LineStyle",null);
							DataBorder[3] = doGet(brd,"brd","Weight",null);
						}
						DataBorder[4] = isBorder;

						boolean isInterior = ((Integer)doGet(interior,"interior","Pattern",null)!=xlNone);
						if (isInterior) {
							HeaderInterior[0] = doGet(interior,"interior","Color",null);
							HeaderInterior[1] = doGet(interior,"interior","ColorIndex",null);
							HeaderInterior[2] = doGet(interior,"interior","Pattern",null);
							HeaderInterior[3] = doGet(interior,"interior","PatternColor",null);
							HeaderInterior[4] = doGet(interior,"interior","PatternColorIndex",null);
						}
						HeaderInterior[5] = isInterior;

						doMethod(rtr,"rtr","Clear",null);
						DataSheet = getSheetNameStr(doGet(Data,"Data","RefersTo",null).toString());
					} else
					if (common.cmptxt(s,"SUM")) {
						Sum = nm;
						doMethod(rtr,"rtr","Clear",null);
						SummmarySheet = getSheetNameStr(doGet(Sum,"Sum","RefersTo",null).toString());
					} else
					if (common.cmptxt(s,"COUNT")) {
						Count = nm;
						doMethod(rtr,"rtr","Clear",null);
					} else {
						common.DebugingLine("createXls.ExportDataSetToExcel FieldName = " + s);
						Object fv = getFieldValue(s,AccountNo);
						if (fv==null) fv = "";
						setFieldValue(rtr,fv);
					}
				}

				if (!common.cmptxt(SummmarySheet,DataSheet)) Sum = null;

				int index = -1;
				xlSheets = (IDispatch)doGet(xlApp,"xlApp","Sheets",null);
				cnt = (Integer)doGet(xlSheets,"xlSheets","Count",null);
				for (int i=0;i<cnt;i++) {
					IDispatch xlSheet = (IDispatch)doGet(xlSheets,"xlSheets","Item",new Object[]{i+1});
					boolean v = ((Integer)doGet(xlSheet,"xlSheet","Visible",null)!=0);
					String nm = doGet(xlSheet,"xlSheet","Name",null).toString();
					if (v&&common.cmptxt(nm,DataSheet)) {
						index = i+1;
						break;
					}
				}
				if (index==-1) index = 1;

				IDispatch xlSheet = (IDispatch)doGet(xlSheets,"xlSheets","Item",new Object[]{index});
				TabSheets.add(xlSheet);

				if (FromTemplate)
					xlActiveSheet = (IDispatch)doGet(xlBook,"xlBook","ActiveSheet",null); else
						xlActiveSheet = xlSheet;

				String appworkingdir=System.getProperty("user.dir");
				String XMLFileName = appworkingdir + "\\s.xml";

				ResultSetMetaData rsmd = rs.getMetaData();
				String columns = "";
				for (int i=0;i<rsmd.getColumnCount();i++) {
					if (common.cmptxt(rsmd.getColumnName(i+1),FieldSheet))
						fieldSheetIndex = i;
					columns +=
						rsmd.getColumnName(i+1) + "\t" +
						rsmd.getColumnTypeName(i+1) + "\t" +
						rsmd.getColumnDisplaySize(i+1) + "\r\n";
				}
				String[] schema = columns.split("\r\n");
				createADOXMLSchema(schema,XMLFileName);

				qMain = new IDispatch(rm,"ADODB.Recordset");
				try {
					qMain.method("Open",new Object[]{XMLFileName,"Provider=MSPersist;",adOpenForwardOnly,adLockUnspecified,adCmdFile});
				} catch(Exception e) {
					common.DebugingLine("createXls.ExportDataSetToExcel [qMain.Open] <EXCEPTION> message="+e.toString());
					return "";
				}

				File fl = new File(XMLFileName);
				if (fl.exists()) fl.delete();

				Vector<String> lst = new Vector<String>();
				boolean EmptyRec = false;
				try {
					boolean neof = rs.first();
					while (neof) {
						if (!common.cmptxt(FieldSheet,"")) {
							s = getSheetName(rs.getString(FieldSheet).trim());
							if (!EmptyRec&&common.cmptxt(s,"")) EmptyRec = true;
							if (!common.cmptxt(s,"")&&lst.indexOf(s)==-1)
								lst.add(s);
						}
						addNewRecord(rs,qMain);
						neof = rs.next();
					}
				} catch(Exception e) {
					common.DebugingLine("createXls.ExportDataSetToExcel <EXCEPTION> message="+e.toString());
				}

				if (!EmptyRec&&lst.size()==1) lst.clear();
				if (lst.size()>0) {
					columns = "";
					for (int i=0;i<rsmd.getColumnCount();i++) {
						if (!common.cmptxt(rsmd.getColumnName(i+1),FieldSheet)) {
							columns +=
								rsmd.getColumnName(i+1) + "\t" +
								rsmd.getColumnTypeName(i+1) + "\t" +
								rsmd.getColumnDisplaySize(i+1) + "\r\n";
						}
					}
					schema = columns.split("\r\n");
					createADOXMLSchema(schema,XMLFileName);
				}

				for (int i=0;i<lst.size();i++) {
					xlSheets = (IDispatch)doGet(xlApp,"xlApp","Sheets",null);
					cnt = (Integer)doGet(xlSheets,"xlSheets","Count",null);
					IDispatch xlSheetAfter = (IDispatch)doGet(xlSheets,"xlSheets","Item",new Object[]{cnt});
					xlSheet = (IDispatch)doMethod(xlSheets,"xlSheets","Add",new Object[]{null,xlSheetAfter,1,null});
					doPut(xlSheet,"xlSheet","Name",lst.get(i));
					TabSheets.add(xlSheet);

					IDispatch q = new IDispatch(rm,"ADODB.Recordset");
					doMethod(q,"q","Open",new Object[]{XMLFileName,"Provider=MSPersist;",adOpenForwardOnly,adLockUnspecified,adCmdFile});
					ExcelSheets.add(q);
				}

				fl = new File(XMLFileName);
				if (fl.exists()) fl.delete();

				if (lst.size()>0) {
					boolean neof = rs.first();
					if (!common.cmptxt(FieldSheet,""))
						while (neof) {
							index = -1;
							s = getSheetName(rs.getString(FieldSheet).trim());
							if (!common.cmptxt(s,"")) index = lst.indexOf(s);
							if (index>-1) addRecord(index,rs);
							neof = rs.next();
						}
				}

				for (int i=0;i<TabSheets.size();i++) {
					setHeader(i,DisplayFormats,FromTemplate,rtl);
				}

				if (!common.cmptxt(Password,"")) {
					try {
						try {
							doPut(xlBook,"xlBook","Password",Password);
						} catch (Exception e) {
							doMethod(xlBook,"xlBook","Protect",new Object[]{Password});
						}
					} catch (Exception e) {
						common.DebugingLine("createXls.ExportDataSetToExcel <EXCEPTION> message="+e.toString());
					}
				}

				cnt = (Integer)doGet(xlNames,"xlNames","Count",null);
				while (cnt>0) {
					IDispatch nm = (IDispatch)doMethod(xlNames,"xlNames","Item",new Object[]{1,null,null});
					doMethod(nm,"nm","Delete",null);
					cnt = (Integer)doGet(xlNames,"xlNames","Count",null);
				}

				doMethod(xlActiveSheet,"xlActiveSheet","Select",null);
				if (FromTemplate) doMethod(xlBook,"xlBook","Save",null); else {
					fl = new File(FileName);
					if (fl.exists()) fl.delete();
					doMethod(xlBook,"xlBook","SaveAs",new Object[]{FileName,xlWorkbookNormal});
				}
				doMethod(xlBooks,"xlBooks","Close",null);
				xlBooks = null;
			} catch (Exception e) {
				common.DebugingLine("createXls.ExportDataSetToExcel <EXCEPTION> message="+e.toString());
			}
			finally {
				if (xlBooks!=null)
					try {
						if (FromTemplate) doMethod(xlBook,"xlBook","Save",null); else {
							File fl = new File(FileName);
							if (fl.exists()) fl.delete();
							doMethod(xlBook,"xlBook","SaveAs",new Object[]{FileName,xlWorkbookNormal});
						}
						doMethod(xlBooks,"xlBooks","Close",null);
					} catch (Exception e) {
						common.DebugingLine("createXls.ExportDataSetToExcel <EXCEPTION> message="+e.toString());
					}
				rm.release();
			}
			return FileName;
		}
		finally {
			System.gc();
			common.DebugingLine("createXls.ExportDataSetToExcel <EXIT> ");
		}
	}

    public static void ExportDataSetToTxt(
			String FileName,
			ResultSet rs,
			String[] ColumnLabel,
			String delimiter) {

		common.DebugingLine("Main.ExportDataSetToTxt <ENTER> ");

		try {

			String s = "";
			for (int i=0;i<ColumnLabel.length;i++) {
				s += ColumnLabel[i] + delimiter;
			}
			s = s.trim() + "\r\n";
			try {
				ResultSetMetaData rsmd = rs.getMetaData();
				while (rs.next()) {
					String v = "";
					for (int i=0;i<rsmd.getColumnCount();i++) {
						try {
							String val = "";
							String ct = rsmd.getColumnTypeName(i+1);
							if (common.cmptxt(ct,"text")||common.cmptxt(ct,"varchar")||common.cmptxt(ct,"nvarchar")) {
								byte[] b = rs.getBytes(i+1);
								if (b!=null)
									val = new String(b); else
										val = rs.getString(i+1);
								if (!common.cmptxt(val,"")) {
									val = val.replaceAll(delimiter," ");
									val = val.replaceAll("\r"," ");
									val = val.replaceAll("\n"," ");
								}
							} else
								val = rs.getString(i+1);
							if (val==null) val = "";
							v += val + delimiter;
						} catch (Exception e) {}
					}
					s += v.trim() + "\r\n";
				}

				OutputStream fout= new FileOutputStream(FileName);
		        OutputStream bout= new BufferedOutputStream(fout);
		        OutputStreamWriter out = new OutputStreamWriter(bout,"UTF-8");
		        out.write(s);
		        out.flush();
		        out.close();
			} catch(Exception e) {
				common.DebugingLine("Main.ExportDataSetToTxt <EXCEPTION> message="+e.toString());
			}

		} finally {
			common.DebugingLine("Main.ExportDataSetToTxt <EXIT> ");
		}
	}

    public static void test(String[] args,DataBaseConnector tdbs) {
		String appworkingdir=System.getProperty("user.dir");
            dbs=tdbs;
			if (dbs!=null) {

				String expr =
					"SELECT top 1 menaya_id,taseid,symbol from menaya_base where menaya_id>30";
				try {
					ResultSet rs = dbs.execSQLnoClose(expr);
                 
					createXls.ExportDataSetToExcel(
							appworkingdir + "\\excelout\\test.xls",
							"",//"USERID",
							"",//"AMOUNT",
							rs,
							new String[]{"מספר","משתמש","תאריך"},
							true,
							"",
							"",//appworkingdir + "\\exceltmpl\\tmpl.xls",
							new String[]{"","","dd.mm.yyyy","","",""},
							false,//true,
							false,
							"",//"A7121861069843402-rD",
							dbs);
				} catch (Exception e) {}
			}
		}
}
