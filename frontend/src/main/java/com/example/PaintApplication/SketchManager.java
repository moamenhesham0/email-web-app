package com.example.PaintApplication;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@Component
public class SketchManager {
    private final ShapeService shapeService;

    public SketchManager(ShapeService shapeService) {
        this.shapeService = shapeService;
    }

    // Save sketch to XML
    public void saveSketchAsXML(String filePath) throws IOException {
        List<Shape> shapes = shapeService.getAllShapes();
        Sketch sketch = new Sketch(shapes);

        File file = new File(filePath);
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.writeValue(file, sketch);
    }

    // Save sketch to JSON
    public void saveSketchAsJSON(String filePath) throws IOException {
        List<Shape> shapes = shapeService.getAllShapes();
        Sketch sketch = new Sketch(shapes);

        File file = new File(filePath);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(file, sketch);
    }

    // Load sketch from XML
    public void loadSketchFromXML(List<Shape>shapes) throws IOException {
        // File file = new File(filePath);
        // XmlMapper xmlMapper = new XmlMapper();
        // Sketch sketch = xmlMapper.readValue(file, Sketch.class);
        shapeService.loadShapes(shapes);
    }

    // Load sketch from JSON
    public void loadSketchFromJSON(List<Shape>shapes) throws IOException {
        // System.out.println("Original file path: " + filePath);
        // filePath = URLDecoder.decode(filePath, StandardCharsets.UTF_8);
        // System.out.println("Decoded file path: " + filePath);
        // File file = new File(filePath);
        // if (!file.exists()) {
        //     throw new IOException("File not found at: " + filePath);
        // }
    
        // ObjectMapper objectMapper = new ObjectMapper();
        // System.out.println("");
        // Sketch sketch = objectMapper.readValue(file, Sketch.class);
        shapeService.loadShapes(shapes);
    }
    
}
