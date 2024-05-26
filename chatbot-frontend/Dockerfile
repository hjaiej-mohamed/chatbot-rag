FROM node:18.19.0-bullseye AS build
WORKDIR /app
ENV PATH /app/node_modules/.bin:$PATH

COPY package.json  ./

# Install dependencies
RUN npm install --f

# Copy the rest of the application code
COPY ./ /app/

RUN npm install -g @angular/cli

EXPOSE 4200
CMD ["ng", "serve", "--host", "0.0.0.0"] # Build the application
# RUN  ng build --aot --output-hashing=all --configuration development

# # Final stage
# FROM nginx:alpine
# COPY --from=build /app/dist/chatbot-front/ /usr/share/nginx/html
# COPY nginx.conf /etc/nginx/conf.d/default.conf
# EXPOSE 80
