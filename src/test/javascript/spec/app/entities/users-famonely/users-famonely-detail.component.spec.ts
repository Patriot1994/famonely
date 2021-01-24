import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FamonelyTestModule } from '../../../test.module';
import { UsersFamonelyDetailComponent } from 'app/entities/users-famonely/users-famonely-detail.component';
import { UsersFamonely } from 'app/shared/model/users-famonely.model';

describe('Component Tests', () => {
  describe('UsersFamonely Management Detail Component', () => {
    let comp: UsersFamonelyDetailComponent;
    let fixture: ComponentFixture<UsersFamonelyDetailComponent>;
    const route = ({ data: of({ usersFamonely: new UsersFamonely(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FamonelyTestModule],
        declarations: [UsersFamonelyDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UsersFamonelyDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UsersFamonelyDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load usersFamonely on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.usersFamonely).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
