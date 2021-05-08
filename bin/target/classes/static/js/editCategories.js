var csrfTextbox = document.querySelector('#csrf')
const editCatInputs = document.getElementById('editCats').querySelectorAll('input');
const editCatButtons = document.getElementById('editCats').querySelectorAll('button.btn-primary');
const deleteCatButtons = document.getElementById('editCats').querySelectorAll('button.btn-outline-grey, button.btn-outline-danger');

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

