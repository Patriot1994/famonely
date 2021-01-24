import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IIncomes } from 'app/shared/model/incomes.model';
import { IncomesService } from './incomes.service';
import { IncomesDeleteDialogComponent } from './incomes-delete-dialog.component';

@Component({
  selector: 'jhi-incomes',
  templateUrl: './incomes.component.html',
})
export class IncomesComponent implements OnInit, OnDestroy {
  incomes?: IIncomes[];
  eventSubscriber?: Subscription;

  constructor(protected incomesService: IncomesService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.incomesService.query().subscribe((res: HttpResponse<IIncomes[]>) => (this.incomes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInIncomes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IIncomes): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInIncomes(): void {
    this.eventSubscriber = this.eventManager.subscribe('incomesListModification', () => this.loadAll());
  }

  delete(incomes: IIncomes): void {
    const modalRef = this.modalService.open(IncomesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.incomes = incomes;
  }
}
