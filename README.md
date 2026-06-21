# Account Handling

A self-contained REST API for simple bank account handling. 
Application Supports:
- Account Creation
- Money depositing
- Money debiting
- Balance checking
- Account Transaction checking
- Exchange money between accounts

</br>I have added basic authentication, so that only logged in users can access the API.
</br>I have Added checks so Users can only access their own accounts.
</br>Project is developed only for demo purposes.

## Notes
To reset the local H2 database, delete the `data` folder in the project root directory.
</br>Seeded users will be created on application startup
1. user1:password123
2. user2:password123
</br> Accounts are not seeded, so you need to create them manually. For simplicity, they only need name and currency.

## Tech Stack
- Java 25
- Spring Boot 4
- H2 Database