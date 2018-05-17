
export default function reducer(current_state = {
    fail: false,
    auth: false,
    data: null,


}, action) {
    switch (action.type) {

        case "IS_NOT_AUTH": {
            return {
                ...current_state,
                auth: false,
                data: null,
            }
        }
        case "IS_AUTH": {
            return {
                ...current_state,
                fail: false,
                auth: true,
                data: action.payload,
            }
        }

        case "FAIL": {
            return {
                ...current_state,
                fail: true,
                auth: false,
                data: null,
            }
        }
        case "SUCCESS": {
            return {
                ...current_state,

                fail: false,
                auth: true,
                data: null,
            }
        }
        case "EMPTY": {
            return {
                ...current_state,
                fail: true,
                auth: false,
                data: null,
            }
        }
    }
    
    return current_state
}

