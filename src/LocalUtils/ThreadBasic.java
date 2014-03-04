
/*
 * ThreadBasic.java
 *
 * Created on October 21, 2006, 11:21 PM
 *
 * The class is basic thread behavior support.
 *use it as
 *derive from implement dorun, dostop and run functions and in run function follow
 *steps like those
 *loop until isrun or/and your needs
 *do wat you do
 *sleep
 *dostop
 */



package LocalUtils;

import Utils.*;

/**
 *
 * @author Dmitry
 */
public class ThreadBasic extends Thread {
    private static int        threadscount = 0;
    private static final long sleeptime    = 10;

    //~--- fields -------------------------------------------------------------

    protected boolean runing = false;

    //~--- constructors -------------------------------------------------------

    /**
     * Creates a new instance of ThreadBasic
     */
    public ThreadBasic() {
        super("SDIThreadBasic");
    }

    public ThreadBasic(String name) {
        super(name);
    }

    //~--- methods ------------------------------------------------------------

    public void dorun() {
        if (runing) {
            dostop();
        }

        runing = true;
        threadscount++;
        start();
    }

    protected void dosleep() {

        // here sleep for sleeptime miliseconds to give chanse to gui to update him self
        try {
            sleep(sleeptime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void dostop() {
        runing = false;
        threadscount--;
    }

    //~--- get methods --------------------------------------------------------

    public int getThreadsCount() {
        return threadscount;
    }

    public boolean isRuning() {
        return runing;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
