package com.example.PaintApplication;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class IsoscelesTriangle extends Triangle{
    private String type;
    private double x1;
    private double y1;
    private double x2;
    private double y2;
    private double x3;
    private double y3;
    private String color;
    private int strokeWidth;
    private String fill;
    public IsoscelesTriangle() {
        super();
    }
    public IsoscelesTriangle(int x, int y, String color, double x1, double y1, double x2, double y2, double x3, double y3,String type,long width, long height,int strokeWidth,String fill) {
        super(x, y, color, x1, y1, x2, y2, y3, x3,type,width,height,strokeWidth,fill);
        //         if (!isIsoscelesTriangle(x1, y1, x2, y2, x3, y3)) {
        //     throw new IllegalArgumentException("The points do not form an isosceles triangle.");
        // }
    }

    private boolean isIsoscelesTriangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        double z1 = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        double z2 = Math.sqrt(Math.pow(x3 - x2, 2) + Math.pow(y3 - y2, 2));
        double z3 = Math.sqrt(Math.pow(x1 - x3, 2) + Math.pow(y1 - y3, 2));
        return Math.abs(z1 - z2) < 1e-6 || Math.abs(z2 - z3) < 1e-6 || Math.abs(z1 - z3) < 1e-6;
    }

    @Override
    public String getType() {
        return "IsoscelesTriangle";
    }
}
