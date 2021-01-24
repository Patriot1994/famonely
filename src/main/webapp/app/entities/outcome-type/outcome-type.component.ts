import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOutcomeType } from 'app/shared/model/outcome-type.model';
import { OutcomeTypeService } from './outcome-type.service';
import { OutcomeTypeDeleteDialogComponent } from './outcome-type-delete-dialog.component';

@Component({
  selector: 'jhi-outcome-type',
  templateUrl: './outcome-type.component.html',
})
export class OutcomeTypeComponent implements OnInit, OnDestroy {
  outcomeTypes?: IOutcomeType[];
  eventSubscriber?: Subscription;

  constructor(
    protected outcomeTypeService: OutcomeTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.outcomeTypeService.query().subscribe((res: HttpResponse<IOutcomeType[]>) => (this.outcomeTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInOutcomeTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IOutcomeType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInOutcomeTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('outcomeTypeListModification', () => this.loadAll());
  }

  delete(outcomeType: IOutcomeType): void {
    const modalRef = this.modalService.open(OutcomeTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.outcomeType = outcomeType;
  }
}
