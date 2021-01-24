import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FamonelySharedModule } from 'app/shared/shared.module';

import { RFA_ROUTE } from './right-famonely-app.route';
import {RightFamonelyAppComponent } from './right-famonely-app.component';

@NgModule({
  imports: [FamonelySharedModule, RouterModule.forChild([RFA_ROUTE])],
  declarations: [RightFamonelyAppComponent],
})

export class RightFamonelyAppModule { }
