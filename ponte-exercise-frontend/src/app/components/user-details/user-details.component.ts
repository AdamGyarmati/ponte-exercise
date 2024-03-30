import {Component} from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {AppUserModel} from "../../models/appUser-model";
import {FormArray, FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrl: './user-details.component.css'
})
export class UserDetailsComponent {
  appUser?: AppUserModel | null;
  userForm: FormGroup;

  constructor(private authService: AuthService,
              private formBuilder: FormBuilder,) {
    this.authService.user.subscribe({
      next: (user) => {
        this.appUser = user;
      }
    })

    console.log(this.appUser);

    this.userForm = this.formBuilder.group({
      name: [this.appUser?.name, Validators.required],
      email: [this.appUser?.email, Validators.required],
      password: ['', Validators.required],
      birthDate: [this.appUser?.birthDate, Validators.required],
      motherName: [this.appUser?.motherName, Validators.required],
      socialSecurityNumber: [this.appUser?.socialSecurityNumber, Validators.required],
      taxIdentificationNumber: [this.appUser?.taxIdentificationNumber, Validators.required],
      phoneNumberUpdateDto: this.formBuilder.array([]),
      addressUpdateDtoList: this.formBuilder.array([]),
    })
  }

}
