import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FamonelySharedModule } from 'app/shared/shared.module';
import { OutcomeTypeComponent } from './outcome-type.component';
import { OutcomeTypeDetailComponent } from './outcome-type-detail.component';
import { OutcomeTypeUpdateComponent } from './outcome-type-update.component';
import { OutcomeTypeDeleteDialogComponent } from './outcome-type-delete-dialog.component';
import { outcomeTypeRoute } from './outcome-type.route';

@NgModule({
  imports: [FamonelySharedModule, RouterModule.forChild(outcomeTypeRoute)],
  declarations: [OutcomeTypeComponent, OutcomeTypeDetailComponent, OutcomeTypeUpdateComponent, OutcomeTypeDeleteDialogComponent],
  entryComponents: [OutcomeTypeDeleteDialogComponent],
})
export class FamonelyOutcomeTypeModule {}
