import {Provider} from "react-redux"
import React from "react"
import ReactDOM from "react-dom"
import my_storage from "./state_storege"
import Home from "./component/home.jsx"
import {Router, Route, IndexRoute, hashHistory} from "react-router";
require('file-loader?name=index.html!../index.html');
var my_app = document.getElementById('react')
ReactDOM.render(<Provider store={my_storage}>
    <Router history={hashHistory}>
        <Route path="/" component={Home}>
            <IndexRoute component={Home}/><Route path="main" name="main" component={Home}></Route>
        </Route>
    </Router>
</Provider>, my_app);
