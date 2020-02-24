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

    const token = localStorage.getItem('token');
    config.headers.Authorization = `Bearer ${token}`;

    return config;
  }, function (error) {
    return Promise.reject(error);
  });

  instanceJava.interceptors.response.use(function (response) {
    return response;
  }, async function (error) {

    if (error.response.status === 401) {
      router.push('/login')
    }


    return Promise.reject(error);
  });
}


Vue.prototype.$axiosJava = instanceJava;
Vue.prototype.$axiosNode = instanceNodeJS;


