import base64 from "base-64";
import axios from "axios";
import utf8 from "utf8";


export function login(username, password) {
    if (username && password) {
        return function (result) {
            var route = 'http://localhost:8082/signin?username='+ username +'&password='+base64.encode(utf8.encode(password))
            axios.post(route)
                .then((resp) => {
                    localStorage.removeItem("token")
                    localStorage.setItem("token", resp.data.token);
                    result({type: "SUCCESS", payload: resp.data})
                })
                .catch((error) => {
                    result({type: "FAILED", payload: error})
                })
        }
    }

    return {
        type: "EMPTY",

        payload: {
            message: "Empty some data",
        }
    }
}


export function isAuth() {
    return function (result) {
        var route = 'http://localhost:8082/home'
        var heder = {
            headers: {'Authorization': 'Token: ' + localStorage.getItem("token")}
        };

        axios.get(route, heder)
            .then((res) => {
                result({type: "IS_AUTH", payload: res.data})
            })
            .catch((error) => {
                result({type: "IS_NOT_AUTH", payload: ""})
            })
    }
}




export function logout() {
    localStorage.removeItem("token");
    return {type: "IS_NOT_AUTH", payload: ""};
}