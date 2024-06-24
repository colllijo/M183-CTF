import { Component } from '@angular/core';
import {MatFormFieldModule} from "@angular/material/form-field";
import {Store} from "@ngrx/store";
import {ChallengeActions} from "@+store/challenge/challenge.actions";

@Component({
  selector: 'ctf-challenge',
  standalone: true,
  imports: [
    MatFormFieldModule
  ],
  templateUrl: './challenge.component.html',
  styleUrl: './challenge.component.scss'
})
export class ChallengeComponent {

  constructor(private store: Store) {
  }


  public solve: {} | null = null;
  public challenge: { name: string, description: string, author: { username: string } } = { name: "The funny challenge", description: "A really funny challenge that will make you laugh", author: { username: "lefunnyman"}};
  // todo

  downloadFile() {
    this.store.dispatch(ChallengeActions.downloadFile({ name: this.challenge.name }));
  }
}
