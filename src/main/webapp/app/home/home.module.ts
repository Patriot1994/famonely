import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FamonelySharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [FamonelySharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent],
})
export class FamonelyHomeModule {}
