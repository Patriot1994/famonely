import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIncomes } from 'app/shared/model/incomes.model';
import { IncomesService } from './incomes.service';

@Component({
  templateUrl: './incomes-delete-dialog.component.html',
})
export class IncomesDeleteDialogComponent {
  incomes?: IIncomes;

  constructor(protected incomesService: IncomesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.incomesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('incomesListModification');
      this.activeModal.close();
    });
  }
}
