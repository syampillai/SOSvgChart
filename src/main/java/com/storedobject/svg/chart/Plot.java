package com.storedobject.svg.chart;

/**
 * This class represents a line plot.
 * It handles axes, grid lines, and data points connected by lines.
 *
 * @author Syam
 */
public class Plot extends Chart {

    private final int tickCount;
    private final double tickStart;
    private double tickStep;
    private boolean showGridLines = true;
    private String gridLineColor = "#ccc";
    private String axisColor = "#ccc";
    private String plotColor = "#3b82f6";

    /**
     * Constructor.
     *
     * @param values The data values to be plotted.
     * @param tickStart The starting value for Y-axis ticks.
     * @param tickStep The step value between Y-axis ticks.
     * @param tickCount The number of ticks on the Y-axis.
     */
    public Plot(Values values, double tickStart, double tickStep, int tickCount) {
        super(values);
        values.setDefaultValueColor(plotColor);
        this.tickCount = tickCount <= 0 ? 10 : tickCount;
        this.tickStart = tickStart;
        this.tickStep = tickStep <= 0 ? 3 : tickStep;
        values.setValueNameColor("#3b82f6");
        values.setLabelNameColor("#222");
    }

    /**
     * Set whether to show grid lines.
     *
     * @param showGridLines True to show grid lines.
     */
    public void setShowGridLines(boolean showGridLines) {
        this.showGridLines = showGridLines;
    }

    /**
     * Set the color for the axes.
     *
     * @param axisColor Color string.
     */
    public void setAxisColor(String axisColor) {
        if(axisColor != null && !axisColor.isBlank()) {
            this.axisColor = axisColor;
            values.unbuild();
        }
    }

    /**
     * Set the color for the grid lines.
     *
     * @param gridLineColor Color string.
     */
    public void setGridLineColor(String gridLineColor) {
        if(gridLineColor != null && !gridLineColor.isBlank()) {
            this.gridLineColor = gridLineColor;
            values.unbuild();
        }
    }

    /**
     * Set the color for the plot line.
     *
     * @param plotColor Color string.
     */
    public void setPlotColor(String plotColor) {
        this.plotColor = plotColor == null || plotColor.isBlank() ? "#3b82f6" : plotColor;
    }

    /**
     * Build the plot SVG.
     */
    @Override
    public final void build() {
        if(values.isBuilt()) {
            return;
        }
        if(values.size() == 0) {
            values.add("Jan", 10);
            values.add("Feb", 25);
            values.add("Mar", 35);
        }
        int n = values.size();
        values.build();
        Values.Value v;
        int h = 60 + (tickCount * 30);
        svg = buildSVG();
        for(int i = 0; i < n; i++) {
            v = values.get(i);
            svg = svg.replace("${X" + i + "}", values.getLabel(v));
            svg = svg.replace("${T" + i + "}", values.getValue(v));
        }
        double d, min = Double.POSITIVE_INFINITY, max = Double.NEGATIVE_INFINITY;
        for(Values.Value value: values.list()) {
            if(value.value() < min) {
                min = value.value();
            }
            if(value.value() > max) {
                max = value.value();
            }
        }
        max -= min;
        while ((max / tickStep) > tickCount) {
            tickStep *= 2;
        }
        while (tickStep > max) {
            tickStep /= 2;
        }
        for(int i = 0; i < n; i++) {
            d = values.get(i).value() - min;
            d = (h - 80) * d / max;
            if(Double.isNaN(d)) {
                d = ((h - 80) >> 1) + 20;
            } else {
                d = (h - 60) - d;
            }
            svg = svg.replace("${V" + i + "}", Values.toString(d));
        }
        int count = 0;
        double value;
        d = tickStart + ((tickCount + 1) * tickStep);
        d -= min;
        while (d < max) {
            d += tickStep;
        }
        while (d > max) {
            d -= tickStep;
        }
        d += min;
        String previous = "", current;
        while(count < tickCount) {
            value = d - min;
            value = (h - 80) * value / max;
            if(Double.isNaN(value)) {
                value = ((h - 80) >> 1) + 20;
            } else {
                value = (h - 60) - value;
            }
            current = Values.toString(value);
            if(value >= 15 && value <= (h - 60)) {
                if(current.equals(previous)) {
                    d -= tickStep;
                    continue;
                }
                svg = svg.replace("${G" + count + "}", gridLine(value));
                svg = svg.replace("${M" + count + "}", current);
                svg = svg.replace("${Y" + count + "}", values.toUnit(d));
                count++;
                previous = current;
            }
            if(value > (h - 60)) {
                break;
            }
            d -= tickStep;
        }
        while(count < tickCount) {
            svg = svg.replace("${G" + count + "}", "");
            svg = svg.replace("${M" + count + "}", "");
            svg = svg.replace("${Y" + count + "}", "");
            count++;
        }
    }

    /**
     * Generate the SVG for a grid line.
     *
     * @param y Y coordinate of the grid line.
     * @return SVG string for the grid line.
     */
    protected String gridLine(double y) {
        if(!showGridLines) {
            return "";
        }
        String sv = Values.toString(y);
        return "<line x1=\"40\" y1=\"" +
                sv +
                "\" x2=\"" + (50 + (35 * values.size())) + "\" y2=\"" +
                sv +
                "\" stroke=\"" + gridLineColor + "\"/>";
    }

    private String buildSVG() {
        int n = values.size();
        int left = 40;
        width = left + 50 + (n * 35) + (values.getLabelName() == null ? 0 : 10);
        int h = 60 + (tickCount * 30);
        height = h + (values.getLabelName() == null ? 0 : 10);
        int i;
        StringBuilder s = new StringBuilder();
        s.append("""
                  <defs>
                    <style>
                      text {
                        font-family: Arial, Helvetica, sans-serif;
                        font-size: 13px;
                        fill: #333;
                      }
                      .axis-label {
                        font-size: 10px;
                        font-weight: bold;
                        fill: ${LC};
                      }
                    </style>
                  </defs>
                  """);
        s.append("<line x1=\"").append(left).append("\" y1=\"");
        s.append(h - 40);
        s.append("\" x2=\"").append((left + 10) + (35 * n)).append("\" y2=\"");
        s.append(h - 40);
        s.append("\" stroke=\"").append(axisColor).append("\"/>\n");
        s.append("<line x1=\"").append(left).append("\" y1=\"");
        s.append(30);
        s.append("\" x2=\"").append(left).append("\" y2=\"");
        s.append(h - 40);
        s.append("\" stroke=\"").append(axisColor).append("\"/>\n");
        s.append("<polyline fill=\"none\" stroke=\"").append(plotColor).append("\" stroke-width=\"2\"\npoints=\"");
        for(i = 0; i < n; i++) {
            if(i > 0) {
                s.append(" ");
            }
            s.append(left + (40 * i));
            s.append(",${V").append(i).append("}");
        }
        s.append("\"/>\n");
        for(i = 0; i < tickCount; i++) {
            s.append("<text x=\"").append(left - 1).append("\" y=\"${M").append(i)
                    .append("}\" text-anchor=\"end\" class=\"axis-label\">${Y").append(i).append("}</text>\n")
                    .append("${G").append(i).append("}\n");
        }
        for(i = 0; i < n; i++) {
            s.append("<text x=\"").append(left + (40 * i) - 5).append("\" y=\"").append(h - 28).append("\"")
                    .append(" class=\"axis-label\">${X").append(i).append("}</text>\n");
        }
        for(i = 0; i < n; i++) {
            s.append("<circle cx=\"").append(left + (i * 40)).append("\" cy=\"${V").append(i)
                    .append("}\" r=\"3\" stroke=\"").append(values.getColor(values.get(i)))
                    .append("\"><title>${T").append(i).append("}</title></circle>\n");
        }
        String label = values.getLabelName();
        if(label != null) {
            s.append("<text x=\"").append(left - 5).append("\" y=\"").append(h - 15).append("\"")
                    .append(" class=\"axis-label\">").append(label).append(" →").append("</text>\n");
        }
        label = values.getValueName();
        if(label != null) {
            s.append("<text x=\"").append((left + 10) + (35 * n) + 10).append("\" y=\"").append(h - 40).append("\"")
                    .append(" class=\"axis-label\" transform=\"rotate(-90 ").append((left + 10) + (35 * n) + 10)
                    .append(",").append(h - 40).append(")\" text-anchor=\"start\">")
                    .append(label).append(" →").append("</text>");
        }
        String ss = s.toString();
        ss = ss.replace("${LC}", values.getLabelNameColor());
        return ss;
    }
}
