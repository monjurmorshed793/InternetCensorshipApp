import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { InternetCensorshipBlockedWebsiteModule } from './blocked-website/blocked-website.module';
import { InternetCensorshipIspModule } from './isp/isp.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        InternetCensorshipBlockedWebsiteModule,
        InternetCensorshipIspModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InternetCensorshipEntityModule {}
