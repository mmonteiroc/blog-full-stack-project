<template>

  <q-page class="row q-pa-lg   ">
    <div class="col-lg-2 col-md-0 col-sm-0 col-o"></div>


    <div class="col-lg-2 col-md-3 col-sm-12 col-12 q-pa-md">

      <q-card class="column  content-center q-ma-sm">
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
      <q-btn label="Eliminar cuenta" class="q-ma-sm full-width" color="accent" outline @click="borrarCuenta= true"/>

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
        <q-separator/>

        <q-card-section class="">
          <div class="row items-center">
            <span class="text-grey-6 col-4 col-md-2 col-sm-3 col-lg-2 q-pa-sm text-right">Username</span>
            <span class="col-6 col-md-4 col-sm-5 col-lg-4 q-pa-sm">
              <q-input outlined dense v-model="usuario.username" color="secondary"/>

            </span>
          </div>
          <div class="row items-center">
            <span class="text-grey-6 col-4 col-md-2 col-sm-3 col-lg-2 q-pa-sm text-right">Nombre</span>
            <span class="col-6 col-md-4 col-sm-5 col-lg-4 q-pa-sm justify-center content-center">
              <q-input outlined dense v-model="usuario.nombre" color="secondary"/>
            </span>
          </div>
          <div class="row items-center">
            <span class="text-grey-6 col-4 col-md-2 col-sm-3 col-lg-2 q-pa-sm text-right">Apellidos</span>
            <span class="col-6 col-md-4 col-sm-5 col-lg-4 q-pa-sm">
              <q-input outlined dense v-model="usuario.apellidos" color="secondary"/>
            </span>
          </div>
          <div class="row items-center">
            <span class="text-grey-6 col-4 col-md-2 col-sm-3 col-lg-2 q-pa-sm text-right">Email</span>
            <span class="col-6 col-md-4 col-sm-5 col-lg-4 q-pa-sm">{{usuario.email}}
              <q-tooltip content-class="bg-red-8" anchor="center right" self="center left" transition-show="flip-right"
                         transition-hide="flip-left">
               Email no se puede modificar
              </q-tooltip>
            </span>
          </div>
        </q-card-section>

        <q-card-actions align="right" class="q-pr-md q-pb-md">
          <q-btn outline color="green-7" label="Guardar cambios" @click="saveProfile"/>
        </q-card-actions>
      </q-card>

      <q-card class="q-ma-sm q-mt-md" v-if="usuario.authMode==='local'">
        <q-card-section class="flex">
          <div class="text-h6">Seguridad</div>
          <q-space/>
          <q-icon name="info" color="secondary" class="cursor-pointer" style="font-size: 2em;">
            <q-tooltip content-class="bg-purple" content-style="font-size: 13px" :offset="[10, 10]">
              Para editar la informacion, click en el campo que quieras modificar y despues pulsa 'Guardar'
            </q-tooltip>
          </q-icon>
        </q-card-section>
        <q-separator/>

        <q-card-section class="">
          <div class="row items-center">
            <span class="text-grey-6 col-4 col-md-2 col-sm-3 col-lg-2 q-pa-sm text-right">Old password</span>
            <span class="col-6 col-md-4 col-sm-5 col-lg-4 q-pa-sm">
              <q-input outlined dense v-model="usuario.oldPassword" color="secondary"
                       :rules="[val => !!val || 'Field is required']"/>
            </span>
          </div>
          <div class="row items-center">
            <span class="text-grey-6 col-4 col-md-2 col-sm-3 col-lg-2 q-pa-sm text-right">New password</span>
            <span class="col-6 col-md-4 col-sm-5 col-lg-4 q-pa-sm">
              <q-input outlined dense v-model="usuario.newPassword" color="secondary"
              />
            </span>
          </div>
          <div class="row items-center">
            <span class="text-grey-6 col-4 col-md-2 col-sm-3 col-lg-2 q-pa-sm text-right">New password</span>
            <span class="col-6 col-md-4 col-sm-5 col-lg-4 q-pa-sm">
              <q-input outlined dense v-model="usuario.newPassword1" color="secondary"
                       :rules="[val => usuario.newPassword1 === usuario.newPassword || 'Passwd not match']"/>
            </span>
          </div>
        </q-card-section>
        <q-card-actions align="right" class="q-pr-md q-pb-md">
          <q-btn outline color="green-7" label="Modificar passwd" @click="saveSecurity"/>
        </q-card-actions>
      </q-card>

    </div>

    <q-dialog v-model="borrarCuenta">
      <q-card class="my-card">

        <q-card-section class="q-pt-sm">
          <div class="text-h6">
            Eliminar cuenta
          </div>
          <div class="text-subtitle2">
            Introduce tu email para eliminar la cuenta
          </div>
        </q-card-section>
        <q-separator/>

        <q-card-section class="">
          <q-input outlined dense v-model="emailToDelete"
                   :rules="[
                     val => $v.emailValidacion.required || 'Campo requerido'
                     ]" label="Email"/>

          <div class="q-pt-md">
            ¿Estas seguro de borrar tu cuenta?
            Esta accion <span class="text-negative text-weight-bolder">no se puede deshacer</span>.
            Si en el futuro quieres volver a utilizar esta aplicacion, tendras que crearte otra cuenta
          </div>
        </q-card-section>

        <q-separator/>

        <q-card-actions align="right">
          <q-btn v-close-popup color="primary" label="Cancel"/>

          <q-btn v-close-popup flat disable color="negative" label="Borrar cuenta"
                 v-if="usuario.email !== emailToDelete"/>
          <q-btn v-close-popup color="negative" label="Borrar cuenta" v-if="usuario.email === emailToDelete"
                 @click="deleteAccount"/>
        </q-card-actions>
      </q-card>
    </q-dialog>

    <q-ajax-bar
      ref="bar"
      position="bottom"
      color="positive"
      size="6px"
      skip-hijack
    />
  </q-page>

</template>

<script>
  import {required} from 'vuelidate/lib/validators';

  export default {
    name: "Account",
    async created() {
      const data = await this.$axiosJava.get('/p/me');
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
          newPassword: '',
          newPassword1: '',
          oldPassword: ''
        },
        borrarCuenta: false,
        emailToDelete: null,
      }
    },
    methods: {
      async saveProfile() {
        const bar = this.$refs.bar;

        bar.start();
        const response = await this.$axiosJava.put('/p/user/perfil', this.usuario);

        bar.stop();
        if (response.status !== 200) {
          this.$q.notify({
            color: 'negative',
            textColor: 'black',
            message: response.data,
            position: 'bottom',
            timeout: 3000
          })
        } else {
          this.$q.notify({
            color: 'primary',
            textColor: 'black',
            message: 'Cambios guardado correctamente',
            position: 'bottom',
            timeout: 3000
          });
        }

      },
      async saveSecurity() {
        const bar = this.$refs.bar;

        bar.start();
        const response = await this.$axiosNode.put('/user/security', this.usuario);

        bar.stop();
        if (response.status !== 200) {
          this.$q.notify({
            color: 'negative',
            textColor: 'black',
            message: response.statusText,
            position: 'bottom',
            timeout: 3000
          })
        } else {
          this.$q.notify({
            color: 'primary',
            textColor: 'black',
            message: 'Cambios guardado correctamente',
            position: 'bottom',
            timeout: 3000
          })
        }
      },
      async deleteAccount() {
        /*
        * Fetch api borrar account
        * */
        const response = await this.$axiosJava.delete('/p/user/');
        console.log(response);
        // Placeholder
        if (response.status === 200) {
          // OK SE HA BORRADO
          // QUITAMOS TODOS LOS TOKENS
          localStorage.removeItem('access_token');
          localStorage.removeItem('refresh_token');
          // REENVIAMOS A LOGIN
          await this.$router.push('/login')
        } else {
          this.$q.notify({
            color: 'negative',
            textColor: 'black',
            message: response.data,
            position: 'bottom',
            timeout: 3000
          })
        }
      }
    },
    validations: {
      emailValidacion: {
        required
      }
    }
  }
</script>

<style scoped>

</style>
