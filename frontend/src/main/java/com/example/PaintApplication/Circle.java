package com.example.PaintApplication;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Circle extends EllipticalShape {
    private String type;
    private int x;
    private int y;
    private String color;
    private double radius1;
    private int strokeWidth;
    private String fill;
public Circle(){
    super();
}

    public Circle(int x, int y, String color, double radius1,String type,int strokeWidth,String fill,double opacity) {
        super(x, y, color, radius1, radius1, type,strokeWidth,fill,opacity);
    }

    @Override
    public String getType() {
        return "Circle";
    }

    @Override
    public Circle clone() {
        return new Circle(x, y, color, radius1, type,strokeWidth,fill,opacity);
    }
}
