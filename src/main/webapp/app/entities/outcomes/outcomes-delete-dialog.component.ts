import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOutcomes } from 'app/shared/model/outcomes.model';
import { OutcomesService } from './outcomes.service';

@Component({
  templateUrl: './outcomes-delete-dialog.component.html',
})
export class OutcomesDeleteDialogComponent {
  outcomes?: IOutcomes;

  constructor(protected outcomesService: OutcomesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.outcomesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('outcomesListModification');
      this.activeModal.close();
    });
  }
}
