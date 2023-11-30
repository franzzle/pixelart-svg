FROM openjdk:17
LABEL maintainer="franzzle"

# Create a working directory inside the container
WORKDIR /opt

# Copy the JAR file from your local build context into the container
COPY build/libs/app.jar /opt/app.jar

ENV JAVA_OPTS="-Xms64m -Xmx128m"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /opt/app.jar" ]