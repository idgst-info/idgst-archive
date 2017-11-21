Below you will find some information on how to build and run the application

# Table of Contents

<!-- TOC -->

- [Table of Contents](#table-of-contents)
    - [About](#about)
    - [How to run into development mode](#how-to-run-into-development-mode)
    - [How to build for production](#how-to-build-for-production)

<!-- /TOC -->

## About

This Rest API for [idgst.info](http://idgst.info) writen in Scala Play Framework.

Main git repository is hosted on [Gitlab](https://gitlab.com/idgst/idgst-archive).
The mirror repository is hosted on [Github](https://github.com/idgst/idgst-rest-api)

## How to run into development mode

```bash
sbt run
```

After that REST API is available on http://localhost:9000

## How to build for production

```bash
sbt dist
```

`idgst*.zip` will be available in `target/universal/` folder.

Official Docs about [how to deploy to Play application](https://www.playframework.com/documentation/2.6.x/Deploying)