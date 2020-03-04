import * as jwt from "jsonwebtoken";

export class TokenService {
    tokenGenerator(user: any, expiresTime: any = '1m') {
        user.password = '';
        const stringUser: string = <string><unknown>user;
        return jwt.sign(stringUser, process.env.JWT_SECRET || '', {
            expiresIn: expiresTime,
            subject: user.idusuario + ""// CAST TO STRING
        });
    }
}