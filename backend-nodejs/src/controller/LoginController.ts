import {OK} from 'http-status-codes';
import {Controller, Delete, Get, Middleware, Post, Put} from '@overnightjs/core';
import {Request, Response} from 'express';
import {Logger} from '@overnightjs/logger';
import * as passport from "passport";
import * as jwt from 'jsonwebtoken';
import {Usuario} from "../model/Usuario";

require('../config/passport');
require('../config/enviroment');

@Controller('login')
export class LoginController {

    @Post()
    private validateLogin(req: Request, res: Response) {
        Logger.Info(req.body, true);
        return res.status(OK).json({
            /*
            * TODO --> validar que el usuario que recibimos es quien
            *  dice ser y crear un token que enviar (NO DE MANERA ESTATICA)
            * */
            token: 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZHVzdWFyaW8iOjEsInN1YiI6IjEyMzQ1Njc4OTAiLCJuYW1lIjoiSm9obiBEb2UiLCJpYXQiOjE1MTYyMzkwMjJ9.xoNb_whYqKTtT5BJWF0NMWg1WXztGcxwdprV7clhpx4'
        });
    }

    @Put('update-user')
    private update(req: Request, res: Response) {
        Logger.Info(req.body);
        return res.status(OK).json({
            message: 'update_called',
        });
    }

    @Delete('delete/:id')
    private delete(req: Request, res: Response) {
        Logger.Info(req.params, true);
        return res.status(OK).json({
            message: 'delete_called',
        });
    }

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

        const stringUser:string =<string><unknown>user;
        const token = jwt.sign(stringUser, process.env.JWT_SECRET || '', {
            expiresIn: '1d',
            subject: 1+""// CAST TO STRING
        });
        res.redirect(process.env.FRONTEND_URL + '/?access_token=' + token + '#/login/callback');


    }

    @Get('gg/failure')
    private async failure(req: Request, res: Response) {
        console.log("GOOGLE HA IDO MUY MAL");
        console.log("REQUEST USER:", req);
        res.end()
    }
}