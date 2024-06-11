import { AfterViewInit, Component } from '@angular/core';
import { TestControllerService } from '@app/core/api/services';

@Component({
  selector: 'ctf-challenges',
  standalone: true,
  imports: [],
  templateUrl: './challenges.component.html',
  styleUrl: './challenges.component.scss'
})
export class ChallengesComponent implements AfterViewInit {
  constructor(private testService: TestControllerService) {}

  public ngAfterViewInit(): void {
    console.log('ChallengesComponent initialized');
    this.testService.test().subscribe((data) => {
      console.log(data);
    });
  }
}
