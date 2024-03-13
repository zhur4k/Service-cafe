let orderItems = [];
let headers = ["Категория", "Название", "Ед.изм.", "Кол-во", "Цена", "Сумма"];

function showReport() {
    getOrderItems();
}

function displayTable() {
    const tableBody = document.getElementById('table');
    tableBody.innerText = '';
    createTableTitle();

    orderItems.forEach(function (item) {
        tableBody.appendChild(createTableRow(item));
    });
}

function createTableRow(item) {
    let row = document.createElement('tr');
    row.appendChild(createTableCell(item.category));
    row.appendChild(createTableCell(item.nameOfItems));
    row.appendChild(createTableCell(item.unit));
    row.appendChild(createTableCell(item.quantity));
    row.appendChild(createTableCell(((item.price / 100) / item.quantity).toFixed(2)));
    row.appendChild(createTableCell((item.price / 100).toFixed(2)));
    return row;
}

function createTableCell(innerText) {
    let tdElement = document.createElement('td');
    tdElement.textContent = innerText;
    return tdElement;
}

function createTableTitle() {
    const tableElement = document.getElementById('table');
    // Создаем строку заголовков
    let headerRow = document.createElement('tr');

    // Добавляем заголовки в строку
    headers.forEach(function (headerText) {
        let th = document.createElement('th');
        th.textContent = headerText;
        headerRow.appendChild(th);
    });

    // Добавляем строку заголовков в thead
    tableElement.appendChild(headerRow);
}

function getOrderItems() {
    let startDateInput = document.getElementById("startDate");
    let endDateInput = document.getElementById("endDate");
    let dateFromPage = {
        startDate: startDateInput.value,
        endDate: endDateInput.value,
    };
    let xhr = new XMLHttpRequest();
    xhr.open('POST', '/admin/getAllOrderItems', true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            orderItems = JSON.parse(xhr.responseText);
            displayTable();
        }
    };
    xhr.send(JSON.stringify(dateFromPage));
}
