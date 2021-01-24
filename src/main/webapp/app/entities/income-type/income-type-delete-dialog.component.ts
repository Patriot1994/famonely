import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIncomeType } from 'app/shared/model/income-type.model';
import { IncomeTypeService } from './income-type.service';

@Component({
  templateUrl: './income-type-delete-dialog.component.html',
})
export class IncomeTypeDeleteDialogComponent {
  incomeType?: IIncomeType;

  constructor(
    protected incomeTypeService: IncomeTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.incomeTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('incomeTypeListModification');
      this.activeModal.close();
    });
  }
}
