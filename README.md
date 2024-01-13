### Task:

Restaurant Voting System
The voting system for deciding where to have lunch.

Technical requirement
Design and implement a REST API using Hibernate/Spring/SpringMVC (Spring-Boot preferred!) without frontend.

The task is: Build a voting system for deciding where to have lunch.

2 types of users: admin and regular users
Admin can input a restaurant, and it's lunch menu of the day (2-5 items usually, just a dish name and price)
Menu changes each day (admins do the updates)
Users can vote on which restaurant they want to have lunch at
Only one vote counted per user
If user votes again the same day:
If it is before 11:00 we assume that he changed his mind.
If it is after 11:00 then it is too late, vote can't be changed
Each restaurant provides a new menu each day.
-----------------------------------------------------
### Stack: 
- JDK 21.0.1, 
- Spring Boot 3.x, 
- Lombok, 
- H2, 
- Caffeine Cache, 
- SpringDoc OpenApi 2.x
-----------------------------------------------------
### Credentials for testing
```
User:  user@yandex.ru / password
User:  user2@yandex.ru / password
Admin: admin@gmail.com / admin
```
### Swagger link

http://localhost:8080/swagger-ui/index.html

#### curl operations:

### Voting

Get all votes of authorised user:
curl -X 'GET' http://localhost:8080/api/profile/votes/ user@yandex.ru:password

Get all votes of authorised users votes for the restaurant {1}:
curl -X 'GET' http://localhost:8080/api/profile/votes/restaurant?restaurantId=1 user@yandex.ru:password

Change vote of authorised user {user2}:
curl -X 'PUT' http://localhost:8080/api/profile/votes/?restaurantId=1 user2@yandex.ru:password

Get all authorised users voted for restaurant {1}:
curl -X 'GET' http://localhost:8080/api/profile/votes/?restaurantId=1 user@yandex.ru:password

Get all votes of authorised users by date:
curl -X 'GET' 

### Restaurants administration:

Get all restaurants:
curl -X 'GET' http://localhost:8080/api/admin/restaurants/ admin@gmail.com:admin

Get all restaurant by ID {1}:
curl -X 'GET' http://localhost:8080/api/admin/restaurants/1 admin@gmail.com:admin

Create restaurant {Volga}:
curl -X 'POST' http://localhost:8080/api/admin/restaurants?name=Volga admin@gmail.com:admin

Update restaurant {2}:
curl -X 'PUT' http://localhost:8080/api/admin/restaurants/2 admin@gmail.com:admin

Delete restaurant {2}:
curl -X 'DELETE' http://localhost:8080/api/admin/restaurants/2 admin@gmail.com:admin

Get restaurant by ID {3} with menu:
curl -X 'GET' http://localhost:8080/api/restaurants/3/with-menu admin@gmail.com:admin

### Dish administration:

Get dish {1} of the restaurant by ID {1}:
curl -X 'GET' http://localhost:8080/api/admin/restaurants/1/dishes/1 admin@gmail.com:admin

Get all dishes of the restaurant ID {1}:
curl -X 'GET' http://localhost:8080/api/admin/restaurants/1/dishes/ admin@gmail.com:admin

Create dish for the restaurant {2}:
curl -X 'POST' http://localhost:8080/api/admin/restaurants/2/dishes admin@gmail.com:admin

Update dish {3} for the restaurant {3}:
curl -X 'PUT' http://localhost:8080/api/admin/restaurants/3/dishes/3 admin@gmail.com:admin

Delete dish {1} for the restaurant {2}:
curl -X 'DELETE' http://localhost:8080/api/admin/restaurants/2/dishes/1 admin@gmail.com:admin

### Administration of users

Get all users:
curl -X 'GET' http://localhost:8080/api/admin/users admin@gmail.com:admin

Get user by e-mail:
curl -X 'GET' http://localhost:8080/api/admin/users/by-email?email=user%40yandex.ru admin@gmail.com:admin

Get user {1}:
curl -X 'GET' http://localhost:8080/api/admin/users/1 admin@gmail.com:admin

### Profile operations:

Get user's profile:
curl -X 'GET' http://localhost:8080/api/profile admin@gmail.com:admin