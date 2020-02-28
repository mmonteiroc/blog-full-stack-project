import {UsuarioRepository} from "../repository/usuarioRepository";
import * as encriptador from 'bcrypt';

export class UsuarioService {
    private repo: UsuarioRepository;


    constructor() {
        this.repo = new UsuarioRepository();
    }

    async findAllUsers() {
        return this.repo.findAll();
    }


    async findByEmail(email: string) {
        return await this.repo.findByEmail(email);
    }

    async createUser(usuario: any) {
        /*
        * Antes de añadirlo, la password la cifraremos
        *
        * */

        const password = usuario.password;
        const saltRounds = 10; // TODO preguntar que es esto de salt rounds

        usuario.password = await encriptador.hash(password, saltRounds);
        await this.repo.create(usuario)
    }


    /*
    * Este metodo valida que el login local sea el correcto
    *
    * Tenemos que recibir un objeto Usuario
    * el cual como minimo recibamos las
    * siguientes propiedades
    *  - authMode
    *  - email
    *  - password
    * */
    async validateUser(usuarioToCheck: any) {
        let user = await this.repo.findByEmail(usuarioToCheck.email);
        if (user === null || !user) return false;
        user = user.dataValues;


        const validacionPassword = await encriptador.compare(usuarioToCheck.password, user.password);

        return validacionPassword && user.authMode === usuarioToCheck.authMode;
    }

}