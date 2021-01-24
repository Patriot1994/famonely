import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPlannedOutcomes } from 'app/shared/model/planned-outcomes.model';
import { PlannedOutcomesService } from './planned-outcomes.service';

@Component({
  templateUrl: './planned-outcomes-delete-dialog.component.html',
})
export class PlannedOutcomesDeleteDialogComponent {
  plannedOutcomes?: IPlannedOutcomes;

  constructor(
    protected plannedOutcomesService: PlannedOutcomesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.plannedOutcomesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('plannedOutcomesListModification');
      this.activeModal.close();
    });
  }
}
