# ---- build stage ----
FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml ./
RUN mvn -q -e -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -q -DskipTests package

# ---- run stage ----
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*SNAPSHOT*.jar /app/app.jar
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75.0"
EXPOSE 8080
# Bind Spring Boot to $PORT provided by Render (fallback 8080 for local)
CMD ["sh","-c","java -Dserver.port=${PORT:-8080} $JAVA_OPTS -jar /app/app.jar"]