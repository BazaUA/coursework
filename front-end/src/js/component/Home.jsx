import React from "react"
import { connect } from "react-redux"

import Login from './Login.jsx';
import ComplateLogin from './ComplateLogin.jsx';

import { Alert } from 'react-bootstrap';
import ReactRedirect from "react-redirect";


import { isAuth } from "../action/actions";


import Bootstrap from 'bootstrap/dist/css/bootstrap.css';


@connect((store) => {
  return {
    authenticated: store.login.authenticated,
    loginFailed: store.login.failed,
    userData: store.login.userData,
  };
})
class Home extends React.Component {

  componentWillMount() {    
    this.props.dispatch(isAuth())       
  }

  componentWillReceiveProps(nextProps) {
    if(nextProps.authenticated && nextProps.userData === null) {
      this.props.dispatch(isAuth())        
    }      
  }

  constructor() {
    super()    
  }  

  render() {
    const { authenticated, loginFailed, userData } = this.props;   
    
    let html;
    if (authenticated) {
      html = (      
        <div>            
             <ComplateLogin /> 
        </div>
      )
    } else {

    if (loginFailed) {
        html = (
          <div>
            <Alert bsStyle="danger">
                <strong>Something went wrong.</strong>
           </Alert>
           <Login /> 
           </div>
        )
     } else {
      html = (
        <Login />
      )
     }
      
    }
    return ( <div>{ html }</div>)
  }
}


export default Home;