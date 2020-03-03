import {OK, UNAUTHORIZED, BAD_REQUEST, FORBIDDEN} from 'http-status-codes';
import {Controller, Get, Middleware, Post} from '@overnightjs/core';
import {Request, Response} from 'express';
import * as passport from "passport";
import * as jwt from 'jsonwebtoken';
import {Usuario} from "../model/Usuario";
import {UsuarioService} from "../service/usuarioService";

require('../config/passport');
require('../config/enviroment');

@Controller('login')
export class LoginController {
    



    /*
    * Placeholder login local ---
    * TODO hablar con joan y preguntarle tema de
    *  como hacer con passport y si se
    *  puede ya que hacemos api rest
    * */
    @Post('local')
    private async loginLocal(req:Request, res:Response){
        /*
        * TODO-> get data pasada con el axios
        *  llamar a validar ese usuario al service
        * */
        const service = new UsuarioService();
        const userToValidate = {
            email: req.body.email,
            password: req.body.password,
            authMode: 'local'
        };

        const result = await service.validateUser(userToValidate);


        if (result){
            // Validado
            const usuarioValidado = await service.findByEmail(userToValidate.email);
            const token = LoginController.tokenGenerator(usuarioValidado.dataValues);
            const refresh_token = LoginController.tokenGenerator(usuarioValidado.dataValues, '1w');
            return res.status(OK).json({
                access_token: token,
                refresh_token: refresh_token
            })

        } else {
            // No validado
            res.status(UNAUTHORIZED).statusMessage = 'Datos de login no validos';
            res.end()
        }

    }


    /*
    * TODO -- Mirar que el usuario existe en la BBDD sino, enviar error
    *  Mirar que si el token refresh no valida dar errror
    * */
    @Post('refresh')
    private async refreshToken(req: Request, res: Response) {
        /*
        * Cogemos el refreshToken
        * */
        const token = <string>req.header("Authorization");
        if (!token && token === '') {
            res.status(BAD_REQUEST).statusMessage = "Refresh token no recibido";
            res.end()
        }

        let usuarioDelToken;
        try {
            usuarioDelToken = <any>jwt.verify(token, process.env.JWT_SECRET || '');
        } catch (e) {
            res.status(UNAUTHORIZED).statusMessage = "Refresh token no valido";
            res.end()
        }

        const service = new UsuarioService();
        let usuario = <any>await service.findByEmail(usuarioDelToken.email);
        if (!usuario) {
            res.status(UNAUTHORIZED).statusMessage = "Refresh token con datos de usuario no validos";
            res.end()
        }

        usuario = usuario.dataValues;
        /*
        * Creamos un nuevo token con la info del refresh token
        * */

        const newToken = LoginController.tokenGenerator({
            email: usuario.email,
            username: usuario.username,
            idusuario: usuario.idusuario
        });
        /*
        * Retornamos el token normal
        * */

        res.status(200).json({
            access_token: newToken
        })
    }

    /*
    * -----------------------------------
    *
    *   Metodos relacionados con Oauth
    *   de GOOGLE
    *
    * -----------------------------------
    * */
    @Get('google')
    @Middleware(passport.authenticate('google', {scope: ['email', 'profile']}))
    private async loginGoogle(req: Request, res: Response) {
        res.end()
    }

    /*
    * A ESTA URL SOLO ENTRA SI EL LOGIN DE GOOGLE HA IDO BIEN
    * */
    @Get('gg/callback')
    @Middleware(passport.authenticate('google', {
        failureRedirect: '/login/gg/failure'
    }))
    private async success(req: Request, res: Response) {
        console.log("GOOGLE HA IDO BIEN");
        const usuario =<Usuario> req.user;
        const user = {
          email: usuario.email,
            username: usuario.username,
            idusuario: usuario.idusuario
        };

        const token = LoginController.tokenGenerator(user);
        res.redirect(process.env.FRONTEND_URL + '/?access_token=' + token + '#/login/callback');
    }


    /*
    *
    * TODO -- Mirar como enviar un error en este
    *  caso al cliente diciendole que puede
    *  que esta cuenta ya se user para login local o que google
    *  no ha podido verificar su identidad
    * */
    @Get('gg/failure')
    private async failure(req: Request, res: Response) {
        res.redirect(process.env.FRONTEND_URL + '/#/login/');
    }


    private static tokenGenerator(user: any, expiresTime: any = '1m') {
        user.password = '';
        const stringUser: string = <string><unknown>user;
        return jwt.sign(stringUser, process.env.JWT_SECRET || '', {
            expiresIn: expiresTime,
            subject: user.idusuario + ""// CAST TO STRING
        });
    }
}