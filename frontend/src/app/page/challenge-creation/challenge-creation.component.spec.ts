import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChallengeCreationComponent } from './challenge-creation.component';

describe('ChallengeCreationComponent', () => {
  let component: ChallengeCreationComponent;
  let fixture: ComponentFixture<ChallengeCreationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ChallengeCreationComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChallengeCreationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
