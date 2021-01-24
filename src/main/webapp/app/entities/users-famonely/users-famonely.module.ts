import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FamonelySharedModule } from 'app/shared/shared.module';
import { UsersFamonelyComponent } from './users-famonely.component';
import { UsersFamonelyDetailComponent } from './users-famonely-detail.component';
import { UsersFamonelyUpdateComponent } from './users-famonely-update.component';
import { UsersFamonelyDeleteDialogComponent } from './users-famonely-delete-dialog.component';
import { usersFamonelyRoute } from './users-famonely.route';

@NgModule({
  imports: [FamonelySharedModule, RouterModule.forChild(usersFamonelyRoute)],
  declarations: [UsersFamonelyComponent, UsersFamonelyDetailComponent, UsersFamonelyUpdateComponent, UsersFamonelyDeleteDialogComponent],
  entryComponents: [UsersFamonelyDeleteDialogComponent],
})
export class FamonelyUsersFamonelyModule {}
