import Vue from 'vue'
import axios from 'axios'

const instanceJava = axios.create({
  baseURL: process.env.API_JAVA
});
const instanceNodeJS = axios.create({
  baseURL: process.env.API_NODE
});


/*
*
* Añadimos el TOKEN
*
* */
instanceJava.interceptors.request.use(function (config) {
  const token = localStorage.getItem('token');
  if (token != null) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
}, function (error) {
  return Promise.reject(error);
});

instanceJava.interceptors.response.use(function (response) {

  return response;
}, function (error) {

  if (error.response.status === 401) {
    window.location = '/#/login' // PLACEHOLDER TODO --> Cambiar esto por el router
  }

  return Promise.reject(error);
});


Vue.prototype.$axiosJava = instanceJava;
Vue.prototype.$axiosNode = instanceNodeJS;


