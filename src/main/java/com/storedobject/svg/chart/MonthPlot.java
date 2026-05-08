package com.storedobject.svg.chart;

import com.storedobject.common.DateUtility;

import java.util.Date;

/**
 * A plot for monthly data.
 *
 * @author Syam
 */
public class MonthPlot extends Plot {

    /**
     * Constructor.
     *
     * @param startDate Starting date of the data.
     * @param values Array of values for each month.
     * @param unit Unit for the values.
     * @param tickStart Starting value for Y-axis ticks.
     * @param tickStep Step value between Y-axis ticks.
     * @param tickCount Number of ticks on the Y-axis.
     */
    public MonthPlot(Date startDate, double[] values, String unit, double tickStart, double tickStep, int tickCount) {
        super(values(startDate, values, unit), tickStart, tickStep, tickCount);
    }

    /**
     * Constructor. The start date is calculated based on the end date and the number of values.
     *
     * @param values Array of values for each month.
     * @param unit Unit for the values.
     * @param tickStart Starting value for Y-axis ticks.
     * @param tickStep Step value between Y-axis ticks.
     * @param tickCount Number of ticks on the Y-axis.
     * @param endDate Ending date of the data.
     */
    public MonthPlot(double[] values, String unit, double tickStart, double tickStep, int tickCount, Date endDate) {
        this(DateUtility.addMonth(endDate, 1 - values.length), values, unit, tickStart, tickStep, tickCount);
    }

    /**
     * Constructor. The data ends today.
     *
     * @param values Array of values for each month.
     * @param unit Unit for the values.
     * @param tickStart Starting value for Y-axis ticks.
     * @param tickStep Step value between Y-axis ticks.
     * @param tickCount Number of ticks on the Y-axis.
     */
    public MonthPlot(double[] values, String unit, double tickStart, double tickStep, int tickCount) {
        this(values, unit, tickStart, tickStep, tickCount, DateUtility.today());
    }

    private static Values values(Date startDate, double[] values, String unit) {
        Values v = new Values(unit);
        for (double value : values) {
            v.add(new Values.Value(DateUtility.getMonthName(startDate), value));
            startDate = DateUtility.addMonth(startDate, 1);
        }
        return v;
    }
}
