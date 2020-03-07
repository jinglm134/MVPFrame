package com.project.lib;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MyClass {

    double format = 31.006;

    /**
     * 保留两位小数，并四舍五入
     */
    public void format1() {
        BigDecimal bg = new BigDecimal(format);
        double f1 = bg.setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
        System.out.println(f1);
    }

    /**
     *      
     *     最简单     
     */
    public void format2() {
        DecimalFormat df = new DecimalFormat("#.00");
        System.out.println(df.format(format));
    }

    /**
     *  * String.format打印最简便
     *          
     */
    public void format3() {
        System.out.println(String.format("%.2f", format));
    }

    public void format4() {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        System.out.println(nf.format(format));
    }

    public static void main(String[] args) {
        MyClass f = new MyClass();
        f.format1();
        f.format2();
        f.format3();
        f.format4();
    }
}
