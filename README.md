# SampleSpringBootProject
Explains SpringBoot backend + simple HTML frontend + Spring Data JPA + JWT

__project is generated from https://start.spring.io/ with required dependencies__
- check build.gradle(dependencies section)

__steps to setup db__
- install xampp and run mysql(make sure mysql is running on port 3306)
- create a db named samplespringbootdb

__steps to run the project__
1. open the project in intellij
2. run ./gradlew clean
3. run ./gradlew --refresh-dependencies
4. run ./gradlew build 
5. run the src/main/java/com/example/sample/sample1/Sample1Application.java by right-clicking it

__testing apis on postman__

- curl -X POST http://localhost:8080/api/transactions \
  -H "Content-Type: application/json" \
  -d '{"description": "Initial Deposit", "amount": 5000.00, "transactionType": "CREDIT"}'

_check db after post_

- curl http://localhost:8080/api/transactions

__check how frontend is working__
- open index.html in browser


__Spring Data JPA__

notice how db queries happening using java, we didn't write a single sql query that's the magic of ORM

**JWT Implementation**
- Implemented JWT in the project for all APIs except User registration and Login API. So user need to login, get the JWT token and pass this token in Authorization header for every API call.
**How To Test JWT Flow**
**Register**:
curl -X POST http://localhost:8080/api/auth/register \
-H "Content-Type: application/json" \
-d '{"username":"testuser","email":"t@t.com","password":"123456"}'
**Login**:
curl -X POST http://localhost:8080/api/auth/login \
-H "Content-Type: application/json" \
-d '{"username":"testuser","password":"123456"}'
Copy the token from the response.
**Access Protected**:
curl http://localhost:8080/api/secret \
-H "Authorization: Bearer YOUR_TOKEN_HERE"
