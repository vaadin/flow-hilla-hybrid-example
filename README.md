# Flow-Hilla Hybrid Example

This project demonstrates how to use [Vaadin Flow](https://vaadin.com/flow) and [Hilla](https://hilla.dev) together in a single app. 

[Hilla](https://hilla.dev/docs/react) is a framework by Vaadin for building reactive web applications on Java backends. It seamlessly integrates a [React](https://reactjs.org/) TypeScript frontend with a [Spring Boot](https://spring.io/projects/spring-boot) backend.


## Running the Application
There are two ways to run the application :  using `mvn` or by running the `Application` class directly from your IDE.


## Running Tests
You can run both: Flow and Hilla tests by executing:

```
mvn verify -Pit,production
```

For running tests in headed mode run:

```
mvn verify -Pit,production -Dheadless=false
```


If you prefer run only Hilla tests, just execute:

```
npm test
```