import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUsuallyOutcomes } from 'app/shared/model/usually-outcomes.model';
import { UsuallyOutcomesService } from './usually-outcomes.service';

@Component({
  templateUrl: './usually-outcomes-delete-dialog.component.html',
})
export class UsuallyOutcomesDeleteDialogComponent {
  usuallyOutcomes?: IUsuallyOutcomes;

  constructor(
    protected usuallyOutcomesService: UsuallyOutcomesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.usuallyOutcomesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('usuallyOutcomesListModification');
      this.activeModal.close();
    });
  }
}
