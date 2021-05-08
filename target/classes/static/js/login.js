var usernameTextbox = document.querySelector('#username')

usernameTextbox.addEventListener('blur', () => {
  if (usernameTextbox.value != '' 
    && checkForValidEmail(usernameTextbox.value) === true) {
      usernameTextbox.className = 'form-control is-valid'
    } else {
      usernameTextbox.value = ''
      usernameTextbox.placeholder="invalid email Address"
      usernameTextbox.className = 'form-control is-invalid'
    }})

function checkForValidEmail(text) { 
    var re = /(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))/;
    return re.test(text);
}