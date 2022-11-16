import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ErrorStateMatcher} from "@angular/material/core";
import {FormControl, FormGroupDirective, NgForm} from "@angular/forms";
import {formControlForTip} from "../util/initFormControlForTip.util";
import {TipUtil} from "../util/tip.util";
import {Game} from "../game/game.model";


export interface DialogData {
  tip1: string;
  tip2: string;
  country1: string;
  country2: string;
  flag1: string;
  flag2: string;
  phase: string;
}

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'app-pop-up',
  templateUrl: './pop-up.component.html',
  styleUrls: ['./pop-up.component.css'],
})
export class PopUpComponent implements OnInit {

  formControlTip1 = formControlForTip();
  formControlTip2 = formControlForTip();
  matcher = new MyErrorStateMatcher();

  constructor(
    public dialogRef: MatDialogRef<PopUpComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit(): void {
  }

  public isTipAValidNumber(tipTeam1: string, tipTeam2: string, phase: string): boolean {
    if (phase == 'GROUP') {
      return TipUtil.isPositiveNumber(tipTeam1, tipTeam2);
    } else {
      return TipUtil.isValidNumberKoPhase(tipTeam1, tipTeam2)
    }
  }
}
