/*
* CONFIG TODAS LAS ESTRATEGIAS DISPONIBLES
* */
import * as passport from "passport";
import * as passportGoogle from 'passport-google-oauth2';
import {UsuarioService} from "../service/usuarioService";
import {Usuario} from "../model/Usuario";

require('./enviroment');

/*
* Estrategias
* */
const GoogleStrategy = passportGoogle.Strategy;

passport.serializeUser(function (user, done) {
    done(null, user);
});

passport.deserializeUser(function (user, done) {
    done(null, user);
});

/*
* Implementacion de las estrategias
* */
passport.use(new GoogleStrategy(
    {
        clientID: process.env.GOOGLE_CLIENT_ID || '',
        clientSecret: process.env.GOOGLE_CLIENT_SECRET || '',
        callbackURL: process.env.GOOGLE_CALLBACK_URL || '',
        passReqToCallback: true
    },
    async function (request: any, accessToken: string, refreshToken: string, profile: any, done: any) {


        const email = profile.email;
        let result;
        const usuarioService = new UsuarioService();
        result = <any>await usuarioService.findByEmail(email);

        if (result === null) {
            /*
            * Usuario no encontrado
            * Creamos nuevo usuario
            * Lo buscamos y lo devolvemos
            * el controlador creara el token para dicho usuario
            * */

            await usuarioService.createUser({
                nombre: profile.given_name,
                email: profile.email,
                username: profile.display_name || profile.given_name || 'Modifica tu username',
                password: '',
                authMode: 'google',
                apellidos: profile.family_name
            });
            result = <any>await usuarioService.findByEmail(email);
        }


        const user = result.dataValues;
        if (user.authMode != 'google'){
            done(null, false) // Enviar al cb de failure
        }else {
            // PREGUNTAR POR QUE ESTE NULL DEBERIA SER UN ERR
            done(null, user);
        }

    }
))
;