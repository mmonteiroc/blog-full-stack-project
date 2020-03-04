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
      const resultado = await instanceNodeJS.post('/login/refresh');
      const newToken = resultado.data.access_token;


      if (newToken !==null && newToken){
        /*
        * Step 2 - Si renovamos token, intentar hacer la peticion otra vez
        * */
        localStorage.setItem("access_token", newToken);
        return instanceJava(error.config);

      }else {
        /*
        * Step 3 - Si no nos renueva el token, mandar a login otra vez
        *
        * Tambien le quitamos los tokens actuales ya que no son validos, asi
        * hasta que no se autentique otra vez no podra entrar a la parte privada
        * */

        localStorage.removeItem('access_token');
        localStorage.removeItem('refresh_token');
        router.push('/login');
      }

    }
    if (error.response.status === 404){
      router.push('/')
    }
    if (error.response.status === 403){
      // FORBIDEN - NO tienes permisos loggeate con usuario que si tenga permisos
      router.push('/login')
    }


    return error.response;
  });


  instanceNodeJS.interceptors.request.use(function (config) {
    config.withCredentials = true;

    const refresh = localStorage.getItem('refresh_token');
    config.headers.Authorization = `${refresh}`;

    return config;
  }, function (error) {
    return Promise.reject(error);
  });


  instanceNodeJS.interceptors.response.use(function (response) {
    return response;
  }, async function (error) {

    return error.response;
  })
}


Vue.prototype.$axiosJava = instanceJava;
Vue.prototype.$axiosNode = instanceNodeJS;


