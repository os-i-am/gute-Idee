//function getCategories() {
//  fetch(`/category/getAllCategories`)
//    .then((response) => response.json())
//    .then((data) => {
//      console.log(data)
//      var filterDropdown = document.querySelector('#filterDropdown')
//      filterDropdown.innerHTML =
//        `<li><a class="dropdown-item" href="#">most liked</a></li>
//         <li><a class="dropdown-item" href="#">completed</a></li>
//         <li><hr class="dropdown-divider"></li>
//         <li class="dropdown-header">categories</li>`
//      data.forEach(aCategory => {       
//        filterDropdown.innerHTML += `<li><a class="dropdown-item" href="/categories/` + aCategory.catTitle + `">` + aCategory.catTitle + `</a>`
//      })
//    })
//}
//
//getCategories()