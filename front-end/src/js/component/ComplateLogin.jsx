import React from "react";
import { connect } from "react-redux"


import { Alert } from 'react-bootstrap';
import { Button } from 'react-bootstrap';

import { logout } from "../action/actions";


@connect((store) => {
	return {

		userData: store.login.userData,
	};
})

class ComplateLogin extends React.Component {

	logout() {
		this.props.dispatch(logout())
	}

	render() {
		const { userData } = this.props;

		const btn = {
			margin: '30px',
		}

		let html;
		if (userData) {
			html = (
				<div>
					<Alert bsStyle="info">
						<h5>Результуючий токен: {localStorage.token}</h5> <br />
						Ім'я під яким залогований {userData.username}. Для виходу натиснути клавішу нижче.
		         </Alert>
					<Button bsStyle="primary" onClick={this.logout.bind(this)} style={btn}  >
						Logout
				</Button>
				</div>
			)
		} else {
			html = (<div>  </div>)
		}
		return (<div>{html}</div>)
	}
}


export default ComplateLogin;