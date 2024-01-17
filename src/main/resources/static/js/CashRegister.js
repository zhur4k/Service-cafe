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

let cashPaid=0;
let cashLessPaid=0;
let establishmentPaid=0;
let total=0;
getCategoriesToPage();
getItemsToPage();

//Display main container
function displayMainContainer() {
    let headButtons = document.getElementById('buttons-head');
    headButtons.innerHTML = '';
    let settingsButton = document.createElement('button');
    settingsButton.classList.add('button-in-header');
    settingsButton.textContent = '☸';
    settingsButton.onclick = function() {
        displaySettings();
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

    let total = document.createElement('h2');
    total.id = 'total';
    total.textContent = '0.00 р';

    let payButton = document.createElement('button');
    payButton.type = 'button';
    payButton.className = 'button';
    payButton.textContent = 'Оплатить';
    payButton.onclick = function() {
        checkOrderAndDisplayPayBlock();
    };

    rightContainer.appendChild(orderList);
    rightContainer.appendChild(total);
    rightContainer.appendChild(payButton);

    // Добавляем левую и правую части в основной контейнер
    mainContainer.appendChild(leftContainer);
    mainContainer.appendChild(rightContainer);
    displayCategories();
    displayOrder();
}

function showListOfUser(rightContainerChild2) {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/getOpenShift', true);
    xhr.setRequestHeader(csrfHeader, csrfToken);

    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            // Обработка успешного ответа от сервера
            if (xhr.responseText)
            shift = xhr.responseText;
            // После получения ответа, проверяем условие
            if (shift) {
                let xhr = new XMLHttpRequest();
                xhr.open('GET', '/getListOfUsers', true);
                xhr.setRequestHeader(csrfHeader, csrfToken);

                xhr.onreadystatechange = function() {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        // Обработка успешного ответа от сервера
                        if (xhr.responseText){
                            let listOfUser = document.createElement('div');
                            listOfUser.style.fontSize= '40px';
                            listOfUser.style.color = 'white';
                            listOfUser.innerHTML =  xhr.responseText;
                            rightContainerChild2.appendChild(listOfUser);
                        }
                    }
                };
                // Отправляем запрос
                xhr.send();
            } else {
                showMessage();
            }
        }
    };
    // Отправляем запрос
    xhr.send();

}

function showUserAdd() {
    let mainContainer = document.getElementById('main-container');
    let rightContainer = document.getElementById('right-container');

// Проверяем, существует ли элемент rightContainer
    if (rightContainer) {
        // Если существует, удаляем его
        rightContainer.remove();
        return
    }

    let loginInput = document.createElement('input');
    loginInput.id='login';
    loginInput.classList.add('button-in-settings');

    rightContainer = document.createElement('div');
    rightContainer.id='right-container';
    rightContainer.classList.add('right-settings');
    let rightContainerChild = document.createElement('div');
    rightContainerChild.classList.add('right-settings-button-container');
    let rightContainerChild2 = document.createElement('div');


    let addCashButton = document.createElement('button');
    addCashButton.type = 'button';
    addCashButton.className = 'button-in-settings';
    addCashButton.textContent = 'Добавить';
    addCashButton.onclick = function() {
        addUserToShift();
    };

    rightContainerChild2.appendChild(loginInput);

    rightContainerChild.appendChild(addCashButton);
    showListOfUser(rightContainerChild2);
    rightContainer.appendChild(rightContainerChild2);
    rightContainer.appendChild(rightContainerChild);
    mainContainer.appendChild(rightContainer);
}

//Display settings page
function displaySettings() {
    let headButtons = document.getElementById('buttons-head');
    headButtons.innerHTML = '';
    let backButton = document.createElement('buttonBack');
    backButton.classList.add('button-in-header');
    backButton.textContent = '🔙';
    backButton.onclick = function() {
        displayMainContainer();
    }
    headButtons.appendChild(backButton);


    let mainContainer = document.getElementById('main-container');

    // Очищаем контейнер перед отрисовкой
    mainContainer.innerHTML = '';

    // Создаем левую часть (leftContainer)
    let leftContainer = document.createElement('div');
    leftContainer.classList.add('left-settings');

    let shiftButton = document.createElement('button');
    shiftButton.id = "shiftButton";
    shiftButton.type = 'button';
    shiftButton.className = 'button-in-settings';
    checkShiftButtonState();

    let xButton = document.createElement('button');
    xButton.type = 'button';
    xButton.className = 'button-in-settings';
    xButton.textContent = 'X-Отчёт';
    xButton.onclick = function() {
        xReportButtonClick();
    };
    let collectionButton = document.createElement('button');
    collectionButton.type = 'button';
    collectionButton.className = 'button-in-settings';
    collectionButton.textContent = 'Инкассация';
    collectionButton.onclick = function() {
        showCollection();
    };
    let addUserButton = document.createElement('button');
    addUserButton.type = 'button';
    addUserButton.className = 'button-in-settings';
    addUserButton.textContent = 'Добавить работника';
    addUserButton.onclick = function() {
        showUserAdd();
    };
    leftContainer.appendChild(shiftButton);
    leftContainer.appendChild(xButton);
    leftContainer.appendChild(collectionButton);
    leftContainer.appendChild(addUserButton);

    mainContainer.appendChild(leftContainer);

}

function displayPayBlock(){
    let headButtons = document.getElementById('buttons-head');
    headButtons.innerHTML = '';
    let backButton = document.createElement('buttonBack');
    backButton.classList.add('button-in-header');
    backButton.textContent = '🔙';
    backButton.onclick = function() {
        displayMainContainer();
    }
    headButtons.appendChild(backButton);


    let mainContainer = document.getElementById('main-container');

    // Очищаем контейнер перед отрисовкой
    mainContainer.innerHTML = '';

    let rightContainer = document.createElement('div');
    rightContainer.classList.add('right-pay');

    mainContainer.appendChild(getInputForm());


    setSumAfterPaidOperation();
    let cashContainer = document.createElement('div');
    cashContainer.classList.add('child-block-pay');
    cashContainer.textContent = 'Наличные $';
    cashContainer.onclick = function () {
        cashPaid+= document.getElementById('sum-input').textContent*100;
        setSumAfterPaidOperation();
    }
    rightContainer.appendChild(cashContainer);

    let lessCashContainer = document.createElement('div');
    lessCashContainer.classList.add('child-block-pay');
    lessCashContainer.textContent = 'Безналичный $'
    lessCashContainer.onclick = function () {
        cashLessPaid+= document.getElementById('sum-input').textContent*100;
        setSumAfterPaidOperation();
    }
    rightContainer.appendChild(lessCashContainer);

    let establishmentContainer = document.createElement('div');
    establishmentContainer.classList.add('child-block-pay');
    establishmentContainer.textContent = 'За счёт заведения';
    establishmentContainer.onclick = function () {
        establishmentPaid+= document.getElementById('sum-input').textContent*100;
        setSumAfterPaidOperation();
    }

    rightContainer.appendChild(establishmentContainer);

    mainContainer.appendChild(rightContainer);
}
function displayCheck() {
    let headButtons = document.getElementById('buttons-head');
    headButtons.innerHTML = '';
    let backButton = document.createElement('buttonBack');
    backButton.classList.add('button-in-header');
    backButton.textContent = '🔙';
    backButton.onclick = function() {
        displayMainContainer();
    }
    headButtons.appendChild(backButton);

    let mainContainer = document.getElementById('main-container');
    // Очищаем контейнер перед отрисовкой
    mainContainer.innerHTML = '';

    // Создаем левую часть (mainCheckBlock)
    let mainCheckBlock = document.createElement('div');
    mainCheckBlock.classList.add('main-check-block');
    let mainCheckBlockChild = document.createElement('div');
    mainCheckBlockChild.classList.add('main-check-block-child');

    let orderList = document.createElement('div');
    orderList.classList.add('order-list-check');

    let table = document.createElement('table');
    table.classList.add('table-check');
    let tbody = document.createElement('tbody');
    tbody.id = 'order-items';

    table.appendChild(tbody);
    orderList.appendChild(table);

    let totalH = document.createElement('div');
    totalH.classList.add('total');
    totalH.id = 'total';
    totalH.textContent  = 'Итог: '+(total/100).toFixed(2)+ 'р';

    let sumOfPaid = document.createElement('div');
    sumOfPaid.classList.add('total');
    sumOfPaid.textContent = 'Наличными: '+(cashPaid/100).toFixed(2)+' р  Картой: '+(cashLessPaid/100).toFixed(2)
    +' р  За счёт заведения: '+(establishmentPaid/100).toFixed(2)+' р';
    mainCheckBlockChild.appendChild(orderList);
    mainCheckBlockChild.appendChild(totalH);
    mainCheckBlock.appendChild(mainCheckBlockChild);
    mainCheckBlock.appendChild(sumOfPaid);
    mainContainer.appendChild(mainCheckBlock);

    const orderItemTable = document.getElementById("order-items");
    orderItemTable.innerHTML = "";

    orderItems.forEach((item, index) => {
        const row = document.createElement("tr");
        const itemCell = document.createElement("td");
        const quantityCell = document.createElement("td");
        const totalCell = document.createElement("td");

        itemCell.textContent = item.items.nameOfItems;
        quantityCell.textContent = item.quantity;
        totalCell.textContent = ((item.items.price * item.quantity) / 100).toFixed(2) + " р";

        quantityCell.style.width = "20%";
        quantityCell.style.textAlign = "center"
        quantityCell.classList.add("order-table-td-check");

        itemCell.style.width = "60%";
        itemCell.classList.add("order-table-td-check");

        totalCell.style.width = "20%";
        totalCell.style.textAlign = "center"
        totalCell.classList.add("order-table-td-check");

        row.appendChild(quantityCell);
        row.appendChild(itemCell);
        row.appendChild(totalCell);

        orderItemTable.appendChild(row);

    });
    clearOrder();
    cashPaid = 0;
    cashLessPaid = 0;
    establishmentPaid=0;
    let displayTime = 4000; // например, 5000 миллисекунд (5 секунд)
    setTimeout(function() {
        displayMainContainer();
    }, displayTime);
}

function setSumAfterPaidOperation(){
    let sumToPai = (total-cashPaid-cashLessPaid-establishmentPaid);
    if(sumToPai===0){
        return submitOrder();
    }
    document.getElementById('sum-input').textContent =(sumToPai/100).toFixed(2);
    document.getElementById( 'sum-info').textContent = 'К оплате: '+(sumToPai/100).toFixed(2)
        +'руб из '+(sumToPai/100).toFixed(2)+'руб';

}
//Show pay block
function checkOrderAndDisplayPayBlock() {
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
                if (orderItems.length<1){
                    return showMessage('Добавьте товары в заказ!!!');
                }
                displayPayBlock();
            } else {
                showMessage();
            }
        }
    };
    // Отправляем запрос
    xhr.send();
}

function showCollection() {
    let mainContainer = document.getElementById('main-container');
    let rightContainer = document.getElementById('right-container');

// Проверяем, существует ли элемент rightContainer
    if (rightContainer) {
        // Если существует, удаляем его
        rightContainer.remove();
        return
    }
    rightContainer = document.createElement('div');
    rightContainer.id='right-container';
    rightContainer.classList.add('right-settings');
    let rightContainerChild = document.createElement('div');
    rightContainerChild.classList.add('right-settings-button-container');

    
    let addCashButton = document.createElement('button');
    addCashButton.type = 'button';
    addCashButton.className = 'button-in-settings';
    addCashButton.textContent = 'Внести';
    addCashButton.onclick = function() {
        sendCollectionMove(true);
    };
    let takeCashButton = document.createElement('button');
    takeCashButton.type = 'button';
    takeCashButton.className = 'button-in-settings';
    takeCashButton.textContent = 'Изъять';
    takeCashButton.onclick = function() {
        sendCollectionMove(false);
    };

    let sumInCashRegister = document.createElement('div');
    sumInCashRegister.id = 'sum-in-cashRegister';
    sumInCashRegister.classList.add('sum-in-cash-register');
    getSumInCashRegister();
    rightContainer.appendChild(getInputForm());
    rightContainerChild.appendChild(addCashButton);
    rightContainerChild.appendChild(takeCashButton);
    rightContainerChild.appendChild(sumInCashRegister);
    rightContainer.appendChild(rightContainerChild);
    mainContainer.appendChild(rightContainer);
}
//Get sum in Cash Register
function getSumInCashRegister() {
// Отправляем заказ на сервер
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/sumInCashRegister', true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.setRequestHeader(csrfHeader, csrfToken); // Передача CSRF-токена в заголовке
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            document.getElementById('sum-in-cashRegister').textContent = 'В кассе: '+xhr.responseText+' руб';
        }
    };
    xhr.send();
}
//Send move of cash in cashRegister
function sendCollectionMove(collectionType) {
    let collection = ({
        typeOfOperation: collectionType,
        sumOfOperation: (parseFloat(document.getElementById('sum-input').innerText).toFixed(2)*100) // Массив с информацией о товарах в заказе
    });
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
                // Отправляем заказ на сервер
                let xhr = new XMLHttpRequest();
                xhr.open('POST', '/collectionMove', true);
                xhr.setRequestHeader('Content-Type', 'application/json');
                xhr.setRequestHeader(csrfHeader, csrfToken); // Передача CSRF-токена в заголовке
                xhr.onreadystatechange = function() {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        showMessage('Инкассация проведена успешно!!!','green');
                        getSumInCashRegister();
                    }else{
                        showMessage("В кассе нет столько денег!!!");}
                };
                xhr.send(JSON.stringify(collection));
            } else {
                showMessage();
            }
        }
    };

    // Отправляем запрос
    xhr.send();
}
//Display categories to page
function displayCategories() {
    let leftContainer = document.getElementById('leftContainer');
    leftContainer.innerHTML = "";
    // Итерация по данным и создание элементов
    categoryData.forEach(function(categories) {
        const categoryDiv = document.createElement('div');
        categoryDiv.classList.add('child-block');

        categoryDiv.textContent = categories.categoriesName;
        categoryDiv.onclick = function() {
            displayProducts(categories);
        };
        leftContainer.appendChild(categoryDiv);
    });
}

//Display order to page
function displayProducts(categories) {
    let leftContainer = document.getElementById('leftContainer');
    leftContainer.innerHTML = "";

    let backDiv = document.createElement('div');
    backDiv.classList.add('child-block');
    backDiv.onclick = function() {
        displayCategories();
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

//Return sum input form
function getInputForm() {
    let inputForm = document.createElement('div')
    inputForm.classList.add('sum-input-container')
    let sumInputContainer = document.createElement('div');
    sumInputContainer.classList.add('sum-input-container-child')

    let sumInput = document.createElement('div');
    sumInput.classList.add('sum-input');
    sumInput.id = 'sum-input';

    let sumInfo = document.createElement('div');
    sumInfo.classList.add('sum-info');
    sumInfo.id = 'sum-info';

    let label = document.createElement('div');
    label.textContent = 'руб';
    let buttonClear = document.createElement('button');
    buttonClear.classList.add('button-clear-form-input');
    buttonClear.textContent = '×';
    buttonClear.onclick = ClearInputFormSum;
    let buttonContainer = document.createElement('div');
    // buttonContainer.classList.add('sum-input-container');
    for (let i = 1; i <= 9; i++) {
        let button = createButtonToSumElement(i);
        buttonContainer.appendChild(button);
    }
    let dotButton = createButtonToSumElement('.');
    buttonContainer.appendChild(dotButton);
    let zeroButton = createButtonToSumElement('0');
    buttonContainer.appendChild(zeroButton);
    let deleteButton = createButtonToSumElement('⌫');
    deleteButton.onclick = deleteLastChar;
    buttonContainer.appendChild(deleteButton);

    sumInputContainer.appendChild(sumInput);
    sumInputContainer.appendChild(label);
    sumInputContainer.appendChild(buttonClear);

    inputForm.appendChild(sumInputContainer);
    inputForm.appendChild(sumInfo);
    inputForm.appendChild(buttonContainer);
    return inputForm;
}

//Clear all char from input form sum element
function ClearInputFormSum() {
    let sumInput = document.getElementById('sum-input');
    sumInput.textContent ='';
}

//Delete last char from input form sum element
function deleteLastChar() {
    let sumInput = document.getElementById('sum-input');
    let currentText = sumInput.textContent;
    sumInput.textContent = currentText.slice(0, -1);
}

//Append Char to sum Input element
function appendToInput(value) {
    let inputElement = document.getElementById('sum-input');
    let currentValue = inputElement.textContent;

    // Проверка наличия точки в текущем вводе
    let hasDot = currentValue.includes('.');
    if(hasDot&&value==='.') {
        return;
        // Если текущий ввод не содержит точку или содержит точку, но после неё нет числа
    }else if (!hasDot || (hasDot && !/\d$/.test(currentValue))) {
        // Если введена точка, добавляем 0 перед ней
        if (value === '.' && !/\d$/.test(currentValue)) {
            inputElement.textContent += '0.';
        }
        // Если текущий ввод - ноль и введено число, то введенное значение будет новым вводом
        else if (currentValue === '0' && /\d/.test(value)) {
            inputElement.textContent = value;
        }
        // Добавляем ввод к текущему значению
        else {
            inputElement.textContent += value;
        }
    }

    // Если текущий ввод содержит точку
    else if (hasDot) {
        // Разбиваем число на целую и десятичную части
        let parts = currentValue.split('.');
        let integerPart = parts[0];
        let decimalPart = parts[1] || '';

        // Если в десятичной части уже есть два знака после точки, не добавляем больше
        if (decimalPart.length < 2) {
            // Добавляем ввод к текущей десятичной части
            inputElement.textContent += value;
        }
    }

    // Ограничиваем ввод, чтобы результат не превышал 10000
    if (parseFloat(inputElement.textContent) > 10000) {
        inputElement.textContent = '10000';
    }
}

//Create button to sum input form
function createButtonToSumElement(value) {
    let button = document.createElement('button');
    button.className = 'button-sum-input';
    button.textContent = value;
    button.onclick = function() {
        appendToInput(value);
    };
    return button;
}

//Add product to order
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

//Display order to page
function displayOrder() {
    const orderItemTable = document.getElementById("order-items");
    orderItemTable.innerHTML = "";
    total = 0;

    orderItems.forEach((item, index) => {
        const row = document.createElement("tr");
        const itemCell = document.createElement("td");
        const quantityCell = document.createElement("td");
        const totalCell = document.createElement("td");
        const removeCell = document.createElement("td");
        const removeButton = document.createElement("button");

        itemCell.textContent = item.items.nameOfItems;
        quantityCell.textContent = (item.quantity*item.items.unitPriceToPage).toFixed(3)+item.items.unit.name;
        const itemTotal = (item.items.priceToPage * item.quantity).toFixed(2) + " р";
        totalCell.textContent = itemTotal;
        removeButton.textContent = "Удалить";
        removeButton.classList.add("remove-button");
        removeButton.addEventListener("click", () => removeItem(index));

        quantityCell.style.width = "20%";
        quantityCell.style.textAlign = "center"
        quantityCell.classList.add("order-table-td");

        itemCell.style.width = "40%";
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

//Remove item from page
function removeItem(index) {
    orderItems.splice(index, 1);
    displayOrder();
}

//Clear order on page
function clearOrder() {
    orderItems = [];
}


function addUserToShift(){
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
                // Отправляем заказ на сервер
                let xhr = new XMLHttpRequest();
                xhr.open('POST', '/addUserToShift', true);
                xhr.setRequestHeader('Content-Type', 'application/json');
                xhr.setRequestHeader(csrfHeader, csrfToken); // Передача CSRF-токена в заголовке
                xhr.onreadystatechange = function() {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        showMessage('Пользователь добавлен!!!','green');
                    }else{
                        showMessage('Пользователь уже добавлен!!!');
                    }
                };
                let login =document.getElementById('login');
                xhr.send(login.value);
                login.value = '';
            } else {
                showMessage();
            }
        }
    };

    // Отправляем запрос
    xhr.send();
}
//Send order to server
function submitOrder() {
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
                // Создаем объект заказа
                let orders = ({
                    establishmentPaid:establishmentPaid,
                    cashPaid:cashPaid,
                    cashLessPaid:cashLessPaid,
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
                        displayCheck();
                    }
                };
                xhr.send(JSON.stringify(orders));
            } else {
                showMessage('error');
            }
        }
    };

    // Отправляем запрос
    xhr.send();
}


//Check state of shift and set corresponding button
function checkShiftButtonState() {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/getOpenShift', true);
    xhr.setRequestHeader(csrfHeader, csrfToken);

    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                // Обработка успешного ответа от сервера
                try {
                    let shift = xhr.responseText ? JSON.parse(xhr.responseText) : null;
                    let shiftButton = document.getElementById("shiftButton");
                    console.log(shift);
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
                } catch (error) {
                    console.error('Ошибка при разборе ответа: ', error);
                }
            } else {
                // Обработка ошибок
                console.error('Ошибка запроса: ', xhr.status);
            }
        }
    };

// Отправляем запрос
    xhr.send();

}

//Show message in messages div
function showMessage(message='Откройте смену!!!',color='red'){
    let messageElement = document.getElementById('messages');
    messageElement.style.fontSize = "26px";
    messageElement.style.color = color;
    messageElement.textContent = message;
    let displayTime = 5000; // например, 5000 миллисекунд (5 секунд)
    setTimeout(function() {
        messageElement.textContent = ''; // Очищаем содержимое элемента
    }, displayTime);
}

//Send GET request to open shift and changing shift button state
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

//Send GET request to close shift and changing shift button state
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

//Checking state of shift and calls xReportSend
function xReportButtonClick() {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/getOpenShift', true);
    xhr.setRequestHeader(csrfHeader, csrfToken);

    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            // Обработка успешного ответа от сервера
            if (xhr.responseText)
                shift = JSON.parse(xhr.responseText);
            shift = xhr.responseText;
            // После получения ответа, проверяем условие
            if (shift) {
                xReportSend();
            } else {
                showMessage();
            }
        }
    };
    // Отправляем запрос
    xhr.send();
}

//Sending GET request to X-Report
function xReportSend(){
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/getXReport', true);
    xhr.setRequestHeader(csrfHeader, csrfToken); // Передача CSRF-токена в заголовке
    xhr.onreadystatechange = function () {
        // и статус ответа сервера 200 (OK)
        if (xhr.readyState === 4 && xhr.status === 200)
            // Обработка успешного ответа от сервера
            console.log(xhr.responseText);
    };
    // Отправляем запрос
    xhr.send();
    }

//Sending GET request that returns all Categories
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
            displayMainContainer()
            displayCategories();
        }
    };
    // Отправляем запрос
    xhr.send();
}

//Sending GET request that returns all Items
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