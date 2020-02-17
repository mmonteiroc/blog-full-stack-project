<template>
  <q-page class="flex flex-center">
    <img alt="Quasar logo" src="~assets/quasar-logo-full.svg">

    <div class="q-pa-md">
      <q-date
        v-model="date"
        landscape
      />
    </div>
  </q-page>
</template>

<script>
  export default {
    name: 'PageIndex',
    data () {
      return {
        date: '2019/02/01'
      }
    }
  }
</script>
