FROM amazoncorretto:17

COPY target/*.jar baidarka.jar

ENTRYPOINT ["java", "-jar", "/baidarka.jar"]
