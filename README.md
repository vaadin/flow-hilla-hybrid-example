# Vaadin Hybrid Example

This project demonstrates how to build [Vaadin](https://vaadin.com) hybrid applications, i.e. server-side (Vaadin Flow) and frontend-side (Vaadin Hilla) views together in a single application.
The project uses [Spring-Boot Security](https://vaadin.com/docs/latest/security/enabling-security) to protect Flow and Hilla views.

## Running the Application
There are two ways to run the application :  using `mvn spring-boot:run` or by running the `Application` class directly from your IDE.

You can use any IDE of your preference,but we suggest Eclipse or Intellij IDEA.
Below are the configuration details to start the project using a `spring-boot:run` command. Both Eclipse and Intellij IDEA are covered.

#### Eclipse
- Right click on a project folder and select `Run As` --> `Maven build..` . After that a configuration window is opened.
- In the window set the value of the **Goals** field to `spring-boot:run`
- You can optionally select `Skip tests` checkbox
- All the other settings can be left to default

Once configurations are set clicking `Run` will start the application

#### Intellij IDEA
- On the right side of the window, select Maven --> Plugins--> `spring-boot` --> `spring-boot:run` goal
- Optionally, you can disable tests by clicking on a `Skip Tests mode` blue button.

Clicking on the green run button will start the application.

After the application has started, you can view your it at http://localhost:8080/ in your browser.


If you want to run the application locally in the production mode, use `spring-boot:run -Pproduction` command instead.

### Running Integration Tests

Integration tests are implemented using [Playwright](https://playwright.dev). The tests take a few minutes to run and are therefore included in a separate Maven profile. We recommend running tests with a production build to minimize the chance of development time toolchains affecting test stability.

Since Playwright supports either Java or TypeScript, tests for Flow views are implemented in Java whereas Hilla views are tested in TS

To run the tests using Google Chrome for both Flow and Hilla views run:

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

Or for interacting with the playwright UI Test:

```
npm test -- --ui
```

## Structure

Vaadin web applications are full-stack and include both client-side and server-side code in the same project.

| Directory                                                     | Description                        |
|:--------------------------------------------------------------|:-----------------------------------|
| `src/main/frontend/`                                          | Client-side source directory       |
| &nbsp;&nbsp;&nbsp;&nbsp;`components/`                         | React components                   |
| &nbsp;&nbsp;&nbsp;&nbsp;`themes/`                             | Application theme sources          |
| &nbsp;&nbsp;&nbsp;&nbsp;`util/`                               | Util scripts                       |
| &nbsp;&nbsp;&nbsp;&nbsp;`views/`                              | UI views in React/TypeScript       |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`@layout.tsx` | Layout/Menu for other views        |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`@index.tsx`  | Public Hilla view                  |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`about.tsx`   | Hilla view for authenticated users |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`hilla.tsx`   | Hilla view for 'USER'              |
| &nbsp;&nbsp;&nbsp;&nbsp;`index.html`                          | HTML template                      |
| &nbsp;&nbsp;&nbsp;&nbsp;`auth.tsx`                            | Authentication helpers             |
| &nbsp;&nbsp;&nbsp;&nbsp;`index.tsx`                           | Application TS entry point         |
| &nbsp;&nbsp;&nbsp;&nbsp;`routes.tsx`                          | React Router configuration         |
| `src/main/java/<groupId>/`                                    | Server-side source directory       |
| &nbsp;&nbsp;&nbsp;&nbsp;`Application.java`                    | Server entrypoint                  |
| &nbsp;&nbsp;&nbsp;&nbsp;`FlowView.java`                       | Server-side view for 'ADMIN'       |
| &nbsp;&nbsp;&nbsp;&nbsp;`GreetService.java`                   | Back-end service example           |

