<template>
  <q-page
    class="window-height window-width row justify-center items-center bg-grey-3"
  >

    <q-card class="shadow-4 q-pb-sm col-11 col-sm-3">
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
          <q-input square v-model="user.email" type="email" label="Email">
            <template v-slot:prepend>
              <q-icon name="email"/>
            </template>
          </q-input>
          <q-input square v-model="user.password" type="password" label="Password">
            <template v-slot:prepend>
              <q-icon name="lock"/>
            </template>
          </q-input>
        </q-form>
      </q-card-section>
      <q-card-section>
        <div class="text-center q-pa-md q-gutter-md">
          <a :href="urlLoginOauthGoogle" style="text-decoration: none">
            <q-btn round color="red-8">
              <q-icon name="fab fa-google" color="white" size="1.2rem"/>
            </q-btn>
          </a>

        </div>
      </q-card-section>
      <q-card-actions class="q-px-lg">
        <q-btn unelevated size="lg" color="primary" class="full-width text-white q-mb-sm" label="Sign In"
               @click="validateLogin"/>
        <q-btn unelevated size="md" flat color="accent" class="full-width" label="Sign Up"
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
        urlLoginOauthGoogle: process.env.API_NODE + '/login/google',
        user: {
          email: '',
          password: ''
        }
      }
    },
    methods: {
      async validateLogin() {
        const response = await this.$axiosNode.post('/login', this.user);
        localStorage.setItem('token', response.data.token);
        this.$router.push('/private')
      }
    }
  }
</script>

<style>

</style>
