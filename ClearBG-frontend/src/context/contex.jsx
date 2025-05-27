import { useAuth, useClerk, useUser } from "@clerk/clerk-react";
import axios from "axios";
import React, { createContext, useState } from "react";
import toast from "react-hot-toast";
import { useNavigate } from "react-router-dom";

export const AppContext = createContext();

const AppContextProvider = ({ children }) => {
  const { getToken } = useAuth();
  const [image, setImage] = useState(false);
  const [resultImage, setResultImage] = useState(false);
  const backEndURL = import.meta.env.VITE_BACKEND_URL;
  const [credit, setCredit] = useState(false);
  const { isSignedIn } = useUser();
  const { openSignIn } = useClerk();
  const navigate = useNavigate();
  const loadUserCredits = async () => {
    try {
      const token = await getToken();
      const response = await axios.get(backEndURL + "/users/credits", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      if (response.data.success) {
        setCredit(response.data.data.credits);
      } else {
        toast.error("Error loading credits.");
      }
    } catch (error) {
      toast.error("Error loading credits.");
    }
  };

  const removeBg = async (selectedImage) => {
    try {
      if (!isSignedIn) {
        return openSignIn();
      }
      if (credit <= 0) {
        navigate("/pricing");
        toast.error("You have no credits")
        return;
      }
      setImage(selectedImage);
      setResultImage(false);
      navigate("/result");

      const token = await getToken();
      const formData = new FormData();
      selectedImage && formData.append("file", selectedImage);

      const { data: base64Image } = await axios.post(
        backEndURL + "/images/remove-background",
        formData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      setResultImage(`data:image/png;base64,${base64Image}`);
      setCredit(credit - 1);
    } catch (error) {
      console.log(error);
      toast.error("Error while removing background image");
    }
  };

  const contextValue = {
    backEndURL,
    credit,
    setCredit,
    loadUserCredits,
    image,
    setImage,
    resultImage,
    setResultImage,
    removeBg,
  };

  return (
    <AppContext.Provider value={contextValue}>{children}</AppContext.Provider>
  );
};

export default AppContextProvider;
