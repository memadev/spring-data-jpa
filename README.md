## Showcases spring-data-jpa

### RESTful
  - Uses **Get**, **Post**, **Put** and **Delete** http methods to perform CRUD operations on Customer.
  - Makes use of **@ControllerAdvice** to convert application-level exceptions to http error codes.

### Customer
  - **Unidirectional One-To-Many** Relationship for Customer-Account.
  - Uses **CrudRepository** for CRUD operations.
  - Uses **Specification** to create dynamic queries at runtime.
  - **Idempotency** is ensured on add/delete operations.

### Order
  - **Bidirectional One-To-Many** Relationship for Order-OrderItem.
  - Uses **PagingAndSortingRepository** for pagination and sorting of results.
  - Used **Validation**.

### Java
  - Uses **record** in Dtos.

### Database
  - Uses **Liquibase** for data model migration.

### Integration Tests
  - Uses **JUnit** for end-to-end integration tests
  - Uses **@Testcontainers**, **@Container**, **@ServiceConnection**, **PostgreSQLContainer** to set up an instance of PostgreSQL. 
  - Uses **WebTestClient** to call RESTful endpoints and assert the response.


### Extensions points
  - **Unidirectional**/**Bidirectional** One-To-One Relationship.
  - Many-To-Many Relationship.
  