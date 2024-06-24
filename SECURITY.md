# Security

## Authentifizierung / Autorisierung

### Frontend

- Angular Guards  
  [Was versteht man unter Angular Route-Guards?](https://www.novacapta.ch/details/angular-route-guards)  
  [Route Guards in Angular](https://medium.com/@hish.abdelshafouk/route-guards-in-angular-c9da0d815ef4)  
  - Nur Admin benutzer können Administration öffnen
  - Nur Autoren kännen CTF Erstellungsseite öffnen

### Backend

- RBAC (Role Based Access Control)  
- Spring Security
  - Security filter chain [The security filter chain](https://docs.spring.io/spring-security/site/docs/3.0.x/reference/security-filter-chain.html)
  - Preauthorize [Method Security](https://docs.spring.io/spring-security/reference/servlet/authorization/method-security.html)

## Login


## Passwörter

- Argon2 gehasht [Password Storage](https://docs.spring.io/spring-security/reference/features/authentication/password-storage.html) [Argon2](https://en.wikipedia.org/wiki/Argon2)

## Token

## Injections

## XSS

### Frontend

- Benutzerdaten werden nie direkt in HTML eingebunden, sondern über Angular Interpolation eingebunden. [Interpolation](https://docs.angular.lat/guide/interpolation)

