import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { HomeComponent } from './home/home.component';
import {LoginComponent} from "./login/login.component"
import { ProfileComponent } from './profile/profile.component';
import {UserComponent} from "./user/user.component"
import {AuthGuard} from "./auth.guard"

const routes: Routes = [
  {
    path:"",
    component:LoginComponent
  },
  {
    path:"login",
    component:LoginComponent
  },
  {
    path: "forgot-password",
    component: ForgotPasswordComponent
  },
  {
    path: "home/:user",
    component:HomeComponent,
    //canActivate:[AuthGuard]
  },
  {
    path: "search/:user/:cuser",
    component: UserComponent,
    //canActivate:[AuthGuard]
  },
  {
    path: "profile/:user",
    component: ProfileComponent,
    //canActivate:[AuthGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
