import React, { createContext, useContext, useState } from "react";

// Create Context
const contactContext = createContext();

// Custom hook to use context
export const useSelectedContacts = () => {
  return useContext(contactContext);
};

// Provider component
export const SelectedContactsProvider = ({ children }) => {
  const [selectedContacts, setSelectedContacts] = useState([]);

  // Function to add a contact to the selection (if not already added)
  const addContact = (userName) => {
    setSelectedContacts((prev) => {
      // Only add contact if it's not already in the selected list
      if (!prev.includes(userName)) {
        return [...prev, userName];
      }
      // If the userName is already present, skip
      return prev;
    });
  };

  // Function to remove a contact from the selection (if it's already selected)
  const removeContact = (userName) => {
    setSelectedContacts((prev) => {
      // Only remove contact if it's in the selected list
      if (prev.includes(userName)) {
        return prev.filter((name) => name !== userName);
      }
      // If the userName is not in the list, skip
      return prev;
    });
  };

  // Function to clear all selected contacts
  const clear = () => {
    setSelectedContacts([]);
  };

  return (
    <contactContext.Provider
      value={{
        selectedContacts,
        addContact,
        removeContact,
        clear,
      }}
    >
      {children}
    </contactContext.Provider>
  );
};
