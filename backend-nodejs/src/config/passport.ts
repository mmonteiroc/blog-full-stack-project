/*
* CONFIG TODAS LAS ESTRATEGIAS DISPONIBLES
* */
import * as passport from "passport";
import * as passportGoogle from 'passport-google-oauth2';

require('./enviroment');

const GoogleStrategy = passportGoogle.Strategy;

passport.serializeUser(function(user, done) {
    done(null, user);
});

passport.deserializeUser(function(user, done) {
    done(null, user);
});

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


        done(null,{
            email: emailsAvaiable,
            idusuario:1
        });

    }
));