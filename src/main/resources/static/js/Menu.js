let categories = [];
getCategories();

function displayMenu() {
    let bodyElement = document.getElementById("main_body");
    let categoryMain = document.createElement("div");
    categoryMain.classList.add("main-category");

    let columnCount = 2;
    let columns = [];
    for (let i = 0; i < columnCount; i++) {
        let column = document.createElement("div");
        column.classList.add("column");
        columns.push(column);
        categoryMain.appendChild(column);
    }

    // Calculate approximate heights of categories to distribute them evenly
    let columnHeights = new Array(columnCount).fill(0);

    categories.forEach(category => {
        let minColumnIndex = columnHeights.indexOf(Math.min(...columnHeights));
        let categoryElement = displayCategory(category);
        columns[minColumnIndex].appendChild(categoryElement);

        // Estimate the height of the category element
        columnHeights[minColumnIndex] += estimateCategoryHeight(category);
    });

    bodyElement.appendChild(categoryMain);
}

function displayCategory(category) {
    let categoryItem = document.createElement("div");
    categoryItem.classList.add("category");

    let categoryName = document.createElement("div");
    categoryName.textContent = category.categoriesName;
    categoryName.classList.add("category-name");
    categoryItem.appendChild(categoryName);

    let itemsList = document.createElement("ul");
    category.items.forEach(item => {
        let itemElement = document.createElement("li");
        itemElement.innerHTML = `<span class="item-name">${item.nameOfItems} ${item.productVolume}</span> <span class="item-price">${(item.price / 100).toFixed(2)}</span>`;
        itemsList.appendChild(itemElement);
    });
    categoryItem.appendChild(itemsList);

    category.childCategories.forEach(childCategory => {
        categoryItem.appendChild(displayCategory(childCategory));
    });

    return categoryItem;
}

function estimateCategoryHeight(category) {
    let baseHeight = 50; // Base height for category name and padding
    let itemHeight = 30; // Estimated height for each item
    let childCategoryHeight = 50; // Estimated height for each child category

    let height = baseHeight + category.items.length * itemHeight;
    category.childCategories.forEach(childCategory => {
        height += estimateCategoryHeight(childCategory);
    });

    return height;
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
