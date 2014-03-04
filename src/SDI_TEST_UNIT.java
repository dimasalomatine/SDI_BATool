//~--- JDK imports ------------------------------------------------------------

import javax.swing.JPanel;

//~--- classes ----------------------------------------------------------------

public class SDI_TEST_UNIT extends JPanel {

    /**
     * Method SDI_TEST_UNIT
     *
     *
     */
    public SDI_TEST_UNIT() {}

    /**
     * Method main to use in Junit
     *
     *
     * @param args
     *
     * # To run your TestCase suite:
     * java -cp whatever:/path/to/junit.jar junit.textui.TestRunner MyTestClass
     *
     * # To run your TestCase suite in a Gui:
     * java -cp whatever:/path/to/junit.jar junit.swingui.TestRunner MyTestClass
     *
     * # To run the TestRunner Gui and select your suite or test methods
     * # from within the Gui:
     * java -cp whatever:/path/to/junit.jar junit.swingui.TestRunner
     *
     * # This runs the suite:
     * java -cp whatever:/path/to/junit.jar org.blah.your.TestClass
     *
     * # This does the same thing with the fancy Gui:
     * java -cp whatever:/path/to/junit.jar org.blah.your.TestClass -g
     *
     * static public void main(String[] sa) {
     * if (sa.length > 0 && sa[0].startsWith("-g")) {
     *   junit.swingui.TestRunner.run(SMTPTest.class);
     * } else {
     *   junit.textui.TestRunner runner = new junit.textui.TestRunner();
     *   System.exit(
     *           runner.run(runner.getTest(SMTPTest.class.getName())).
     *                   wasSuccessful() ? 0 : 1
     *   );
     * }
     * }
     */
}


//~ Formatted by Jindent --- http://www.jindent.com
