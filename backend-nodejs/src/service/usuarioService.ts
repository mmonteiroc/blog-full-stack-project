import {UsuarioRepository} from "../repository/usuarioRepository";
import {Usuario} from "../model/Usuario";

export class UsuarioService {
    private repo: UsuarioRepository;


    constructor() {
        this.repo = new UsuarioRepository();
    }

    async findAllUsers() {
        return await this.repo.findAll();
    }


    async findByEmail(email: string) {
        return await this.repo.findByEmail(email);
    }

    async createUser(usuario: any) {

        await this.repo.create(usuario)
    }
}