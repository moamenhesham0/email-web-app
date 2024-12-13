package com.example.PaintApplication;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Triangle extends Polygon {
    protected double x1,y1;
    protected double x2,y2;
    protected double x3,y3;
public Triangle(){
    super();
}
    // Constructor for Triangle
    public Triangle(int x, int y, String color, double x1,double y1 ,double x2,double y2,double y3, double x3, String type, long width, long height,int strokeWidth,String fill) {
        super(x, y, color,type,width,height,strokeWidth,fill); // Triangle has 3 sides
        this.x1 = x1;
        this.y1=y1;
        this.y2=y2;
        this.y3=y3;
        this.x2 = x2;
        this.x3 = x3;
        this.type = type;

    }

    @Override
    public String getType() {
    return "Triangle";
    }
    public Triangle clone() {
    return new Triangle(x, y, color, x1,x2,x3,y1,y2,y3,type,width,height,strokeWidth,fill);
    }
}
