const routes = [
    {
      path: '/',
      component: () => import('layouts/PublicLayout.vue'),
      children: [
        {path: '', component: () => import('pages/public/Posts.vue')}
      ]
    },
    {
      path: '/private',
      component: () => import('layouts/PrivateLayout.vue'),
      children: [
        {path: "", redirect: 'dashboard'},
        {path: "dashboard", component: () => import('pages/private/Dashboard.vue')},
        {path: "posts", component: () => import('pages/private/Posts.vue')},
        {path: "create", component: () => import('pages/private/Form.vue')},
        {path: "update/:id", component: () => import('pages/private/Form.vue')},
        {path: "calculadora", component: () => import('pages/private/Calculadora.vue')},
        {path: "account", component: () => import('pages/private/Account.vue')},
      ],
      beforeEnter: (to, from, next) => {
        const token = localStorage.getItem('access_token');
        if (!token) {
          next('login');
        } else {
          next();
        }
      }
    },
    {
      path: '/',
      component: () => import('layouts/EmptyLayout.vue'),
      children: [
        {
          path: 'login', component: () => import('pages/public/Login.vue'),
          children: [
            {
              path: 'callback',
              beforeEnter: (to, from, next) => {
                const url = new URL(location);
                const access_token = url.searchParams.get('access_token');
                const refresh_token = url.searchParams.get('refresh_token');
                /*
                * Get refresh
                * */

                /*
                * Save tokens
                * */
                localStorage.setItem('access_token', access_token);
                localStorage.setItem('refresh_token', refresh_token);

                /*
                * Limpiar url de params
                * */
                /*
                *
                * Esto es un placeholder hasta encontrar la manera correcta
                * */
                window.history.pushState({},document.title, "/");
                next('/private/')
              }
            }
          ]
        },
        {
          path: 'register', component: () => import('pages/public/Register.vue')
        }
      ]
    }
  ]
;

// Always leave this as last one
if (process.env.MODE !== 'ssr') {
  routes.push({
    path: '*',
    component: () => import('pages/error/Error404.vue')
  })
}

export default routes

