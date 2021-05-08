var csrfTextbox = document.querySelector('#csrf')
var usernameTextbox = document.querySelector('#username')
var nameTextbox = document.querySelector('#name')
var passwordTextbox = document.querySelector('#password')
var verifyPasswordTextbox = document.querySelector('#verifyPassword')
var resetBtn = document.querySelector('#reset')
var removeAdminBtn = document.querySelector('#removeAdmin')
var addAdminBtn = document.querySelector('#addAdmin')

//removeAdminBtn.addEventListener('click', () => {
//  
//  
//})
//
//addAdminBtn.addEventListener('click', () => {
//  
//  
//})

usernameTextbox.addEventListener('blur', () => {
  if (usernameTextbox.value != '' 
    && checkForValidEmail(usernameTextbox.value) === true) {
    var user = {
      'username': usernameTextbox.value
    }
    fetch('/exists', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'X-CSRF-TOKEN': csrfTextbox.value
      },
      body: JSON.stringify(user)
    }).then((responseEntity) => responseEntity.json())
      .then((data) => {
        if (data === true) {
          usernameTextbox.value = ''
          usernameTextbox.className = 'form-control is-invalid'
          usernameTextbox.placeholder = 'username already exists'
        } else {
          usernameTextbox.className = 'form-control is-valid'
        }
      })
  } else {
    usernameTextbox.className = 'form-control is-invalid'
    usernameTextbox.value = ''
    usernameTextbox.placeholder = 'not a valid Email Address'
  }
})

function checkForValidEmail(text) { 
    var re = /(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))/;
    return re.test(text);
}

nameTextbox.addEventListener('blur', () => {
  if (nameTextbox.value === '') {
    nameTextbox.className = 'form-control is-invalid'
    nameTextbox.placeholder = 'please enter a Name'
  } else {
    nameTextbox.className = 'form-control is-valid'
  }
})

passwordTextbox.addEventListener('blur', () => {
  if (passwordTextbox.value === '') {
    passwordTextbox.className = 'form-control is-invalid'
    passwordTextbox.placeholder = 'please enter a Password'    
  } else {
    passwordTextbox.className = 'form-control is-valid'
  }
})

verifyPasswordTextbox.addEventListener('blur', () => {
  if (verifyPasswordTextbox.value === '') {
    verifyPasswordTextbox.className = 'form-control is-invalid'
    verifyPasswordTextbox.placeholder = 'please enter a Password'
  } else if (verifyPasswordTextbox.value != passwordTextbox.value) {
    verifyPasswordTextbox.className = 'form-control is-invalid'
    verifyPasswordTextbox.value = ''
    verifyPasswordTextbox.placeholder = `password doesn't match!`    
  } else {
    passwordTextbox.className = 'form-control is-valid'
    verifyPasswordTextbox.className = 'form-control is-valid'   
  }
})

resetBtn.addEventListener('click', () => {
  usernameTextbox.className = 'form-control'
  nameTextbox.className = 'form-control'
  passwordTextbox.className = 'form-control'
  verifyPasswordTextbox.className = 'form-control'
  })