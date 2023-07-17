# Дипломная работа «Облачное хранилище»

Приложение — REST-сервис. Сервис предоставляет REST-интерфейс
для загрузки файлов и вывода списка уже загруженных файлов пользователя.

- Порт: 8080

## DB (не в составе контейнера)
- MySQL
- Схема: cloudstorage
- Ports: 3306 и 33060
- User: root
- Password: password

DBMS: MySQL (ver. 8.0.33)  
Case sensitivity: plain=exact, delimited=exact 
Driver: MySQL Connector/J (ver. mysql-connector-java-8.0.25 (Revision: 08be9e9b4cba6aa115f9b27b215887af40b159e0), JDBC4.2)


#### Запуск приложения
- docker-compose up
