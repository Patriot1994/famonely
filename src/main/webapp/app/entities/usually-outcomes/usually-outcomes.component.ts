import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUsuallyOutcomes } from 'app/shared/model/usually-outcomes.model';
import { UsuallyOutcomesService } from './usually-outcomes.service';
import { UsuallyOutcomesDeleteDialogComponent } from './usually-outcomes-delete-dialog.component';

@Component({
  selector: 'jhi-usually-outcomes',
  templateUrl: './usually-outcomes.component.html',
})
export class UsuallyOutcomesComponent implements OnInit, OnDestroy {
  usuallyOutcomes?: IUsuallyOutcomes[];
  eventSubscriber?: Subscription;

  constructor(
    protected usuallyOutcomesService: UsuallyOutcomesService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.usuallyOutcomesService.query().subscribe((res: HttpResponse<IUsuallyOutcomes[]>) => (this.usuallyOutcomes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInUsuallyOutcomes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IUsuallyOutcomes): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInUsuallyOutcomes(): void {
    this.eventSubscriber = this.eventManager.subscribe('usuallyOutcomesListModification', () => this.loadAll());
  }

  delete(usuallyOutcomes: IUsuallyOutcomes): void {
    const modalRef = this.modalService.open(UsuallyOutcomesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.usuallyOutcomes = usuallyOutcomes;
  }
}
