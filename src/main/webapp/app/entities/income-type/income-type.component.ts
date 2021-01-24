import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IIncomeType } from 'app/shared/model/income-type.model';
import { IncomeTypeService } from './income-type.service';
import { IncomeTypeDeleteDialogComponent } from './income-type-delete-dialog.component';

@Component({
  selector: 'jhi-income-type',
  templateUrl: './income-type.component.html',
})
export class IncomeTypeComponent implements OnInit, OnDestroy {
  incomeTypes?: IIncomeType[];
  eventSubscriber?: Subscription;

  constructor(protected incomeTypeService: IncomeTypeService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.incomeTypeService.query().subscribe((res: HttpResponse<IIncomeType[]>) => (this.incomeTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInIncomeTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IIncomeType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInIncomeTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('incomeTypeListModification', () => this.loadAll());
  }

  delete(incomeType: IIncomeType): void {
    const modalRef = this.modalService.open(IncomeTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.incomeType = incomeType;
  }
}
