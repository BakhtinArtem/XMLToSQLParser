FROM node:16-alpine 

EXPOSE 3000

WORKDIR /react-app

COPY package.json .
COPY package-lock.json .
RUN npm install
COPY . .
CMD ["npm", "start"]