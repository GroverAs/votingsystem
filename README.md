## Restaurant Voting System 
#### The voting system for deciding where to have lunch

---------------------

### Technical requirement:

Design and implement a REST API using Hibernate/Spring/SpringMVC (Spring-Boot preferred!) __without frontend__.

The task is: __Build a voting system for deciding where to have lunch.__

- 2 types of users: admin and regular users
- Admin can input a restaurant, and it's lunch menu of the day (2-5 items usually, just a dish name and price)
- Menu changes each day (admins do the updates)
- Users can vote on which restaurant they want to have lunch at
- Only one vote counted per user
- If user votes again the same day:
  - If it is before 11:00 we assume that he changed his mind.
  - If it is after 11:00 then it is too late, vote can't be changed
- Each restaurant provides a new menu each day.
-----------------------------------------------------
### Stack: 
- JDK 21.0.1, 
- Spring Boot 3.2, 
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
-----------------------------------------------------

### Swagger link

http://localhost:8080/swagger-ui/index.html

------------------------------------------------------

### curl operations:

------------------------------------------------------

### Voting

- Get all votes of authorised user:
```bash
curl -X GET http://localhost:8080/api/profile/votes/ -v --user user@yandex.ru:password
```

- Get all votes of authorised users votes for the restaurant {1}:
```bash
curl -X GET http://localhost:8080/api/profile/votes/by-restaurant?restaurantId=1 -v --user user@yandex.ru:password
```

- Change vote of authorised user {user2}:
```bash
curl -X PUT http://localhost:8080/api/profile/votes/?restaurantId=1 -v --user user2@yandex.ru:password
```

- Get all authorised users voted for restaurant {1}:
```bash
curl -X GET http://localhost:8080/api/profile/votes/?restaurantId=1 -v --user user@yandex.ru:password
```

- Get all votes of authorised users by date:
```bash
curl -X GET http://localhost:8080/api/profile/votes/by-date -v --user user@yandex.ru:password
```

### Restaurants administration:

- Get all restaurants:
```bash
curl -X GET http://localhost:8080/api/admin/restaurants/ -v --user admin@gmail.com:admin
```

- Get all restaurant by ID {1}:
```bash
curl -X GET http://localhost:8080/api/admin/restaurants/1 -v --user admin@gmail.com:admin
```

- Create restaurant {Volga}:
```bash
curl -X POST http://localhost:8080/api/admin/restaurants?name=Volga -v --user admin@gmail.com:admin
```

- Update restaurant {2}:
```bash
curl -X PUT http://localhost:8080/api/admin/restaurants/2 -v --user admin@gmail.com:admin
```

- Delete restaurant {2}:
```bash
curl -X DELETE http://localhost:8080/api/admin/restaurants/2 -v --user admin@gmail.com:admin
```

- Get restaurant by ID {3} with menu:
```bash
curl -X GET http://localhost:8080/api/restaurants/3/with-menu -v --user admin@gmail.com:admin
```

### Dish administration:

- Get dish {1} of the restaurant by ID {1}:
```bash
curl -X GET http://localhost:8080/api/admin/restaurants/1/dishes/1 -v --user admin@gmail.com:admin
```

- Get all dishes of the restaurant ID {1}:
```bash
curl -X GET http://localhost:8080/api/admin/restaurants/1/dishes/ -v --user admin@gmail.com:admin
```

- Create dish for the restaurant {2}:
```bash
curl -X POST http://localhost:8080/api/admin/restaurants/2/dishes -v --user admin@gmail.com:admin
```

- Update dish {3} for the restaurant {3}:
```bash
curl -X PUT http://localhost:8080/api/admin/restaurants/3/dishes/3 -v --user admin@gmail.com:admin
```

- Delete dish {1} for the restaurant {2}:
```bash
curl -X DELETE http://localhost:8080/api/admin/restaurants/2/dishes/1 -v --user admin@gmail.com:admin
```

### Administration of users

- Get all users:
```bash
curl -X GET http://localhost:8080/api/admin/users -v --user admin@gmail.com:admin
```

- Get user by e-mail:
```bash
curl -X GET http://localhost:8080/api/admin/users/by-email?email=user%40yandex.ru -v --user admin@gmail.com:admin
```

- Get user {1}:
```bash
curl -X GET http://localhost:8080/api/admin/users/1 -v --user admin@gmail.com:admin
```

### Profile operations:

- Get user's profile:
```bash
curl -X GET http://localhost:8080/api/profile -v --user admin@gmail.com:admin
```