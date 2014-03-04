interface SQL_DG_OPS {
    public void saveAll();

    public void saveAll(int record);

    public void fetchdata();
     public void fetchdata(String filters[]);

    public void fetchdata(int uid);

    /*
     * must define in table class and call after fetch
     * if you whant apply advanced table formating
     */
    public void initAdvancedTableParams();

    public void initTableControlPanel();

    /*
    public void onDeleteBut();

    public void onNewBut();

    public void onUpdateBut();
     */
}


//~ Formatted by Jindent --- http://www.jindent.com
