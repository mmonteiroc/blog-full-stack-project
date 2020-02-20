<template>
  <q-page class="bg-grey-2 column flex-center">


    <!-- SELECCION DE TRADUCCION PARA NUESTRO CONTENIDO-->
    <q-select filled v-model="editor.idiomaTraduccion" use-input input-debounce="0" label="Idioma a traducir"
              :options="options" @filter="filterFn" style="width: 250px" behavior="menu" value="code"
              @input="translate">
      <template v-slot:prepend>
        <q-icon name="g_translate"/>
      </template>
      <template v-slot:no-option>
        <q-item>
          <q-item-section class="text-grey">
            No results
          </q-item-section>
        </q-item>
      </template>
      <template v-slot:append v-if="editor.idiomaTraduccion!=null">
        <q-icon name="close" @click.stop="editor.idiomaTraduccion = null" class="cursor-pointer"/>
      </template>
    </q-select>


    <div class="q-pa-md q-gutter-sm row flex-center" style="width: 100%">
      <div class="editor-container">
        <q-input outlined v-model="editor.tituloOriginal" label="Titulo" class="q-mb-sm bg-white" @input="translate"/>
        <q-editor v-model="editor.contenidoOriginal" class="editor-content" @input="translate"/>
      </div>


      <q-card flat bordered class="editor-container">
        <q-input v-model="editor.tituloTraducido" readonly class="q-mb-sm q-pl-md q-pr-md"/>
        <q-card-section v-html="editor.contenidoTraducido" class="editor-content"/>
      </q-card>
    </div>


    <q-page-sticky position="top" :offset="[0,7]">

    </q-page-sticky>


    <q-page-sticky position="bottom-right" :offset="[50, 18]" v-if="recording">
      <q-btn @click="record" icon="clear" flat rounded>
        <q-tooltip content-class="bg-purple-5" transition-show="scale"
                   transition-hide="scale" content-style="font-size: 1em" :offset="[10, 10]">
          Parar grabación
        </q-tooltip>
      </q-btn>
      <q-spinner-bars color="red-14" size="3em"/>
    </q-page-sticky>
    <q-page-sticky position="right" :offset="[50, 18]">
      <q-fab color="info" icon="keyboard_arrow_up" direction="up">
        <q-fab-action color="accent" @click="clear" icon="delete_sweep" label="Borrar" class="actions-button"/>
        <q-fab-action color="warning" @click="record" icon="record_voice_over" label="REC" class="actions-button"/>
        <q-fab-action color="positive" @click="onSave" icon="save_alt" label="Guardar" class="actions-button"/>
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
  const API_TRANSTALATE_URL = 'http://server247.cfgs.esliceu.net/bloggeri18n/blogger.php';

  export default {
    name: "Form",
    /*
    * Funcion al inicial la instancia de VUE
    * */
    async created() {
      /*
      * Pintamos todas las opciones de traduccion
      * TODO mirar como hacer las peticiones con AXIOS.
      * */
      const idiomas = await fetch(API_TRANSTALATE_URL, {
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
        options: stringOptions,
        recording: false
      }
    },


    methods: {
      async translate() {
        if (this.editor.idiomaTraduccion === null) this.editor.idiomaTraduccion = this.options[0];


        /*
        * TODO mirar como hacer abort para los translate.
        * TODO mirar como hacer las peticiones con AXIOS.
        * */
        const promesas = [];
        // titulo
        promesas.push(
          fetch(API_TRANSTALATE_URL, {
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
          fetch(API_TRANSTALATE_URL, {
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
        const bar = this.$refs.bar;
        bar.start();
        this.$refs.bar.stop();
      },
      record() {
        /*
        * TODO la grabacion de audio + recibimiento
        * */

        if (this.recording) {
          /*
          * Parar de grabar.
          * Coger el blob.
          * Enviarlo a la API de Joan.
          * Recuperar los datos y escribirlos.
          * Mirar si se traducen automaticamente.
          * */
        } else {
          /*
          * Habilitar API de audio para grabar y grabar.
          * */
        }

        this.recording = !this.recording;
      },
      clear() {
        this.editor.tituloOriginal = '';
        this.editor.tituloTraducido = '';
        this.editor.contenidoTraducido = '';
        this.editor.contenidoOriginal = '';
        this.editor.idiomaTraduccion = null;
        this.recording = false;
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
    min-width: 100%;
  }

  .actions-button {
    min-width: 7vw;
  }

  .editor-container {
    width: 40%;
  }
</style>
