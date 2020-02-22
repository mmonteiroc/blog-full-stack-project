<template>
  <q-page class="flex flex-center">
    <div class="flex-center flex main">

      <q-card class="food-section q-mr-lg">
        <q-card-section>
          <div class="text-h6">Dieta actual</div>
        </q-card-section>

        <q-separator/>
        <q-scroll-area style="height: 400px">

          <q-list bordered separator>


            <q-item v-for="item in allFoodItems">
              <q-item-section>
                <q-item-label>{{item.nombre}}</q-item-label>
                <q-item-label caption>{{item.kcal}} kcal</q-item-label>
              </q-item-section>
              <q-item-section avatar>
                <q-btn flat v-ripple text-color="negative" dense icon="delete" label="Borrar"
                       @click="deleteItem(item)"/>
              </q-item-section>
            </q-item>

          </q-list>
        </q-scroll-area>


        <q-separator/>
        <q-card-actions align="right">
          <q-btn flat @click="removeAllItems"> Borrar todos los alimentos</q-btn>
        </q-card-actions>


      </q-card>
      <div class="column flex-center form-section">
        <h3>Calcula las tus calorias</h3>

        <div>
          <q-radio v-model="form.sexo" val="H" label="Hombre"/>
          <q-radio v-model="form.sexo" val="M" label="Mujer"/>
        </div>
        <div class="flex-center flex" style="width: 100%">
          <q-input v-model.number="form.peso" type="number" outlined style="min-width: 25%" label="Peso"
                   class="q-pa-sm"/>
          <q-input v-model.number="form.altura" type="number" outlined style="min-width: 25%" label="Altura (cm)"
                   class="q-pa-sm"/>
        </div>
        <q-input v-model.number="form.edad" type="number" outlined style="width: 50%" label="Edad"
                 class="q-pa-sm"/>


        <div class="row flex-center" style="width: 70%" id="containerButtons">
          <q-btn color="primary" :data-multiplyvalue="ejercico.poco" label="Poco ejercicio / Nada" class="q-ma-sm"
                 draggable="true" @dragstart="onDragStart" id="poco"/>
          <q-btn color="primary" :data-multiplyvalue="ejercico.ligero" label="Ligero (1-3 dias/semana)" class="q-ma-sm"
                 draggable="true" @dragstart="onDragStart" id="ligero"/>

          <q-btn color="primary" :data-multiplyvalue="ejercico.moderado" label="Moderado (3-5 dias/semana)"
                 class="q-ma-sm" draggable="true" @dragstart="onDragStart" id="moderado"/>
          <q-btn color="primary" :data-multiplyvalue="ejercico.fuerte" label="Fuerte (6 dias/semana)" class="q-ma-sm"
                 draggable="true" @dragstart="onDragStart" id="fuerte"/>
          <q-btn color="primary" :data-multiplyvalue="ejercico.profesional" label="Profesional / Extremo"
                 class="q-ma-sm"
                 draggable="true" @dragstart="onDragStart" id="profesionar"/>
        </div>

        <div class="row bg-grey-3 inputDragDrop rounded-borders q-pa-md q-mt-md flex-center" id="areaDragDrop"
             @dragover="onDragOver" @drop="onDrop">
          <q-tooltip :delay="500" :offset="[0, 10]"
                     content-class="bg-purple-5"
                     transition-show="scale"
                     transition-hide="scale" content-style="font-size: 1em">
            Drag & drop aqui el deporte que haces
          </q-tooltip>
        </div>

      </div>


    </div>


    <!-- STICKY ELEMENTS-->

    <q-page-sticky style="width: 300px" position="top-right" class=" flex-center" :offset="[30, 18]"
                   v-show="webcamEnabled">
      <video id="webcam" width="100%" height="200" autoplay></video>
      <q-btn flat icon="far fa-stop-circle" class="full-width" color="negative" @click="record"
             label="Stop recording"></q-btn>
    </q-page-sticky>


    <q-page-sticky position="bottom-right" :offset="[100, 18]">
      <q-fab color="purple" icon="keyboard_arrow_up" direction="up">
        <q-fab-action color="secondary" @click="record" icon="fas fa-video" label="Food recon"/>
        <q-fab-action color="primary" @click="calcCalories" icon="alarm" label="Calculate "/>
      </q-fab>
    </q-page-sticky>

    <q-ajax-bar
      ref="bar"
      position="bottom"
      color="accent"
      size="7px"
      skip-hijack
    />
  </q-page>
</template>

<script>


  export default {
    name: "Calculadora",
    data() {
      return {
        /*
        * Valores de la Indexed DB
        * */
        DB_NAME: 'dieta',
        DB_TABLE_NAME: 'alimentos',
        dbPromise: null,

        /*
        * Valores de la deteccion de alimentos
        * */
        estadistica: {
          nombre: '',
          porcentaje: null
        },
        webcamEnabled: false,
        allFoodItems: [],
        stream: null,
        STOP_LOOP: null,

        /*
        * Valores de la calculadora
        * */
        form: {
          sexo: '',
          peso: '',
          altura: '',
          edad: ''
        },
        ejercico: {
          poco: 1.2,
          ligero: 1.375,
          moderado: 1.55,
          fuerte: 1.725,
          profesional: 1.9
        }

      }
    },
    created() {

      /*
      * INDEXED DB
      * */
      this.dbPromise = indexedDB.open(this.DB_NAME, 1);

      this.dbPromise.onupgradeneeded = async function (upgradeDB) {
        // Pillamos la DB
        const database = upgradeDB.target.result;

        const tabla = database.createObjectStore(this.DB_TABLE_NAME, {keyPath: "nombre"});
        tabla.createIndex("nombre", "nombre", {unique: true});
        /*
        * Ejemplos de alimentos por defecto
        * */

        tabla.add({
          nombre: 'peach'
        });
        tabla.add({
          nombre: 'melon'
        });
      }.bind(this);

      this.dbPromise.onsuccess = function () {
        console.log("DB READY TO USE");
        const alimentos = this.getAll();

        alimentos.onsuccess = function (ev) {
          ev.target.result.forEach(item => {
            this.allFoodItems.push({
              nombre: item.nombre,
              kcal: item.kcal
            })
          });
        }.bind(this)
      }.bind(this);

    },
    methods: {
      onDragStart(event) {
        event.dataTransfer.setData('id', event.target.id);
        event.dataTransfer.dropEffect = 'move'
      },
      onDragOver(event) {
        event.preventDefault()
      },
      onDrop(event) {
        const id = event.dataTransfer.getData("id");
        const area = document.querySelector('#areaDragDrop');

        if (area.children.length !== 0) {

          /*
          * Cogemos el 1 ya qu el tooltip esta dentro del area tambn
          * */
          const node = area.childNodes[1];
          const containerButtons = document.querySelector('#containerButtons');
          containerButtons.appendChild(node);
        }

        const nodeDragged = document.querySelector('#' + id);
        area.appendChild(nodeDragged);

      },
      calcCalories() {
        const area = document.querySelector('#areaDragDrop');
        const nodo = area.childNodes[1];
        if (this.form.altura !== '' && this.form.edad !== '' && this.form.peso !== '' && this.form.sexo !== '' && nodo !== '') {
          let TMB = 0;
          if (this.form.sexo === 'H') {
            // Hombres
            TMB = (10 * this.form.peso) + (6.25 * this.form.altura) - (5 * this.form.edad) + 5;
          } else if (this.form.sexo === 'M') {
            // Mujeres
            TMB = (10 * this.form.peso) + (6.25 * this.form.altura) - (5 * this.form.edad) - 161;
          }
          TMB *= nodo.dataset.multiplyvalue;

          this.$q.notify({
            color: 'primary',
            textColor: 'black',
            message: 'Tus calorias bassales son: ' + TMB,
            position: 'center',
            avatar: 'https://cdn.quasar.dev/img/boy-avatar.png',
            multiLine: 'true',
            timeout: 5000
          })
        } else {
          this.$q.notify({
            progress: true,
            message: 'Campos introducidos incorrectos',
            icon: 'mail',
            color: 'negative',
            textColor: 'white'
          })
        }
      },
      async record() {
        const video = document.querySelector('#webcam');
        if (!this.webcamEnabled) {
          // Init GRABAR datos
          this.stream = await navigator.mediaDevices.getUserMedia({audio: false, video: true});
          video.srcObject = this.stream;
          this.STOP_LOOP = false;
          let clasifier = await ml5.imageClassifier('MobileNet', video);
          this.loop(clasifier);

        } else {
          // Parar y mandar a peticion API
          const bar = this.$refs.bar;
          bar.start();


          this.STOP_LOOP = true;
          this.stream.getTracks().forEach(function (track) {
            track.stop();
          });
          const alimentos = this.getAll();
          alimentos.onsuccess = async function (ev) {
            const allfood = ev.target.result;

            /*
            * Hacemos las peticiones a la api para obtener las kcal de los alimentos
            * */
            const promesas = [];
            /*
            * Guardamos todas las promesas que nos retorna este metodo
            * */
            allfood.forEach(individualFood => {
              if (!individualFood.kcal) {
                const promesa = this.getFoodInfo(individualFood.nombre);
                promesas.push(promesa)
              }
            });

            /*Cuando esten todas, las mandamos a paintFullDiet*/
            const result = await Promise.all(promesas);
            result.forEach(food => {
              if (food.isFood !== false) {
                this.updateFood(food);
              } else {
                this.removeFood(food);
              }
            });

            const allTheFood = this.getAll();
            allTheFood.onsuccess = function (ev) {
              this.allFoodItems.length = 0;
              ev.target.result.forEach(item => {
                this.allFoodItems.push(item)
              });
              this.$refs.bar.stop();
            }.bind(this)
          }.bind(this)
        }

        this.webcamEnabled = !this.webcamEnabled;
      },
      loop(classifier) {
        if (!this.STOP_LOOP) {
          classifier.classify()
            .then(results => {

              this.estadistica.nombre = results[0].label;
              this.estadistica.porcentaje = results[0].confidence.toFixed(2);


              /*
              * Solo si estamos un 70% seguros lo guardamos en la BBDD
              *
              * */
              if (this.estadistica.porcentaje >= 0.7) {
                this.inserData({nombre: this.estadistica.nombre});
              }
              this.loop(classifier) // Call again to create a loop
            })
        }
      },
      getAll() {
        const TRANSACTION = this.dbPromise.result.transaction([this.DB_TABLE_NAME], 'readwrite');
        const table = TRANSACTION.objectStore(this.DB_TABLE_NAME);
        return table.getAll();
      },
      inserData(food) {
        const TRANSACTION = this.dbPromise.result.transaction([this.DB_TABLE_NAME], 'readwrite');
        const foodTable = TRANSACTION.objectStore(this.DB_TABLE_NAME);
        try {
          /*
          * Solo añade si no hay ningun elemento con el mismo nombre
          * */
          foodTable.add(food);
        } catch (ConstraintError) {
          /*
          * console.error(ConstraintError)
          * */
        }
      },
      updateFood(food) {
        const TRANSACTION = this.dbPromise.result.transaction([this.DB_TABLE_NAME], 'readwrite');
        const foodTable = TRANSACTION.objectStore(this.DB_TABLE_NAME);
        foodTable.put(food)
      },
      deleteItem(footItem) {
        this.removeFood(footItem);
        this.allFoodItems = this.allFoodItems.filter(item => {
          return item !== footItem
        });
      },
      removeAllItems() {
        this.allFoodItems.map(item => {
          this.removeFood(item)
        });
        this.allFoodItems = []
      },
      removeFood(food) {
        const TRANSACTION = this.dbPromise.result.transaction([this.DB_TABLE_NAME], 'readwrite');
        const foodTable = TRANSACTION.objectStore(this.DB_TABLE_NAME);

        foodTable.delete(food.nombre);
      },
      async getFoodInfo(foodName) {
        const APP_ID = "f8c6bbf3";
        const API_KEY = "09beb6c98488ceda8d4fbdcaa31557f1";
        console.log("API SEARCH OF: ", foodName);


        /*
        * IMPORTANT -- ESTA URL API, no la ponemos en el
        * properties ya que los params van en la URL, y
        * daria igual poner solo en comienzo en el properties,
        * ya que si cambia el comienzo, seguramente tambien
        * cambien la manera de enviar los params
        * */
        const result = await fetch("https://api.edamam.com/api/food-database/parser?app_id=" + APP_ID + "&app_key=" + API_KEY + "&ingr=" + foodName).then(x => x.json());
        if (result.parsed.length > 0) {
          const kcal = result.parsed[0].food.nutrients.ENERC_KCAL;

          /*
          * Retornamos este objeto food el cual se retorna como promesa
          * */
          return {
            nombre: foodName,
            kcal: kcal,
            isFood: true
          }
        } else {
          /*
          * En el caso de que lo que hayamos enviado no sea un alimento o
          * no lo tengan en sus BBDD lo que hacemos es nosotros poner un false
          * y el paintFullDiet se encargara de no pintar esta promesa
          * */
          return {
            nombre: foodName,
            isFood: false
          };
        }
      }


    }
  }


</script>

<style scoped>

  .column {
    width: 50%;
  }

  .inputDragDrop {
    width: 50%;
    min-height: 10vh;
  }

  .food-section {
    min-width: 30%;
    max-width: 30%;
  }

  .form-section {
    max-width: 60%;
  }

  .main {
    margin: 0;
    max-width: 80%;
  }

</style>
