import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { FamonelySharedModule } from 'app/shared/shared.module';
import { FamonelyCoreModule } from 'app/core/core.module';
import { FamonelyAppRoutingModule } from './app-routing.module';
import { FamonelyHomeModule } from './home/home.module';
import { FamonelyEntityModule } from './entities/entity.module';
import {RightFamonelyAppModule} from './right-famonely-app/right-famonely-app.module'
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    FamonelySharedModule,
    FamonelyCoreModule,
    FamonelyHomeModule,
    RightFamonelyAppModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    FamonelyEntityModule,
    FamonelyAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent],
})
export class FamonelyAppModule {}
