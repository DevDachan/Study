import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';


import 'bootstrap/dist/css/bootstrap.css';

/*
//   demo example
import Library from './demo/Library';


const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    ///<App />
    <Library / >
  </React.StrictMode>
)
*/

/*
//   demo2 example
import Clock from './demo2(clock)/Clock'

const root = ReactDOM.createRoot(document.getElementById('root'));
setInterval(() => {
    root.render(
        <React.StrictMode>
            <Clock />
        </React.StrictMode>,
    );
}, 1000);
*/

import CommentList from './demo3(comment)/CommentList'

const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
  <React.StrictMode>
      <CommentList />
  </React.StrictMode>,
  document.getElementById("root")
);




// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
