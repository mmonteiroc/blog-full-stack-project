<template>
  <q-page class="flex-center flex">
    <div class="column flex-center">
      <h2>Calcula las tus calorias</h2>

      <div>
        <q-radio v-model="form.sexo" val="H" label="Hombre"/>
        <q-radio v-model="form.sexo" val="M" label="Mujer"/>
      </div>
      <div class="flex-center flex" style="width: 100%">
        <q-input v-model.number="form.peso" type="number" outlined style="min-width: 25%" label="Peso" class="q-pa-sm"/>
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
        <q-btn color="primary" :data-multiplyvalue="ejercico.profesional" label="Profesional / Extremo" class="q-ma-sm"
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
    <q-page-sticky position="bottom-right" :offset="[100, 18]">
      <q-fab color="purple" icon="keyboard_arrow_up" direction="up">
        <q-fab-action color="secondary" @click="" icon="mail" label="Food recon"/>
        <q-fab-action color="primary" @click="calcCalories" icon="alarm" label="Calculate "/>
      </q-fab>
    </q-page-sticky>


  </q-page>
</template>

<script>
  export default {
    name: "Calculadora",
    data() {
      return {
        form: {
          sexo: null,
          peso: null,
          altura: null,
          edad: null
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
        if (this.form.altura != null && this.form.edad != null && this.form.peso != null && this.form.sexo != null && nodo != null) {
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

  .invisible {
    display: none;
  }
</style>
