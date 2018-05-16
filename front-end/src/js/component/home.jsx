import React from "react"
import {Alert} from 'react-bootstrap';
import {isAuth} from "../action/actions";
import Login from './login.jsx';
import ComplateLogin from './complateLogin.jsx';
import {connect} from "react-redux"

@connect((my_store) => {
    return {
        auth: my_store.login.auth,
        fail: my_store.login.fail,
        data: my_store.login.data,
    };
})
class Home extends React.Component {
    componentWillReceiveProps(n_prop) {
        if (n_prop.auth && n_prop.data === null) {
            this.props.dispatch(isAuth());
        }
    }
    componentWillMount() {
        this.props.dispatch(isAuth());
    }
    constructor() {
        super();
    }

    render() {
        const {auth, fail, data} = this.props;
        var html_to_render;
        if (auth) {
            html_to_render = (
                <div><ComplateLogin/></div>
            )
        }
        else {
            if (fail) {
                html_to_render = (
                    <div>
                        <Alert bsStyle="info"><b>Некоректні дані</b></Alert>
                        <Login/>
                    </div>
                )
            } else {
                html_to_render = (
                    <Login/>
                )
            }
        }
        return (<div>{html_to_render}</div>)
    }
}
export default Home;