import React, { useState, useEffect } from "react";
import "./Bottombar.css";

const Bottombar = () => {
  const [zoom, setZoom] = useState(100); // Default zoom level: 100%
  const [mousePosition, setMousePosition] = useState({ x: 0, y: 0 });

  useEffect(() => {
    const handleMouseMove = (e) => {
      setMousePosition({ x: e.clientX, y: e.clientY });
    };
    window.addEventListener("mousemove", handleMouseMove);

    // Cleanup event listener
    return () => {
      window.removeEventListener("mousemove", handleMouseMove);
    };
  }, []);

  const handleZoomChange = (e) => {
    setZoom(Number(e.target.value));
  };

  const incrementZoom = () => {
    setZoom((prevZoom) => Math.min(prevZoom + 10, 200)); // Max: 200%
  };

  const decrementZoom = () => {
    setZoom((prevZoom) => Math.max(prevZoom - 10, 10)); // Min: 10%
  };

  const resetZoom = () => setZoom(100);

  const [theme, setTheme] = useState(() => {
    // Retrieve the theme from localStorage or default to "light"
    return localStorage.getItem('theme') || 'light';
});
  useEffect(() => {
    // Apply the theme to the root element
    document.documentElement.setAttribute('data-theme', theme);
    // Save the theme preference
    localStorage.setItem('theme', theme);
  }, [theme]);

  const toggleTheme = () => {
    setTheme((prevTheme) => (prevTheme === 'light' ? 'dark' : 'light'));
};

  return (
    <div className="bottombar">
      <button className="clear">
        <img
            src="/icons/clear.svg"
            alt="clear"
        />
      </button>
        <div className="corrdinates">
            X: {mousePosition.x}, Y: {mousePosition.y}
        </div>
        <button className="theme-toggle" onClick={toggleTheme}>
                Switch to {theme === 'light' ? 'Dark' : 'Light'} Mode
            </button>
        <div className="zoom-toolbottom">
        <button className="zoom-btn" onClick={resetZoom}>
    Reset
</button>
            <button className="zoom-btn" onClick={decrementZoom}>
                -
            </button>
            <input
                type="range"
                className="zoom-slider"
                min="10"
                max="200"
                step="1"
                value={zoom}
                onChange={handleZoomChange}
            />
            <span className="zoom-percentage">{zoom}%</span>
            <button className="zoom-btn" onClick={incrementZoom}>
                +
            </button>
        </div>
    </div>
  );
};

export default Bottombar;
