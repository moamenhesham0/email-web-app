package com.example.PaintApplication;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class Polygon extends Shape {
    public Polygon(){

    }
    public Polygon(int x, int y, String color,String type, long width, long height,int strokeWidth,String fill) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.type = type;
        this.width = width;
        this.height = height;
        this.strokeWidth = strokeWidth;
        this.fill = fill;

    }
    @Override
    public String getType() {
        return "Polygon";
    }
}

