package com.example.PaintApplication;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shapes")
@CrossOrigin(origins = "http://localhost:3000")
public class ShapeController {

    private final ShapeService shapeService;
    private final SketchManager sketchManager;

    @Autowired
    public ShapeController(ShapeService shapeService, SketchManager sketchManager) {
        this.shapeService = shapeService;
        this.sketchManager = sketchManager;
    }

    @GetMapping
    public List<Shape> getAllShapes() {
        return shapeService.getAllShapes();
    }

    @GetMapping("/{id}")
    public Shape getShapeById(@PathVariable Long id) {
        return shapeService.getShapeById(id);
    }

    @PostMapping
    public Shape createShape(@RequestBody Shape shape) {
        System.out.println("Received POST request to create a shape");
        return shapeService.saveShape(shape);
    }

    @PutMapping("/{id}")
    public Shape updateShape(@PathVariable Long id, @RequestBody Shape shape) {
        return shapeService.updateShape(id, shape);
    }

    @DeleteMapping("/{id}")
    public void deleteShape(@PathVariable Long id) {
        shapeService.deleteShape(id);
    }

    @PostMapping("/{id}/resize")
    public Shape resizeShape(@PathVariable Long id, @RequestParam double factor) {
        return shapeService.resizeShape(id, factor);
    }

    @PostMapping("/{id}/copy")
    public Shape copyShape(@PathVariable Long id) {
        return shapeService.copyShape(id);
    }

    @PostMapping("/undo")
    public void undo() {
        shapeService.undo();
    }

    @PostMapping("/redo")
    public void redo() {
        shapeService.redo();
    }

    @PostMapping("/save")
    public String saveShapes(@RequestParam String format, @RequestParam String filePath) {
        try {
            if ("json".equalsIgnoreCase(format)) {
                sketchManager.saveSketchAsJSON(filePath);
                return "Shapes saved successfully in JSON format.";
            } else if ("xml".equalsIgnoreCase(format)) {
                sketchManager.saveSketchAsXML(filePath);
                return "Shapes saved successfully in XML format.";
            } else {
                return "Invalid format. Please specify 'json' or 'xml'.";
            }
        } catch (IOException e) {
            return "Error saving shapes: " + e.getMessage();
        }
    }

    @PostMapping(value="/load")
public String loadShapes(@RequestParam String format,@RequestBody List<Shape>shapes) {
    for (Shape i : shapes){System.out.println(i);}
    try {
        if ("json".equalsIgnoreCase(format)) {
            sketchManager.loadSketchFromJSON(shapes);
            return "Shapes loaded successfully from JSON.";
        } else if ("xml".equalsIgnoreCase(format)) {
            sketchManager.loadSketchFromXML(shapes);
            return "Shapes loaded successfully from XML.";
        } else {
            return "Invalid format. Please specify 'json' or 'xml'.";
        }
    } catch (IOException e) {
        System.err.println("Error loading shapes: " + e.getMessage()); // Debugging
        return "Error loading shapes: " + e.getMessage();
    }
}


    @PostMapping("/freehand")
    public Shape drawFreehand(@RequestBody Freehand request) {
        return shapeService.drawFreehand(
                request.getPoints(),
                request.getToolType(),
                request.getColor(),
                request.getThickness()
        );
    }
}
