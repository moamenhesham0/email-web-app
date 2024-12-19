import React, { createContext, useContext, useState } from "react";

// Create Context
const SelectedEmailsContext = createContext();

// Custom hook to use context
export const useSelectedEmails = () => {
  return useContext(SelectedEmailsContext);
};

// Provider component
export const SelectedEmailsProvider = ({ children }) => {
  const [selectedEmails, setSelectedEmails] = useState([]);

  // Function to add an email to the selection
  const addEmail = (emailId) => {
    setSelectedEmails((prev) => [...prev, emailId]);
  };

  // Function to remove an email from the selection
  const removeEmail = (emailId) => {
    setSelectedEmails((prev) => prev.filter((id) => id !== emailId));
  };

  // Function to toggle email selection
  const toggleEmailSelection = (emailId) => {
    setSelectedEmails((prevSelectedEmails) => {
      // If the email is already selected, remove it from the selection
      if (prevSelectedEmails.includes(emailId)) {
        return prevSelectedEmails.filter((id) => id !== emailId);
      }
      // If the email is not selected, add it to the selection
      return [...prevSelectedEmails, emailId];
    });
  };

  // Function to select all emails
  const selectAllEmails = (emailIds) => {
    setSelectedEmails(emailIds);
  };

  // Function to unselect all emails
  const deselectAllEmails = () => {
    setSelectedEmails([]);
  };

  return (
    <SelectedEmailsContext.Provider
      value={{
        selectedEmails,
        addEmail,
        removeEmail,
        toggleEmailSelection,
        selectAllEmails,
        deselectAllEmails,
      }}
    >
      {children}
    </SelectedEmailsContext.Provider>
  );
};






