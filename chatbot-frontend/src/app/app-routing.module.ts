import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ChatComponent } from './chat/chat.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './auth/login/login.component';
import { AuthGuard } from './core/guards/auth.guard';
import { InProgressComponent } from './shared/in-progress/in-progress.component';
import { RegisterComponent } from './auth/register/register.component';
import { AdminComponent } from './admin/admin.component';

const routes:Routes = [
  {
    path:'home',
    component:HomeComponent,
    
  },
  {
    path:'chat',
    component: ChatComponent,
    canActivate:[AuthGuard]
  },
  {
    path:'login',
    component:LoginComponent
  },
  {
    path:'register',
    component:RegisterComponent
  },
  {
    path:'in-development',
    component:InProgressComponent,
    canActivate:[AuthGuard]

  },
  {
    path:'admin',
    component:AdminComponent,
    canActivate:[AuthGuard]
  },
  { path: '',
    redirectTo: '/home',
    pathMatch: 'full' 
  },
  {
    path: '**',
    redirectTo: '/home'
},
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
