// store/state.js
import { reactive } from 'vue';

// Check if 'LoggedIn' is already set in sessionStorage, if not, set it to '0'
if (!sessionStorage.getItem('LoggedIn')) {
  sessionStorage.setItem('LoggedIn', '0');
}

const state = reactive({
  loggedIn: sessionStorage.getItem('LoggedIn')
});

function setLoggedIn(value) {
  state.loggedIn = value;
  sessionStorage.setItem('LoggedIn', value);
}

export { state, setLoggedIn };