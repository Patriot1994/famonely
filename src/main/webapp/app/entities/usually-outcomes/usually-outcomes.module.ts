import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FamonelySharedModule } from 'app/shared/shared.module';
import { UsuallyOutcomesComponent } from './usually-outcomes.component';
import { UsuallyOutcomesDetailComponent } from './usually-outcomes-detail.component';
import { UsuallyOutcomesUpdateComponent } from './usually-outcomes-update.component';
import { UsuallyOutcomesDeleteDialogComponent } from './usually-outcomes-delete-dialog.component';
import { usuallyOutcomesRoute } from './usually-outcomes.route';

@NgModule({
  imports: [FamonelySharedModule, RouterModule.forChild(usuallyOutcomesRoute)],
  declarations: [
    UsuallyOutcomesComponent,
    UsuallyOutcomesDetailComponent,
    UsuallyOutcomesUpdateComponent,
    UsuallyOutcomesDeleteDialogComponent,
  ],
  entryComponents: [UsuallyOutcomesDeleteDialogComponent],
})
export class FamonelyUsuallyOutcomesModule {}
