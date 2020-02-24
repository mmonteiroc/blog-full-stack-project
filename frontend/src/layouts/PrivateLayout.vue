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
      <q-list>
        <q-item-label header class="flex flex-center" style="font-size: 2em">Menu</q-item-label>
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

  </q-layout>
</template>

<script>

  export default {

    data() {
      return {
        value: this.$q.dark.isActive,
        left: false,
        linksMenu: [
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
          },
          {
            title: 'Disconect',
            icon: 'highlight_off',
            link: '/logout'
          }
        ]
      }
    },
    methods: {
      changeColor() {
        this.$q.dark.toggle();
      }
    }
  }
</script>
