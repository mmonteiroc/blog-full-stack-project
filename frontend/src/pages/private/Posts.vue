<template>
  <q-page class=" gradient-background row flex-center">
    <ul class="row col-12 col-md-6 flex-center">
      <li v-for="item in postsFiltered" class="col-12 col-md-6 col-sm-8 q-pa-sm">
        <q-card flat bordered class="my-card bg-grey-1 ">
          <q-card-section>
            <div class="row items-center no-wrap">
              <div class="col">
                <div class="text-h6">{{item.titulo}}</div>
                <div class="text-subtitle2">{{item.fecha}}</div>
              </div>

              <div class="col-auto">
                <q-btn color="grey-7" flat label="Share" style=""/>
              </div>
            </div>
          </q-card-section>


          <q-card-section class="overflow-auto">
            {{item.contenido}}
          </q-card-section>
          <q-separator/>

          <q-card-actions align="right">
            <q-btn flat :to="item.urlUpdate">Update</q-btn>
            <q-btn flat color="accent" @click="confirmRemove(item)">Delete
              <q-icon name="delete"/>
            </q-btn>
          </q-card-actions>

        </q-card>
      </li>
    </ul>

    <q-page-sticky position="top-right" :offset="[100, 18]">
      <q-input standout="bg-teal text-white" clearable
               clear-icon="close" @click.stop="postsFiltered=allPosts" v-model="filtro" @input="filter"
               label="Filtrar posts"/>
    </q-page-sticky>


  </q-page>
</template>

<script>

  export default {
    name: "Posts",
    data() {
      return {
        filtro: '',
        expanded: false,
        allPosts: [],
        postsFiltered: []
      }
    },
    async created() {
      /*
      * Inicializamos todos los posts de nuestro blog
      * */
      const posts = await this.$axiosJava.get('/p/user/my/posts');
      if (posts) {
        posts.data.map(post => {
          this.allPosts.push({
            id: post.idpost,
            titulo: post.tituloTraducido,
            fecha: post.creacion,
            contenido: post.contenidoTraducido,
            urlUpdate: "/private/update/" + post.idpost
          })
        });
        /*
        * Invertimos los posts ya que nos vienen ordenados de antiguo a moderno
        * */
        this.allPosts = this.allPosts.reverse();
        this.postsFiltered = this.allPosts;
      }
    },
    methods: {
      filter() {
        this.postsFiltered = this.allPosts.filter(post => {
          return post.titulo.toLowerCase().includes(this.filtro.toLowerCase()) || post.contenido.toLowerCase().includes(this.filtro.toLowerCase())
        })
      },
      confirmRemove(post){
        this.$q.dialog({
          title: '¿Seguro?',
          message: 'Estas seguro de borrar este post?',
          cancel: true,
          persistent: true
        }).onOk(() => {
          this.remove(post)
        })
      },
      async remove(post) {
        await this.$axiosJava.delete("/p/post/" + post.id);
        this.allPosts = this.allPosts.filter(item => {
          return item.id !== post.id;
        });
        this.postsFiltered = this.allPosts;
      }
    }
  }
</script>

<style scoped>


  .gradient-background {
    background: linear-gradient(to right bottom, #ffffff, #fbfbfb, #f6f6f6, #f2f2f2, #eeeeee);
  }

  ul {
    margin: 0;
    padding: 0;
  }

  ul li {
    list-style: none;
  }
</style>
