# java-explore-with-me

Сервис для организации событий и поиска компании. Позволяет делиться информацией о мероприятиях и анализировать их популярность.

## Оглавление
- [Технологии](#%EF%B8%8F-технологии)
- [Функции](#-функции)
- [REST API](#%EF%B8%8F-rest-api)
- [Модели данных](#-модели-данных)
- [Валидация](#-валидация)
- [Запуск](#-запуск)
- [Схема БД](#-схема-бд)
- [Особенности](#-особенности)

## ⚙️ Технологии
- Java 17
- Spring Boot 3
- PostgreSQL 15
- Docker
- Maven
- Hibernate
- Spring Data JPA
- Spring Actuator

## 🎯 Функции
### Основной сервис
**Публичный API:**
- Поиск событий с фильтрацией
- Просмотр категорий и подборок
- Статистика просмотров событий

**Закрытый API:**
- Создание/редактирование событий
- Подача заявок на участие
- Управление заявками (подтверждение/отклонение)

**Административный API:**
- Управление категориями и подборками
- Модерация событий
- Управление пользователями

### Сервис статистики
- Фиксация запросов к эндпоинтам
- Анализ популярности событий
- Формирование отчётов за период

### Дополнительно (feature)
- Комментарии к событиям с модерацией
- Подписки на пользователей
- Рейтинг мероприятий

## 🛠️ REST API
### Публичный API
| Метод | Путь                          | Действие                                  |
|-------|-------------------------------|-------------------------------------------|
| GET   | `/categories`                 | Получить все категории с пагинацией      |
| GET   | `/categories/{catId}`         | Получить категорию по ID                 |
| GET   | `/compilations`               | Получить подборки событий с фильтрами    |
| GET   | `/compilations/{compId}`      | Получить подборку по ID                  |
| GET   | `/events`                     | Поиск событий с фильтрами                |
| GET   | `/events/{id}`                | Получить полную информацию о событии     |
| GET   | `/comments/{id}`              | Получить комментарий по ID               |
| GET   | `/comments/events/{eventId}`  | Получить комментарии к событию с сортировкой |

### Закрытый API (пользователи)
| Метод | Путь                                      | Действие                                  |
|-------|-------------------------------------------|-------------------------------------------|
| POST  | `/users/{userId}/events`                  | Создать новое событие                     |
| GET   | `/users/{userId}/events`                  | Получить события пользователя с пагинацией|
| GET   | `/users/{userId}/events/{eventId}`        | Получить событие пользователя по ID       |
| PATCH | `/users/{userId}/events/{eventId}`        | Обновить событие пользователя             |
| GET   | `/users/{userId}/events/{eventId}/requests` | Получить запросы на участие в событии    |
| POST  | `/users/{userId}/requests`                | Создать запрос на участие в событии       |
| PATCH | `/users/{userId}/requests/{requestId}/cancel` | Отменить свой запрос на участие       |
| POST  | `/users/{userId}/comments/events/{eventId}` | Добавить комментарий к событию          |
| PATCH | `/users/{userId}/comments/{comId}`        | Обновить комментарий                     |
| DELETE| `/users/{userId}/comments/{comId}`        | Удалить комментарий                      |

### Административный API
| Метод | Путь                          | Действие                                  |
|-------|-------------------------------|-------------------------------------------|
| POST  | `/admin/categories`           | Создать новую категорию                   |
| DELETE| `/admin/categories/{catId}`   | Удалить категорию                         |
| PATCH | `/admin/categories/{catId}`   | Обновить категорию                        |
| POST  | `/admin/compilations`         | Создать новую подборку событий            |
| DELETE| `/admin/compilations/{compId}`| Удалить подборку                          |
| PATCH | `/admin/compilations/{compId}`| Обновить подборку                         |
| GET   | `/admin/events`               | Поиск событий с расширенными фильтрами    |
| PATCH | `/admin/events/{eventId}`     | Модерация события (публикация/отклонение) |
| GET   | `/admin/users`                | Получить пользователей с фильтрами        |
| POST  | `/admin/users`                | Создать нового пользователя               |
| DELETE| `/admin/users/{userId}`       | Удалить пользователя                      |
| PATCH | `/admin/comments/{comId}`     | Модерация комментария                     |
| DELETE| `/admin/comments/{comId}`     | Удалить комментарий администратором       |

### Особенности параметров:
- Пагинация: `from` (начиная с 0) и `size` (≥1) 
- Сортировка комментариев: `sort=true` (по возрастанию даты)
- Фильтры событий: 
  - `text` (поиск в аннотации и описании)
  - `categories` (список ID категорий)
  - `paid` (платные/бесплатные)
  - `rangeStart/rangeEnd` (формат "yyyy-MM-dd HH:mm:ss")
  - `onlyAvailable` (только с доступными местами)
    
## 🗃️ Модели данных
### Класс `Event`
```java
public class Event {
    private Long id;
    private String title;
    private String annotation;
    private Category category;
    private LocalDateTime created;
    private String description;
    private LocalDateTime eventDate;
    private User initiator;
    private Boolean paid;
    private Integer participantLimit;
    private LocalDateTime published;
    private Boolean requestModeration;
    private EventStatus state;
}
```

### Класс `Category`
```java
public class Category {
    private Long id;
    private String name;
}
```

### Класс `Comment`
```java
public class Comment {
    private Long id;
    private String text;
    private Long eventId;
    private User author;
    private LocalDateTime created;
}
```

### Класс `Compilation`
```java
public class Compilation {
    private Long id;
    private List<Event> events;
    private Boolean pinned;
    private String title;
}
```

### Класс `Location`
```java
public class Location {
    private Float lat;
    private Float lon;
}
```

### Класс `Request`
```java
public class Request {
    private Long id;
    private User requester;
    private LocalDateTime created;
    private Event event;
    private RequestStatus status;
}
```

### Класс ``
```java
public class User {
    private Long id;
    private String name;
    private String email;
}
```

## 🔍 Валидация
  - Дата события не может быть в прошлом
  - Категории должны быть уникальными
  - Статус события переводится по схеме: Pending → Published/Rejected
  - Заявки подтверждаются только для свободных мест
  - Комментарии редактируются в течение 24 часов после создания

## 🚀 Запуск
Клонировать репозиторий:
```bash
git clone https://github.com/your-username/explore-with-me.git
```

Собрать проект:
```bash
mvn clean package
```

Запустить в Docker:
```bash
docker-compose up
```
Сервисы:
  - Основной сервис: http://localhost:8080
  - Статистика: http://localhost:9090

## 📊 Схема БД
Основные таблицы:
  - events - мероприятия
  - categories - категории
  - users - пользователи
  - participation_requests - заявки
  - stats - статистика запросов

DDL-скрипты: src/main/resources/schema.sql

## 🌟 Особенности
  - Микросервисная архитектура
  - Сбор статистики в реальном времени
  - Трёхуровневая модерация контента
  - Расширяемость через feature-модули
  - Интеграция с Docker
  - Система ошибок с HTTP-кодами:
      - 400 Bad Request - неверные параметры
      - 403 Forbidden - недостаточно прав
      - 404 Not Found - объект не найден
      - 409 Conflict - конфликт состояний
