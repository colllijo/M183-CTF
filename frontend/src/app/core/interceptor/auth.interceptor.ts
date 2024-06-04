import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const accessToken = sessionStorage.getItem('accessToken');

  let headers = req.headers;
  if (accessToken)
    headers = headers.set('Authorization', `Bearer ${accessToken}`);

  return next(req.clone({ headers, withCredentials: true }));
};
