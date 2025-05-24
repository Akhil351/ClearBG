import React, { createContext } from "react";

export const AppContext = createContext();

const AppContextProvider = ({ children }) => {
  const backEndURL = import.meta.env.VITE_BACKEND_URL;
  const contextValue = {
    backEndURL,
  };

  return (
    <AppContext.Provider value={contextValue}>{children}</AppContext.Provider>
  );
};

export default AppContextProvider;
