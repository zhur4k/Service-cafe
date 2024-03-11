let categories = [];
getCategories();

function displayMenu() {
    let bodyElement = document.getElementById("main_body");

    categories.forEach(category => {
        bodyElement.appendChild(displayCategory(category));
    });

}
function displayCategory(category){
    let categoryItem = document.createElement("li");
    categoryItem.textContent = category.categoriesName;

    let itemsList = document.createElement("ul");
    category.items.forEach(item => {
        // Создаем элемент списка для товара
        let itemElement = document.createElement("li");
        itemElement.textContent = `${item.nameOfItems} - ${(item.price/100).toFixed(2)} рублей`;

        // Добавляем товар в подсписок
        itemsList.appendChild(itemElement);
    });
    categoryItem.appendChild(itemsList);
    category.childCategories.forEach(category => {
        categoryItem.appendChild(displayCategory(category))
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