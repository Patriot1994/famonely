import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPlannedOutcomes } from 'app/shared/model/planned-outcomes.model';
import { PlannedOutcomesService } from './planned-outcomes.service';
import { PlannedOutcomesDeleteDialogComponent } from './planned-outcomes-delete-dialog.component';

@Component({
  selector: 'jhi-planned-outcomes',
  templateUrl: './planned-outcomes.component.html',
})
export class PlannedOutcomesComponent implements OnInit, OnDestroy {
  plannedOutcomes?: IPlannedOutcomes[];
  eventSubscriber?: Subscription;

  constructor(
    protected plannedOutcomesService: PlannedOutcomesService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.plannedOutcomesService.query().subscribe((res: HttpResponse<IPlannedOutcomes[]>) => (this.plannedOutcomes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPlannedOutcomes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPlannedOutcomes): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPlannedOutcomes(): void {
    this.eventSubscriber = this.eventManager.subscribe('plannedOutcomesListModification', () => this.loadAll());
  }

  delete(plannedOutcomes: IPlannedOutcomes): void {
    const modalRef = this.modalService.open(PlannedOutcomesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.plannedOutcomes = plannedOutcomes;
  }
}
