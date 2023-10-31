orderItems =[];

function toggleCategory(category) {
    const products = document.getElementById(category);
    products.style.display = products.style.display === 'block' ? 'none' : 'block';
}

function addProduct(productName, pric) {
    const price = Number(pric) / 100;
    // Поиск продукта в массиве по имени
    const existingProduct = orderItems.find(orderItem => orderItem.items.nameOfItems === productName);

    if (existingProduct) {
        // Если продукт с таким именем уже существует, увеличиваем количество
        existingProduct.quantity += 1;
    } else {
        // Иначе создаем новый продукт
        orderItems.push({ quantity: 1, items: { price: price ,nameOfItems: productName } });
    }

    displayOrder();
}

    function displayOrder() {
    const orderItemsd = document.getElementById("order-items");
    orderItemsd.innerHTML = "";
    let total = 0;

    orderItems.forEach((item, index) => {
        const row = document.createElement("tr");
        const itemCell = document.createElement("td");
        const priceCell = document.createElement("td");
        const quantityCell = document.createElement("td");
        const totalCell = document.createElement("td");
        const removeCell = document.createElement("td");
        const removeButton = document.createElement("button");

        itemCell.textContent = item.items.nameOfItems;
        priceCell.textContent = item.items.price.toFixed(2);
        quantityCell.textContent = item.quantity;
        const itemTotal = (item.items.price * item.quantity).toFixed(2);
        totalCell.textContent = itemTotal;
        removeButton.textContent = "Удалить";
        removeButton.addEventListener("click", () => removeItem(index));

        row.appendChild(itemCell);
        row.appendChild(priceCell);
        row.appendChild(quantityCell);
        row.appendChild(totalCell);
        removeCell.appendChild(removeButton);
        row.appendChild(removeCell);

        orderItemsd.appendChild(row);

        total += parseFloat(itemTotal);
    });

    document.getElementById("total").textContent = total.toFixed(2);
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
    const paymentMethod = document.querySelector('input[name="payment"]:checked').value;
    // Создаем объект заказа
    const order = {
        paymentMethod: paymentMethod,
        orderItems: orderItems, // Массив с информацией о товарах в заказе
    };

        const csrfToken = document.querySelector('meta[name="_csrf"]').content;
        const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

        // Отправляем заказ на сервер
        const xhr = new XMLHttpRequest();
        xhr.open('POST', '/submitOrder', true);
        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.setRequestHeader(csrfHeader, csrfToken); // Передача CSRF-токена в заголовке
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4) {
                console.log('Статус ответа:', xhr.status);
                if (xhr.status === 200) {
                    console.log('Заказ успешно отправлен!');
                    // Очищаем корзину после успешной отправки
                    clearOrder();
                }
            }
        };
        xhr.send(JSON.stringify(order));


}
