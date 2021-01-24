import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOutcomeType } from 'app/shared/model/outcome-type.model';
import { OutcomeTypeService } from './outcome-type.service';

@Component({
  templateUrl: './outcome-type-delete-dialog.component.html',
})
export class OutcomeTypeDeleteDialogComponent {
  outcomeType?: IOutcomeType;

  constructor(
    protected outcomeTypeService: OutcomeTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.outcomeTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('outcomeTypeListModification');
      this.activeModal.close();
    });
  }
}
