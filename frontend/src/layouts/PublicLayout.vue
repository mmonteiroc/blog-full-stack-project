<template>
  <q-layout view="hHh lpR fFf">

    <q-header bordered class="bg-primary text-white">
      <q-toolbar>
        <q-toolbar-title>
          <q-avatar square>
            <img src="~assets/zanahoria.png">
          </q-avatar>
          Es liceu - Restaurant
        </q-toolbar-title>
        <q-btn to="/login" label="Login" class="bg-dark"/>
      </q-toolbar>
    </q-header>

    <q-page-container>
      <router-view/>
    </q-page-container>

  </q-layout>
</template>

<script>
  export default {
    data() {
      return {}
    }
  }
</script>
