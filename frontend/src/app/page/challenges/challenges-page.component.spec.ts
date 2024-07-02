import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChallengesPageComponent } from './challenges-page.component';

describe('ChallengesPageComponent', () => {
  let component: ChallengesPageComponent;
  let fixture: ComponentFixture<ChallengesPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ChallengesPageComponent]
    }).compileComponents();

    fixture = TestBed.createComponent(ChallengesPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
