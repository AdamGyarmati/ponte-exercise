import { Component } from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {HttpErrorResponse} from "@angular/common/http";
import {validationHandler} from "../../utils/validationHandler";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginForm: FormGroup;
  invalidLogin!: boolean;

  constructor(private authService: AuthService,
              private formBuilder: FormBuilder,
              private router: Router
              ) {
    this.loginForm = this.formBuilder.group(({
      username: ['', Validators.required],
      password: ['', Validators.required]
    }))
  }

  submitLoginForm() {
    const data = {...this.loginForm.value};
    this.authService.authenticate(data).subscribe({
      next: (response) => {
        this.router.navigate(['/me']);
      },
      error: (error) => {
        this.invalidLogin = true;
        validationHandler(error, this.loginForm);
      }
    });

    return false;
  }
}
