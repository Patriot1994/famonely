import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FamonelySharedModule } from 'app/shared/shared.module';
import { OutcomesComponent } from './outcomes.component';
import { OutcomesDetailComponent } from './outcomes-detail.component';
import { OutcomesUpdateComponent } from './outcomes-update.component';
import { OutcomesDeleteDialogComponent } from './outcomes-delete-dialog.component';
import { outcomesRoute } from './outcomes.route';

@NgModule({
  imports: [FamonelySharedModule, RouterModule.forChild(outcomesRoute)],
  declarations: [OutcomesComponent, OutcomesDetailComponent, OutcomesUpdateComponent, OutcomesDeleteDialogComponent],
  entryComponents: [OutcomesDeleteDialogComponent],
})
export class FamonelyOutcomesModule {}
