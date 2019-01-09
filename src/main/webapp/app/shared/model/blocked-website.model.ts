export interface IBlockedWebsite {
    id?: string;
    siteName?: string;
}

export class BlockedWebsite implements IBlockedWebsite {
    constructor(public id?: string, public siteName?: string) {}
}
