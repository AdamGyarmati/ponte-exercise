FROM node:20 AS node
WORKDIR /app
COPY . .
RUN npm install
RUN npm run build

FROM nginx:latest
COPY --from=node /app/dist/ponte-exercise-frontend/browser /usr/share/nginx/html
EXPOSE 4200
