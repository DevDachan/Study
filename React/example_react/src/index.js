import React from 'react';
import ReactDOM from 'react-dom';
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
/*    //demo3 example
import CommentList from './demo3(comment)/CommentList'

const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
  <React.StrictMode>
      <CommentList />
  </React.StrictMode>,
  document.getElementById("root")
);
*/
/*    //demo4 example
import Accommodate from './demo4(Hook)/Accommodate';

const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
  <React.StrictMode>
    <Accommodate />
  </React.StrictMode>,
  document.getElementById('root')
);
*/

/*  //demo5
import ConfirmButton from './demo5(event)/ConfirmButton';

const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
  <React.StrictMode>
    <ConfirmButton />
  </React.StrictMode>,
  document.getElementById('root')
);
*/


import LandingPage from './demo6(inline)/LandingPage';

const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
  <React.StrictMode>
    <LandingPage />
  </React.StrictMode>,
  document.getElementById('root')
);



// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
