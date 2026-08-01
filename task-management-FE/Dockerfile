
FROM node:22-alpine AS build

WORKDIR /app


COPY package*.json ./
RUN npm ci


COPY . .
RUN npm run build -- --configuration=production


FROM nginx:alpine

COPY --from=build /app/dist/task-management-FE/browser /usr/share/nginx/html

# Fallback for Angular SPA routing (handles page refreshes without 404s)
RUN echo 'server { \
    listen 80; \
    location / { \
        root /usr/share/nginx/html; \
        index index.html index.htm; \
        try_files $uri $uri/ /index.html; \
    } \
}' > /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]