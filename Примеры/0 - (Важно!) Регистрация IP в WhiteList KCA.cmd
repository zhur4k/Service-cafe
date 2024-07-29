cd ..
rem ECHO При необходимости очищаем таблицу
TitanXCheck.exe "примеры\Изменение таблицы whiteIP.txt" "service" "751426"                 
ECHO регистрируемся
TitanXCheck.exe "примеры\регистрация оператора в WhiteList КСА.txt" "1" "1"
rem ECHO запрос таблицы
rem TitanXCheck.exe "примеры\whiteIP.txt"
