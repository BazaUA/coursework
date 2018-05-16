import React from "react";
import {connect} from "react-redux"
import {Col} from 'react-bootstrap';
import {Form} from 'react-bootstrap';
import {FormGroup} from 'react-bootstrap';
import {ControlLabel} from 'react-bootstrap';
import {Panel} from 'react-bootstrap';
import {FormControl} from 'react-bootstrap';
import {Button} from 'react-bootstrap';
import {login} from "../action/actions";

@connect((my_store) => {
    return {
        loginFailed: my_store.login.fail
    };
})

class Login extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            login: '',
            pass: ''
        };
    }

    changeEmail(e) {
        var fleldText = e.target.value;
        this.setState({login: fleldText, pass: this.state.pass});
    }
    login() {
        this.props.dispatch(login(this.state.login, this.state.pass))
    }
    changePass(e) {
        let fleldText = e.target.value;
        this.setState({pass: fleldText, login: this.state.login});
    }

    pressEnter(e) {
        if (e.charCode == 13) {
            this.login();
        }
    }
    render() {
        const {loginFailed: failed} = this.props;
        const formStyle = {
            marginTop: '10%',
            marginLeft: '40%',
            width: '30%',
        };
        const button = {
            width: '80%',
        }
        const mytitle = "Сторінка аутентифікації";
        return (
            <div>
                <Panel header={mytitle} bsStyle="success" style={formStyle}>
                    <div>
                        <Form horizontal>
                            <FormGroup controlId="formHorizontalEmail">
                                <Col componentClass={ControlLabel} sm={1}>
                                </Col>
                                <Col sm={12}>
                                    <FormControl type="text" placeholder="Логін" name="login" onChange={this.changeEmail.bind(this)} onKeyPress={this.pressEnter.bind(this)}/>
                                </Col>
                            </FormGroup>
                            <FormGroup controlId="formHorizontalPassword">
                                <Col componentClass={ControlLabel} sm={1}></Col>
                                <Col sm={12}>
                                    <FormControl type="password" placeholder="Пароль" name="pass" onChange={this.changePass.bind(this)} onKeyPress={this.pressEnter.bind(this)}/>
                                </Col>
                            </FormGroup>
                            <FormGroup>
                                <Col smOffset={1} sm={12}>
                                    <Button bsStyle="success" onClick={this.login.bind(this)} style={button}>Увійти</Button>
                                </Col>
                            </FormGroup>
                        </Form>
                    </div>
                </Panel>

            </div>
        );
    }
}
export default Login;