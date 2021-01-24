import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FamonelySharedModule } from 'app/shared/shared.module';
import { StateOfMoneyComponent } from './state-of-money.component';
import { StateOfMoneyDetailComponent } from './state-of-money-detail.component';
import { StateOfMoneyUpdateComponent } from './state-of-money-update.component';
import { StateOfMoneyDeleteDialogComponent } from './state-of-money-delete-dialog.component';
import { stateOfMoneyRoute } from './state-of-money.route';

@NgModule({
  imports: [FamonelySharedModule, RouterModule.forChild(stateOfMoneyRoute)],
  declarations: [StateOfMoneyComponent, StateOfMoneyDetailComponent, StateOfMoneyUpdateComponent, StateOfMoneyDeleteDialogComponent],
  entryComponents: [StateOfMoneyDeleteDialogComponent],
})
export class FamonelyStateOfMoneyModule {}
