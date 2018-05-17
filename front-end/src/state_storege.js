import promise from "redux-promise-middleware"
import logger from "redux-logger"
import thunk from "redux-thunk"
import {applyMiddleware, createStore} from "redux"

import reducer from "./index"
const apply_middlevare = applyMiddleware(promise(), thunk, logger())
export default createStore(reducer, apply_middlevare)
