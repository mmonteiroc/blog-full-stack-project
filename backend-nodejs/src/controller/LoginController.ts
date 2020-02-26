import { OK, BAD_REQUEST, UNAUTHORIZED } from 'http-status-codes';
import { Controller, Middleware, Get, Post, Put, Delete } from '@overnightjs/core';
import { Request, Response } from 'express';
import { Logger } from '@overnightjs/logger';

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

}