import React, { useEffect, useState } from 'react';
import axios from 'axios';


const BASE_URL = 'http://localhost:8080/api/shapes'; // Your Spring Boot API base URL

// Function to get all shapes
export const getShapes = async () => {
  try {
    const response = await axios.get(BASE_URL);
    console.log("hi");
    return response.data; // Returns the list of shapes
  } catch (error) {
    console.error("There was an error fetching the shapes!", error);
  }
};

// Function to get a shape by ID
export const getShapeById = async (id) => {
  try {
    const response = await axios.get(`${BASE_URL}/${id}`);
    return response.data; // Returns a single shape
  } catch (error) {
    console.error(`There was an error fetching the shape with ID ${id}`, error);
  }
};

// Function to create a new shape
export const createShape = async (shape) => {
  try {
    const response = await axios.post(BASE_URL, shape);
    return response.data; // Returns the created shape
  } catch (error) {
    console.error("There was an error creating the shape!", error);
  }
};

// Function to update an existing shape
export const updateShape = async (id, shape) => {
  try {
    const response = await axios.put(`${BASE_URL}/${id}`, shape);
    return response.data; // Returns the updated shape
  } catch (error) {
    console.error(`There was an error updating the shape with ID ${id}`, error);
  }
};

// Function to delete a shape
export const deleteShape = async (id) => {
  try {
    await axios.delete(`${BASE_URL}/${id}`);
  } catch (error) {
    console.error(`There was an error deleting the shape with ID ${id}`, error);
  }
};

// Function to resize a shape
export const resizeShape = async (id, factor) => {
  try {
    const response = await axios.post(`${BASE_URL}/${id}/resize`, null, {
      params: { factor },
    });
    return response.data; // Returns the resized shape
  } catch (error) {
    console.error(`There was an error resizing the shape with ID ${id}`, error);
  }
};

// Function to copy a shape
export const copyShape = async (id) => {
  try {
    const response = await axios.post(`${BASE_URL}/${id}/copy`);
    return response.data; // Returns the copied shape
  } catch (error) {
    console.error(`There was an error copying the shape with ID ${id}`, error);
  }
};

// Function for undo operation
export const undo = async () => {
  try {
    await axios.post(`${BASE_URL}/undo`);
  } catch (error) {
    console.error("There was an error performing the undo operation", error);
  }
};

// Function for redo operation
export const redo = async () => {
  try {
    await axios.post(`${BASE_URL}/redo`);
  } catch (error) {
    console.error("There was an error performing the redo operation", error);
  }
};