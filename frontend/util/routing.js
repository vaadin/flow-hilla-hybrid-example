import { useMatches } from 'react-router-dom';
/**
 * Returns the `handle` object containing the metadata for the current route,
 * or undefined if the route does not have defined a handle.
 */
export function useRouteMetadata() {
    const matches = useMatches();
    const match = matches[matches.length - 1];
    return match?.handle;
}
//# sourceMappingURL=routing.js.map