import {CONFIG} from "../../auth_config";

export const environment = {
    production: false,
    auth: {
        domain: CONFIG.domain,
        clientId: CONFIG.clientId,
        redirectUri: window.location.origin,
    }
};
