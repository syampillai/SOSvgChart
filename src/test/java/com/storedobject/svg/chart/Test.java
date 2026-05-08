package com.storedobject.svg.chart;

public class Test {

    public static void main(String[] args) {
        Chart c;
        c = new DayPlot(new double[] { 108, 20, 30, 25, 50, 40, 70, 80, 90, 100 }, "", 20, 20, 5);
        c.setLabelName("Days");
        c.setValueName("In mg/lit");
        System.err.println(c.getSvg());
        System.err.println("---------------");
        c = new Bars();
        c.setUnit("%");
        c.setLabelName("Categories");
        c.setValueName("Category");
        c.addValue("Category A", 10);
        c.addValue("Category B", 90);
        c.addValue("Category C", 45);
        c.addValue("Category D", 20);
        c.addValue("Category E", 30);
        c.addValue("Category F", 50);
        c.addValue("Category G", 15);
        c.addValue("Category H", 100);
        System.err.println(c.getSvg());
        System.err.println("------------------");
        c = new Pie();
        c.addValue("Category A", 15);
        c.addValue("Category B", 15);
        c.addValue("Category C", 43);
        c.addValue("Category D", 17);
        c.addValue("Category E", 30);
        ((Pie)c).setDonutHoleRadius(40);
        System.err.println(c.getSvg());
    }
}
