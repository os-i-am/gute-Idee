var csrfTextbox = document.querySelector('#csrf')
var catTitleTextbox = document.querySelector('[name="title"]')
const editCatInputs = document.getElementById('editCats').querySelectorAll('input')
const editCatButtons = document.getElementById('editCats').querySelectorAll('button.btn-primary')
const deleteCatButtons = document.getElementById('editCats').querySelectorAll('button.btn-outline-grey, button.btn-outline-danger');

function checkIfCatExists(e) {
  e.preventDefault()
  var category = {
    'title': catTitleTextbox.value
  }
  fetch('/categoryExists', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'X-CSRF-TOKEN': csrfTextbox.value
    },
    body: JSON.stringify(category)
  }).then((responseEntity) => responseEntity.json())
    .then((data) => {
      if (data === true) {
        catTitleTextbox.value = ''
        catTitleTextbox.className = 'form-control is-invalid'
        catTitleTextbox.placeholder = 'Category already exists'

      } else {
        catTitleTextbox.className = 'form-control is-valid'
        document.createCat.submit();
      }
    })
}

catTitleTextbox.addEventListener('focus', () => {
  catTitleTextbox.className = 'form-control'
  catTitleTextbox.placeholder = 'Category Title'
})

for (let i = 0; i < editCatInputs.length; i++) {
  editCatInputs[i].addEventListener('blur', () => {
    if (editCatInputs[i].value === '') {
      editCatInputs[i].className = 'form-control is-invalid'
      editCatInputs[i].placeholder = 'please enter a Title'
    } else {
      editCatInputs[i].className = 'form-control'
    }
  })
  
  editCatInputs[i].addEventListener('focus', () => {
    editCatInputs[i].className = 'form-control'
    editCatInputs[i].placeholder = 'please enter a Title'
  })
}

for (let i = 0; i < editCatButtons.length; i++) {
  editCatButtons[i].addEventListener('click', () => {
    if (editCatInputs[i].value != '') {
      var category = {
        'id': editCatInputs[i].id,
        'title': editCatInputs[i].value
      }
      fetch('/admin/editCategory', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'X-CSRF-TOKEN': csrfTextbox.value
        },
        body: JSON.stringify(category)
      }).then((responseEntity) => responseEntity.json())
        .then((data) => {
          editCatInputs[i].value = data.title
          editCatInputs[i].className = 'form-control is-valid'
        })
        .catch((error) => {
          editCatInputs[i].value = ''
          editCatInputs[i].className = 'form-control is-invalid'
          editCatInputs[i].placeholder = 'Category already exists'
        })
    }
  })
}

for (let i = 0; i < deleteCatButtons.length; i++) {

  deleteCatButtons[i].addEventListener('focus', () => {
    console.log(editCatInputs[i].id)
    console.log(editCatInputs[i].value)
  })

  deleteCatButtons[i].addEventListener('click', () => {
    if (editCatInputs[i].value != '') {
      console.log('delete ' + editCatInputs[i].value)
      var category = {
        'id': editCatInputs[i].id,
        'title': editCatInputs[i].value
      }
      fetch('/admin/deleteCategory', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'X-CSRF-TOKEN': csrfTextbox.value
        },
        body: JSON.stringify(category)
      }).then((responseEntity) => responseEntity.json())
        .then((data) => {
          if (data === true) { }
          location.reload()
        })
    }
  })
}

