const csrfToken = document.querySelector('meta[name="_csrf"]').content;
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;
//All category to page
let categoryData = [];
//All items from server
let itemsToPage = [];
//Items in order on page
let orderItems = [];
//Shift state
let shift=[]

getCategoriesToPage();
getItemsToPage();

function drawMainContainer() {
    let headButtons = document.getElementById('buttons-head');
    headButtons.innerHTML = '';
    let settingsButton = document.createElement('button');
    settingsButton.classList.add('button-settings');
    settingsButton.textContent = '☸';
    settingsButton.onclick = function() {
        showSettings();
    }
    headButtons.appendChild(settingsButton);
    // Получаем основной контейнер
    let mainContainer = document.getElementById('main-container');

    // Очищаем контейнер перед отрисовкой
    mainContainer.innerHTML = '';

    // Создаем левую часть (leftContainer)
    let leftContainer = document.createElement('div');
    leftContainer.classList.add('left');
    leftContainer.id = 'leftContainer';

    // Создаем правую часть (rightContainer)
    let rightContainer = document.createElement('div');
    rightContainer.classList.add('right');

    let orderList = document.createElement('div');
    orderList.classList.add('order-list');

    let table = document.createElement('table');
    let tbody = document.createElement('tbody');
    tbody.id = 'order-items';
    table.appendChild(tbody);

    orderList.appendChild(table);

    let total = document.createElement('h3');
    total.classList.add('total');
    total.id = 'total';
    total.textContent = '0.00 р';

    let payButton = document.createElement('button');
    payButton.type = 'button';
    payButton.className = 'button';
    payButton.textContent = 'Оплатить';
    payButton.onclick = function() {
        if (orderItems.length>0)
            submitOrder();
        showPayBlock();
    };

    rightContainer.appendChild(orderList);
    rightContainer.appendChild(total);
    rightContainer.appendChild(payButton);

    // Добавляем левую и правую части в основной контейнер
    mainContainer.appendChild(leftContainer);
    mainContainer.appendChild(rightContainer);
    showCategory();
    displayOrder();
}

function showSettings() {
    let headButtons = document.getElementById('buttons-head');
    headButtons.innerHTML = '';
    let backButton = document.createElement('buttonBack');
    backButton.classList.add('button-settings');
    backButton.textContent = '🔙';
    backButton.onclick = function() {
        drawMainContainer();
    }
    headButtons.appendChild(backButton);


    let mainContainer = document.getElementById('main-container');

    // Очищаем контейнер перед отрисовкой
    mainContainer.innerHTML = '';

    // Создаем левую часть (leftContainer)
    let leftContainer = document.createElement('div');
    leftContainer.classList.add('left-Settings');

    let shiftButton = document.createElement('button');
    shiftButton.type = 'button';
    shiftButton.className = 'button';

    getShiftToSettings(shiftButton);

    let xButton = document.createElement('button');
    xButton.type = 'button';
    xButton.className = 'button';
    xButton.textContent = 'X-Отчёт';
    xButton.onclick = function() {
        xReport();
    };
    leftContainer.appendChild(shiftButton);
    leftContainer.appendChild(xButton);
    mainContainer.appendChild(leftContainer);

}

function showCategory() {
    let leftContainer = document.getElementById('leftContainer');
    leftContainer.innerHTML = "";
    // Итерация по данным и создание элементов
    categoryData.forEach(function(categories) {
        const categoryDiv = document.createElement('div');
        categoryDiv.classList.add('child-block');

        categoryDiv.textContent = categories.categoriesName;
        categoryDiv.onclick = function() {
            showProducts(categories);
        };
        leftContainer.appendChild(categoryDiv);
    });
}

function showProducts(categories) {
    let leftContainer = document.getElementById('leftContainer');
    leftContainer.innerHTML = "";

    let backDiv = document.createElement('div');
    backDiv.classList.add('child-block');
    backDiv.onclick = function() {
        showCategory();
    }
    let backDivHeading = document.createElement('h2');
    backDivHeading.textContent = 'Вернуться';
    backDiv.appendChild(backDivHeading);
    backDiv.style.backgroundColor = "red";
    leftContainer.appendChild(backDiv);

    categories.items.forEach(function(item) {
        let productDiv = document.createElement('div');
        productDiv.classList.add('child-block');
        productDiv.onclick = function() {
            addProduct(item.id);
        };

        let productInfoDiv =
            document.createElement('div');
        productInfoDiv.classList.add('child-block-Info');

        let itemNameHeading = document.createElement('div');
        itemNameHeading.textContent = item.nameOfItems;

        let itemPriceParagraph = document.createElement('div');
        itemPriceParagraph.textContent = (item.price / 100).toFixed(2) + " руб";

        productInfoDiv.appendChild(itemNameHeading);
        productInfoDiv.appendChild(itemPriceParagraph);
        productDiv.appendChild(productInfoDiv);

        leftContainer.appendChild(productDiv);
    });
}

function addProduct(id) {
    // Поиск продукта в массиве по имени
    const existingProduct = orderItems.find(orderItems => (orderItems.items.id === id));

    if (existingProduct) {
        // Если продукт с таким именем уже существует, увеличиваем количество
        existingProduct.quantity += 1;
    } else {
        let items = itemsToPage.find(items => (items.id === id));
        // Иначе создаем новый продукт
        orderItems.push({
            quantity: 1,
            items
        });
    }
    displayOrder();
}

function displayOrder() {
    const orderItemTable = document.getElementById("order-items");
    orderItemTable.innerHTML = "";
    let total = 0;

    orderItems.forEach((item, index) => {
        const row = document.createElement("tr");
        const itemCell = document.createElement("td");
        const quantityCell = document.createElement("td");
        const totalCell = document.createElement("td");
        const removeCell = document.createElement("td");
        const removeButton = document.createElement("button");

        itemCell.textContent = item.items.nameOfItems;
        quantityCell.textContent = item.quantity;
        const itemTotal = ((item.items.price * item.quantity) / 100).toFixed(2) + " р";
        totalCell.textContent = itemTotal;
        removeButton.textContent = "Удалить";
        removeButton.classList.add("remove-button");
        removeButton.addEventListener("click", () => removeItem(index));

        quantityCell.style.width = "10%";
        quantityCell.style.textAlign = "center"
        quantityCell.classList.add("order-table-td");

        itemCell.style.width = "50%";
        itemCell.classList.add("order-table-td");

        totalCell.style.width = "20%";
        totalCell.style.textAlign = "center"
        totalCell.classList.add("order-table-td");

        removeCell.style.width = "20%";
        removeCell.classList.add("order-table-td");

        row.appendChild(quantityCell);
        row.appendChild(itemCell);
        row.appendChild(totalCell);
        removeCell.appendChild(removeButton);
        row.appendChild(removeCell);

        orderItemTable.appendChild(row);

        total += parseFloat(itemTotal) * 100;
    });

    document.getElementById("total").textContent = (total / 100).toFixed(2) + " р";
}

function removeItem(index) {
    orderItems.splice(index, 1);
    displayOrder();
}

function clearOrder() {
    orderItems = [];
    displayOrder();
}

function submitOrder() {
    // Создаем объект заказа
    let orders = ({
        paymentMethod: true,
        orderItems, // Массив с информацией о товарах в заказе
    });
    // Отправляем заказ на сервер
    let xhr = new XMLHttpRequest();
    xhr.open('POST', '/submitOrder', true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.setRequestHeader(csrfHeader, csrfToken); // Передача CSRF-токена в заголовке
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.log('Заказ успешно отправлен!');
            // Очищаем корзину после успешной отправки
            clearOrder();
        }
    };
    xhr.send(JSON.stringify(orders));
}

function showPayBlock() {
    let headButtons = document.getElementById('buttons-head');
    headButtons.innerHTML = '';
    let backButton = document.createElement('buttonBack');
    backButton.classList.add('button-settings');
    backButton.textContent = '🔙';
    backButton.onclick = function() {
        drawMainContainer();
    }
    headButtons.appendChild(backButton);


    let mainContainer = document.getElementById('main-container');

    // Очищаем контейнер перед отрисовкой
    mainContainer.innerHTML = '';

    // Создаем левую часть (leftContainer)
    let leftContainer = document.createElement('div');
    leftContainer.classList.add('left-Settings');


    mainContainer.appendChild(leftContainer);
}

function getShiftToSettings(shiftButton) {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/getOpenShift', true);
    xhr.setRequestHeader(csrfHeader, csrfToken);

    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            // Обработка успешного ответа от сервера
            if(xhr.responseText)
                shift = JSON.parse(xhr.responseText);
            shift=xhr.responseText;
            // После получения ответа, проверяем условие
            if (shift) {
                shiftButton.textContent = 'Закрыть смену';
                shiftButton.onclick = function() {
                    closeShift(shiftButton);
                };
            } else {
                shiftButton.textContent = 'Открыть смену';
                shiftButton.onclick = function() {
                    openShift(shiftButton);
                };
            }
        }
    };

    // Отправляем запрос
    xhr.send();
}

function openShift(shiftButton) {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/openShift', true);
    xhr.setRequestHeader(csrfHeader, csrfToken);

    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            // Обработка успешного ответа от сервера
            shift = JSON.parse(xhr.responseText);

            // После получения ответа, проверяем условие
                shiftButton.textContent = 'Закрыть смену';
                shiftButton.onclick = function() {
                    closeShift(shiftButton);
                };
            }
        };
    // Отправляем запрос
    xhr.send();
}

function closeShift(shiftButton) {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/closeShift', true);
    xhr.setRequestHeader(csrfHeader, csrfToken); // Передача CSRF-токена в заголовке
    xhr.onreadystatechange =  function() {
        // и статус ответа сервера 200 (OK)
        if (xhr.readyState === 4 && xhr.status === 200)
            // Обработка успешного ответа от сервера
            console.log(xhr.responseText);
        shiftButton.textContent = 'Открыть смену';
        shiftButton.onclick = function() {
            openShift(shiftButton);
        };

    };
    // Отправляем запрос
    xhr.send();
}

function xReport() {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/getXReport', true);
    xhr.setRequestHeader(csrfHeader, csrfToken); // Передача CSRF-токена в заголовке
    xhr.onreadystatechange =  function() {
        // и статус ответа сервера 200 (OK)
        if (xhr.readyState === 4 && xhr.status === 200)
            // Обработка успешного ответа от сервера
            console.log(xhr.responseText);
    };
    // Отправляем запрос
    xhr.send();
}

function getCategoriesToPage() {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/getCategory', true);
    xhr.setRequestHeader(csrfHeader, csrfToken); // Передача CSRF-токена в заголовке
    xhr.onreadystatechange = function() {
        // Проверяем, что запрос завершен (readyState = 4)
        // и статус ответа сервера 200 (OK)
        if (xhr.readyState === 4 && xhr.status === 200) {
            // Обработка успешного ответа от сервера
            categoryData = JSON.parse(xhr.responseText);
            drawMainContainer()
            showCategory();
        }
    };
    // Отправляем запрос
    xhr.send();
}

function getItemsToPage() {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/getItemsToPage', true);
    xhr.setRequestHeader(csrfHeader, csrfToken); // Передача CSRF-токена в заголовке
    xhr.onreadystatechange = function() {
        // Проверяем, что запрос завершен (readyState = 4)
        // и статус ответа сервера 200 (OK)
        if (xhr.readyState === 4 && xhr.status === 200) {
            // Обработка успешного ответа от сервера
            itemsToPage = JSON.parse(xhr.responseText);
        }
    };
    // Отправляем запрос
    xhr.send();
}
