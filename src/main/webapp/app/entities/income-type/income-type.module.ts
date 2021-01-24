import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FamonelySharedModule } from 'app/shared/shared.module';
import { IncomeTypeComponent } from './income-type.component';
import { IncomeTypeDetailComponent } from './income-type-detail.component';
import { IncomeTypeUpdateComponent } from './income-type-update.component';
import { IncomeTypeDeleteDialogComponent } from './income-type-delete-dialog.component';
import { incomeTypeRoute } from './income-type.route';

@NgModule({
  imports: [FamonelySharedModule, RouterModule.forChild(incomeTypeRoute)],
  declarations: [IncomeTypeComponent, IncomeTypeDetailComponent, IncomeTypeUpdateComponent, IncomeTypeDeleteDialogComponent],
  entryComponents: [IncomeTypeDeleteDialogComponent],
})
export class FamonelyIncomeTypeModule {}
