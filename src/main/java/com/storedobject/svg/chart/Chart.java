package com.storedobject.svg.chart;

/**
 * Base class for all SVG charts.
 *
 * @author Syam
 */
public abstract class Chart {

    /**
     * SVG content of the chart.
     */
    protected String svg;

    /**
     * Data values of the chart.
     */
    protected final Values values;

    /**
     * Default width of the chart.
     */
    protected int width = 600;

    /**
     * Default height of the chart.
     */
    protected int height = 400;

    /**
     * Constructor.
     *
     * @param values The data values to be plotted.
     */
    public Chart(Values values) {
        this.values = values;
    }

    /**
     * Get the data values.
     *
     * @return The {@link Values} object.
     */
    public Values getValues() {
        return values;
    }

    /**
     * Add a data value.
     *
     * @param value The value to add.
     */
    public void addValue(Values.Value value) {
        values.add(value);
    }

    /**
     * Add a data value with a label.
     *
     * @param label The label for the value.
     * @param value The value.
     */
    public void addValue(Object label, double value) {
        values.add(label, value);
    }

    /**
     * Set the label name (typically used as X-axis name).
     *
     * @param labelName Label name.
     */
    public void setLabelName(String labelName) {
        values.setLabelName(labelName);
    }

    /**
     * Set the value name (typically used as Y-axis name).
     *
     * @param valueName Value name.
     */
    public void setValueName(String valueName) {
        values.setValueName(valueName);
    }

    /**
     * Set the unit for values.
     *
     * @param unit Unit to be set.
     */
    public void setUnit(String unit) {
        values.setUnit(unit);
    }

    /**
     * Automatically assign colors to data points.
     */
    public void colorize() {
        values.colorize();
    }

    /**
     * Build the chart. This method should be called before getting the SVG output.
     * It ensures the underlying {@link Values} are also built.
     */
    public void build() {
        if (!values.isBuilt()) {
            values.build();
        }
    }

    /**
     * Check if the chart is built.
     *
     * @return True if built.
     */
    public final boolean isBuilt() {
        return values.isBuilt();
    }

    /**
     * Generate the SVG string with specified dimensions.
     *
     * @param width Width of the SVG (can be null for default).
     * @param height Height of the SVG (can be null for default).
     * @return SVG string.
     */
    private String svg(String width, String height) {
        build();
        if(width == null) {
            width = this.width + "";
        }
        if(height == null) {
            height = this.height + "";
        }
        return "<svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 " + this.width + " " + this.height
                + "\" width=\"" + width + "\" height=\"" + height + "\">\n" + svg + "\n</svg>";
    }

    /**
     * Get the SVG output for the chart using its default dimensions.
     *
     * @return SVG string.
     */
    public final String getSvg() {
        return svg(null, null);
    }

    /**
     * Get the SVG output that fills its container (100% width and height).
     *
     * @return SVG string.
     */
    public final String getFilledSvg() {
        return getScaledSvg(100, 100);
    }

    /**
     * Get the SVG output with specified scale percentages for width and height.
     *
     * @param scaleWidthPercentage Scale percentage for width (0 to 100).
     * @param scaleHeightPercentage Scale percentage for height (0 to 100).
     * @return SVG string.
     */
    public final String getScaledSvg(double scaleWidthPercentage, double scaleHeightPercentage) {
        return svg(scaleWidthPercentage <= 0 || scaleWidthPercentage > 100 ? "auto"
                        : Values.toString(scaleWidthPercentage, "%"),
                scaleHeightPercentage <= 0 || scaleHeightPercentage > 100 ? "auto"
                        : Values.toString(scaleHeightPercentage, "%"));
    }
}
