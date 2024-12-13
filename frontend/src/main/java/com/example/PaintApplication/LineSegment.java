package com.example.PaintApplication;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class LineSegment extends Shape {
    private String type;
    private String color;
    private int strokeWidth;
    private int x;
    private int y;
    private int x2;
    private int y2;
    public LineSegment(){

    }
    @Override
    public String getType() {
        return "LineSegment";
    }
}

