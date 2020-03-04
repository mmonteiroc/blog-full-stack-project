import {BAD_REQUEST, OK} from 'http-status-codes';
import {Controller, Post} from '@overnightjs/core';
import {Request, Response} from 'express';
import {UsuarioService} from "../service/usuarioService";
import {TokenService} from "../service/TokenService";

require('../config/enviroment');

@Controller('user')
export class UsuarioController {
    private servicioDeTokens = new TokenService();

    @Post()
    private async create(req: Request, res: Response) {

        const user = {
            email: req.body.email,
            password: req.body.password, // PUEDE SER  NULL PERO SOLO PARA LOS USERS OAUTH
            authMode: 'local',
            username: req.body.username,
            nombre: req.body.name,
            apellidos: req.body.surname, // PUEDE SER NULL
        };


        /*
        * NO OK no hemos recibido todos los parametros obligatorios
        *
        * Si no recibimos los parametros, los recibimos como NULL
        * */

        if (user.email == null || user.email === ''
            || user.password == null || user.password === ''
            || user.username == null || user.username === ''
            || user.nombre == null || user.nombre === '') {

            res.status(BAD_REQUEST).statusMessage = 'No se han recibido los parametros obligatorios';
            res.end();
        }


        /*
        * Comprobar que el email que recibimos
        * no exista ya en la Base de Datos
        *
        * Si existe, mandamos un error
        * diciendo que el email no es valido
        * */

        const usuarioService = new UsuarioService();
        const usuario = <any>await usuarioService.findByEmail(user.email);

        if (usuario !== null) {
            res.status(BAD_REQUEST).statusMessage = 'Email ya en uso';
            res.end();
        }


        // OK,guardamos el usuario
        await usuarioService.createUser(user);
        const userCreado = await usuarioService.findByEmail(user.email);

        const access_token = this.servicioDeTokens.tokenGenerator(userCreado.dataValues);
        const refresh_token = this.servicioDeTokens.tokenGenerator(userCreado.dataValues,'1w');

        res.status(OK).json({
                access_token: access_token,
                refresh_token: refresh_token
            }).end()
    }


}