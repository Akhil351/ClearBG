import { useAuth, useUser } from "@clerk/clerk-react";
import React, { useContext, useEffect, useState } from "react";
import { AppContext } from "../context/contex";
import axios from "axios";
import toast from "react-hot-toast";

function UserSyncHandler() {
  const { isLoaded, isSignedIn, getToken } = useAuth();
  const { user } = useUser();
  const [synced, setSynced] = useState(false);
  const { backEndURL, loadUserCredits } = useContext(AppContext);

  useEffect(() => {
    const saveUser = async () => {
      if (!isLoaded || !isSignedIn || synced || !user) return;

      try {
        const token = await getToken();
        const userData = {
          clerkId: user.id,
          email: user.primaryEmailAddress.emailAddress,
          firstName: user.firstName,
          lastName: user.lastName,
          photoUrl: user.imageUrl,
        };

        await axios.post(`${backEndURL}/users`, userData, {
          headers: { Authorization: `Bearer ${token}` },
        });
        setSynced(true);
        await loadUserCredits();
      } catch (error) {
        console.error("User sync failed", error);
        toast.error("Unable to create account. Please try again.");
      }
    };

    saveUser();
  }, [isLoaded, isSignedIn, getToken, user, synced, backEndURL]);

  return null;
}

export default UserSyncHandler;
