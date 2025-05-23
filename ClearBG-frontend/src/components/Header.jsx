import React from "react";
import { assets } from "../assets/assets";

function Header() {
  return (
    <div className="grid grid-cols-1 md:grid-cols-2 gap-12 items-center mb-16">
      <div className="order-2 md:order-1 flex justify-center">
        <div className="shadow-[0_25px_50px_-12px_rgba(0,0,0,0.15)] rounded-3xl overflow-hidden">
          <video
            autoPlay
            loop
            muted
            playsInline
            aria-hidden="true"
            className="w-full max-w-[400px] h-auto object-cover"
          >
            <source src={assets.video_banner} type="video/mp4" />
            Your browser does not support the video tag.
          </video>
        </div>
      </div>

      <div className="order-1 md:order-2">
        <h1 className="text-4xl md:text-5xl font-bold text-gray-900 mb-6 leading-tight">
          The fastest{" "}
          <span className="text-indigo-700">background eraser.</span>
        </h1>
        <p className="text-gray-600 mb-8 text-lg leading-relaxed">
          Transform your photos with our background remove app! Highlight your
          subject and create transparent background, so you can place it in a
          variety of new designs and destinations. Try it now and immerse your
          subject in a completely different environemnt
        </p>
        <div>
          <input type="file" accept="image" id="upload1" hidden />
          <label
            htmlFor="upload1"
            className="bg-black text-white font-medium px-8 py-4 rounded-full hover:opacity-90 transition-transform hover:scale-105 text-lg "
          >
            Upload your image
          </label>
        </div>
      </div>
    </div>
  );
}

export default Header;
