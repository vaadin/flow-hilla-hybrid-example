import { VerticalLayout } from "@vaadin/react-components/VerticalLayout.js";

/**
 * Hilla views that allows access only for authenticated users.
 */
export default function About() {
    return (
        <VerticalLayout theme="padding">
            <p>This is a Hilla view available for authenticated users.</p>
        </VerticalLayout>
    );
}
