import { CanActivateFn, Router } from '@angular/router';
import { UserStorageService } from '../services/user-storage.service';
import { inject } from '@angular/core';

export const RoleGuard: CanActivateFn = (route, state) => {
  const router = inject(Router)
  const token=UserStorageService.getToken()
  if(token){
    const role=UserStorageService.getUserRole()
    if(role===route.data?.['userRole']){
      return true;
    }
  }
  router.navigateByUrl('/login')
  return false;
};
