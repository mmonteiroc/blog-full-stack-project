import {BAD_REQUEST, OK, UNAUTHORIZED} from 'http-status-codes';
import {Controller, Post, Put} from '@overnightjs/core';
import {Request, Response} from 'express';
import {UsuarioService} from "../service/usuarioService";
import {TokenService} from "../service/TokenService";
import * as jwt from "jsonwebtoken";
import * as encriptador from 'bcrypt';

require('../config/enviroment');

@Controller('user')
export class UsuarioController {
    private servicioDeTokens = new TokenService();
    private usuarioService = new UsuarioService();

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
        * TODO definir politicas de password y validarla
        * */


        /*
        * Comprobar que el email que recibimos
        * no exista ya en la Base de Datos
        *
        * Si existe, mandamos un error
        * diciendo que el email no es valido
        * */

        const usuario = <any>await this.usuarioService.findByEmail(user.email);

        if (usuario !== null) {
            res.status(BAD_REQUEST).statusMessage = 'Email ya en uso';
            res.end();
        }


        // OK,guardamos el usuario
        await this.usuarioService.createUser(user);
        const userCreado = await this.usuarioService.findByEmail(user.email);

        const access_token = this.servicioDeTokens.tokenGenerator(userCreado.dataValues);
        const refresh_token = this.servicioDeTokens.tokenGenerator(userCreado.dataValues,'1w');

        res.status(OK).json({
                access_token: access_token,
                refresh_token: refresh_token
            }).end()
    }


    @Put('security')
    private async editSecurity(req:Request, res:Response){

        /*
        * Validamos que el token que nos pasas valide,
        * despues cogemos el usuario de dicho token
        * */
        const token = <string>req.header("Authorization");
        let usuarioDelToken;
        try {
            usuarioDelToken = <any>jwt.verify(token, process.env.JWT_SECRET || '');
        } catch (e) {
            res.status(UNAUTHORIZED).statusMessage = "Token no valido";
            return res.end()
            }

        /*
        * Aqui cogemos las 3 passwd
        * */
        const passwords = {
            oldPassword: req.body.oldPassword,
            newPassword: req.body.newPassword,
            newPassword1: req.body.newPassword1,
        };

        // PLACEHOLDER -- AQUI COMPROBAR QUE LAS PASSWORDS CUMPLEN REQUISITOS Y SON IGUALES LAS DOS NEW
        if (passwords.newPassword !== passwords.newPassword1){
            res.status(BAD_REQUEST).statusMessage = 'La passwd nueva no coincide';
            return res.end()
        }


        /*
        * Cogemos el user de la DDBB
        * */
        let usuario = <any>await this.usuarioService.findByEmail(usuarioDelToken.email);

        if (usuario === null) {
            res.status(BAD_REQUEST).statusMessage = 'Usuario no existente';
            return res.end()
        }
        usuario = usuario.dataValues;

        const validacionPassword = await encriptador.compare(passwords.oldPassword, usuario.password);
        if (!validacionPassword){
            res.status(BAD_REQUEST).statusMessage = 'Old passwd no coincide con DDBB';
            return res.end()
        }
        usuario.password = passwords.newPassword;
        await this.usuarioService.update(usuario,true);

        res.status(OK).end();
    }
}