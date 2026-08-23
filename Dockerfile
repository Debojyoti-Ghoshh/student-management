FROM eclipse-temurin:22-jdk

WORKDIR /app

COPY . .

RUN chmod +x gradlew

RUN ./gradlew clean bootJar --no-daemon

CMD ["sh", "-c", "java -jar build/libs/student-management-0.0.1-SNAPSHOT.jar"]