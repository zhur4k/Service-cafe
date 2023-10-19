let order = [];

function toggleCategory(category) {
    const products = document.getElementById(category);
    products.style.display = products.style.display === 'block' ? 'none' : 'block';
}

function addProduct(productName, price) {
    const quantity = 1;
    order.push({ item: productName, price, quantity });
    displayOrder();
}

function displayOrder() {
    const orderItems = document.getElementById("order-items");
    orderItems.innerHTML = "";
    let total = 0;

    order.forEach((item, index) => {
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
    order.splice(index, 1);
    displayOrder();
}

function clearOrder() {
    order = [];
    displayOrder();
}

function submitOrder() {
    const paymentMethod = document.querySelector('input[name="payment"]:checked').value;

    // Создаем объект заказа
    const orderData = {
        paymentMethod: paymentMethod,
        items: order, // Массив с товарами в заказе
        total: parseFloat(document.getElementById("total").textContent)
    };

    // Отправляем заказ на сервер
    const xhr = new XMLHttpRequest();
    xhr.open('POST', '/submitOrder', true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            alert('Заказ успешно отправлен!');
            // Очищаем корзину после успешной отправки
            clearOrder();
        }
    };

    xhr.send(JSON.stringify(orderData));
}