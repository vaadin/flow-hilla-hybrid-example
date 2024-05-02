import { VerticalLayout } from "@vaadin/react-components";
import type { ViewConfig } from "@vaadin/hilla-file-router/types.js";

export const config: ViewConfig = {
    menu: {
        icon: 'vaadin:group'
    }
};

/**
 * Hilla view that is available publicly.
 */
export default function Public() {
    return (
        <VerticalLayout theme="padding">
            <p>This is a Hilla view available publicly.</p>
        </VerticalLayout>
    );
}
