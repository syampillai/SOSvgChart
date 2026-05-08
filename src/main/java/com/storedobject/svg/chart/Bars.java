package com.storedobject.svg.chart;

/**
 * Horizontal Bar Chart.
 *
 * @author Syam
 */
public class Bars extends Chart {

    private static final String start = """
                  <defs>
                    <style>
                      text {
                        font-family: Arial, Helvetica, sans-serif;
                        font-size: 13px;
                        fill: #333;
                      }
                      .bar-label {
                        fill: black;
                        font-size: 12px;
                        font-weight: bold;
                      }
                      .x-axis-label {
                        font-size: 15px;
                        font-weight: bold;
                        fill: ${LNC};
                      }
                      .y-axis-label {
                        font-size: 15px;
                        font-weight: bold;
                        fill: ${VNC};
                      }
                    </style>
                  </defs>
                  <text class="y-axis-label" x="15" y="40" transform="rotate(90, 15, 40)" text-anchor="start">${YL} →</text>
                  <text class="x-axis-label" x="35" y="30" text-anchor="start">${XL} →</text>
                """;

    /**
     * Constructor.
     */
    public Bars() {
        this(360);
    }

    /**
     * Constructor with width.
     *
     * @param width Width of the chart.
     */
    public Bars(int width) {
        this(new Values(), width);
    }

    /**
     * Constructor with values and width.
     *
     * @param values Values to be plotted.
     * @param width Width of the chart.
     */
    public Bars(Values values, int width) {
        super(values);
        this.width = Math.max(width, 70);
        values.setLabelNameColor("#222");
        values.setValueNameColor("#222");
    }

    /**
     * Build the bar chart SVG.
     */
    @Override
    public final void build() {
        if(values.isBuilt()) {
            return;
        }
        height = 43 + (values.size() * 37);
        colorize();
        values.build();
        String labelName = values.getLabelName();
        if(labelName == null) {
            labelName = "";
        }
        String valueName = values.getValueName();
        if(valueName == null) {
            valueName = "";
        }
        StringBuilder svg = new StringBuilder(start.replace("${XL}", labelName)
                .replace("${YL}", valueName)
                .replace("${LNC}", values.getLabelNameColor())
                .replace("${VNC}", values.getValueNameColor()));
        double max = values.stream().mapToDouble(Values.Value::value).max().orElse(0);
        double w;
        int y = 42;
        String color;
        for(Values.Value bar: values.list()) {
            w = bar.value() / max * (width - 50);
            color = values.getColor(bar);
            svg.append("<rect x=\"35\" y=\"")
                    .append(y)
                    .append("\" width=\"")
                    .append(Values.toString(w))
                    .append("\" height=\"30\" rx=\"4\" ry=\"4\" fill=\"")
                    .append(color)
                    .append("\" />\n");
            svg.append("<text class=\"bar-label\" x=\"45\" y=\"")
                    .append(y + 20)
                    .append("\" text-anchor=\"start\">")
                    .append(bar.label())
                    .append(" — ")
                    .append(values.toUnit(bar))
                    .append("</text>\n");
            y += 37;
        }
        this.svg = svg.toString();
    }
}
