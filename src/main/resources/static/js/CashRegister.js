products = [];

function toggleCategory(category) {
    const products = document.getElementById(category);
    products.style.display = products.style.display === 'block' ? 'none' : 'block';
}

function addProduct(productName, pric) {
    const quantity = 1;
    const price = Number(pric)/100;
    products.push({ item: productName, price, quantity });
    displayOrder();
}

function displayOrder() {
    const orderItems = document.getElementById("order-items");
    orderItems.innerHTML = "";
    let total = 0;

    products.forEach((item, index) => {
        const row = document.createElement("tr");
        const itemCell = document.createElement("td");
        const priceCell = document.createElement("td");
        const quantityCell = document.createElement("td");
        const totalCell = document.createElement("td");
        const removeCell = document.createElement("td");
        const removeButton = document.createElement("button");

        itemCell.textContent = item.item;
        priceCell.textContent = item.price.toFixed(2);
        quantityCell.textContent = item.quantity;
        const itemTotal = (item.price * item.quantity).toFixed(2);
        totalCell.textContent = itemTotal;
        removeButton.textContent = "Удалить";
        removeButton.addEventListener("click", () => removeItem(index));

        row.appendChild(itemCell);
        row.appendChild(priceCell);
        row.appendChild(quantityCell);
        row.appendChild(totalCell);
        removeCell.appendChild(removeButton);
        row.appendChild(removeCell);

        orderItems.appendChild(row);

        total += parseFloat(itemTotal);
    });

    document.getElementById("total").textContent = total.toFixed(2);
}

function removeItem(index) {
    products.splice(index, 1);
    displayOrder();
}

function clearOrder() {
    products = [];
    displayOrder();
}

function submitOrder() {
    const paymentMethod = document.querySelector('input[name="payment"]:checked').value;

    // Создаем объект заказа
    const order = {
        paymentMethod: paymentMethod,
        items: products, // Массив с товарами в заказе
        total: parseFloat(document.getElementById("total").textContent)
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
