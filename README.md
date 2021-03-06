#### *The Pioneer* cinema
Система **Кинотеатр**. Вы пишете интернет витрину маленького **Кинотеатра** с одним залом. В нем есть **Расписание** показа фильмов на все 7 дней недели с 9:00 до 22:00 (начало последнего фильма). **Незарегистрированный пользователь** может видеть: расписание, свободные места в зале, возможность зарегистрироваться. **Зарегистрированный пользователь** должен быть в состоянии выкупить билет на выбранное место. **Администратор** может: внести в расписание новый фильм, отменить фильм, просматривать посещаемость зала.

#### Общие требования

**Необходимо построить веб-приложение, поддерживающую следующую функциональность:**
1. На основе сущностей предметной области создать **классы** их описывающие.
2. **Классы** и **методы** должны иметь отражающую их функциональность названия и должны быть грамотно структурированы по пакетам.
3. Информацию о предметной области хранить в **БД**, для доступа использовать **API JDBC** с использованием пула соединений, стандартного или разработанного самостоятельно. В качестве **СУБД** рекомендуется **MySQL**.
4. **Приложение** должно поддерживать работу с **кириллицей** (быть многоязычной), в том числе и при хранении информации в **БД**.
5. Код должен быть **документирован**.
6. Приложение должно быть покрыто **Юнит-тестами**.
7. При разработке бизнес логики использовать **сессии** и **фильтры**, и **события** в системе обрабатывать с помощью **Log4j**.
8. В приложении необходимо реализовать **Pagination**, **Transaction** в зависимости от Вашего проекта.
9. Используя **сервлеты** и **JSP**, реализовать функциональности, предложенные в постановке конкретной задачи.
10. В страницах **JSP** применять библиотеку **JSTL**.
11. Приложение должно корректно реагировать на ошибки и исключения разного рода (Пользователь никогда не должен видеть **stack-trace** на стороне **front-end**).
12. В приложении должна быть реализована система **Авторизации** и **Аутентификации**.


#### Инструкция по установке
1. Установить контейнер сервлетов **Tomcat** 
2. Установить СУБД **MySQL 5.7** 
    1. Создать базу и пользователя. [dbName: pioneer; user: user; password: password]. 
        *CREATE DATABASE pioneer CHARACTER SET utf8 COLLATE utf8_general_ci;*
    2. Предоставить пользователю необходимые права к базе. 
        *GRANT SELECT,INSERT,UPDATE,DELETE,CREATE,DROP,ALTER,REFERENCES ON pioneer TO 'user'@'localhost';*
    3. При необходимости параметры можно изменить в файле *src/main/resources/db/mysql.properties*
    4. Инициализировать базу данных, выполнив содержимое файла *src/main/resources/db/init_mysql.sql*  
    5. Наполнить базу тестовыми данными, выполнив содержимое файла *src/main/resources/db/populate_mysql.sql*
    6. Пункты 4 и 5 выполнятся автоматически при старте приложения если установить параметр *app.db_reset=true*
3. Установить сборщик проектов **Maven**


#### Инструкция по запуску проекта
1. Склонировать проект в локальную директорию, выполнив *git clone*
2. Собрать проект, выполнив *mvn package* в корневой директории проекта
3. Поместить *pioneer.war* в директорию *webapps* контейнера сервлетов *tomcat*. Распаковать вебархив.
4. Запустить контейнер сервлетов *tomcat*, выполнив скрипт *startup.sh* (*startup.bat*) в паке *bin*
5. Приложение будет доступно в браузере по ссылке *http://localhost:8080/pioneer/*


Авторизация в роли администратора: login=*ro@gmail.com* password=*admin*
у всех пользователей пароль - *password*