package com.yulkost.service.dto.mapper;
import java.util.HashMap;
import java.util.Map;

public class ErrorCodeMapper {
    private static final Map<String, String> errorMap = new HashMap<>();

    static {
        errorMap.put("x01", "Цена не указана");
        errorMap.put("x02", "Количество не указано");
        errorMap.put("x03", "Отдел не указан");
        errorMap.put("x04", "Группа не указана");
        errorMap.put("x25", "Нет бумаги");
        errorMap.put("x31", "Пользователь уже зарегистрирован");
        errorMap.put("x32", "Неверный пароль");
        errorMap.put("x33", "Неверный номер таблицы");
        errorMap.put("x34", "Доступ к таблице запрещен");
        errorMap.put("x35", "Умолчание не найдено");
        errorMap.put("x36", "Неверный индекс");
        errorMap.put("x37", "Неверное поле");
        errorMap.put("x38", "Таблица переполнена");
        errorMap.put("x39", "Неверная длина двоичных данных");
        errorMap.put("x3A", "Попытка модификации поля только для чтения");
        errorMap.put("x3B", "Неверное значение поля");
        errorMap.put("x3C", "Товар уже существует");
        errorMap.put("x3D", "По товару были продажи");
        errorMap.put("x3E", "Запрос запрещен");
        errorMap.put("x3F", "Неверная закладка");
        errorMap.put("x40", "Ключ не найден");
        errorMap.put("x41", "Процедура уже исполняется");
        errorMap.put("x42", "Количество товара отрицательно");
        errorMap.put("x8A", "Нет бумаги для контрольной ленты");
        errorMap.put("x8B", "Нет бумаги");
        errorMap.put("x8D", "Выдача сдачи запрещена");
        errorMap.put("xA6", "Есть 3 или более непереданных отчета");
        errorMap.put("xBB", "Лента не пуста");
        errorMap.put("xB6", "Ресторанный режим не активен");
        errorMap.put("xB7", "Ресторанный счет не открыт");
        errorMap.put("xB8", "Переполнение количества заказов");
        errorMap.put("xB9", "Ресторанный чек открыт");
        errorMap.put("xBA", "Неверный номер счета");
        errorMap.put("xBC", "Режим тренировки");
        errorMap.put("xBD", "Текущая дата неверна");
        errorMap.put("xBE", "Запрещено изменение времени");
        errorMap.put("xBF", "Истек сервисный таймер");
        errorMap.put("xC0", "Ошибка работы с терминалом НСМЕП");
        errorMap.put("xC1", "Неверный номер налога");
        errorMap.put("xC2", "Неверный параметр у процедуры");
        errorMap.put("xC3", "Режим фискального принтера не активен");
        errorMap.put("xC4", "Изменялось название товара или его налог");
        errorMap.put("xC5", "СКНО занято");
        errorMap.put("xC6", "Ошибка обмена с СКНО (нет связи)");
        errorMap.put("xC7", "Смена не открыта");
        errorMap.put("xC8", "СКНО переполнено");
        errorMap.put("xC9", "Неверный статус СКНО");
        errorMap.put("xCA", "Ошибка идентификации СКНО");
        errorMap.put("xCB", "Запрещена операция продажи");
        errorMap.put("xCC", "Начата операция возврата");
        errorMap.put("xCE", "Неверный тип кода товара");
        errorMap.put("xCF", "Не выведен отчет Z1");
        errorMap.put("xD0", "Не сделана инкассация денег");
        errorMap.put("xD1", "Сейф не закрыт");
        errorMap.put("xD2", "Печать ленты прервана");
        errorMap.put("xD3", "Достигнут конец текущей смены, или изменилась дата");
        errorMap.put("xD4", "Не указано значение процентной скидки по умолчанию");
        errorMap.put("xD5", "Не указано значение скидки по умолчанию");
        errorMap.put("xD6", "Дневной отчет не выведен");
        errorMap.put("xD7", "Дневной отчет уже выведен (и пуст)");
        errorMap.put("xD8", "Нельзя отменить товар на который сделана скидка без ее предварительной отмены");
        errorMap.put("xD9", "Товар не продавался в этом чеке");
        errorMap.put("xDA", "Нечего отменять");
        errorMap.put("xDB", "Отрицательная сумма продажи товара");
        errorMap.put("xDC", "Неверный процент");
        errorMap.put("xDD", "Нет ни одной продажи");
        errorMap.put("xDE", "Скидки запрещены");
        errorMap.put("xDF", "Неверная сумма платежа");
        errorMap.put("xE0", "Тип оплаты не предполагает введения кода клиента");
        errorMap.put("xE1", "Неверная сумма платежа");
        errorMap.put("xE2", "Идет оплата чека");
        errorMap.put("xE3", "Товар закончился");
        errorMap.put("xE4", "Номер группы не может меняться");
        errorMap.put("xE5", "Неверная группа");
        errorMap.put("xE6", "Номер отдела не может меняться");
        errorMap.put("xE7", "Неверный отдел");
        errorMap.put("xE8", "Нулевое произведение количества на цену");
        errorMap.put("xE9", "Переполнение внутренних сумм");
        errorMap.put("xEA", "Дробное количество запрещено");
        errorMap.put("xEB", "Неверное количество");
        errorMap.put("xEC", "Цена не может быть изменена");
        errorMap.put("xED", "Неверная цена");
        errorMap.put("xEE", "Товар не существует");
        errorMap.put("xEF", "Начат чек внесения-изъятия денег");
        errorMap.put("xF0", "Чек содержит продажи");
        errorMap.put("xF1", "Не существующий или запрещенный тип оплаты");
        errorMap.put("xF2", "Поле в строке переполнено");
        errorMap.put("xF3", "Отрицательная сумма по дневному отчету");
        errorMap.put("xF4", "Отрицательная сумма по чеку");
        errorMap.put("xF5", "Чек переполнен");
        errorMap.put("xF6", "Дневной отчет переполнен");
        errorMap.put("xF7", "Чек для копии не найден");
        errorMap.put("xF8", "Оплата чека не завершена");
        errorMap.put("xF9", "Кассир не зарегистрирован");
        errorMap.put("xFA", "У кассира нет прав на эту операцию");
        errorMap.put("xFB", "Нефискальный чек не открыт");
        errorMap.put("xFC", "Чек не открыт");
        errorMap.put("xFD", "Нефискальный чек уже открыт");
        errorMap.put("xFE", "Чек уже открыт");
        errorMap.put("xFF", "Переполнение ленты");
    }


    public static String getErrorDescription(String errorCode) {
        // Получаем описание по коду ошибки
        return errorMap.getOrDefault(errorCode, "Неизвестный код ошибки");
    }
}
