<template >
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
                  <q-btn color="grey-7"  flat label="Share" style=""/>
              </div>
            </div>
          </q-card-section>


          <q-card-section class="overflow-auto">
            {{item.contenido}}
          </q-card-section>


        </q-card>
      </li>
    </ul>

    <q-page-sticky position="top-right" :offset="[100, 18]">
      <q-input standout="bg-teal text-white"    clearable
               clear-icon="close" @click.stop="postsFiltered=allPosts" v-model="filtro" @input="filter" label="Filtrar posts" />
    </q-page-sticky>


  </q-page>
</template>

<script>

  export default {
    name: "Posts",
    data() {
      return {
        filtro:'',
        expanded: false,
        allPosts: [],
        postsFiltered: []
      }
    },
    async created() {
      /*
      * Inicializamos todos los posts de nuestro blog
      * */
      const posts = await this.$axiosJava.get('/post');
      if (posts) {
        posts.data.map(post => {
          this.allPosts.push({
            titulo: post.tituloTraducido,
            fecha: post.creacion,
            contenido: post.contenidoTraducido
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
        this.postsFiltered = this.allPosts.filter(post=>{
          return post.titulo.toLowerCase().includes(this.filtro.toLowerCase()) || post.contenido.toLowerCase().includes(this.filtro.toLowerCase())
        })
      }
    }
  }
</script>

<style scoped>


  .gradient-background {
    background: linear-gradient(to right bottom, #ffffff, #fbfbfb, #f6f6f6, #f2f2f2, #eeeeee);
  }

  ul{
    margin: 0;
    padding:  0;
  }
  ul li {
    list-style: none;
  }
</style>
