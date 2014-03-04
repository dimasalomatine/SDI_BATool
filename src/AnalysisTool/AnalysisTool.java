package AnalysisTool;

//~--- JDK imports ------------------------------------------------------------

import java.util.*;

//~--- classes ----------------------------------------------------------------

public class AnalysisTool {
    protected java.util.List tdp    = new ArrayList();
    protected HashMap        params = new HashMap();

    //~--- constructors -------------------------------------------------------

    /**
     * Method AnalysisTool
     */
    public AnalysisTool() {

        // TODO: Add your code here
    }

    public AnalysisTool(String aname) {
        setParam("ToolName", aname);
    }

    //~--- methods ------------------------------------------------------------

    /**
     * Method void Run
     *
     *
     * @return
     *
     */
    public void run(java.util.List datasource) {

        // TODO: Add your code here
    }

    //~--- get methods --------------------------------------------------------

    /**
     * Method getData
     *
     *
     * @return
     *
     */
    public java.util.List getData() {
        return tdp;
    }

    /**
     * Method  getDataAsArray
     *
     *
     * @return
     *
     */
    public Object[] getDataAsArray() {
        return tdp.toArray();
    }

    /**
     * Method getParam
     *
     *
     * @param nameandtype
     *
     * @return
     *
     */
    public Object getParam(String name) {
        return params.get(name);
    }

    //~--- set methods --------------------------------------------------------

    /**
     * Method SetData-sets data by copy
     *
     *
     * @param data
     *
     *  @return
     *
     *
     */
    public boolean setData(java.util.List data) {
        return true;
    }

    /**
     * Method setParam
     *
     *
     * @param nameandtype
     * @param val
     *
     * @return
     *
     */
    public void setParam(String name, Object val) {
        params.put(name, val);
    }

    public void dumpParams()
    {
     System.out.println(params.toString());
    }
    public void dumpResults()
    {
     for(int i=0;i<tdp.size();i++)
     {
       System.out.println(tdp.get(i).toString());
     }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
