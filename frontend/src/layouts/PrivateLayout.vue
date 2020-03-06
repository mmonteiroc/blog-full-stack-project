<template>
  <q-layout view="hhr Lpr lfr">

    <q-header class="bg-primary text-white">
      <q-toolbar>
        <q-btn dense flat round icon="menu" @click="left = !left"/>

        <q-toolbar-title>
          <q-avatar square>
            <img src="~assets/zanahoria.png">
          </q-avatar>
          Es liceu Solutions
        </q-toolbar-title>
        <q-btn color="indigo-10" icon="settings" round flat>
          <q-menu>
            <q-list style="min-width: 100px">
              <q-item v-close-popup class="flex flex-center">
                <q-icon name="wb_sunny"/>

                <q-toggle
                  v-model="value"
                  color="indigo-10"
                  @input="changeColor()"
                />
                <q-icon name="nights_stay"/>

              </q-item>

            </q-list>
          </q-menu>
        </q-btn>
      </q-toolbar>

    </q-header>

    <q-drawer v-model="left" side="left" overlay bordered>
      <!-- drawer content -->
      <div class="row q-mt-md">
        <div class="col-4 flex flex-center">
          <q-avatar rounded size="50px">
            <img :src="user.imgUrl" alt="">
          </q-avatar>
        </div>

        <div class="col-8">
          <div class="row flex-center">
            <span class="text-h5">{{user.name}}</span>
          </div>
          <q-separator class="q-mt-sm q-mb-sm"/>
          <div class="row flex-center justify-evenly q-mb-sm">
            <q-btn outline icon="person" color="primary" to="/private/account">
              <q-tooltip content-class="bg-indigo" :delay="300" :offset="[10, 10]">
                Account
              </q-tooltip>
            </q-btn>

            <q-btn icon="lock" color="accent" @click="confirmDisconnect = true" outline>
              <q-tooltip content-class="bg-red" :delay="300" :offset="[10, 10]">
                Log Out
              </q-tooltip>
            </q-btn>
          </div>

        </div>
      </div>

      <q-list>
        <q-separator/>
        <q-item clickable v-ripple v-for="link in linksMenu" :to="link.link">
          <q-item-section avatar top>
            <q-avatar :icon="link.icon" color="light-blue-6" text-color="white"/>
          </q-item-section>
          <q-item-section>
            <q-item-label lines="1">{{link.title}}</q-item-label>
            <q-item-label caption>{{link.caption}}</q-item-label>
          </q-item-section>
        </q-item>
      </q-list>
    </q-drawer>

    <q-page-container>
      <router-view/>
    </q-page-container>

    <q-dialog v-model="confirmDisconnect" persistent>
      <q-card>
        <q-card-section class="row items-center">
          <q-avatar rounded size="50px">
            <img :src="user.imgUrl" alt="">
          </q-avatar>
          <span class="q-ml-sm">Estas seguro que deseas desconectarte?</span>
        </q-card-section>

        <q-card-actions align="right">
          <q-btn flat label="Cancel" color="primary" v-close-popup />
          <q-btn flat label="Desconectar" color="primary" v-close-popup to="/" @click="disconnect"/>
        </q-card-actions>
      </q-card>
    </q-dialog>
  </q-layout>
</template>

<script>

  export default {
    async created() {
      const data = await this.$axiosJava.get('/p/me');
      this.user.name = data.data.username;

    },
    data() {
      return {
        confirmDisconnect: false,
        value: this.$q.dark.isActive,
        left: false,


        user:{
          name: "",
          imgUrl: 'https://cdn.quasar.dev/img/boy-avatar.png'
        },
        linksMenu: [
          {
            title: 'Dashboard',
            caption: '',
            icon: 'fas fa-tachometer-alt',
            link: '/private/dashboard'
          },
          {
            title: 'Posts',
            caption: 'Acceder a tus posts',
            icon: 'desktop_windows',
            link: '/private/posts'
          },
          {
            title: 'Formulario',
            caption: 'Añadir un nuevo post',
            icon: 'create',
            link: '/private/create'
          },
          {
            title: 'Caloreister 3000',
            caption: 'Calculadora de calorias ',
            icon: 'keyboard',
            link: '/private/calculadora'
          }
        ]
      }
    },
    methods: {
      changeColor() {
        this.$q.dark.toggle();
      },
      disconnect(){
        localStorage.removeItem('access_token');
        localStorage.removeItem('refresh_token');
      }
    }
  }
</script>
