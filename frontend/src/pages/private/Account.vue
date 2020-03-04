<template>

  <q-page class="row q-pa-lg   ">
    <div class="col-lg-2 col-md-0 col-sm-0 col-o"></div>


    <div class="col-lg-2 col-md-3 col-sm-12 col-12 q-pa-md">

      <q-card class=" column  content-center q-ma-sm">
        <q-card-section class="flex flex-center">
          <q-avatar size="110px" text-color="white">
            <img src="https://cdn.quasar.dev/img/avatar.png">
            <!-- TODO en el caso de añadir la opcion de fotos en el perfil, aqui ira la foto del usuario-->
          </q-avatar>
        </q-card-section>
        <q-card-section>
          <div class="text-center column ">
            Hello, {{usuario.username}}
          </div>
        </q-card-section>

      </q-card>

    </div>
    <div class="col-lg-6 col-md-9 col-sm-12 col-12 q-pa-md">
      <q-card class=" q-ma-sm">
        <q-card-section class="flex">
          <div class="text-h6">Personal info</div>
          <q-space/>
          <q-icon name="info" color="secondary" class="cursor-pointer" style="font-size: 2em;">
            <q-tooltip content-class="bg-purple" content-style="font-size: 13px" :offset="[10, 10]">
              Para editar la informacion, click en el campo que quieras modificar y despues pulsa 'Guardar'
            </q-tooltip>
          </q-icon>

        </q-card-section>
        <q-card-section class="">
          <div class="row">
            <span class="text-grey-6 col-2 q-pa-sm text-right">Username</span>
            <span class="col-2 q-pa-sm">
              {{usuario.username}}
              <q-popup-edit v-model="usuario.username" :cover="true">
                <q-input color="accent" v-model="usuario.username" dense autofocus/>
              </q-popup-edit>
            </span>
          </div>
          <div class="row">
            <span class="text-grey-6 col-2 q-pa-sm text-right">Nombre</span>
            <span class="col-2 q-pa-sm">
              {{usuario.nombre}}
              <q-popup-edit v-model="usuario.nombre" :cover="true">
                <q-input color="accent" v-model="usuario.nombre" dense autofocus/>
              </q-popup-edit>
            </span>
          </div>
          <div class="row">
            <span class="text-grey-6 col-2 q-pa-sm text-right">Apellidos</span>
            <span class="col-2 q-pa-sm">
              {{usuario.apellidos}}
              <q-popup-edit v-model="usuario.apellidos" :cover="true">
                  <q-input color="accent" v-model="usuario.apellidos" dense autofocus/>
              </q-popup-edit>
            </span>
          </div>
          <div class="row">
            <span class="text-grey-6 col-2 q-pa-sm text-right">Email</span>
            <span class="col-2 q-pa-sm">{{usuario.email}}
              <q-tooltip content-class="bg-red-8" anchor="center right" self="center left" transition-show="flip-right"
                         transition-hide="flip-left">
               Email no se puede modificar
              </q-tooltip>
            </span>
          </div>

        </q-card-section>
      </q-card>

      <q-card class=" q-ma-sm" v-if="usuario.authMode==='local'">
        <q-card-section class="flex">
          <div class="text-h6">Seguridad</div>
          <q-space/>
          <q-icon name="info" color="secondary" class="cursor-pointer" style="font-size: 2em;">
            <q-tooltip content-class="bg-purple" content-style="font-size: 13px" :offset="[10, 10]">
              Para editar la informacion, click en el campo que quieras modificar y despues pulsa 'Guardar'
            </q-tooltip>
          </q-icon>
        </q-card-section>
        <q-card-section class="">
          <div class="row">
            <span class="text-grey-6 col-2 q-pa-sm">Password</span>
            <span class="col-2 q-pa-sm text-weight-bolder cursor-pointer" @click="modifyPassword">
                ·············
              <q-popup-edit v-model="usuario.oldPassword" :cover="true" buttons>
                <q-input color="accent" v-model="usuario.oldPassword" dense autofocus label="old password"/>
                <q-input color="accent" v-model="usuario.newPassword" dense autofocus label="new password"/>
                <q-input color="accent" v-model="usuario.newPassword1" dense autofocus label="repeat new password"/>
              </q-popup-edit>
            </span>
            <!-- AQUI NUNCA ESTARA LA PASSWORD, esto solo es visual-->
          </div>


        </q-card-section>
      </q-card>

    </div>

  </q-page>

</template>

<script>
  export default {
    name: "Account",
    async created() {
      const data = await this.$axiosJava.get('/p/me');
      console.log(data.data);
      this.usuario.nombre = data.data.nombre;
      this.usuario.apellidos = data.data.apellidos;
      this.usuario.username = data.data.username;
      this.usuario.email = data.data.email;
      this.usuario.authMode = data.data.authMode;
    },
    data() {
      return {
        usuario: {
          nombre: '',
          apellidos: '',
          username: '',
          email: '', // No se puede modificar, con el que te registrar, te quedas
          authMode: '',
          newPassword:'',
          newPassword1:'',
          oldPassword:''
        },
      }
    },
    methods: {
      modifyPassword(){

      }
    }
  }
</script>

<style scoped>

</style>
