# Flow-Hilla Hybrid Example

This project demonstrates how to use [Vaadin Flow](https://vaadin.com/flow) and [Hilla](https://hilla.dev) together in a single app. 

[Hilla](https://hilla.dev/docs/react) is a framework by Vaadin for building reactive web applications on Java backends. It seamlessly integrates a [React](https://reactjs.org/) TypeScript frontend with a [Spring Boot](https://spring.io/projects/spring-boot) backend.

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

## Structure

Vaadin web applications are full-stack and include both client-side and server-side code in the same project.

| Directory                                                     | Description                  |
|:--------------------------------------------------------------|:-----------------------------|
| `src/main/frontend/`                                          | Client-side source directory |
| &nbsp;&nbsp;&nbsp;&nbsp;`components/`                         | React components             |
| &nbsp;&nbsp;&nbsp;&nbsp;`themes/`                             | Application theme sources    |
| &nbsp;&nbsp;&nbsp;&nbsp;`util/`                               | Util scripts                 |
| &nbsp;&nbsp;&nbsp;&nbsp;`views/`                              | UI views in React/TypeScript |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`$layout.tsx` | Layout/Menu for other views  |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`$index.tsx`  | Root mapping About view      |
| &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`hilla.tsx`   | Hilla view example           |
| &nbsp;&nbsp;&nbsp;&nbsp;`index.html`                          | HTML template                |
| `src/main/java/<groupId>/`                                    | Server-side source directory |
| &nbsp;&nbsp;&nbsp;&nbsp;`Application.java`                    | Server entrypoint            |
| &nbsp;&nbsp;&nbsp;&nbsp;`FlowView.java`                       | Server-side view             |
| &nbsp;&nbsp;&nbsp;&nbsp;`GreetService.java`                   | Back-end service example     |

## Routing
This project uses FileSystem-based routing for React views: Vaadin looks up for views in `src/main/frontend/views/` folder and sets up the React Router accordingly. 
It's also possible to use the explicit route configuration:

1. Move `routes.tsx` file from `src/main/frontend/generated/` to `src/main/frontend/`.
2. Rename `$layout.tsx` to `MainLayout.tsx`, `$index.tsx` to `AboutView.tsx` and `hilla.tsx` to `HillaView.tsx`.
3. Replace its content with the following:
```javascript
import { createBrowserRouter, RouteObject } from 'react-router-dom';
import { buildRoute } from "Frontend/generated/flow/Flow";
import MainLayout from "Frontend/views/MainLayout";
import HillaView from "Frontend/views/hilla/HillaView";
import AboutView from "Frontend/views/about/AboutView";

// To define routes manually, use the following code as an example and remove the above code:
let routing = [
  {
    element: <MainLayout />,
    handle: { title: 'Main' },
    children: [
       { path: '/', element: <AboutView />, handle: { title: 'Welcome' } },
       { path: '/hilla', element: <HillaView />, handle: { title: 'Hilla' } }
    ],
  },
] as RouteObject[];
export const routes = buildRoute(routing, routing[0].children);

export default createBrowserRouter(routes);
```