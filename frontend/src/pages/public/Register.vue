<template>
  <q-page
    class="window-height window-width row justify-center items-center"
    style="background: linear-gradient(#8274C5, #5A4A9F);"
  >
    <div class="column q-pa-lg">
      <div class="row">

        <div class="column q-pa-lg">
          <div class="row">
            <q-card square class="shadow-24" style="width: 20vw">

              <q-card-section class="bg-deep-purple-7">

                <h4 class="text-h5 text-white q-my-md">Register now !</h4>
                <div class="absolute-bottom-right q-pr-md" style="transform: translateY(50%);">
                  <q-avatar square>
                    <img src="~assets/zanahoria.png">
                  </q-avatar>
                </div>

              </q-card-section>

              <q-card-section>
                <q-form class="q-px-sm q-pt-xl q-pb-lg">
                  <q-input outlined clearable v-model="person.email" type="email" label="Email" class="q-mb-sm">
                    <template v-slot:prepend>
                      <q-icon name="email"/>
                    </template>
                  </q-input>
                  <q-input outlined clearable v-model="person.username" type="username" label="Username"
                           class="q-mb-sm">
                    <template v-slot:prepend>
                      <q-icon name="person"/>
                    </template>
                  </q-input>
                  <q-input outlined clearable v-model="person.password" type="password" label="Password"
                           class="q-mb-lg">
                    <template v-slot:prepend>
                      <q-icon name="lock"/>
                    </template>
                  </q-input>
                  <q-input outlined clearable v-model="person.name" type="text" label="Name" class="q-mb-sm">
                    <template v-slot:prepend>
                      <q-icon name="person"/>
                    </template>
                  </q-input>
                  <q-input outlined clearable v-model="person.surname" type="text" label="Surname" class="">
                    <template v-slot:prepend>
                      <q-icon name="person"/>
                    </template>
                  </q-input>
                </q-form>
              </q-card-section>

              <q-card-actions class="q-px-lg flex flex-center">
                <q-btn size="lg" color="primary" class=" text-white q-mb-sm button-action" label="Register !"/>
                <q-btn unelevated size="md" color="secondary" class="text-white q-mb-sm button-action"
                       label="Have an account ?" to="/login"/>
              </q-card-actions>

            </q-card>
          </div>
        </div>
      </div>
    </div>
  </q-page>
</template>

<script>
  export default {
    name: "Register",
    data() {
      return {
        person: {
          email: '',
          password: '',
          username: '',
          name: '',
          surname: ''
        }
      }
    }
  }
</script>

<style scoped>
  .button-action {
    min-height: 6vh;
  }
</style>
