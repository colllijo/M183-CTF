FROM node:lts-iron AS build

WORKDIR /usr/src/app

COPY ./cert /etc/ssl
COPY ./package*.json ./

RUN npm config set strict-ssl false
RUN npm remove cypress
RUN npm install
# RUN npm test

COPY . .
RUN npm run build --configuration=production
RUN npm ci --omit=dev && npm cache clean --force

FROM jitesoft/lighttpd:1.4.76 AS production

COPY .docker/lighttpd.conf /etc/lighttpd/conf.d/000-default.conf
COPY --from=build /usr/src/app/dist/frontend/browser /var/www/html
