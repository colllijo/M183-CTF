# Sicherheit

Über Themen, bei denen wir nicht einfach eine Bibliothek nutzen konnten, sind weniger Informationen gelistet.
Die Umsetzung kann aber im Code nachgeschaut werden.

## Authentifizierung / Autorisierung

- Angular Guards  
  [Was versteht man unter Angular Route-Guards?](https://www.novacapta.ch/details/angular-route-guards)  
  [Route Guards in Angular](https://medium.com/@hish.abdelshafouk/route-guards-in-angular-c9da0d815ef4)  
  - Nur Admin benutzer können Administration öffnen
  - Nur Autoren kännen CTF Erstellungsseite öffnen
- RBAC (Role Based Access Control)  
- Spring Security
  - Security filter chain [The security filter chain](https://docs.spring.io/spring-security/site/docs/3.0.x/reference/security-filter-chain.html)
  - Preauthorize [Method Security](https://docs.spring.io/spring-security/reference/servlet/authorization/method-security.html)

## Passwörter

- [Spring Security Password Storage](https://docs.spring.io/spring-security/reference/features/authentication/password-storage.html)
- Argon2 gehasht [Password Storage](https://docs.spring.io/spring-security/reference/features/authentication/password-storage.html) [Argon2](https://en.wikipedia.org/wiki/Argon2)

## Tokens

- [JWT](https://jwt.io/)
- [JJWT](https://github.com/jwtk/jjwt)

## Injection

Spring Data JPA verwendet Prepared Statements, um SQL-Injections zu verhindern. [Prepared Statements](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation)
https://stackoverflow.com/questions/73617743/is-springboot-data-jpa-repository-safe-against-sql-injection
https://docs.spring.io/spring-framework/docs/3.0.x/reference/jdbc.html

## XSS

- Benutzerdaten werden nie direkt in HTML eingebunden, sondern über Angular Interpolation eingebunden. [Interpolation](https://docs.angular.lat/guide/interpolation)

## Input Validation

- [Angular Custom Form Controls: Complete Guide](https://blog.angular-university.io/angular-custom-form-controls/)
- Für Formulare werden Formulargruppen erstellt
- Für jedes Feld wird eine Kontrolle erstellt, welche die Validation für das Feld macht
- Angular Reactive Forms FormGroup + FormControl
https://blog.angular-university.io/angular-custom-form-controls/
