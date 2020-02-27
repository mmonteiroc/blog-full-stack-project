<template>
  <q-page class=" row q-pt-md q-pb-md">
    <div class="col-md-2 "></div> <!-- EMPTY SPACE, nos permite poner el siguiente div en centro-->
    <div class="col-md-8 col-sm-12 row">
      <div class="colum col-md-5 col-sm-5  col-12  q-pa-lg">

        <q-card  bordered class="my-card bg-indigo-13 text-white col-md-8 overflow-auto" style="height: 50vh">
          <q-card-section>
            <div class="text-h6">Numero de posts totales: {{totalPosts.length}}</div>
          </q-card-section>
          <q-separator inset/>

          <q-card-section class="q-pt-none">
            <q-list separator>
              <q-item v-for="persona in allUsers">
                <q-item-section avatar>
                  <q-avatar square>

                    <!-- TODO Cambiar este place holder por la foto actual del usuario -->
                    <img src="https://cdn.quasar.dev/img/boy-avatar.png">
                  </q-avatar>
                </q-item-section>
                <q-item-section style="font-size: 1.2em" class="flex">
                  {{persona.nombre}} tiene {{persona.posts.length}} posts
                </q-item-section>
              </q-item>

            </q-list>
          </q-card-section>
        </q-card>

        <q-card class="bg-primary q-mt-md flex-center flex" >
          <q-card-section class="text-h4">
            Total users {{allUsers.length}}
          </q-card-section>
        </q-card>
      </div>
        <div class="col-md-7 col-sm-7 col-12 column q-pa-lg">
        <q-table
          title="All posts"
          :data="totalPosts"
          :columns="columnsTable"
          row-key="name"

        />
      </div>


    </div>
  </q-page>
</template>

<script>
  export default {
    name: "Dashboard",
    data() {
      return {

        columnsTable: [
          {
            name: 'usuario',
            label: 'Usuario',
            align: 'left',
            field: row => row.usuario,
            sortable: true
          },
          {
            name: 'titulo',
            label: 'Titulo',
            field: 'tituloOriginal',
            sortable: true
          },
          {
            name: 'fecha',
            label: 'Creacion',
            field: 'creacion',
            sortable: true
          }
        ],
        totalPosts: [],
        allUsers: []
      }
    },
    async created() {

      const result = await this.$axiosJava.get('/p/user');
      result.data.forEach(usuario => {
        this.allUsers.push(
          {
            nombre: usuario.username,
            posts: usuario.posts
          }
        );
        usuario.posts.forEach(post => {
          post.usuario = usuario.username;
          this.totalPosts.push(post);
        });
      });

      console.log('posts: ', this.totalPosts)

    }
  }
</script>

<style scoped>

</style>
