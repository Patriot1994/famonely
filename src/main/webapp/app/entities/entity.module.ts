import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'users-famonely',
        loadChildren: () => import('./users-famonely/users-famonely.module').then(m => m.FamonelyUsersFamonelyModule),
      },
      {
        path: 'usually-outcomes',
        loadChildren: () => import('./usually-outcomes/usually-outcomes.module').then(m => m.FamonelyUsuallyOutcomesModule),
      },
      {
        path: 'planned-outcomes',
        loadChildren: () => import('./planned-outcomes/planned-outcomes.module').then(m => m.FamonelyPlannedOutcomesModule),
      },
      {
        path: 'outcome-type',
        loadChildren: () => import('./outcome-type/outcome-type.module').then(m => m.FamonelyOutcomeTypeModule),
      },
      {
        path: 'outcomes',
        loadChildren: () => import('./outcomes/outcomes.module').then(m => m.FamonelyOutcomesModule),
      },
      {
        path: 'income-type',
        loadChildren: () => import('./income-type/income-type.module').then(m => m.FamonelyIncomeTypeModule),
      },
      {
        path: 'incomes',
        loadChildren: () => import('./incomes/incomes.module').then(m => m.FamonelyIncomesModule),
      },
      {
        path: 'state-of-money',
        loadChildren: () => import('./state-of-money/state-of-money.module').then(m => m.FamonelyStateOfMoneyModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class FamonelyEntityModule {}
