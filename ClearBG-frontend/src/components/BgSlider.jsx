import React, { useState } from "react";
import { assets, categories } from "../assets/assets";
import { SlidersIcon } from "lucide-react";

function BgSlider() {
  const [sliderPosition, setSliderPosition] = useState(50);
  const [activeCategory, setActiveCategory] = useState("People");

  const handleSliderChange = (e) => {
    setSliderPosition(Number(e.target.value));
  };

  return (
    <div className="mb-16 relative px-4">
      <h2 className="text-3xl md:text-4xl font-bold text-gray-900 mb-12 text-center">
        Stunning quality
      </h2>

      {/* Category buttons */}
      <div className="flex justify-center mb-10 flex-wrap">
        <div className="inline-flex gap-4 bg-gray-100 p-2 rounded-full flex-wrap justify-center">
          {categories.map((category) => (
            <button
              key={category}
              onClick={() => setActiveCategory(category)}
              className={`px-6 py-2 rounded-full font-medium transition ${
                activeCategory === category
                  ? "bg-white text-gray-800 shadow-sm"
                  : "text-gray-600 hover:bg-gray-200"
              }`}
            >
              {category}
            </button>
          ))}
        </div>
      </div>

      {/* Image comparison container */}
      <div className="relative w-full max-w-4xl overflow-hidden m-auto rounded-xl shadow-lg aspect-[2/1]">
        {/* Left (original) */}
        <img
          src={assets.people_org}
          alt="Original image"
          style={{ clipPath: `inset(0 ${100 - sliderPosition}% 0 0)` }}
          className="w-full h-full object-cover"
        />

        {/* Right (background removed) */}
        <img
          src={assets.people}
          alt="Background removed image"
          style={{ clipPath: `inset(0 0 0 ${sliderPosition}%)` }}
          className="absolute top-0 left-0 w-full h-full object-cover"
        />

        {/* Slider line and icon */}
        <div
          className="absolute top-0 bottom-0 w-0.5 bg-white z-20"
          style={{ left: `${sliderPosition}%` }}
        >
          <div className="absolute top-1/2 -translate-y-1/2 -left-4 w-8 h-8 bg-white border border-gray-300 rounded-full flex items-center justify-center shadow">
            <img
              src="/src/assets/slide_icon.svg" // Import this using: import slideIcon from '../assets/slide_icon.svg';
              alt="slider"
              className="w-6 h-6"
            />
          </div>
        </div>

        {/* Range input */}
        <input
          type="range"
          min={0}
          max={100}
          value={sliderPosition}
          onChange={handleSliderChange}
          className="absolute top-0 left-0 w-full h-full opacity-0 cursor-pointer z-30"
        />
      </div>
    </div>
  );
}

export default BgSlider;
