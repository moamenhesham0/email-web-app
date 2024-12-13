package com.example.PaintApplication;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class RightTriangle extends Triangle{
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
    public RightTriangle() {
        super();
    }
    public RightTriangle(int x, int y, String color, boolean isVisible, double x1, double y1, double x2, double y2, double x3, double y3,String type,long width, long height,int strokeWidth,String fill) {
        super(x, y, color, x1, y1, x2, y2, y3, x3,type,width,height,strokeWidth,fill);
        //         if (!isRightTriangle(x1, y1, x2, y2, x3, y3)) {
        //     throw new IllegalArgumentException("The points do not form a right triangle.");
        // }
    }

    private boolean isRightTriangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        double z1 = Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2);
        double z2 = Math.pow(x3 - x2, 2) + Math.pow(y3 - y2, 2);
        double z3 = Math.pow(x1 - x3, 2) + Math.pow(y1 - y3, 2);
        return z1 + z2 == z3 || z2 + z3 == z1 || z3 + z1 == z2;
    }

    @Override
    public String getType() {
            return "RightTriangle";
    }
}
