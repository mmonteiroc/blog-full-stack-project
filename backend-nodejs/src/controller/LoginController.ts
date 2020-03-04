import {BAD_REQUEST, OK, UNAUTHORIZED} from 'http-status-codes';
import {Controller, Get, Middleware, Post} from '@overnightjs/core';
import {Request, Response} from 'express';
import * as passport from "passport";
import * as jwt from 'jsonwebtoken';
import {Usuario} from "../model/Usuario";
import {UsuarioService} from "../service/usuarioService";
import {TokenService} from "../service/TokenService";


require('../config/passport');
require('../config/enviroment');

@Controller('login')
export class LoginController {
    private servicioDeTokens = new TokenService();


    @Post('local')
    private async loginLocal(req: Request, res: Response) {


        passport.authenticate('local',
            {
                session: false
            },
            (req, usuario, info) => {


                if (!usuario){
                    res.status(UNAUTHORIZED).statusMessage = 'Datos de login no validos';
                    res.end()
                }else {
                    const token = this.servicioDeTokens.tokenGenerator(usuario.dataValues);
                    const refresh_token = this.servicioDeTokens.tokenGenerator(usuario.dataValues, '1w');
                    return res.status(OK).json({
                        access_token: token,
                        refresh_token: refresh_token
                    })
                }
            })(req,res);

    }


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

        const newToken = this.servicioDeTokens.tokenGenerator({
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
        const usuario = <Usuario>req.user;
        const user = {
            email: usuario.email,
            username: usuario.username,
            idusuario: usuario.idusuario
        };


        const token = this.servicioDeTokens.tokenGenerator(user);
        const refreshToken = this.servicioDeTokens.tokenGenerator(user, '1w');
        res.redirect(process.env.FRONTEND_URL + '/?access_token=' + token + '&refresh_token='+ refreshToken +
            '#/login/callback');
    }


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