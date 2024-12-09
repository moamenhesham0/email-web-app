import React from "react";
import PropTypes from "prop-types";
import "./SidebarOption.css";

function SidebarOption({ Icon, title, number, selected }) {
  return (
    <button 
      className={`sidebarOption ${selected ? "sidebarOption--active" : ""}`}
      aria-label={title}
    >
      {Icon && <Icon className="sidebarOption__icon" />}
      <h3 className="sidebarOption__title">{title}</h3>
      {number !== undefined && <p className="sidebarOption__number">{number}</p>}
    </button>
  );
}

SidebarOption.propTypes = {
  Icon: PropTypes.elementType,
  title: PropTypes.string.isRequired,
  number: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  selected: PropTypes.bool,
};

SidebarOption.defaultProps = {
  Icon: null,
  number: undefined,
  selected: false,
};

export default SidebarOption;
