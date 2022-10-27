import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ErrorStateMatcher} from "@angular/material/core";
import {FormControl, FormGroupDirective, NgForm} from "@angular/forms";
import {formControlForTip} from "../util/initFormControlForTip.util";
import { errorMessage } from '../util/errorMessage.util';
import {TipUtil} from "../util/tip.util";


export interface DialogData {
  tip1: string;
  tip2: string;
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
  styleUrls: ['./pop-up.component.css'],
})
export class PopUpComponent implements OnInit {

  // ngClass?: string | string[] | Set<string> | { [klass: string]: any; }

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

  public isTipAPositiveNumber(tipTeam1: string, tipTeam2: string): boolean {
    return TipUtil.isPositiveNumber(tipTeam1, tipTeam2);
  }

  // public hasError() {
  //   if (!this.isTipAPositivNumber(this.data.tip1, this.data.tip2)) {
  //     return true
  //   } else {
  //     return false
  //   }
  // }
}
