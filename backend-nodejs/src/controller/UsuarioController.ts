import {OK, UNAUTHORIZED, BAD_REQUEST, FORBIDDEN} from 'http-status-codes';
import {Controller, Get, Middleware, Post} from '@overnightjs/core';
import {Request, Response} from 'express';
import {Usuario} from "../model/Usuario";
import {UsuarioService} from "../service/usuarioService";

require('../config/enviroment');

@Controller('user')
export class UsuarioController {


    @Post()
    private async create(req:Request, res:Response){

        const user = {
            email: req.body.email,
            password: req.body.password,
            authMode: 'local',
            username: req.body.username,
            nombre: req.body.name,
            apellidos: req.body.surname,
        };

        console.log(user);

        // NO OK no hemos recibido todos los parametros obligatorios


        // OK,guardamos el usuario
        const service = new UsuarioService();
        await service.createUser(user);
        res.status(OK).end()
    }



}