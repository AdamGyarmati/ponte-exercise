import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';

import {Observable} from 'rxjs';

@Injectable()
export class HttpRequestInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler):
    Observable<HttpEvent<any>> {

    const requestWithHeader = req.clone({
      headers        : req.headers.set('X-Requested-With', 'XMLHttpRequest'),
      withCredentials: true,
    });
    console.log('Bent vagyok az interceptorban')

    return next.handle(requestWithHeader);
  }
}
