import { VerticalLayout } from "@vaadin/react-components/VerticalLayout.js";


export default function AboutView() {

    return (
        <VerticalLayout theme="padding">
            <p>This project is a simplified example on how to mix:</p>
            <ul>
                <li>Hilla with React</li>
                <li>Flow in pure Java</li>
            </ul>
        </VerticalLayout>
    );
}
