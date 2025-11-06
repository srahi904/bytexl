import React from "react";
import logo from "./logo.svg";
import "./App.css";

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <h1>🚀 React App Dockerized on Bytexl Workspace!</h1>
        <p>This React app is built with Node and served with Nginx using Docker multi-stage build.</p>
      </header>
    </div>
  );
}

export default App;
