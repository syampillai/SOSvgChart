# SO SVG Chart

** Note: This project is discontinued. Please use the more powerfull SVG package - [SOsvg](https://github.com/syampillai/SOsvg)

SO SVG Chart is a lightweight Java library for generating SVG-based charts. It provides a simple and intuitive API to create various types of charts including Bar charts, Pie charts (including Donut charts), and simple Plots.

The main goal of this library is to provide a simple and efficient way to generate SVG charts in Java applications. The generated SVG code is highly scalable and can be easily embedded in web pages or other applications.

## Features

- **Lightweight**: Minimal dependencies (only depends on `so-common`).
- **SVG Output**: Generates clean, scalable SVG code.
- **Various Chart Types**:
    - **Bars**: Horizontal bar charts.
    - **Pie**: Classic pie charts and donut charts.
    - **Plots**: Simple plots for Days, Months, and Years.
- **Customizable**: Control labels, colors, units, and dimensions easily.

## Installation

Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>com.storedobject</groupId>
    <artifactId>so-svg-chart</artifactId>
    <version>0.0.2</version>
</dependency>
```

## Usage Examples

### 1. Bar Chart

```java
import com.storedobject.svg.chart.Bars;
import com.storedobject.svg.chart.Chart;

Chart c = new Bars();
c.setLabelName("Categories");
c.setValueName("Revenue");
c.setUnit("$");

c.addValue("Product A", 1200);
c.addValue("Product B", 2500);
c.addValue("Product C", 1800);

String svg = c.getSvg();
```

### 2. Pie / Donut Chart

```java
import com.storedobject.svg.chart.Pie;

Pie pie = new Pie();
pie.addValue("Red", 30);
pie.addValue("Green", 50);
pie.addValue("Blue", 20);

// To make it a donut chart:
pie.setDonutHoleRadius(40); // radius in pixels

String svg = pie.getSvg();
```

### 3. Day Plot

Plots allow you to visualize values over a period of time.

```java
import com.storedobject.svg.chart.DayPlot;
import java.util.Date;

double[] values = { 10.5, 20.0, 15.3, 30.2, 25.0, 40.0, 35.5 };
// Create a plot for the last 7 days ending today
DayPlot plot = new DayPlot(values, "mg/L", 0, 10, 5); 
plot.setLabelName("Days");
plot.setValueName("Concentration");

String svg = plot.getSvg();
```

## API Overview

### Common `Chart` Methods

- `addValue(Object label, double value)`: Adds a data point.
- `setLabelName(String labelName)`: Sets the name for the category/X-axis.
- `setValueName(String valueName)`: Sets the name for the value/Y-axis.
- `setUnit(String unit)`: Sets the unit of measurement (e.g., "%", "$").
- `getSvg()`: Returns the full SVG string.
- `getScaledSvg(double widthPercent, double heightPercent)`: Returns SVG with relative dimensions.

### Customization

- **Colors**: Charts automatically colorize data points, but you can customize colors via the `Values` object or specific chart settings.
- **Plots**: `Plot` classes allow setting `tickStart`, `tickStep`, and `tickCount` for the Y-axis.

## License

This project is licensed under the Apache License, Version 2.0. See the [LICENSE](https://www.apache.org/licenses/LICENSE-2.0.txt) for details.

## Developer

Syam Pillai (syam.s.pillai@gmail.com)
