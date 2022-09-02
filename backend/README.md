# WM-Tippspiel Backend

## Authentication

REST endpoints are protected by Spring Boot Security and expect following HTTP request headers to be present to
authenticate users:

- X-Forwarded-User: Containing an Ergon user login name, e.g. nicustei, jvontobe
- X-Forwarded-Mail: Containing an Ergon user unique email address, e.g. niculin.steiner@ergon.ch, joel.vontobel@ergon.ch

HTTP requests need both headers to be accepted. A browser plugin-in such as [0] allows to add custom HTTP header per
request a domain. For further information check https://confluence.ergon.ch/display/ergonbb/System+Infrastruktur.

[0] https://addons.mozilla.org/de/firefox/addon/modify-header-value/ (there may be other/better plugins)

### Roles

A simple role system based on 'USER_ROLE' and 'ADMIN_ROLE' provides support for admin only endpoints.
