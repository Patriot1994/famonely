import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStateOfMoney } from 'app/shared/model/state-of-money.model';
import { StateOfMoneyService } from './state-of-money.service';

@Component({
  templateUrl: './state-of-money-delete-dialog.component.html',
})
export class StateOfMoneyDeleteDialogComponent {
  stateOfMoney?: IStateOfMoney;

  constructor(
    protected stateOfMoneyService: StateOfMoneyService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.stateOfMoneyService.delete(id).subscribe(() => {
      this.eventManager.broadcast('stateOfMoneyListModification');
      this.activeModal.close();
    });
  }
}
