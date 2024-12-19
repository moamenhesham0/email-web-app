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
 // Function to add an email to the selection (if not already added)
const addEmail = (emailId) => {
  setSelectedEmails((prev) => {
    // Only add email if it's not already in the selected list
    if (!prev.includes(emailId)) {
      return [...prev, emailId];
    }
    // If the emailId is already present, skip
    return prev;
  });
};

// Function to remove an email from the selection (if it's already selected)
const removeEmail = (emailId) => {
  setSelectedEmails((prev) => {
    // Only remove email if it's in the selected list
    if (prev.includes(emailId)) {
      return prev.filter((id) => id !== emailId);
    }
    // If the emailId is not in the list, skip
    return prev;
  });
};




  return (
    <SelectedEmailsContext.Provider
      value={{
        selectedEmails,
        addEmail,
        removeEmail,
      }}
    >
      {children}
    </SelectedEmailsContext.Provider>
  );
};






