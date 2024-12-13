package com.example.PaintApplication;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Ellipse extends EllipticalShape{
    private String type;
    private int x;
    private int y;
    private String color;
    private double radius1;
    private double radius2;
    private int strokeWidth;
    private String fill;
public Ellipse(){
    super();
}
    public Ellipse(int x, int y, String color, double radius1,double radius2, String type,int strokeWidth,String fill,double opacity) {
        super(x, y, color, radius1, radius2,type, strokeWidth, fill,opacity);
    }


    @Override
    public String getType(){
        return "Ellipse";
    }
    public Ellipse clone() {
        return new Ellipse(x, y, color, radius1, radius2,type, strokeWidth, fill,opacity);
    }
}
