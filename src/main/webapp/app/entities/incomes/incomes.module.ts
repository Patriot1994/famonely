import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FamonelySharedModule } from 'app/shared/shared.module';
import { IncomesComponent } from './incomes.component';
import { IncomesDetailComponent } from './incomes-detail.component';
import { IncomesUpdateComponent } from './incomes-update.component';
import { IncomesDeleteDialogComponent } from './incomes-delete-dialog.component';
import { incomesRoute } from './incomes.route';

@NgModule({
  imports: [FamonelySharedModule, RouterModule.forChild(incomesRoute)],
  declarations: [IncomesComponent, IncomesDetailComponent, IncomesUpdateComponent, IncomesDeleteDialogComponent],
  entryComponents: [IncomesDeleteDialogComponent],
})
export class FamonelyIncomesModule {}
