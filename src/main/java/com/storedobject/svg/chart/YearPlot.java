package com.storedobject.svg.chart;

import com.storedobject.common.DateUtility;

import java.util.Date;

/**
 * A plot for yearly data.
 *
 * @author Syam
 */
public class YearPlot extends Plot {

    /**
     * Constructor.
     *
     * @param startDate Starting date of the data.
     * @param values Array of values for each year.
     * @param unit Unit for the values.
     * @param tickStart Starting value for Y-axis ticks.
     * @param tickStep Step value between Y-axis ticks.
     * @param tickCount Number of ticks on the Y-axis.
     */
    public YearPlot(Date startDate, double[] values, String unit, double tickStart, double tickStep, int tickCount) {
        super(values(startDate, values, unit), tickStart, tickStep, tickCount);
    }

    /**
     * Constructor. The start date is calculated based on the end date and the number of values.
     *
     * @param values Array of values for each year.
     * @param unit Unit for the values.
     * @param tickStart Starting value for Y-axis ticks.
     * @param tickStep Step value between Y-axis ticks.
     * @param tickCount Number of ticks on the Y-axis.
     * @param endDate Ending date of the data.
     */
    public YearPlot(double[] values, String unit, double tickStart, double tickStep, int tickCount, Date endDate) {
        this(DateUtility.addYear(endDate, 1 - values.length), values, unit, tickStart, tickStep, tickCount);
    }

    /**
     * Constructor. The data ends today.
     *
     * @param values Array of values for each year.
     * @param unit Unit for the values.
     * @param tickStart Starting value for Y-axis ticks.
     * @param tickStep Step value between Y-axis ticks.
     * @param tickCount Number of ticks on the Y-axis.
     */
    public YearPlot(double[] values, String unit, double tickStart, double tickStep, int tickCount) {
        this(values, unit, tickStart, tickStep, tickCount, DateUtility.today());
    }

    private static Values values(Date startDate, double[] values, String unit) {
        Values v = new Values(unit);
        for (double value : values) {
            v.add(new Values.Value("" + DateUtility.getYear(startDate), value));
            startDate = DateUtility.addYear(startDate, 1);
        }
        return v;
    }
}
