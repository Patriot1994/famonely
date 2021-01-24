import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IStateOfMoney } from 'app/shared/model/state-of-money.model';
import { StateOfMoneyService } from './state-of-money.service';
import { StateOfMoneyDeleteDialogComponent } from './state-of-money-delete-dialog.component';

@Component({
  selector: 'jhi-state-of-money',
  templateUrl: './state-of-money.component.html',
})
export class StateOfMoneyComponent implements OnInit, OnDestroy {
  stateOfMonies?: IStateOfMoney[];
  eventSubscriber?: Subscription;

  constructor(
    protected stateOfMoneyService: StateOfMoneyService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.stateOfMoneyService.query().subscribe((res: HttpResponse<IStateOfMoney[]>) => (this.stateOfMonies = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInStateOfMonies();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IStateOfMoney): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInStateOfMonies(): void {
    this.eventSubscriber = this.eventManager.subscribe('stateOfMoneyListModification', () => this.loadAll());
  }

  delete(stateOfMoney: IStateOfMoney): void {
    const modalRef = this.modalService.open(StateOfMoneyDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.stateOfMoney = stateOfMoney;
  }
}
