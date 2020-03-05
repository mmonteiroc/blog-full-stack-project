# Front-end


[![Build](https://travis-ci.org/jaredhanson/passport.svg?branch=master)](https://upload.wikimedia.org/wikipedia/commons/thumb/8/8e/Build_Passing_Shield_Badge.svg/1280px-Build_Passing_Shield_Badge.svg.png)


En este repositorio encontraras la parte de cliente de nuestro proyecto global.
Para lanzar este proyecto necesitaras ejecutar los siguientes pasos:

## Instalar QUASAR/CLI
```bash
npm install -g @quasar/cli
```


## Instalar todas las dependecias
```bash
npm install
```

### Si quieres lanzar el proyecto en modo desarrollo:
```bash
quasar dev
```


### Si quieres compilar en modo produccion:
```bash
quasar build
```
Este nos genera una carpeta ```dist/``` la cual contendra nuestro programa ya compilado.
Para lanzar en modo produccion has de tener en cuenta en quasar.conf.js modificar las ips de produccion antes de compilarlo

