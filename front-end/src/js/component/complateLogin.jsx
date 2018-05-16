import React from "react";
import {connect} from "react-redux"
import {logout} from "../action/actions";
import {Alert} from 'react-bootstrap';
import {Button} from 'react-bootstrap';

@connect((my_store) => {
    return {
        data: my_store.login.data,
    };
})
class ComplateLogin extends React.Component {
    logout() {
        this.props.dispatch(logout())
    }

    render() {
        const {data} = this.props;

        const btn = {
            margin: '20px',
        }

        var html_to_render;
        if (data) {
            html_to_render = (
                <div>
                    <Alert bsStyle="success">
                        <h5>Результуючий токен: {localStorage.token}</h5> <br/>
                        Ім'я під яким залогований {data.username}. Для виходу натиснути клавішу нижче.
                    </Alert>
                    <Button bsStyle="success" onClick={this.logout.bind(this)} style={btn}>
                        Вийти
                    </Button>
                </div>
            )
        } else {
            html_to_render = (<div></div>)
        }
        return (<div>{html_to_render}</div>)
    }
}


export default ComplateLogin;