<template>
  <q-page class="flex flex-center">
    <div>
      <q-input rounded outlined v-model="text" label="Rounded outlined"/>
      <q-input rounded outlined v-model="text" label="Rounded outlined"/>
      <q-btn push color="primary" label="Push" class="q-ml-"/>
    </div>
  </q-page>
</template>

<script>
  export default {
    name: "Login"
  }
</script>

<style scoped>

</style>
