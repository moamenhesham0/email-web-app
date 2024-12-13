import React, { useRef, useState } from "react";
import axios from "axios";
import { Stage, Layer, Rect, Circle, Ellipse, Line, RegularPolygon, Transformer,Text } from "react-konva";

const Canvas = ({ 
  shapes,
  setShapes,
  selectedShape,
  selectedColor,
  selectedSize,
  selectedFill,
  selectedPencil,
  selectedBrush,
  selectedEraser,
  selectedAirbrush,
  selectedFloodFill,
  selectedUndo,
  setselectedUndo,
  setUndoID,
  UndoID,
  UndoShape,
  selectedRedo,
  setselectedRedo,
  RedoShape,
  setSelectedText,
  selectedText,
  textValue,
  setTextValue,
  selectedCopy,
  setselectedCopy,
  selectedPaste,
  setselectedPaste,
  }) => {
  const [isDrawing, setIsDrawing] = useState(false);
  const [newShape, setNewShape] = useState(null);
  const [lines, setLines] = useState([]);
  const [currentLine, setCurrentLine] = useState([]);
  const [Blines, setBLines] = useState([]);
  const [BcurrentLine, setBCurrentLine] = useState([]);
  const [airbrushCircles, setAirbrushCircles] = useState([]);
  const stageRef = useRef(null);
  const transformerRef = useRef(null);
  const [selectedId, setSelectedId] = useState(null);
  const [texts, setTexts] = useState([
    { id: 1, text: 'Click to edit', x: 50, y: 50 },
  ]);
    const [textOP, setTextOP] = useState(0);
  const [isEditing, setIsEditing] = useState(true);
  const [editingText, setEditingText] = useState("");
  const [inputPosition, setInputPosition] = useState({ x: 0, y: 0 });
  const inputRef = useRef(null);
  const [copyShape, setcopyShape] = useState(null);


  const handleShapeSelect = async (Shape) => {
    const stage = stageRef.current;
    const pointerPosition = stage.getPointerPosition();
    console.log(Shape);
    try {
      const apiUrl = `http://localhost:8080/api/shapes`;
      const response = await axios.post(apiUrl, Shape);
      console.log("Created Shape:", response.data);
      console.log(response.data.id);
      return response.data;
    } catch (error) {
      console.error("Error creating shape:", error);
      alert("Failed to create shape.");
    }
  };

  const handleMouseDown = async (e) => {
    if (transformerRef.current) {
      transformerRef.current.getLayer().batchDraw();
    }
    else if(selectedPencil){
      setIsDrawing(true);
      const position = e.target.getStage().getPointerPosition();
      setCurrentLine([position.x, position.y]);
    }
    else if(selectedBrush){
      setIsDrawing(true);
      const position = e.target.getStage().getPointerPosition();
      setBCurrentLine([position.x, position.y]);
    }
    else if(selectedAirbrush){
      setIsDrawing(true);
    }
    else if(selectedEraser){
      const clickedShape = e.target.attrs.id;
      console.log(clickedShape);
      if (clickedShape) {
      console.log("clickedShape");
      deleteShape(String(clickedShape));
      deleteBrush(String(clickedShape));
      deleteLine(String(clickedShape));
      deleteAirbrush(String(clickedShape));
      }
    }else if(selectedText){
      const pos = e.target.getStage().getPointerPosition();
  
      setInputPosition({x: pos.x + 10,y: pos.y + 10})
      const newShape = {
        id: `${Date.now()}-${Math.random()}`,
        type: "Text",
        text: textValue,
        x: pos.x,
        y: pos.y,
        color: selectedColor,
      };
      setTextValue("");
      console.log(newShape.id);
      setSelectedText(false);
      setShapes((prevShapes) => [...prevShapes, newShape]);
      setTextOP(1);
      setIsEditing(true);
    }

    else if (selectedShape) {
      const fill = selectedFill ? selectedColor : "transparent";
      if (selectedShape === "IsoscelesTriangle" || selectedShape === "EquilateralTriangle" || selectedShape === "RightTriangle") {
        const pos = e.target.getStage().getPointerPosition();
        setNewShape({
          id: `${Date.now()}-${Math.random()}`,
          type: selectedShape,
          x1: pos.x,
          y1: pos.y,
          color: selectedColor,
          fill,
          strokeWidth: selectedSize,
        });
      }
      else{
        const pos = e.target.getStage().getPointerPosition();
      const initialShape = {
        id: `${Date.now()}-${Math.random()}`,
        type: selectedShape,
        x:  pos.x,
        y: pos.y,
        color: selectedColor,
        fill,
        strokeWidth: selectedSize || 2,
      };
      setNewShape(initialShape);
    }

      setIsDrawing(true);
    } else if (e.target.attrs.id) {
      handleSelect(String(e.target.attrs.id)); // Ensure ID is a string
    } else {
      handleDeselect();
    }
  };

  const handleMouseClick = () => {
    if (!selectedFloodFill) return;  
    
    setShapes((prevShapes) => {
      return prevShapes.map((shape) => ({
          ...shape,
          fill: selectedColor,
      }));
  });
    }
  

  const handleMouseMove = async (e) => {
    if (!isDrawing) return;
    if(selectedPencil){
      const stage = e.target.getStage();
      const position = stage.getPointerPosition();
      setCurrentLine((prev) => [...prev, position.x, position.y]);
      }
    else if( selectedBrush){
      const stage = e.target.getStage();
      const position = stage.getPointerPosition();
      setBCurrentLine((Bprev) => [...Bprev, position.x, position.y]);
      }
    else if( selectedAirbrush){
      const stage = e.target.getStage();
      const pointerPosition = stage.getPointerPosition();
    
      // Create small circles (airbrush effect)
      const newCircle = {
        id: `${Date.now()}-${Math.random()}`,
        type: "AirBrush",
        x: pointerPosition.x + Math.random() * 10 - 5, // Random variation for a "spray" effect
        y: pointerPosition.y + Math.random() * 10 - 5, // Random variation
        radius1: Math.random() * 5 + 2, // Random radius to vary size
        opacity: Math.random() * 0.3 + 0.1, // Semi-transparent
        color: selectedColor, // Color from the airbrush tool
        strokeWidth: selectedSize, // Brush size
      };
    
      // Add this new circle to the airbrushCircles state
      const shapeData = await handleShapeSelect(newCircle);
      setShapes((prev) => [...prev, shapeData]);
      }
    else if (selectedShape === "EquilateralTriangle") {
      const pos = e.target.getStage().getPointerPosition();
      const { x1, y1 } = newShape;
      const sideLength = pos.x - x1;  // Horizontal distance is the side length
  
      // Second vertex (B) is directly horizontally to the right
      const x2 = x1 + sideLength;
      const y2 = y1; // Same y-coordinate as the first vertex
    
      // Third vertex (C) is vertically above the midpoint of AB
      const height = (sideLength * Math.sqrt(3)) / 2;  // Height of the equilateral triangle
      const x3 = (x1 + x2) / 2;  // Midpoint of AB
      const y3 = y1 - height;  // Height above the base

      setNewShape((prev) => ({
        ...prev,
        x2,
        y2,
        x3,
        y3,
      }));

    }
    else if (selectedShape === "IsoscelesTriangle") {
      const pos = e.target.getStage().getPointerPosition();
      const { x1, y1 } = newShape;

      const x2 = pos.x;
      const y2 = y1; // Same y-coordinate as the first vertex
    

      const x3 = (x1 + x2) / 2;  // Midpoint of AB
      const y3 = pos.y;  // Height above the base
    

      setNewShape((prev) => ({
        ...prev,
        x2,
        y2,
        x3,
        y3,
      }));

    }
    else if (selectedShape === "RightTriangle") {
      const pos = e.target.getStage().getPointerPosition();
      const { x1, y1 } = newShape;

      const x2 = pos.x;
      const y2 = y1; // Same y-coordinate as the first vertex
    
      const x3 = x1;  // Midpoint of AB
      const y3 = pos.y;  // Height above the base
    

      setNewShape((prev) => ({
        ...prev,
        x2,
        y2,
        x3,
        y3,
      }));

    }
    else if (selectedShape==="LineSegment" && newShape && newShape.type === "LineSegment") {
      const pos = e.target.getStage().getPointerPosition();
      setNewShape((prev) => ({
        ...prev,
        x2: pos.x,
        y2: pos.y,
      }));
    }else if(selectedShape === "Circle"){
      const stage = stageRef.current;
      const pointerPosition = stage.getPointerPosition();
  
      const updatedShape = {
        ...newShape,
        radius1: Math.abs(pointerPosition.x - newShape.x)/2,
      };
      setNewShape(updatedShape);
    }else if(selectedShape === "Ellipse"){
      const stage = stageRef.current;
      const pointerPosition = stage.getPointerPosition();
  
      const updatedShape = {
        ...newShape,
        radius1: Math.abs(pointerPosition.x - newShape.x)/2,
        radius2: Math.abs(pointerPosition.y - newShape.y)/2,
      };
      setNewShape(updatedShape);
    }else if(selectedShape === "Square"){
      const stage = stageRef.current;
      const pointerPosition = stage.getPointerPosition();
  
      const updatedShape = {
        ...newShape,
        width: pointerPosition.x - newShape.x,
        height: pointerPosition.x - newShape.x,
      }
      setNewShape(updatedShape);
    }
    else{
    const stage = stageRef.current;
    const pointerPosition = stage.getPointerPosition();

    const updatedShape = {
      ...newShape,
      width: pointerPosition.x - newShape.x,
      height: pointerPosition.y - newShape.y,
    };

    setNewShape(updatedShape);
  }
  };
  // const getCanvasBackgroundColor = () => {
  //   const rootStyles = getComputedStyle(document.documentElement);
  //   return rootStyles.getPropertyValue('--canvas-bg').trim();
  // };
  const handleMouseUp = async () => {
    if (isDrawing && selectedPencil ) {
      const newLineStroke = {
        id: `${Date.now()}-${Math.random()}`,
          type: "Pencil",
        points: currentLine,
        color: "black",
        strokeWidth: 2,
      };
  
      const shapeData = await handleShapeSelect(newLineStroke);
      setShapes([...shapes, shapeData]);
      setCurrentLine([]);
    }
    else if (isDrawing && selectedAirbrush) {
      setIsDrawing(false);
    }
    else if (isDrawing && selectedBrush) {
      const newBrushStroke = {
        id: `${Date.now()}-${Math.random()}`,
        type: "Brush",
        points: BcurrentLine,
        color: selectedColor, // Use the selected color for this stroke
        strokeWidth: selectedSize, // Use the selected stroke size
      };
      const shapeData = await handleShapeSelect(newBrushStroke);
      setShapes([...shapes, shapeData]);
      setBCurrentLine([]);
    }
  
    else if (isDrawing && newShape) {
      const shapeData = await handleShapeSelect(newShape);
      setShapes([...shapes, shapeData]);
    }
    setIsDrawing(false);
    setNewShape(null);
  };

  const handleSelect = (id) => {
    console.log("l");
    if(selectedFloodFill){
      handleMouseClick(new MouseEvent("click"),id);
    }else if(selectedCopy){
      const shapeToCopy = shapes.find((shape) => String(shape.id) === String(id));
      if (shapeToCopy) {
        const newShape = {
          ...shapeToCopy, // Copy all properties of the shape
          id: `${Date.now()}-${Math.random()}` // Assign a new unique ID
        };
        setcopyShape(newShape);
      }
      console.log(copyShape);
      setselectedCopy(false);
    }
    else{
      console.log("gg");
    setSelectedId(id);
    selectedShape = null;
    const node = stageRef.current.findOne(`#${id}`);
    if (node && transformerRef.current) {
      transformerRef.current.nodes([node]);
      transformerRef.current.getLayer().batchDraw();
    }
  }
  };

  const handleDeselect = () => {
    setSelectedId(null);
    if (transformerRef.current) {
      transformerRef.current.nodes([]);
      transformerRef.current.getLayer().batchDraw();
    }
  };

  const handleTransformerChange = (id, node) => {
    const updatedShapes = shapes.map((shape) => {
      if (Number(shape.id) === Number(id)) {
        switch (shape.type) {
          case "Rectangle":
          case "Square":
            return {
              ...shape,
              x: node.x(),
              y: node.y(),
              width: node.width(),
              height: node.height(),
            };
  
          case "Circle":
            return {
              ...shape,
              x: node.x(),
              y: node.y(),
              radius1: node.width() / 2,
            };
  
          case "Ellipse":
            return {
              ...shape,
              x: node.x(),
              y: node.y(),
              radius1: node.width() / 2,
              radius2: node.height() / 2,
            };
  
          case "LineSegment":
            return {
              ...shape,
              x: node.x(),
              y: node.y(),
              x2: node.x() + (node.width() || 0),
              y2: node.y() + (node.height() || 0),
            };
  
          case "IsoscelesTriangle":
          case "EquilateralTriangle":
          case "RightTriangle":
            return {
              ...shape,
              x1: node.x(),
              y1: node.y(),
              x2: node.x() + (node.width() || 0),
              y2: node.y(),
              x3: node.x() + (node.width() || 0) / 2,
              y3: node.y() + (node.height() || 0),
            };
  
          default:
            return shape; // Leave unhandled shapes untouched
        }
      }
      return shape;
    });
  
    setShapes(updatedShapes);
  };
  


  const deleteShape = (id) => {
    console.log("clickedShape");
    setShapes((prevShapes) => prevShapes.filter((shape) => String(shape.id) !== id));
  };
  const deleteBrush = (id) => {
    setBLines((prevShapes) => prevShapes.filter((shape) => String(shape.id) !== id));
  };
  const deleteLine = (id) => {
    setLines((prevShapes) => prevShapes.filter((shape) => String(shape.id) !== id));
  };
  const deleteAirbrush = (id) => {
    setAirbrushCircles((prev) => prev.filter((shape) => String(shape.id) !== id));
  };
  const clearAllAirbrushes = () => {
    setAirbrushCircles([]);
  };

  const paste = (e) => {
    if (selectedPaste) {
      const pos = e.target.getStage().getPointerPosition();
      
      // Create a new copy of the shape with updated position
      const newShape = {
        ...copyShape, // Copy all properties of the shape
        x: pos.x,     // Update x position
        y: pos.y,      // Update y position
        id: `${Date.now()}-${Math.random()}`,
      };
      console.log(newShape);
      // Add the new shape to the shapes array
      setShapes([...shapes, newShape]);
      handleShapeSelect(newShape);
      // Reset selectedPaste
      setselectedPaste(false);
    }
  };

  return (
    <Stage
      ref={stageRef}
      width={1919}
      height={800}
      onMouseDown={handleMouseDown}
      onMouseMove={handleMouseMove}
      onMouseUp={handleMouseUp}
      onClick={(e) => {
        // Deselect if clicked outside
        if (e.target === e.target.getStage()) {
          setSelectedId(null);
          paste(e);
          // if(selectedText){
          // handleTextClick(e);
          // }
        }else{
           handleMouseClick(); // Handle mouse click normally
}
      }}
      style={{ border: "1px solid black" }}
    >
      <Layer>
        {shapes.map((shape) => {
          if (shape.type === "Text") {
            return (
            <Text
              key={shape.id}              // Use the unique ID as the key
              id={shape.id}
              text={shape.text}
              x={shape.x}
              y={shape.y}
              color = {shape.color}
              opacity={1}
              fontSize={24}
              draggable
            />
            );
          }
          if (shape.type === "Pencil") {
            return (
            <Line
            key={shape.id}              // Use the unique ID as the key
            id={shape.id}
            points={shape.points}       // Points for this stroke
            stroke={shape.color}        // Color for this stroke
            strokeWidth={shape.strokeWidth} // Stroke width for this stroke
            lineCap="round"
            lineJoin="round"
        />
            );
          }
          if (shape.type === "AirBrush") {
            return (
              <Circle
              key={shape.id} // Use id as the key
              id={shape.id} // Assign id to each circle
              x={shape.x}
              y={shape.y}
              radius={shape.radius1}
              fill={shape.color}
              opacity={shape.opacity}
              strokeWidth={shape.strokeWidth}
              onClick={() => clearAllAirbrushes()}
            />
            );
          }
          if (shape.type === "Brush") {
            return (
            <Line
            key={shape.id}              // Use the unique ID as the key
            id={shape.id}
            points={shape.points}
            stroke={shape.color}
            strokeWidth={shape.strokeWidth}
            lineCap="round"
            lineJoin="round"
        />
            );
          }
          if (shape.type === "Rectangle") {
            return (
              <Rect
                key={String(shape.id)} // Ensure key is a string
                id={String(shape.id)} // Ensure id is a string
                x={shape.x}
                y={shape.y}
                width={shape.width}
                height={shape.height}
                stroke={shape.color}
                strokeWidth={shape.strokeWidth}
                fill={shape.fill}
                draggable
                onClick={() => handleSelect(String(shape.id))} // Pass ID as a string
                isSelected={selectedId === shape.id}
                onSelect={() => setSelectedId(shape.id)}
              />
            );
          }
          if (shape.type === "Circle") {
            return (
              <Circle
                key={String(shape.id)} // Ensure key is a string
                id={String(shape.id)} // Ensure id is a string
                x={shape.x}
                y={shape.y}
                radius={shape.radius1}
                stroke={shape.color}
                strokeWidth={shape.strokeWidth}
                fill={shape.fill}
                draggable
                onClick={() => handleSelect(String(shape.id))} // Pass ID as a string
              />
            );
          }
          if (shape.type === "Square") {
            return (
              <Rect
                key={String(shape.id)}
                id={String(shape.id)}
                x={shape.x}
                y={shape.y}
                width={Math.abs(shape.width)}
                height={Math.abs(shape.width)} // Square: equal width and height
                stroke={shape.color}
                strokeWidth={shape.strokeWidth}
                fill={shape.fill}
                draggable
                onClick={() => handleSelect(String(shape.id))}
              />
            );
          }

          if (shape.type === "Ellipse") {
            return (
              <Ellipse
                key={String(shape.id)}
                id={String(shape.id)}
                x={shape.x}
                y={shape.y}
                radiusX={shape.radius1} // Half width for radiusX
                radiusY={shape.radius2} // Half height for radiusY
                stroke={shape.color}
                strokeWidth={shape.strokeWidth}
                fill={shape.fill}
                draggable
                onClick={() => handleSelect(String(shape.id))}
              />
            );
          }
          if (shape.type === "LineSegment") {
            return (
              <Line
                key={String(shape.id)}
                id={String(shape.id)}
                points={[shape.x, shape.y, shape.x2, shape.y2]}
                stroke={shape.color}
                strokeWidth={shape.strokeWidth}
                draggable
                onClick={() => handleSelect(String(shape.id))}
              />
            );
          }
          if (shape.type === "EquilateralTriangle" || shape.type === "IsoscelesTriangle"|| shape.type === "RightTriangle") {
            return (
              <Line
                key={String(shape.id)}
                id={String(shape.id)}
                points={[
                  shape.x1,
                  shape.y1,
                  shape.x2,
                  shape.y2,
                  shape.x3,
                  shape.y3,
                ]}
                closed
                stroke={shape.color}
                strokeWidth={shape.strokeWidth}
                fill={shape.fill}
                draggable
                onClick={() => handleSelect(String(shape.id))}
              />
            );
          }
          return null;
        })}

        {newShape && newShape.type === "Rectangle" && (
          <Rect
            x={newShape.x}
            y={newShape.y}
            width={newShape.width}
            height={newShape.height}
            stroke={newShape.color}
            strokeWidth={newShape.strokeWidth}
            fill={newShape.fill}
          />
        )}
        {newShape && newShape.type === "Square" && (
          <Rect
            x={newShape.x}
            y={newShape.y}
            width={newShape.width}
            height={newShape.width}
            stroke={newShape.color}
            strokeWidth={newShape.strokeWidth}
            fill={newShape.fill}
          />
        )}

        {newShape && newShape.type === "Circle" && (
          <Circle
            x={newShape.x}
            y={newShape.y}
            radius={newShape.radius1}
            stroke={newShape.color}
            strokeWidth={newShape.strokeWidth}
            fill={newShape.fill}
          />
        )}

        {newShape && newShape.type === "Ellipse" && (
        <Ellipse
          x={newShape.x}
          y={newShape.y}
          radiusX={newShape.radius1}
          radiusY={newShape.radius2}
          stroke={newShape.color}
          strokeWidth={newShape.strokeWidth}
          fill={newShape.fill}
          />
      )}

        {newShape && newShape.type === "LineSegment" && (
          <Line
            points={[newShape.x, newShape.y, newShape.x2, newShape.y2]}
            stroke={newShape.color}
            strokeWidth={newShape.strokeWidth}
          />
        )}

        {newShape && (newShape.type === "EquilateralTriangle" || newShape.type === "IsoscelesTriangle"|| newShape.type === "RightTriangle") && (
          <Line
            points={[newShape.x1, newShape.y1, newShape.x2, newShape.y2, newShape.x3, newShape.y3]}
            closed
            stroke={newShape.color}
            strokeWidth={newShape.strokeWidth}
            fill={newShape.fill}
          />
        )}
  
{isDrawing && selectedPencil && currentLine.length > 0 && (
  <Line
    points={currentLine}
    stroke="black"
    strokeWidth={2}
    lineCap="round"
    lineJoin="round"
  />
)}

{isDrawing && selectedBrush && BcurrentLine.length > 0 && (
  <Line
  points={BcurrentLine}
  stroke={selectedColor}      // Use selected color
  strokeWidth={selectedSize}  // Use selected size
  lineCap="round"
  lineJoin="round"
  />
)}

        {selectedId &&
          (() => {
            const node = stageRef.current.findOne(`#${selectedId}`);
            return (
              <Transformer
                ref={transformerRef}
                nodes={[node]}
                onTransformEnd={() => {
                  handleTransformerChange(selectedId, node);
                }}
                borderStroke="blue"
                borderStrokeWidth={2}
                anchorStroke="red"
                anchorFill="red"
                anchorSize={8}
              />
            );
          })()}
      </Layer>
    </Stage>
  );
};

export default Canvas;
