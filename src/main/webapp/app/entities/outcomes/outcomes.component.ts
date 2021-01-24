import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOutcomes } from 'app/shared/model/outcomes.model';
import { OutcomesService } from './outcomes.service';
import { OutcomesDeleteDialogComponent } from './outcomes-delete-dialog.component';

@Component({
  selector: 'jhi-outcomes',
  templateUrl: './outcomes.component.html',
})
export class OutcomesComponent implements OnInit, OnDestroy {
  outcomes?: IOutcomes[];
  eventSubscriber?: Subscription;

  constructor(protected outcomesService: OutcomesService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.outcomesService.query().subscribe((res: HttpResponse<IOutcomes[]>) => (this.outcomes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInOutcomes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IOutcomes): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInOutcomes(): void {
    this.eventSubscriber = this.eventManager.subscribe('outcomesListModification', () => this.loadAll());
  }

  delete(outcomes: IOutcomes): void {
    const modalRef = this.modalService.open(OutcomesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.outcomes = outcomes;
  }
}
