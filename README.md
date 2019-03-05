# QA-automation-test-task
1.	Установить JDK для Java 9, создать переменную JAVA_HOME, указывающую на путь к JDK и добавить в переменную Path путь к JDK - C:\Program Files\Java\jdk-9.0.4\bin 
2.	Скачать Maven, распаковать его и создать переменную M2_HOME и добавить в Path - %M2_HOME%\bin
3.	Установить Git
4.	Зайти в консоль, перейти на диск D: и выполнить команду git clone https://github.com/vsilver/QA-automation-test-task.git
5.	Установить IntelligeIDEA, указать в ней путь к JDK  Java 9
6.	В IDEA выбрать export project и указать путь к папке QA-automation-test-task
7.	Скачать Allure 2 версии 2.10.0, распаковать его и путь где он лежит прописать в переменной Path
8.	Подождать пока Maven скачает все зависимости
9.	Подготовить целое пятизначное значение для переменной nonce, вычислить timestamp
10.	Используя HMAC-SHA для вычисления signature, строку application_id=76077&auth_key=4M6nWT7TjY45vEc&nonce=12699&timestamp=1551679855&user[login]=MytestUser&user[password]=MyDochka1  с подставленными соответствующими значениями и Authorization secret - BpfxuSMG8EVRENU вычисляем signature
11.	Подставиви соответствующие значения в метод sessionCreation
12.	Запускаем его на выполнение и получаем token, который копируем в соответствующий параметр для остальных методов
13.	Запускаем все методы в соответствии с их приоритетом
14.	Заходим в консоль, переходим в папку проекта и выполняем команду allure serve allure-results
