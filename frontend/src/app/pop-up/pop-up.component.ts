import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ErrorStateMatcher} from "@angular/material/core";
import {FormControl, FormGroupDirective, NgForm} from "@angular/forms";
import {formControlForTip} from "../util/initFormControlForTip.util";
import { errorMessage } from '../util/errorMessage.util';
import {isTipAPositivNumber} from "../util/tip.util";


export interface DialogData {
  tip1: number;
  tip2: number;
  country1: string;
  country2: string;
  flag1: string;
  flag2:string;
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
  styleUrls: ['./pop-up.component.css']
})
export class PopUpComponent implements OnInit {

  public readonly errorMessage = errorMessage;
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

  public isTipAPositivNumber(tipTeam1: any, tipTeam2: any): boolean {
    return isTipAPositivNumber(tipTeam1, tipTeam2);
  }
}
