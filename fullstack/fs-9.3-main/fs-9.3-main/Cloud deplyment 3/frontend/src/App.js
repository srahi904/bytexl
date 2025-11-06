import React, {useEffect, useState} from "react";
import "./App.css";
function App(){
  const [msg, setMsg] = useState('Loading...');
  useEffect(()=>{
    fetch('/api/message').then(r=>r.json()).then(d=>setMsg(d.message + ' at ' + d.time)).catch(e=>setMsg('Error: '+e.message));
  },[]);
  return (
    <div className="App">
      <header className="App-header">
        <h1>Fullstack App on AWS with Load Balancer</h1>
        <p>{msg}</p>
      </header>
    </div>
  );
}
export default App;
