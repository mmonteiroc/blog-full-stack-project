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
      <q-input v-model.number="form.altura" type="number" outlined style="width: 50%" label="Altura (cm)"
               class="q-pa-sm"/>


      <div class="row ">
        <q-btn color="primary" label="Poco ejercicio / Nada" class="q-ma-sm" draggable="true"/>
        <q-btn color="primary" label="Ligero (1-3 dias/semana)" class="q-ma-sm" draggable="true"/>
      </div>
      <div class="row">
        <q-btn color="primary" label="Moderado (3-5 dias/semana)" class="q-ma-sm" draggable="true"/>
        <q-btn color="primary" label="Fuerte (6 dias/semana)" class="q-ma-sm" draggable="true"/>
        <q-btn color="primary" label="Profesional / Extremo" class="q-ma-sm" draggable="true"/>
      </div>

      <div class="row bg-grey-3 inputDragDrop rounded-borders q-pa-md q-mt-md">
        <q-tooltip :delay="500" :offset="[0, 10]"
                   content-class="bg-purple-5"
                   transition-show="scale"
                   transition-hide="scale" content-style="font-size: 1em">
          Drag & drop aqui el deporte que haces
        </q-tooltip>
      </div>
    </div>

  </q-page>
</template>

<script>
  export default {
    name: "Calculadora",
    data() {
      return {
        form: {
          sexo: '',
          peso: null,
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
</style>
