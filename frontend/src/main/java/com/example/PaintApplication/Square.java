package com.example.PaintApplication;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Square extends Polygon {
    private String type;
    private int x;
    private int y;
    private long width;
    private long height;
    private String color;
    private int strokeWidth;
    private String fill;
    public Square(){
        super();
    }
    public Square(int x, int y, String color,String type, long width, long height,int strokeWidth,String fill) {
        super(x, y, color,type, width,height,strokeWidth,fill);
        this.width = width;
        this.height = width;
    }
    @Override
    public String getType() {
        return "Square";
    }


    public Square clone() {
        return new Square(x, y, color,type, width,height,strokeWidth,fill);
    }
}
