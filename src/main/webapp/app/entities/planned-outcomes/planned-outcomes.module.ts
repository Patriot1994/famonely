import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FamonelySharedModule } from 'app/shared/shared.module';
import { PlannedOutcomesComponent } from './planned-outcomes.component';
import { PlannedOutcomesDetailComponent } from './planned-outcomes-detail.component';
import { PlannedOutcomesUpdateComponent } from './planned-outcomes-update.component';
import { PlannedOutcomesDeleteDialogComponent } from './planned-outcomes-delete-dialog.component';
import { plannedOutcomesRoute } from './planned-outcomes.route';

@NgModule({
  imports: [FamonelySharedModule, RouterModule.forChild(plannedOutcomesRoute)],
  declarations: [
    PlannedOutcomesComponent,
    PlannedOutcomesDetailComponent,
    PlannedOutcomesUpdateComponent,
    PlannedOutcomesDeleteDialogComponent,
  ],
  entryComponents: [PlannedOutcomesDeleteDialogComponent],
})
export class FamonelyPlannedOutcomesModule {}
