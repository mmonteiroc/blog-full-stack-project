<template>
  <q-page class="flex flex-center gradient-background">
    <ul class="lista">
      <li v-for="post in posts">
        <q-card flat bordered class="post bg-white q-mb-sm">
          <q-card-section>
            <div class="row items-center no-wrap">
              <div class="col">
                <div class="text-h6">{{ post.titulo }}</div>
                <div class="text-subtitle2">{{ post.fecha }}</div>
              </div>

              <div class="col-auto">
                <q-btn color="teal-4" flat rounded label="Share"/>
              </div>
            </div>
          </q-card-section>

          <q-card-section>
            {{ post.contenido }}
          </q-card-section>

          <q-separator/>
          <!--Mostrar acciones solo en parte privada-->
          <!--          <q-card-actions>-->
          <!--            <q-btn flat>Action 1</q-btn>-->
          <!--            <q-btn flat>Action 2</q-btn>-->
          <!--          </q-card-actions>-->
        </q-card>
      </li>
    </ul>
  </q-page>
</template>

<script>
  const posts = [{
    titulo: "Post 1",
    fecha: "24/02/2020",
    contenido: "Cointenido del post 1 sdfgksudghfakhjgsdkfhjgadkjhagskdjhfgadskjhfgahkjdsgfkj",
    contenidoCaption: "..."
  }, {
    titulo: "Post 1",
    fecha: "24/02/2020",
    contenido: "Cointenido del post 1 sdfgksudghfakhjgsdkfhjgadkjhagskdjhfgadskjhfgahkjdsgfkj" +
      "Cointenido del post 1 sdfgksudghfakhjgsdkfhjgadkjhagskdjhfgadskjhfgahkjdsgfkj" +
      "Cointenido del post 1 sdfgksudghfakhjgsdkfhjgadkjhagskdjhfgadskjhfgahkjdsgfkj" +
      "Cointenido del post 1 sdfgksudghfakhjgsdkfhjgadkjhagskdjhfgadskjhfgahkjdsgfkj" +
      "Cointenido del post 1 sdfgksudghfakhjgsdkfhjgadkjhagskdjhfgadskjhfgahkjdsgfkj" +
      "",
    contenidoCaption: "..."
  }];
  export default {
    name: "Posts",
    data() {
      return {
        expanded: false,
        posts
      }
    }
  }
</script>

<style scoped>
  .lista {
    min-width: 50%;
    max-width: 50%;
  }

  .post {
    min-width: 100%;
  }

  .gradient-background {
    background: linear-gradient(to right bottom, #ffffff, #fbfbfb, #f6f6f6, #f2f2f2, #eeeeee);
  }

  ul li {
    list-style: none;
  }
</style>
