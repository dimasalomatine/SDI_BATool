//~--- non-JDK imports --------------------------------------------------------

/*
 * @author Salomatine Dmitry and Natalia Polak
 * @version 1.00 04/04/20
 */
import DBCONNECT.TRS;
import Utils.LoggerNB;

//~--- JDK imports ------------------------------------------------------------

import java.sql.*;

import javax.swing.*;

//~--- classes ----------------------------------------------------------------

public class MySimpleInput {
    public int useraction                = -1;
    Object     selectedValuesecond       = null;
    Object     selectedValue             = null;
    Object[]   possibleValuesSecondArray = null;
    Object[]   possibleValues            = null;

    //~--- constructors -------------------------------------------------------

    public MySimpleInput(JFrame frame, String stitle, Object[] possibleValues,
                         Object[] possibleValuesSecondArray) {
        makeinternalPVDatacopy( possibleValues,possibleValuesSecondArray);
        selectedValue                  = JOptionPane.showInputDialog(frame,
                stitle, "Input", JOptionPane.INFORMATION_MESSAGE, null,
                this.possibleValues, this.possibleValues[0]);
        useraction = -1;

        for (int i = 0; i < possibleValues.length; i++) {
            if (possibleValues[i] == selectedValue) {
                useraction = i;

                break;
            }
        }
    }

    public MySimpleInput(JFrame frame, String stitle, Connection con,
                         String table, String vc1) {
        TRS  RS = new TRS(con);
        Integer si;
        int     count, i;

        try {
            RS.execSQL("SELECT Count(*) from " + table);

            if (RS.rs.next()) {
                i = count = Integer.parseInt(RS.rs.getString(1));
                RS.reuse();
                RS.execSQL("SELECT * from " + table);
                possibleValues = new Object[i];

                while (RS.rs.next()) {
                    //possibleValues[count - i] =new String(RS.rs.getString(vc1));
                    possibleValues[count - i] =RS.rs.getString(vc1);
                    i--;
                }
            }

            selectedValue = JOptionPane.showInputDialog(frame, stitle,
                    "Input", JOptionPane.INFORMATION_MESSAGE, null,
                    this.possibleValues, this.possibleValues[0]);
            useraction = -1;

            for (i = 0; i < possibleValues.length; i++) {
                if (possibleValues[i] == selectedValue) {
                    useraction = i;

                    break;
                }
            }
        } catch (Exception e) {
            if(LoggerNB.debuging){System.out.println("Error 47: " + e);}
        } finally {
            RS.close();
            RS = null;
        }
    }

    public MySimpleInput(JFrame frame, String stitle, Connection con,
                         String table, String vc1, String vc2) {
        TRS RS = new TRS(con);
        int    count, i;

        try {
            RS.execSQL("SELECT Count(*) from " + table);

            if (RS.rs.next()) {
                i = count = Integer.parseInt(RS.rs.getString(1));
                RS.reuse();
                RS.execSQL("SELECT * from " + table);
                possibleValues            = new Object[i];
                possibleValuesSecondArray = new Object[i];

                while (RS.rs.next()) {
                    //possibleValues[count - i] =new String(RS.rs.getString(vc1));
                    possibleValues[count - i] =RS.rs.getString(vc1);
                    //possibleValuesSecondArray[count - i] =new String(RS.rs.getString(vc2));
                    possibleValuesSecondArray[count - i] =RS.rs.getString(vc2);
                    i--;
                }
            }

            selectedValue = JOptionPane.showInputDialog(frame, stitle,
                    "Input", JOptionPane.INFORMATION_MESSAGE, null,
                    this.possibleValues, this.possibleValues[0]);
            useraction = -1;

            for (i = 0; i < possibleValues.length; i++) {
                if (possibleValues[i] == selectedValue) {
                    useraction          = i;
                    selectedValuesecond =
                        possibleValuesSecondArray[useraction];

                    break;
                }
            }
        } catch (Exception e) {
            {System.out.println("Error 46: " + e);}
        } finally {
            RS.close();
            RS = null;
        }
    }
    private void makeinternalPVDatacopy(Object[] possibleValues,Object[] possibleValuesSecondArray)
    {
        int i;
         this.possibleValues=new Object[possibleValues.length];
         for(i=0;i<this.possibleValues.length;i++)this.possibleValues[i]=possibleValues[i];
         this.possibleValuesSecondArray = new Object[possibleValuesSecondArray.length];
         for(i=0;i<this.possibleValuesSecondArray.length;i++)this.possibleValuesSecondArray[i]=possibleValuesSecondArray[i];
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
