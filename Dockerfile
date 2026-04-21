# ==========================================
# Етап 1: Збірка Vue.js (Фронтенд)
# ==========================================
FROM node:20-alpine AS frontend-builder
WORKDIR /app/frontend

# Копіюємо package.json та встановлюємо залежності
COPY frontend/package*.json ./
RUN npm ci

# Копіюємо вихідний код і збираємо проект
COPY frontend/ ./
RUN npm run build
# Результат збірки лежить у /app/frontend/dist

# ==========================================
# Етап 2: Збірка Spring Boot (Бекенд)
# ==========================================
FROM eclipse-temurin:21-jdk-alpine AS backend-builder
WORKDIR /app/backend

# Копіюємо файли Maven
COPY pom.xml mvnw ./
COPY .mvn .mvn/
# Кешуємо залежності Maven
RUN ./mvnw dependency:go-offline

# Копіюємо вихідний код бекенду
COPY src ./src

# КРИТИЧНИЙ КРОК: Копіюємо зібраний Vue.js у папку static Spring Boot-а
COPY --from=frontend-builder /app/frontend/dist ./src/main/resources/static

# Збираємо .jar файл (пропускаючи тести для швидкості збірки образу)
RUN ./mvnw clean package -DskipTests
# Результат: /app/backend/target/homelabsuit-0.0.1-SNAPSHOT.jar

# ==========================================
# Етап 3: Фінальний образ (Тільки необхідне)
# ==========================================
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Копіюємо готовий .jar з попереднього етапу
COPY --from=backend-builder /app/backend/target/*.jar app.jar

# Відкриваємо порт
EXPOSE 8080

# Запускаємо застосунок
ENTRYPOINT ["java", "-jar", "app.jar"]