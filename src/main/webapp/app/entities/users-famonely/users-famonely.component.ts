import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUsersFamonely } from 'app/shared/model/users-famonely.model';
import { UsersFamonelyService } from './users-famonely.service';
import { UsersFamonelyDeleteDialogComponent } from './users-famonely-delete-dialog.component';

@Component({
  selector: 'jhi-users-famonely',
  templateUrl: './users-famonely.component.html',
})
export class UsersFamonelyComponent implements OnInit, OnDestroy {
  usersFamonelies?: IUsersFamonely[];
  eventSubscriber?: Subscription;

  constructor(
    protected usersFamonelyService: UsersFamonelyService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.usersFamonelyService.query().subscribe((res: HttpResponse<IUsersFamonely[]>) => (this.usersFamonelies = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInUsersFamonelies();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IUsersFamonely): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInUsersFamonelies(): void {
    this.eventSubscriber = this.eventManager.subscribe('usersFamonelyListModification', () => this.loadAll());
  }

  delete(usersFamonely: IUsersFamonely): void {
    const modalRef = this.modalService.open(UsersFamonelyDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.usersFamonely = usersFamonely;
  }
}
