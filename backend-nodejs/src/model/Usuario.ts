import {Column, Model, Table} from 'sequelize-typescript';

@Table({
    tableName: "usuario",
    createdAt: false,
    updatedAt: false,
})
export class Usuario extends Model<Usuario> {

    @Column({
        field: 'idusuario',
        primaryKey: true,
        type: 'number'
    })
    idusuario: number | undefined;

    @Column({
        field: 'username',
        type: 'string'
    })
    username: string | undefined;

    @Column({
        field: 'email',
        type: 'string'
    })
    email: string | undefined;

    @Column({
        field: 'password',
        type: 'string'
    })
    password: string | undefined;

    @Column({
        field: 'nombre',
        type: 'string'
    })
    nombre: string | undefined;

    @Column({
        field: 'apellidos',
        type: 'string'
    })
    apellidos: string | undefined;

    @Column({
        field: 'auth_mode',
        type: 'string'
    })
    authMode: string | undefined;
}