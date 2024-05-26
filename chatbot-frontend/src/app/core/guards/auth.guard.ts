import { CanActivateFn, Router } from '@angular/router';
import { UserStorageService } from '../services/user-storage.service';
import { inject } from '@angular/core';
import { NzNotificationService } from 'ng-zorro-antd/notification';

export const AuthGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const token= UserStorageService.getToken()
  const notification = inject(NzNotificationService)
  if(token){
    return true;
  }
  notification.error(
    'Not authorized',
    'You need to sign in to enter to the application',
    { nzDuration: 3000 }
  );
  router.navigateByUrl('/login')
  return false;
};
