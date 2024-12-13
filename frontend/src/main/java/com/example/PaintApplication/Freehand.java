package com.example.PaintApplication;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.util.List;
@Data
@MappedSuperclass
public class Freehand extends Shape{
    private List<Point> points; // List of points representing the freehand path

    private String toolType; // "Pencil", "Brush", "Airbrush"
    private double thickness; // Line thickness

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public String getToolType() {
        return toolType;
    }

    public void setToolType(String toolType) {
        this.toolType = toolType;
    }

    public double getThickness() {
        return thickness;
    }

    public void setThickness(double thickness) {
        this.thickness = thickness;
    }

    @Override
    public String getType() {
        return toolType;
    }
}
