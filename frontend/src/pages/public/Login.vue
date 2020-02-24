<template>
  <q-page
    class="window-height window-width row justify-center items-center"
    style="background: linear-gradient(#8274C5, #5A4A9F);"
  >

    <q-card class="shadow-24 q-pb-sm carta-login">
      <q-card-section class="bg-deep-purple-7">
        <h4 class="text-h5 text-white q-my-md">Sign In</h4>
        <div class="absolute-bottom-right q-pr-md" style="transform: translateY(50%);">
          <q-avatar square>
            <img src="~assets/zanahoria.png">
          </q-avatar>
        </div>
      </q-card-section>
      <q-card-section>
        <q-form class="q-px-sm q-pt-xl">
          <q-input square v-model="email" type="email" label="Email">
            <template v-slot:prepend>
              <q-icon name="email"/>
            </template>
          </q-input>
          <q-input square v-model="password" type="password" label="Password">
            <template v-slot:prepend>
              <q-icon name="lock"/>
            </template>
          </q-input>
            </q-form>
          </q-card-section>
          <q-card-section>
            <div class="text-center q-pa-md q-gutter-md">
              <q-btn round color="indigo-7" disable>
                <q-icon name="fab fa-facebook-f" size="1.2rem"/>
              </q-btn>
              <q-btn round color="red-8">
                <q-icon name="fab fa-google" size="1.2rem"/>
              </q-btn>
              <q-btn round color="light-blue-5" disable>
                <q-icon name="fab fa-twitter" size="1.2rem"/>
              </q-btn>
            </div>
          </q-card-section>
          <q-card-actions class="q-px-lg">
            <q-btn unelevated size="lg" color="primary" class="full-width text-white q-mb-sm" label="Sign In"/>
            <q-btn unelevated size="md" flat color="accent" class="full-width" label="Register now"
                   to="/register"/>
          </q-card-actions>
          <q-card-section class="text-center q-pa-sm">
            <p class="text-grey-6">Forgot your password?</p>
          </q-card-section>
        </q-card>


  </q-page>
</template>

<script>
  export default {
    name: 'Login',
    data() {
      return {
        email: '',
        username: '',
        password: ''
      }
    }
  }
</script>

<style>

</style>
