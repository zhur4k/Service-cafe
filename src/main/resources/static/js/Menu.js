let categories = [];
getCategories();

function displayMenu() {
    let bodyElement = document.getElementById("main_body");
    let categoryMain = document.createElement("div");
    categoryMain.classList.add("main-category"); // Добавляем класс "category" для стилизации
    categories.forEach(category => {
        categoryMain.appendChild(displayCategory(category));
    });
    bodyElement.appendChild(categoryMain);
}

function displayCategory(category) {
    let categoryItem = document.createElement("div");
    categoryItem.classList.add("category"); // Добавляем класс "category" для стилизации

    let categoryName = document.createElement("div");
    categoryName.textContent = category.categoriesName;
    categoryName.classList.add("category-name"); // Добавляем класс "category-name" для стилизации
    categoryItem.appendChild(categoryName);

    let itemsList = document.createElement("ul");
    category.items.forEach(item => {
        let itemElement = document.createElement("li");
        itemElement.innerHTML = `<span class="item-name">${item.nameOfItems}</span> <span class="item-price">${(item.price / 100).toFixed(2)}</span>`;
        itemsList.appendChild(itemElement);
    });
    categoryItem.appendChild(itemsList);

    category.childCategories.forEach(childCategory => {
        categoryItem.appendChild(displayCategory(childCategory));
    });

    return categoryItem;
}
function getCategories() {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/menu/getCategoriesForMenu', true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            categories = JSON.parse(xhr.responseText);
            displayMenu();
        }
    };
    xhr.send();
}