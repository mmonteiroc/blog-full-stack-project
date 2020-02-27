/*
* CONFIG TODAS LAS ESTRATEGIAS DISPONIBLES
* */
import * as passport from "passport";
import * as passportGoogle from 'passport-google-oauth2';
import {Usuario} from '../model/Usuario';

require('./enviroment');

/*
* Estrategias
* */
const GoogleStrategy = passportGoogle.Strategy;

passport.serializeUser(function(user, done) {
    done(null, user);
});

passport.deserializeUser(function(user, done) {
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
    async function (request:any,accessToken:string, refreshToken:string, profile:any,done:any) {

        const emailsAvaiable = profile.emails.map((email:any)=>{
            if (email.verified || profile.email_verified){
                return email.value;
            }
        });

        /*
        * TODO buscar en DDBB y si no existe crear nuevo usuario
        * */

        // PLACE HOLDER, este usuario nos ha de venir de la BBDD
        const user = new Usuario(1, profile.name.givenName,profile.email,'',profile.name.givenName,profile.name.familyName,"google");


        // PREGUNTAR POR QUE ESTE NULL DEBERIA SER UN ERR
        done(null,user);
    }
));