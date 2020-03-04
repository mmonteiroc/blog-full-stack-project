<template>
  <q-page
    class="window-height window-width row justify-center items-center bg-grey-3"
  >

            <q-card  class="shadow-4 col-11 col-sm-3" >

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
                  <q-input outlined clearable v-model="person.email" type="email" label="Email" class="q-mb-sm" placeholder="me@myaccount.org">
                    <template v-slot:prepend>
                      <q-icon name="email"/>
                    </template>
                  </q-input>
                  <q-input outlined clearable v-model="person.username" type="username" label="Username"
                           class="q-mb-sm" placeholder="jdoe_27">
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
                  <q-input outlined clearable v-model="person.name" type="text" label="Name" class="q-mb-sm" placeholder="Jane">
                    <template v-slot:prepend>
                      <q-icon name="person"/>
                    </template>
                  </q-input>
                  <q-input outlined clearable v-model="person.surname" type="text" label="Surname" class="" placeholder="Doe">
                    <template v-slot:prepend>
                      <q-icon name="person"/>
                    </template>
                  </q-input>
                </q-form>
              </q-card-section>

              <q-card-actions class="q-px-lg">
                <q-btn size="lg" color="primary" outline class="  q-mb-sm button-action full-width" label="Sign Up" @click="saveUser"/>
                <q-btn unelevated size="md" flat color="accent" class=" q-mb-sm  full-width"
                       label="Sign In" to="/login"/>

              </q-card-actions>

            </q-card>

  </q-page>
</template>

<script>
  export default {
    name: "Register",
    data() {
      return {
        person: {
          email: null,
          password: null,
          username: null,
          name: null,
          surname: null
        }
      }
    },
    methods:{
      async saveUser(){

        const response = await this.$axiosNode.post('/user',this.person);
        if (response.status!==200){
          this.$q.notify({
            color: 'negative',
            textColor: 'white',
            message: response.statusText,
            position: 'center',
            multiLine: 'true',
            timeout: 3000
          })
        }else if (response.status === 200){

          localStorage.setItem("refresh_token", response.data.refresh_token);
          localStorage.setItem("access_token", response.data.access_token);
          await this.$router.push('/private')
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
