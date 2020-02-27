import {Sequelize as MySequalize} from 'sequelize-typescript';
import {Usuario} from "../model/Usuario";


/*
* PLACEHOLDER
* TODO-> añadir en el .env
* */
export const sequelize = new MySequalize({
    repositoryMode: true,
    database: 'PRACTICA_GLOBAL',
    dialect: "mysql", // NO PUEDE VENIR DEL PROPERTIES YA QUE ES UN ENUM Y SI NO PETA
    username: 'monteiro',
    password: 'monteiro',
    models: [__dirname + '/../model/']
});