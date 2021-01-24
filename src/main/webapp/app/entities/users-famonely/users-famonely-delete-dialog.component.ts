import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUsersFamonely } from 'app/shared/model/users-famonely.model';
import { UsersFamonelyService } from './users-famonely.service';

@Component({
  templateUrl: './users-famonely-delete-dialog.component.html',
})
export class UsersFamonelyDeleteDialogComponent {
  usersFamonely?: IUsersFamonely;

  constructor(
    protected usersFamonelyService: UsersFamonelyService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.usersFamonelyService.delete(id).subscribe(() => {
      this.eventManager.broadcast('usersFamonelyListModification');
      this.activeModal.close();
    });
  }
}
