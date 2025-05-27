import axios from "axios";
import toast from "react-hot-toast";


export const placeOrder = async ({ planId, getToken, onSuccess, backEndURL }) => {
  try {
    const token = await getToken();
    const response = await axios.post(
      `${backEndURL}/orders?planId=${planId}`,
      {},
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );

    if (response.data.success) {
      initializePayment({
        order: response.data.data,
        getToken,
        onSuccess,
        backEndURL,
      });
    } else {
      toast.error("Failed to create order.");
    }
  } catch (error) {
    toast.error(error?.response?.data?.message || error.message);
  }
};


const initializePayment = ({ order, getToken, onSuccess, backEndURL }) => {
  const options = {
    key: import.meta.env.VITE_RAZORPAY_KEY_ID,
    amount: order.amount,
    currency: order.currency,
    name: "Credit Payment",
    description: "Credit Payment",
    order_id: order.id,
    receipt: order.receipt,

    handler: async (paymentDetails) => {
      try {
        const token = await getToken(); 
        const response = await axios.post(
          `${backEndURL}/orders/verify`,
          paymentDetails,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );

        if (response.status === 200) {
          toast.success("Credits added.");
          onSuccess?.(); // Optional chaining: call if defined
        } else {
          toast.error("Payment verification failed.");
        }
      } catch (error) {
        toast.error(error?.response?.data?.message || error.message);
      }
    },

    theme: {
      color: "#3399cc",
    },
  };

  try {
    const rzp = new window.Razorpay(options);
    rzp.open();
  } catch (err) {
    toast.error("Failed to initiate Razorpay checkout.");
  }
};


// **async** means: “This function might take time, and it will return a promise.”

// **await** means: “Wait here until the promise finishes, then move on.”
