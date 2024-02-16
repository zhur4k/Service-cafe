let items = [];
let category = [];
let units = [];
let headers = ["Код", "Имя", "Цена", "Кол-во в ед.", "Наценка (%)", "Видимость", "Тип", "Категория", "Ед. изм.", "Состав"];

getItems();

function displayTable() {
    const tableBody = document.getElementById('table');
    tableBody.innerText = '';
    createTableTitle();

    items.forEach(function(item) {
        tableBody.appendChild(createTableRow(item));
    });
}

function createTableRow(item) {
    let row = document.createElement('tr');

    let codeInput = createInput('text', item,'code', true);
    let nameInput = createInput('text', item,'nameOfItems', true);
    let priceInput = createInput('number', item,'priceToPage', true, 0.01);
    let unitPriceInput = createInput('number', item,'unitPriceToPage', true, 0.001);
    let markupTd = document.createElement('td');
    markupTd.textContent =item.markup;
    let markupCell = markupTd;

    let viewSelect = createSelect(['true', 'false'], ['Виден', 'Не виден'], item.view, item,'view');
    let typeOfItemSelect = createSelect(['true', 'false'], ['Кухня', 'Бар'],item.typeOfItem, item,'typeOfItem');

    let categoriesSelect = createSelectObject(
        category,
        category.map(function(categor) { return categor.categoriesName; }),
        item.categories.id,
        item,
        'categories'
    );

    let unitsSelect = createSelectObject(
        units,
        units.map(function(unit) { return unit.name; }),
        item.unit.id,
        item,
        'unit'
    );

    let editLink = document.createElement('td');
    let editLinkAnchor = document.createElement('a');
    editLinkAnchor.href = '/admin/items/' + item.id;
    editLinkAnchor.className = 'btnchange';
    editLinkAnchor.textContent = 'Изменить';
    editLink.appendChild(editLinkAnchor);

    // Добавляем ячейки в строку
    row.appendChild(codeInput);
    row.appendChild(nameInput);
    row.appendChild(priceInput);
    row.appendChild(unitPriceInput);
    row.appendChild(markupCell);
    row.appendChild(viewSelect);
    row.appendChild(typeOfItemSelect);
    row.appendChild(categoriesSelect);
    row.appendChild(unitsSelect);
    row.appendChild(editLink);

    return row;
}

// Вспомогательная функция для создания input элемента
function createInput(type, item,fieldName, required, step) {
    let input = document.createElement('input');
    input.type = type;
    input.value = item[fieldName];
    if (required) input.required = true;
    if (step) input.step = step;
    input.addEventListener('input', function() {
            // Обновляем свойство в объекте item при изменении значения в поле ввода
            item[fieldName] = input.value;
        });
    return createCell(input);
}

// Вспомогательная функция для создания td элемента
function createCell(content) {
    let cell = document.createElement('td');
    cell.appendChild(content);
    return cell;
}
function createSelectObject(values, texts, selectedValue, item, fieldName) {
    let select = document.createElement('select');

    for (let i = 0; i < values.length; i++) {
        let option = document.createElement('option');
        option.value = values[i].id; // Assuming values[i] is an object with an 'id' property
        option.text = texts[i];
        if (values[i].id === selectedValue) option.selected = true;
        select.appendChild(option);
    }

    // Добавляем обработчик события для изменения выбора
    select.addEventListener('change', function() {
        // Find the selected category object by its id
        item[fieldName] = category.find(obj => obj.id === parseInt(select.value, 10));
    });

    return createCell(select);
}
// Вспомогательная функция для создания select элемента
function createSelect(values, texts, selectedValue, item, fieldName) {
    let select = document.createElement('select');

    for (let i = 0; i < values.length; i++) {
        let option = document.createElement('option');
        option.value = values[i];
        option.text = texts[i];
        // Convert selectedValue to string for comparison
        if (String(values[i]) === String(selectedValue)) {
            option.selected = true;
        }
        select.appendChild(option);
    }

    // Добавляем обработчик события для изменения выбора
    select.addEventListener('change', function() {
        item[fieldName] = select.value;
    });

    return createCell(select);
}

function createTableTitle() {
    const tableElement = document.getElementById('table');
    // Создаем строку заголовков
    let headerRow = document.createElement('tr');

    // Добавляем заголовки в строку
    headers.forEach(function(headerText) {
        let th = document.createElement('th');
        th.textContent = headerText;
        headerRow.appendChild(th);
    });

    // Добавляем строку заголовков в thead
    tableElement.appendChild(headerRow);
}
function getItems() {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/admin/items/getAll', true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            items = JSON.parse(xhr.responseText);
            getUnits();
            getCategories();
        }
    };
    xhr.send();
}
function getUnits() {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/admin/items/getUnits', true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            units = JSON.parse(xhr.responseText);
        }
    };
    xhr.send();
}
function getCategories() {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/admin/items/getCategories', true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            category = JSON.parse(xhr.responseText);
            displayTable();
        }
    };
    xhr.send();
}
function saveChanges(){
    let xhr = new XMLHttpRequest();
    xhr.open('POST', '/admin/items/saveAll', true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200){
            getItems();
        }
        console.log(xhr.responseText);
    };
    xhr.send(JSON.stringify(items));
}