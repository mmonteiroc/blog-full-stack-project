

/*
* TODO --> IMPLEMENTAR 'ORM' cuando lo sepamos hacer
* */
export class Usuario {


    private _idusuario: number;
    private _username: string;
    private _email: string;
    private _password: string;
    private _nombre: string;
    private _apellidos: string;
    private _authMode: string;

    constructor(idusuario: number, username: string, email: string, password: string, nombre: string, apellidos: string, authMode: string) {
        this._idusuario = idusuario;
        this._username = username;
        this._email = email;
        this._password = password;
        this._nombre = nombre;
        this._apellidos = apellidos;
        this._authMode = authMode;
    }

    get idusuario(): number {
        return this._idusuario;
    }

    set idusuario(value: number) {
        this._idusuario = value;
    }

    get username(): string {
        return this._username;
    }

    set username(value: string) {
        this._username = value;
    }

    get email(): string {
        return this._email;
    }

    set email(value: string) {
        this._email = value;
    }

    get password(): string {
        return this._password;
    }

    set password(value: string) {
        this._password = value;
    }

    get nombre(): string {
        return this._nombre;
    }

    set nombre(value: string) {
        this._nombre = value;
    }

    get apellidos(): string {
        return this._apellidos;
    }

    set apellidos(value: string) {
        this._apellidos = value;
    }

    get authMode(): string {
        return this._authMode;
    }

    set authMode(value: string) {
        this._authMode = value;
    }
}