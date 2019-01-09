import { NgModule } from '@angular/core';

import { InternetCensorshipSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [InternetCensorshipSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [InternetCensorshipSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class InternetCensorshipSharedCommonModule {}
