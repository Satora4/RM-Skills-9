import {FormControl, Validators} from "@angular/forms";

export function formControlForTip(): FormControl {
  return new FormControl('', [Validators.required, Validators.pattern('^[0-9]*$')]);
}
