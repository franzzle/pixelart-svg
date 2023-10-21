Based on the PHP code in this git repo :

https://github.com/meyerweb/px2svg

This service transform a pixelart PNG into a SVG that has the same blocky look.
This is normally hard to do in even the most sophisticated vector applications.
When converted to SVG it is possible to retain the crisp edges on the cluster 
of pixel squares when scaling.

PNG Input (binary, and a lot smaller)

![secretagent.png](documentation%2Fimages%2Fsecretagent.png)

SVG Output (can be scaled to any size)

![secretagent.svg](documentation%2Fimages%2Fsecretagent.svg)


Visit this for overview of Rest API
```
http://localhost:9380/v3/api-docs
```

## Open Simple GUI to see the service in action

```
http://localhost:9380/swagger-ui/index.html
```