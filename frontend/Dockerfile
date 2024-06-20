# Stage 1: Build the Vite app
FROM node:18.20.3-alpine AS build

# Set the working directory inside the container
WORKDIR /myworld-frontend

# Copy package.json and package-lock.json to the working directory
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the app's source code to the working directory
COPY . .

# Expose port 443
EXPOSE 443

# Build the app
CMD ["npm", "run", "dev", "--", "--host", "0.0.0.0"]

