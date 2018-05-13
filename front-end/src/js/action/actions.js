import axios from "axios";
import base64 from "base-64";
import utf8 from "utf8";



export function isAuth() {   
  return function(dispatch) {                
     let url =  'http://localhost:8082/home'
     var config = {
      headers: {'Authorization':  'Token: ' + localStorage.getItem( "token" )}
    };

     axios.get(url, config)
        .then((response) => {
          dispatch({type: "IS_AUTHENTICATED", payload: response.data})
        })
        .catch((err) => {
          dispatch({type: "IS_NOT_AUTHENTICATED", payload: ''})
        })
    }  
}

export function login(username, password) {
  if(username && password) {
    return function(dispatch) {            
     let url =  'http://localhost:8082/signin?username=' + username +  '&password=' + base64.encode(utf8.encode(password))    
     axios.post(url)
        .then((response) => {    
          localStorage.removeItem( "token" )       
          localStorage.setItem( "token", response.data.token );                
          dispatch({type: "LOGIN_SUCCESS", payload: response.data}) 
        })
        .catch((err) => {
          dispatch({type: "LOGIN_FAILED", payload: err})
        })
    }
  }
  return {
    type: "LOGIN_EMPTY",
    payload: {
      message : "Empty username or password.",
    }
  }
}


export function logout() {   
  localStorage.removeItem( "token" )   
  return {
    type: "IS_NOT_AUTHENTICATED",
    payload: ''
  }
}