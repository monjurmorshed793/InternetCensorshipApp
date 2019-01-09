export const enum CensorshipType {
    DNS = 'DNS',
    TCP_IP = 'TCP_IP',
    HTTP = 'HTTP',
    URL = 'URL'
}

export interface ICensorshipStatus {
    id?: string;
    ispId?: string;
    ispName?: string;
    ooniStatus?: CensorshipType;
    systemStatus?: CensorshipType;
    description?: string;
}

export class CensorshipStatus implements ICensorshipStatus {
    constructor(
        public id?: string,
        public ispId?: string,
        public ispName?: string,
        public ooniStatus?: CensorshipType,
        public systemStatus?: CensorshipType,
        public description?: string
    ) {}
}
