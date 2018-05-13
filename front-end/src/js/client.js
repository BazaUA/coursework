import React from "react"
import ReactDOM from "react-dom"
import { Provider } from "react-redux"
import { Router, Route, IndexRoute, hashHistory } from "react-router";

import Home from "./component/Home.jsx"

import storege from "./storege"

require('file-loader?name=index.html!../index.html');

const app = document.getElementById('app')

ReactDOM.render(<Provider store={storege}>
  <Router history={hashHistory}>
    <Route path="/" component={Home}>
      <IndexRoute component={Home}/>      
      <Route path="main" name="main" component={Home}></Route>     
    </Route>
  </Router>
</Provider>, app);
