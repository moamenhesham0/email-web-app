import React, { useState } from "react";
import { v4 as uuidv4 } from 'uuid';
import axios from "axios";
import { saveAs } from "file-saver";
import xmljs from "xml-js";
import "./Toolbar.css";

const Toolbar = ({
  shapes,
  setShapes,
  onShapeSelect,
  selectedColor,
  onColorSelect,
  selectedSize,
  onSizeChange,
  setFillSelect,
  setselectedPencil,
  setselectedBrush,
  setselectedEraser,
  setselectedAirbrush,
  setselectedFloodFill,
  setselectedUndo,
  setUndoID,
  setUndoShape,
  setselectedRedo,
  setRedoShape,
  setSelectedText,
  textValue,
  setTextValue,
  setselectedCopy,
  setselectedPaste,
}) => {
  const [selectedWidth, setSelectedWidth] = useState(selectedSize || 1);
  const [currentColor, setCurrentColor] = useState(selectedColor || "#000000");
  const [textInputVisible, setTextInputVisible] = useState(false);

  const handleColorSelect = (color) => {
    setCurrentColor(color);
    onColorSelect(color); // Notify parent
  };

  const handleSizeChange = (size) => {
    setSelectedWidth(size);
    onSizeChange(size); // Notify parent
  };

  const ColorOptions = ({ colors, onColorSelect }) => (
    <div className="color-options">
      {colors.map((color, index) => (
        <span
          key={index}
          className="color-circle"
          style={{ backgroundColor: color }}
          title={color}
          onClick={() => onColorSelect(color)}
        ></span>
      ))}
    </div>
  );

  const ButtonGroup = ({ buttons }) => (
    <div className="buttons">
      {buttons.map((button, index) =>
      (
          <button key={index} title={button.label || button} onClick={() => ButtonFunctions(button.label)}>
            {button.icon ? (
              <img
                src={button.icon}
                alt={button.label || "icon"}
                className="button-icon"
              />
            ) : (
              button.label || button
            )}
          </button>
        )
      )}
    </div>
  );

  const saveAsFile = async (shapes) => {
    try {
      // Open the Save File Picker with options for JSON and XML formats
      const fileHandle = await window.showSaveFilePicker({
        suggestedName: "drawing",
        types: [
          {
            description: "Supported Formats",
            accept: {
              "application/json": [".json"],
              "application/xml": [".xml"],
            },
          },
        ],
      });
  
      // Determine the selected format based on the file extension
      const fileName = fileHandle.name;
      const format = fileName.endsWith(".json") ? "json" : fileName.endsWith(".xml") ? "xml" : null;
  
      if (!format) {
        throw new Error("Unsupported file format selected.");
      }
  
      // Prepare file content based on the selected format
      let content, mimeType;
      if (format === "json") {
        content = JSON.stringify(shapes, null, 2);
        mimeType = "application/json";
      } else if (format === "xml") {
        const xml = xmljs.js2xml({ shapes: { shape: shapes } }, { compact: true, spaces: 2 });
        content = xml;
        mimeType = "application/xml";
      }
  
      // Write the content to the file
      const writableStream = await fileHandle.createWritable();
      await writableStream.write(new Blob([content], { type: mimeType }));
      await writableStream.close();
  
      alert(`File saved successfully as ${fileHandle.name}`);
    } catch (err) {
      if (err.name !== "AbortError") {
        console.error("File save cancelled or failed:", err);
      }
    }
  };
  
  const loadFile = async () => {
    try {
      // Open the file picker for JSON and XML files
      const [fileHandle] = await window.showOpenFilePicker({
        types: [
          {
            description: "Supported Files",
            accept: {
              "application/json": [".json"],
              "application/xml": [".xml"],
            },
          },
        ],
        multiple: false,
      });
  
      // Get file content
      const file = await fileHandle.getFile();
      const content = await file.text();
  
      // Determine file type and parse accordingly
      let loadedShapes;
      if (file.name.endsWith(".json")) {
        loadedShapes = JSON.parse(content);
      } else if (file.name.endsWith(".xml")) {
        const parsedXML = xmljs.xml2js(content, { compact: true });
        loadedShapes = parsedXML.shapes.shape.map((shape) => ({
          ...shape,
          type: shape.type._text,
          id: shape.id._text,
          x: parseInt(shape.x._text),
          y: parseInt(shape.y._text),
          color: shape.color._text,
          width: parseInt(shape.width._text),
          height: parseInt(shape.height._text),
          strokeWidth: parseInt(shape.strokeWidth._text),
          fill: shape.fill._text,
          radius1: shape.radius1 ? parseFloat(shape.radius1._text) : 0,
          radius2: shape.radius1 ? parseFloat(shape.radius2._text) : 0,
          x1: shape.x1 ? parseInt(shape.x1._text) : null,
          y1: shape.y1 ? parseInt(shape.y1._text) : null,
          x2: shape.x2 ? parseInt(shape.x2._text) : null,
          y2: shape.y2 ? parseInt(shape.y2._text) : null,
          x3: shape.x3 ? parseInt(shape.x3._text) : null,
          y3: shape.y3 ? parseInt(shape.y3._text) : null,
        }));
      } else {
        throw new Error("Unsupported file format");
      }
  
      // Update shapes in state
      setShapes(loadedShapes);
      try{
        const response = await axios.post('http://localhost:8080/api/shapes/load', loadedShapes, {
          params: {
              format:file.name.endsWith(".json") ? "json" : "xml", // or "xml"
          }
      });
      console.log('Shapes saved successfully:', response.data);
      console.log(response.data);
    }catch (error){
      console.error('Server responded with error:', error.response.data);
    }
      alert(`File ${file.name} loaded successfully.`);
    } catch (err) {
      if (err.name !== "AbortError") {
        console.error("File load cancelled or failed:", err);
      }
    }
  };

  const ButtonFunctions = async (button,event,) => {
    onShapeSelect(null);
    setselectedPencil(false);
    setselectedBrush(false);
    setselectedEraser(false);
    setFillSelect(false);
    setselectedEraser(false)
    setselectedAirbrush(false);
    setselectedFloodFill(false);
    setselectedUndo(false);
    setselectedRedo(false);
    setSelectedText(false);
    setselectedCopy(false);
    setselectedPaste(false);
    switch (button) {
      case "Text":
      setTextInputVisible(true);
      setSelectedText(true);
      break;

      case "Flood":
      setselectedFloodFill(true);
      break;

      case "Airbrush":
      setselectedAirbrush(true);
      break;

      case "Eraser":
      setselectedEraser(true);
      break;

      case "Pencil":
      setselectedPencil(true);
      break;

      case "Brush":
      setselectedBrush(true);
      break;

      case "Outline only":
        setFillSelect(false);
        break;

      case "Fill":
        setFillSelect(true); // Assuming `setFillSelect` is a state updater
        break;
  
      case "Save":
      saveAsFile(shapes);
    break;

    case "Load":
      loadFile();
    break;

    case "Undo":
      setselectedUndo(true);
      try{
      const response = await axios.post('http://localhost:8080/api/shapes/undo')
      setShapes((prevShapes) => prevShapes.filter((shape) => String(shape.id) !== String(response.data.id)));
      console.log('Shapes undo successfully:');
    }catch (error){
      console.error('Server responded with error:', error.response.data);
    }
      break;

      case "Redo":
        setselectedRedo(true);
        try{
          const response = await axios.post('http://localhost:8080/api/shapes/redo');
          setShapes([...shapes, response.data]);
        console.log('Shapes redo successfully:');
      }catch (error){
        console.error('Server responded with error:', error.response.data);
      }
        break;

      case "Copy":
        setselectedCopy(true);
        break;

        case "Paste":
          setselectedPaste(true);
          break;

        default:
        console.warn(`Unknown button action: ${button}`);
    }
  };

  const ShapeSelector = ({ shape }) => (
    <div style={styles.container}>
      <div style={styles.grid}>
        {shape.map((shap) => (
          <button
            onClick={() => {
              onShapeSelect(shap.label)
              setselectedPencil(false);
              setselectedBrush(false);
              setselectedEraser(false);
              setselectedAirbrush(false);
              setselectedFloodFill(false);
              setselectedUndo(false);
              setSelectedText(false);
            }}
            key={shap.id}
            style={styles.button}
            title={shap.label}
          >
            {shap.icon ? (
              <img
                src={shap.icon}
                alt={shap.label}
                className="button-icon"
              />
            ) : (
              shap.label
            )}
          </button>
        ))}
      </div>
    </div>
  );

  const LineWidthMenu = ({ lineWidths, selectedWidth, onChange }) => (
    <div className="line-width-menu">
      <label htmlFor="line-width">Line Width:</label>
      <select
        id="line-width"
        value={selectedWidth}
        onChange={(e) => onChange(Number(e.target.value))}
      >
        {lineWidths.map((width, index) => (
          <option key={index} value={width}>
            {width}px
          </option>
        ))}
      </select>
    </div>
  );

  const buttonsConfig = {
    menu1: [
      { label: "Save", icon: "/icons/save.svg" },
      { label: "Load", icon: "/icons/load.svg" },
      { label: "Copy", icon: "/icons/copy.svg" },
    ],
    menu2: [
      { label: "Undo", icon: "/icons/undo.svg" },
      { label: "Redo", icon: "/icons/redo.svg" },
      { label: "Paste", icon: "/icons/paste.svg" },
    ],
    menu3: [
      { label: "Eraser", icon: "/icons/eraser.svg" },
      { label: "Pencil", icon: "/icons/pencil.svg" },
      { label: "Airbrush", icon: "/icons/airbrush.png" },
    ],
    menu4: [
      { label: "Flood" , icon: "/icons/flood.svg" },
      { label: "Brush", icon: "/icons/brush.svg" },
      { label: "Text", icon: "/icons/text.svg" },
    ],
    menu5: [
      { label: "Outline only", icon: "/icons/outline.svg" },
      { label: "Fill", icon: "/icons/fill.svg" },
      { label: "Outline and fill",},
    ],
  };

  const shape = [
    { id: uuidv4(), label: "Square", icon: "/icons/square.svg" },
    { id: uuidv4(), label: "Rectangle", icon: "/icons/rectangle.svg" },
    { id: uuidv4(), label: "Circle", icon: "/icons/circle.svg" },
    { id: uuidv4(), label: "Ellipse", icon: "/icons/ellipse.svg" },
    { id: uuidv4(), label: "LineSegment", icon: "/icons/line.svg" },
    { id: uuidv4(), label: "IsoscelesTriangle", icon: "/icons/iso_triangle.webp" },
    { id: uuidv4(), label: "EquilateralTriangle", icon: "/icons/equi_triangle.svg" },
    { id: uuidv4(), label: "RightTriangle", icon: "/icons/right_triangle.svg" },
  ];

  const row1Colors = ["black", "red", "green", "blue", "yellow"];
  const row2Colors = ["purple", "orange", "pink", "cyan", "lime"];
  const lineWidths = [1, 2, 4, 8, 16];

  return (
    <div className="topbar">
      <div className="toolbox">
        <ButtonGroup buttons={buttonsConfig.menu1} />
        <br />
        <ButtonGroup buttons={buttonsConfig.menu2} />
      </div>
      <span className="divider"></span>
      <div className="toolbox">
        <ButtonGroup buttons={buttonsConfig.menu3} />
        <br />
        <ButtonGroup buttons={buttonsConfig.menu4} />
      </div>
      <span className="divider"></span>
      <ShapeSelector shape={shape} />
      <span className="divider"></span>
      <div className="toolbox">
        <LineWidthMenu
          lineWidths={lineWidths}
          selectedWidth={selectedWidth}
          onChange={handleSizeChange}
        />
        <br />
        <ButtonGroup buttons={buttonsConfig.menu5} />
      </div>
      { textInputVisible && (
  <input
    type="text"
    style={{
      position: "absolute",
      top: "105px",
      left: "185px",
      fontSize: "24px",
      zIndex: 9999,
      backgroundColor: "white",
      border: "2px solid #ccc",
    }}
    value={textValue}
    onChange={(e) => setTextValue(e.target.value)}
    onBlur={() => setTextInputVisible(false)} // Hide input when it loses focus
    autoFocus
  />
)}
      <div className="color-picker-container">
        <button
          className="big-color-button"
          style={{ backgroundColor: currentColor }}
          title="Pick any color"
        >
          <span className="big-color-label">+</span>
        </button>
        <input
          type="color"
          className="big-color-input"
          value={currentColor}
          onChange={(e) => handleColorSelect(e.target.value)}
        />
      </div>
      <div className="toolcolorsbox">
        <ColorOptions colors={row1Colors} onColorSelect={handleColorSelect} />
        <br />
        <ColorOptions colors={row2Colors} onColorSelect={handleColorSelect} />
      </div>
    </div>
  );
};

const styles = {
  container: {
    height: "80px",
    padding: "10px",
    borderRight: "1px solid gray",
    backgroundColor: "#f0f0f0",
    clipPath: "inset(0 0 10px 0)",
  },
  grid: {
    display: "grid",
    gridTemplateColumns: "repeat(4, 50px)",
    gap: "15px",
  },
  button: {
    width: "30px",
    height: "30px",
    border: "1px solid #ccc",
    borderRadius: "4px",
    backgroundColor: "white",
    fontSize: "13px",
    textAlign: "center",
    cursor: "pointer",
    display: "flex", // Flexbox container
    alignItems: "center", // Vertically align content to the center
    justifyContent: "flex-start", // Horizontally align content to the left
    padding: "4.44px", // Optional, adjust as needed for spacing
  },
};

export default Toolbar;
