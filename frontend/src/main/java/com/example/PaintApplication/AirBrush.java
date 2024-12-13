package com.example.PaintApplication;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class AirBrush extends EllipticalShape{
    private double opacity;
    public AirBrush(){
        super();
    }

    public AirBrush(int x, int y, String color, double radius1,String type,int strokeWidth,String fill,double opacity) {
        super(x, y, color, radius1, radius1, type,strokeWidth,fill,opacity);
    }

    @Override
    public String getType() {
        return "AirBrush";
    }

    @Override
    public AirBrush clone() {
        return new AirBrush(x, y, color, radius1, type,strokeWidth,fill,opacity);
    }
}
