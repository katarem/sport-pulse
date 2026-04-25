FROM eclipse-temurin:17 AS build

WORKDIR /app
COPY . /app

RUN ./mvnw -Dmaven.test.skip=true clean package
RUN chmod +x /app/target/*.jar

FROM eclipse-temurin:17

WORKDIR /app
RUN addgroup --system spring && adduser --system --group spring
USER spring:spring
EXPOSE 8080
COPY --from=build /app/target/*.jar server.jar

ENV SERVER_PORT=8080

ENTRYPOINT ["java", "-jar", "server.jar"]