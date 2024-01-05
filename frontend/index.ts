import { createElement } from "react";
import { createRoot } from "react-dom/client";
import App from "./App.js";

createRoot(document.getElementById("outlet")!).render(createElement(App));

// Function to add a <script> tag dynamically to the document
function loadScript(url: string, callback: () => void) {
    const script = document.createElement('script');
    script.src = url;

    script.onload = callback;

    document.head.appendChild(script);
}

// Usage: Call the function to add a script dynamically
loadScript('/web-component/login-form.js', () => {
    // This function will be executed after the script is loaded
    console.log('Script loaded!');
    // You can perform actions here that depend on the loaded script
});