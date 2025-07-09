# AuthOn

<img src="https://github.com/tracystacktrace/authon/raw/main/src/main/resources/assets/authon/icon.png" alt="Mod Icon" width="256" height="256" />

[![Running on - FoxLoader](https://img.shields.io/badge/Running_on-FoxLoader-orange)](https://github.com/Fox2Code/FoxLoader) [![GitHub release](https://img.shields.io/github/release/tracystacktrace/authon?include_prereleases=&sort=semver&color=purple)](https://github.com/tracystacktrace/authon/releases/)

AuthOn - модификация для серверов на FoxLoader (ReIndev), созданный для внедрения альтернативной системы авторизации.

Примером альтернативной системы авторизации может послужить `AuthMe` или `OpeNLogin`.
AuthOn эксклюзивно разрабатывается для серверов FoxLoader (ReIndev).

В качестве основной базы данных используется H2, которое впрочем сделает всю грязную работу по хранению данных.

**ВНИМАНИЕ! Это СЕРВЕРНЫЙ мод, он НЕ БУДЕТ работать в клиенте!**

## Установка

Просто скачайте готовый релиз и переместите файл `.jar` в папку `mods`.

[Последний релиз можно найти здесь](https://github.com/tracystacktrace/authon/releases)

## Команды

```
# Команды игроков:

# Позволяет игроку зарегестрироваться, только единожды
/register <password>

# Позволяет игроку войти, только раз в сессию
/login <password>

# Позволяет игроку поменять пароль
/changepwd <old password> <new password>
```

```
# Команды операторов:

# Позволяет оператору зарегестрировать нового игрока
/authon register <username> <password>

# Позволяет оператору удалить игрока из регистр. базы
/authon unregister <username>

# Позволяет оператору изменять пароль игрока
/authon changepwd <username> <new password>

# Отправляет сообщение с текущей версией мода
/authon version
```

## Конфигурация

Конфиг. файл мода (`authon.cfg`) может быть использован для изменения надписей и сообщении, а также:

```json5
{
  // Время в секундах разрешенное на регистрацию/авторизацию игрокам
  // После истечения кикает игрока
  "waitingTime": 30,

  // Кикать игрока с первой же неудачной попытки
  "instantKick": true,

  // Включает систему сессии с IP адрессами
  "allowsSessions": false,

  // Прятать содержимое инвентаря пока авторизация не пройдет успешно
  "hideInventoryContent": true,
  
  // Прятать координаты игрока (временно перемещая в лимбо) пока авторизация не пройдет успешно
  "hideCoordinates": true
}
```

## License

Данный мод имеет лицензию [LGPL-3.0-or-later](https://github.com/tracystacktrace/authon/blob/main/LICENSE).

Встроенная библиотека H2 имеет лицензию [Mozilla Public License, version 2.0](https://github.com/h2database/h2database/blob/master/LICENSE.txt).