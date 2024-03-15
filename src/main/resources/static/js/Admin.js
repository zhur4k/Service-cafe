let itemsOnPage = [];

function sortItems() {
    const option = document.getElementById('sortBy').value; // Приводим значение к нижнему регистру для сравнения
    itemsOnPage.sort(function(a, b) {
        let valueA = a[option];
        let valueB = b[option];

        // Преобразуем значения в строки для сравнения, если они не являются строками
        if (typeof valueA !== 'string') {
            valueA = String(valueA);
        }
        if (typeof valueB !== 'string') {
            valueB = String(valueB);
        }

        // Проверяем, являются ли значения числами
        if (!isNaN(Number(valueA)) && !isNaN(Number(valueB))) {
            // Если оба значения числа, сравниваем их как числа
            return Number(valueA) - Number(valueB);
        } else {
            // Если хотя бы одно значение не число, сравниваем их как строки
            if (valueA < valueB) {
                return -1;
            }
            if (valueA > valueB) {
                return 1;
            }
            return 0; // Если значения равны
        }
    });
    displayTable();
}

function displayTable() {
    const tableBody = document.getElementById('table');
    tableBody.innerHTML = ''; // Лучше использовать innerHTML для очистки таблицы
    createTableTitle(); // Создаем заголовок таблицы
    itemsOnPage.forEach(function(item) {
        tableBody.appendChild(createTableRow(item));
    });
}

function createTableRow(item) {
    const row = document.createElement('tr');
    Object.values(item).forEach(function(value) {
        row.appendChild(createTableCell(value));
    });
    return row;
}

function createSortOptions() {
    const sortSelect = document.getElementById('sortBy');
    sortSelect.innerHTML = ''; // Очищаем предыдущие опции

    // Получаем все возможные названия столбцов
    const options = Object.keys(itemsOnPage[0]); // Обрабатываем случай, когда itemsOnPage пустой массив

    options.forEach(function(option) {
        const optionElement = document.createElement('option');
        optionElement.value = option;
        optionElement.textContent = option;
        sortSelect.appendChild(optionElement);
    });
}

function createTableCell(innerText) {
    const tdElement = document.createElement('td');
    tdElement.textContent = innerText;
    return tdElement;
}

function createTableTitle() {
    const tableElement = document.getElementById('table');
    const headerRow = document.createElement('tr');

    // Получаем все возможные названия столбцов
    const headers = Object.keys(itemsOnPage[0]);
    headers.forEach(function(headerText) {
        const th = document.createElement('th');
        th.textContent = headerText;
        headerRow.appendChild(th);
    });
    tableElement.appendChild(headerRow);
}

function getItemsOnPage() {
    const startDateInput = document.getElementById("startDate");
    const endDateInput = document.getElementById("endDate");
    const dateFromPage = {
        startDate: startDateInput.value,
        endDate: endDateInput.value,
    };
    const option = document.getElementById('reportType').value;
    const xhr = new XMLHttpRequest();
    xhr.open('POST', '/admin/getAll' + option, true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            itemsOnPage = JSON.parse(xhr.responseText);
            createSortOptions();
            displayTable();
        }
    };
    xhr.send(JSON.stringify(dateFromPage));
}
