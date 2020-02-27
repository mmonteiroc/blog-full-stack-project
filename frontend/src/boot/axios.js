import Vue from 'vue'
import axios from 'axios'

const instanceJava = axios.create({
  baseURL: process.env.API_JAVA
});
const instanceNodeJS = axios.create({
  baseURL: process.env.API_NODE
});

export default async ({Vue, router}) => {

  /*
  *
  * Añadimos el TOKEN
  *
  * */
  instanceJava.interceptors.request.use(function (config) {
    config.withCredentials = true;

    const token = localStorage.getItem('access_token');
    config.headers.Authorization = `Bearer ${token}`;

    return config;
  }, function (error) {
    return Promise.reject(error);
  });

  instanceJava.interceptors.response.use(function (response) {
    return response;
  }, async function (error) {

    if (error.response.status === 401) {
       // UNAUTORIZED, token no valido o token caducado.

      /*
      * Step 1- Intentar renovar token
      * */

      /*
      * Step 2 - Si renovamos token, intentar hacer la peticion otra vez
      * */

      /*
      * Step 3 - Si no nos renueva el token, mandar a login otra vez
      * */
    }
    if (error.response.status === 404){
      router.push('/')
    }
    if (error.response.status === 403){
      // FORBIDEN - NO tienes permisos loggeate con usuario que si tenga permisos
      router.push('/login')
    }


    return Promise.reject(error.response);
  });
}


Vue.prototype.$axiosJava = instanceJava;
Vue.prototype.$axiosNode = instanceNodeJS;


