export interface IIsp {
    id?: string;
    shortName?: string;
    longName?: string;
}

export class Isp implements IIsp {
    constructor(public id?: string, public shortName?: string, public longName?: string) {}
}
