# 99Ranch

A Spring Boot application to manage users and their purchase data. It consists of two services:

- Customer Service
- Purchase Service

The Customer Service handles user-related functionalities such as authentication and user data management for the 99Ranch e-commerce platform.

The Purchase Service manages users' purchase information and their reward points for the 99Ranch e-commerce platform.

To Run the Customer Service:
- Update the database credentials in the `src/main/resources/application.properties` file.

To Run the Purchase Service:
- Update the database credentials and the admin token in the `src/main/resources/application.properties` file.

## How the Application Works:

### Customer Service:

- Users need to register themselves by using the '/customer/create' API.
    Sample Data request:
    `{
    "username": "username",
    "password": "password@123",
    "userAddress": "Gujarat, India"
    }`

- Users can log in using their credentials with the '/customer/login' API. After successful login, the user will receive an access token.
  Sample Data request:
  `{
  "username": "username",
  "password": "password@123"
  }`

- Using this token as a Bearer token, users can fetch their information and update basic information.

- For admin users, registration is required as a user, and the role needs to be changed in the database.

- Admin users can change user roles or create new admin users.

- Admin users can update other user's data. User URLs start with '/user', and admin URLs start with '/admin'.

### Purchase Service:

- The Purchase Service is used to add user purchases.

- Using the same access token, users can add purchases.
  To add a purchase:
    - Use the '/purchase/create' API:
      `{
      "purchaseAmount": 120
      }`
- After adding a purchase, the service will calculate points for that purchase and return purchase information.
  `{
  "purchaseId": "bc706bbe-e347-4922-b85d-229bdac61ba1",
  "purchaseAmount": 120,
  "purchaseDate": "2024-04-15T18:26:28.094+00:00",
  "userId": "afd0885d-7064-4c30-863f-a46f797b65e5",
  "purchasePoints": 90
  }`

- Users can check their total earned points in the user service by fetching their data.
