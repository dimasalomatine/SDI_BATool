/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package charts;

/**
 *
 * @author Dmitry
 */

import Utils.TDoc;
import org.jfree.data.DomainInfo;
import org.jfree.data.DomainOrder;
import org.jfree.data.Range;
import org.jfree.data.RangeInfo;
import org.jfree.data.general.AbstractSeriesDataset;
import org.jfree.data.xy.XYDataset;

/**
 * Random data for a scatter plot demo.
 * <P>
 * Note that the aim of this class is to create a self-contained data source for demo purposes -
 * it is NOT intended to show how you should go about writing your own datasets.
 *
 * @author David Gilbert
 */
public class SampleXYDataset2 extends AbstractSeriesDataset implements XYDataset,
                                                                       DomainInfo, RangeInfo {

    /** The series count. */
    private static final int DEFAULT_SERIES_COUNT = 4;

    /** The item count. */
    private static final int DEFAULT_ITEM_COUNT = 100;

    /** The range. */
    private static final double DEFAULT_RANGE = 200;

    /** The x values. */
    private Double[][] xValues;

    /** The y values. */
    private Double[][] yValues;
    
    private String[]skeys;

    /** The number of series. */
    private int seriesCount;

    /** The number of items. */
    private int itemCount;

    /** The minimum domain value. */
    private Number domainMin;

    /** The maximum domain value. */
    private Number domainMax;

    /** The minimum range value. */
    private Number rangeMin;

    /** The maximum range value. */
    private Number rangeMax;

    /** The range of the domain. */
    private Range domainRange;

    /** The range. */
    private Range range;

    /**
     * Creates a sample dataset using default settings (4 series, 100 data items per series,
     * random data in the range 0 - 200).
     */
    public SampleXYDataset2() {
        this(DEFAULT_SERIES_COUNT, DEFAULT_ITEM_COUNT);
    }

    /**
     * Creates a sample dataset.
     *
     * @param seriesCount  the number of series.
     * @param itemCount  the number of items.
     */
    public SampleXYDataset2(int seriesCount, int itemCount) {

        this.xValues = new Double[seriesCount][itemCount];
        this.yValues = new Double[seriesCount][itemCount];
        this.skeys = new String[seriesCount];
        this.seriesCount = seriesCount;
        this.itemCount = itemCount;

        double minX = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;

        for (int series = 0; series < seriesCount; series++) {
            for (int item = 0; item < itemCount; item++) {

                double x = (Math.random() - 0.5) * DEFAULT_RANGE;
                this.xValues[series][item] = new Double(x);
                if (x < minX) {
                    minX = x;
                }
                if (x > maxX) {
                    maxX = x;
                }

                double y = (Math.random() + 0.5) * 6 * x + x;
                this.yValues[series][item] = new Double(y);
                if (y < minY) {
                    minY = y;
                }
                if (y > maxY) {
                    maxY = y;
                }

            }
        }

        this.domainMin = new Double(minX);
        this.domainMax = new Double(maxX);
        this.domainRange = new Range(minX, maxX);

        this.rangeMin = new Double(minY);
        this.rangeMax = new Double(maxY);
        this.range = new Range(minY, maxY);

    }
    
    public SampleXYDataset2(int seriesCount, int itemCount,java.util.List atd,int risk,int earn) {

        this.xValues = new Double[seriesCount][itemCount];
        this.yValues = new Double[seriesCount][itemCount];
        this.skeys = new String[seriesCount];
        this.seriesCount = seriesCount;
        this.itemCount = itemCount;

        double minX = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;

        for (int i=0;i<atd.size();i++)
  {
   TDoc t=(TDoc)atd.get(i);
skeys[i]=new String((String)t.o[5]);
   this.xValues[i][0] = new Double((Double)t.o[risk]);
                if (this.xValues[i][0]  < minX) {
                    minX = this.xValues[i][0] ;
                }
                if (this.xValues[i][0]  > maxX) {
                    maxX = this.xValues[i][0] ;
                }
                this.yValues[i][0] = new Double((Double)t.o[earn]);
                if (this.yValues[i][0]  < minY) {
                    minY = this.yValues[i][0] ;
                }
                if (this.yValues[i][0] > maxY) {
                    maxY = this.yValues[i][0] ;
                }
  }
        

        this.domainMin = new Double(minX);
        this.domainMax = new Double(maxX);
        this.domainRange = new Range(minX, maxX);

        this.rangeMin = new Double(minY);
        this.rangeMax = new Double(maxY);
        this.range = new Range(minY, maxY);

    }

    /**
     * Returns the x-value for the specified series and item.  Series are numbered 0, 1, ...
     *
     * @param series  the index (zero-based) of the series.
     * @param item  the index (zero-based) of the required item.
     *
     * @return the x-value for the specified series and item.
     */
    public double getXValue(int series, int item) {
        return this.xValues[series][item];
    }

    /**
     * Returns the y-value for the specified series and item.  Series are numbered 0, 1, ...
     *
     * @param series  the index (zero-based) of the series.
     * @param item  the index (zero-based) of the required item.
     *
     * @return  the y-value for the specified series and item.
     */
    public double getYValue(int series, int item) {
        return this.yValues[series][item];
    }

    /**
     * Returns the number of series in the dataset.
     *
     * @return the series count.
     */
    public int getSeriesCount() {
        return this.seriesCount;
    }

    /**
     * Returns the name of the series.
     *
     * @param series  the index (zero-based) of the series.
     *
     * @return the name of the series.
     */
    public String getSeriesName(int series) {
        return "Sample " + series;
    }

    /**
     * Returns the number of items in the specified series.
     *
     * @param series  the index (zero-based) of the series.
     *
     * @return the number of items in the specified series.
     */
    public int getItemCount(int series) {
        return this.itemCount;
    }

    /**
     * Returns the minimum domain value.
     *
     * @return the minimum domain value.
     */
    public Number getMinimumDomainValue() {
        return this.domainMin;
    }

    /**
     * Returns the maximum domain value.
     *
     * @return the maximum domain value.
     */
    public Number getMaximumDomainValue() {
        return this.domainMax;
    }

    /**
     * Returns the range of values in the domain.
     *
     * @return the range.
     */
    public Range getDomainRange() {
        return this.domainRange;
    }

    /**
     * Returns the minimum range value.
     *
     * @return the minimum range value.
     */
    public Number getMinimumRangeValue() {
        return this.rangeMin;
    }

    /**
     * Returns the maximum range value.
     *
     * @return the maximum range value.
     */
    public Number getMaximumRangeValue() {
        return this.rangeMax;
    }

    /**
     * Returns the range of values in the range (y-values).
     *
     * @return the range.
     */
    public Range getValueRange() {
        return this.range;
    }

  DomainOrder order=DomainOrder.DESCENDING;
    public DomainOrder getDomainOrder() {
        return order;
    }


    public double getDomainLowerBound(boolean arg0) {
        return domainRange.getLowerBound();
    }

    public double getDomainUpperBound(boolean arg0) {
        return domainRange.getUpperBound();
    }

    public Range getDomainBounds(boolean arg0) {
        return domainRange;
    }

    public double getRangeLowerBound(boolean arg0) {
       return range.getLowerBound();
    }

    public double getRangeUpperBound(boolean arg0) {
       return range.getUpperBound();
    }

    public Range getRangeBounds(boolean arg0) {
        return range;
    }

    public Number getX(int arg0, int arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Number getY(int arg0, int arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Comparable getSeriesKey(int arg0) {
       if(arg0>=0 &&arg0<=seriesCount)return skeys[arg0];
       else throw new IndexOutOfBoundsException ();
    }


}

