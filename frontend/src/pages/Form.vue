<template>
  <q-page class="bg-grey-2 column flex-center">

    <q-select filled v-model="editor.idiomaTraduccion" use-input input-debounce="0" label="Idioma a traducir"
              :options="options" @filter="filterFn" style="width: 250px" behavior="menu" value="code"
              @input="translate">
      <template v-slot:no-option>
        <q-item>
          <q-item-section class="text-grey">
            No results
          </q-item-section>
        </q-item>
      </template>
    </q-select>
    <div class="q-pa-md q-gutter-sm row flex-center">
      <div>
        <q-input outlined v-model="editor.tituloOriginal" label="Titulo" class="q-mb-sm bg-white" @input="translate"/>
        <q-editor v-model="editor.contenidoOriginal" class="editor-content" @input="translate"/>
      </div>


      <q-card flat bordered>
        <q-input v-model="editor.tituloTraducido" readonly class="q-mb-sm q-pl-md q-pr-md"/>
        <q-card-section v-html="editor.contenidoTraducido" class="editor-content"/>
      </q-card>
    </div>


    <q-page-sticky position="top" :offset="[0,7]">

    </q-page-sticky>


    <q-page-sticky position="right" :offset="[50, 18]">
      <q-fab color="info" icon="keyboard_arrow_up" direction="up">
        <q-fab-action color="accent" @click="clear" icon="delete_sweep" label="Borrar"/>
        <q-fab-action color="warning" @click="record" icon="record_voice_over" label="REC"/>
        <q-fab-action color="positive" @click="onSave" icon="save_alt" label="Guardar"/>
      </q-fab>
    </q-page-sticky>


    <q-ajax-bar
      ref="bar"
      position="bottom"
      color="green-6"
      size="5px"
      skip-hijack
    />

  </q-page>

</template>
<script>
  const stringOptions = [];
  const API_TRANSTALET_URL = 'http://server247.cfgs.esliceu.net/bloggeri18n/blogger.php';

  export default {
    name: "Form",
    /*
    * Funcion al inicial la instancia de VUE
    * */
    async created() {
      /*
      * Pintamos todas las opciones de traduccion
      * */
      const idiomas = await fetch(API_TRANSTALET_URL, {
        method: 'POST',
        body: JSON.stringify({
          MethodName: 'languages',
          params: ''
        })
      }).then(x => x.json());
      idiomas.map(idioma => {
        stringOptions.push({
          label: idioma.name,
          code: idioma.code
        })
      })
    },
    data() {
      return {
        editor: {
          tituloOriginal: '',
          tituloTraducido: '',
          contenidoOriginal: '',
          contenidoTraducido: '',
          idiomaTraduccion: null
        },
        stringOptions,
        options: stringOptions
      }
    },
    methods: {
      async translate() {
        if (this.editor.idiomaTraduccion === null) this.editor.idiomaTraduccion = this.options[0];


        const promesas = [];
        // titulo
        promesas.push(
          fetch(API_TRANSTALET_URL, {
            method: 'post',
            body: JSON.stringify({
              MethodName: 'translate',
              params: {
                source: 'es',
                target: this.editor.idiomaTraduccion.code,
                text: this.editor.tituloOriginal
              }
            })
          }).then(x => x.text())
        );
        // Contenido
        promesas.push(
          fetch(API_TRANSTALET_URL, {
            method: 'post',
            body: JSON.stringify({
              MethodName: 'translate',
              params: {
                source: 'es',
                target: this.editor.idiomaTraduccion.code,
                text: this.editor.contenidoOriginal
              }
            })
          }).then(x => x.json())
        );

        const resultado = await Promise.all(promesas);

        /*
        * Este substring lo hacemos por que por algun motivo,
        * nuestro servidor nos esta enviando un string
        * dentro de otro string """"
        * */
        this.editor.tituloTraducido = resultado[0].substr(1, resultado[0].length - 2);
        this.editor.contenidoTraducido = resultado[1];


      },
      onSave() {
        console.log(this.editor.idiomaTraduccion);
        const bar = this.$refs.bar;

        bar.start();

        /*
        * TODO cuando se haga la peticion ajax de guardado, al salir del await, tendremos que hacer el stop de la barra
        * */
        this.timer = setTimeout(() => {
          if (this.$refs.bar) {
            this.$refs.bar.stop()
          }
        }, Math.random() * 3000 + 1000)

      },
      record() {
        /*
        * TODO la grabacion de audio + recibimiento
        * */
      },
      clear() {
        this.editor.tituloOriginal = '';
        this.editor.tituloTraducido = '';
        this.editor.contenidoTraducido = '';
        this.editor.contenidoOriginal = '';
        this.editor.idiomaTraduccion = null;
      },
      filterFn(val, update) {
        if (val === '') {
          update(() => {
            this.options = stringOptions
          });
          return
        }

        update(() => {
          const needle = val.toLowerCase();
          this.options = stringOptions.filter(v => v.label.toLowerCase().indexOf(needle) > -1)
        })
      }
    }
  }
</script>

<style scoped>
  .editor-content {
    height: 70vh;
    min-width: 40vw;
  }
</style>
