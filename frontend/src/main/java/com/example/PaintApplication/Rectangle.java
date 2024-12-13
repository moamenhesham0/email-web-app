package com.example.PaintApplication;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)

@Data
@Entity
public class Rectangle extends Polygon {
    private String type;
    private int x;
    private int y;
    private long width;
    private long height;
    private String color;
    private int strokeWidth;
    private String fill;

    public Rectangle() {
        super();
    }

    public Rectangle(int x, int y, String color,String type, long width, long height,int strokeWidth,String fill) {
        super(x, y, color,type, width,height,strokeWidth,fill);
    }

    @Override
    public String getType() {
        return "Rectangle";
    }


    public Rectangle clone() {
        return new Rectangle(x, y, color,type, width,height,strokeWidth,fill);
    }
}
