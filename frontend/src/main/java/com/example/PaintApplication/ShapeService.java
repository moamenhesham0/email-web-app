package com.example.PaintApplication;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Service
public class ShapeService {
        @Autowired
        private ShapeRepository shapeRepository;
    private final Deque<List<Shape>> undoStack = new ArrayDeque<>();
    private final Deque<List<Shape>> redoStack = new ArrayDeque<>();
    public List<Shape> getAllShapes() {
        return shapeRepository.findAll();
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RuntimeException.class)
    public String handleNotFoundException(RuntimeException e) {
        return e.getMessage();
    }

    public Shape getShapeById(Long id) {
        return shapeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shape not found"));
    }
    @Transactional
    public Shape saveShape(Shape shape) {
        saveToUndoStack();
        return shapeRepository.save(shape);
    }
    public void deleteShape(Long id) {
        saveToUndoStack();
        shapeRepository.deleteById(id);
    }
    public Shape updateShape(Long id, Shape updatedShape) {
        saveToUndoStack();
        Shape existingShape = getShapeById(id);
        existingShape.setX(updatedShape.getX());
        existingShape.setY(updatedShape.getY());
        existingShape.setColor(updatedShape.getColor());
        return shapeRepository.save(existingShape);
    }
    public Shape resizeShape(Long id, double factor) {
        saveToUndoStack();
        Shape shape = getShapeById(id);
        if (shape instanceof EllipticalShape) {
            EllipticalShape elliptical = (EllipticalShape) shape;
            elliptical.setRadius1(elliptical.getRadius1() * factor);
            elliptical.setRadius2(elliptical.getRadius2() * factor);
        }
        else if(shape instanceof LineSegment){
            LineSegment Line =(LineSegment) shape;
            Line.setLength(Line.getLength()*factor);
            Line.setAngle(Line.getAngle()%360);
        }
        else if (shape instanceof Rectangle) {
            Rectangle rectangle = (Rectangle) shape;
        }
        else if(shape instanceof Square){
            Square square = (Square) shape;
            square.setWidth(square.getWidth() * factor);
        }
        else if(shape instanceof Triangle){
            Triangle triangle = (Triangle) shape;
            triangle.setX1((int) (triangle.getX1() * factor));
            triangle.setX2((int) (triangle.getX2() * factor));
            triangle.setX3((int) (triangle.getX3() * factor));
        }
        return shapeRepository.save(shape);
    }
    public Shape copyShape(Long id) {
        saveToUndoStack();
        Shape original = getShapeById(id);
        Shape copy = null;
        copy = (Shape) original.clone(); // Deep copy via cloning
        copy.setId(null); // Reset ID for new entity
        return shapeRepository.save(copy);
    }
    public void undo() {
        if (!undoStack.isEmpty()) {
            redoStack.push(new ArrayList<>(shapeRepository.findAll()));
            List<Shape> previousState = undoStack.pop();
            shapeRepository.deleteAll();
            shapeRepository.saveAll(previousState);
        }
    }
    public void redo() {
        if (!redoStack.isEmpty()) {
            undoStack.push(new ArrayList<>(shapeRepository.findAll()));
            List<Shape> nextState = redoStack.pop();
            shapeRepository.deleteAll();
            shapeRepository.saveAll(nextState);
        }
    }
    private void saveToUndoStack() {
        List<Shape> currentShapes = shapeRepository.findAll();
        List<Shape> deepCopy = new ArrayList<>();
        for (Shape shape : currentShapes) {
            deepCopy.add(shape.clone());
        }
        undoStack.push(deepCopy);
        redoStack.clear();
    }
    public void loadShapes(List<Shape> shapes) {
        shapeRepository.deleteAll();
        for (Shape i : shapes){shapeRepository.save(i);}
    }
    public Shape drawFreehand(List<Point> points, String toolType, String color, double thickness) {
        saveToUndoStack();
        Freehand freehandShape = new Freehand();
        freehandShape.setPoints(points);
        freehandShape.setToolType(toolType);
        freehandShape.setColor(color);
        freehandShape.setThickness(thickness);

        return shapeRepository.save(freehandShape);
    }
    
}
