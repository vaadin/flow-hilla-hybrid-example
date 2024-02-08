import { VerticalLayout } from "@vaadin/react-components/VerticalLayout.js";


export default function AboutView() {

    return (
        <VerticalLayout theme="padding spacing">
            <h3>About View</h3>
            <p>This is a simplified example on how to mix:
                <ul>
                    <li>Hilla with React</li>
                    <li>Flow in pure Java</li>
                </ul>
            </p>
        </VerticalLayout>
    );
}
