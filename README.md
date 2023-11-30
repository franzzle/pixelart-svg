This service transform a PNG in a block pixel-art style into a SVG 
that has the same pixel-art square style look.

This is normally cumbersome to do in as most of the sophisticated vector applications 
are geared towards curvatures.

When converted to SVG it is possible to retain the crisp edges on the cluster 
of pixel squares when scaling.

PNG Input (binary, and a lot smaller)

![secretagent.png](documentation/images/secretagent.png)

SVG Output (can be scaled to any size)

![secretagent.svg](documentation/images/secretagent.svg)

## Build and run

```sh
./gradlew bootRun
```

Visit this for overview of Rest API

## Open Simple GUI to see the service in action

```sh
http://localhost:9380/swagger-ui/index.html
```

and if you want the machine readable docs

```sh
http://localhost:9380/v3/api-docs
```

You can verify the output with these tools for example :

Online SVG [SVG Viewer](https://www.svgviewer.dev).   
Gimp [Gimp](https://www.gimp.org/).

I have Based on the PHP code in this git repo :

https://github.com/meyerweb/px2svg

## Docker image for x64 (Intel based computers)

Create a docker with this command :

```
export JAVA_HOME=~/Library/Java/JavaVirtualMachines/temurin-17.0.7/Contents/Home 
export JAVA_HOME=~/Library/Java/JavaVirtualMachines/azul-17.0.6/Contents/Home 
./gradlew clean build
docker build --no-cache -t  franzzle/pixelart-svg:0.1.0 .
```

## Run docker

```sh
docker run -d -p 9380:9380 franzzle/pixelart-svg:0.1.0
```

## Pull from Docker Hub
```sh
docker pull franzzle/pixelart-svg:0.1.0
docker run -d -p 9380:9380 franzzle/pixelart-svg:0.1.0
```

## Docker compose run

```sh
docker-compose up pixelartsvg
```

## Future improvements 
* Optimize the scanning of the Square blocks.
* Optimize the SVG with a library or code it myself.
* Stream the scanning so large pixel art files can be processed.