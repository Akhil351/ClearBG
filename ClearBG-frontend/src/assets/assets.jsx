import logo from "./logo.png";
import video_banner from "./home-page-banner.mp4";
import {
  faFacebook,
  faInstagram,
  faLinkedin,
  faTwitter,
} from "@fortawesome/free-brands-svg-icons";
// People
import people from "./people.png";
import people_org from "./people-org.png";
import credits from "./dollar.png";
export const assets = {
  logo,
  video_banner,

  // People
  people,
  people_org,
  credits,
};

export const steps = [
  {
    step: "Step 1",
    title: "Select an image",
    description: `First, choose the image you want to remove the background from by clicking on "Start from a photo".
Your image format can be PNG or JPG. We support all image dimensions.`,
  },
  {
    step: "Step 2",
    title: "Let magic remove the background",
    description: `Our tool automatically removes the background from your image.
Next, you can choose a background color. Our most popular options are white and transparent, but you can pick any color you like.`,
  },
  {
    step: "Step 3",
    title: "Download your image",
    description: `After selecting a new background color, download your photo and you're done! You can also save your picture in the Photoroom App by creating an account.`,
  },
];

export const categories = ["People", "Products", "Animals", "Cars", "Graphics"];

export const plans = [
  {
    id: "Basic",
    name: "Basic Package",
    price: "999",
    credits: "100 Credits",
    description: "Ideal for personal use.",
    popular: false,
  },
  {
    id: "Ultimate",
    name: "Ultimate Package",
    price: "3,999",
    credits: "1,000 Credits",
    description: "Best suited for enterprise solutions.",
    popular: true,
  },
  {
    id: "Premium",
    name: "Premium Package",
    price: "1,999",
    credits: "250 Credits",
    description: "Perfect for business needs.",
    popular: false,
  },
];

export const testimonials = [
  {
    id: 1,
    quote:
      "We're truly impressed with the AI—it stands out as the best solution available in the market.",
    author: "Akhil",
    handle: "akhil@2004",
  },
  {
    id: 2,
    quote:
      "remove.bg is miles ahead of the competition—significantly better in every way. It has completely streamlined our workflow.",
    author: "Eshwar",
    handle: "eshwar@351",
  },
  {
    id: 3,
    quote:
      "Its precision is remarkable, especially when handling fine details like wispy hair. The results are smooth and professional—never jagged or amateurish.",
    author: "Hitesh",
    handle: "hitesh@2008",
  },
];

export const FOOTER_CONSTANTS = [
  { url: "https://facebook.com", icon: faFacebook },
  { url: "https://instagram.com", icon: faInstagram },
  { url: "https://linkedin.com", icon: faLinkedin },
  { url: "https://twitter.com", icon: faTwitter },
];
