import { AfterViewInit, Component } from '@angular/core';
import { TestControllerService } from '@app/core/api/services';
import {MatCardModule} from "@angular/material/card";
import {NgForOf} from "@angular/common";
import {RouterLink} from "@angular/router";
import {MatButton} from "@angular/material/button";

@Component({
  selector: 'ctf-challenges',
  standalone: true,
  imports: [
    MatCardModule,
    NgForOf,
    RouterLink,
    MatButton
  ],
  templateUrl: './challenges.component.html',
  styleUrl: './challenges.component.scss'
})
export class ChallengesComponent {
  constructor() {}
  public challenges: { name: string, description: string, author: { username: string } }[] = [{ name: "The funny challenge", description: "A really funny challenge that will make you laugh", author: { username: "lefunnyman"}}];
  // todo
}
