
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
      {path: "posts", component: () => import('pages/private/Posts.vue')},
      {path: "create", component: () => import('pages/private/Form.vue')},
      {path: "update/:id", component: () => import('pages/private/Form.vue')},
      {path: "calculadora", component: () => import('pages/private/Calculadora.vue')},
    ],
    beforeEnter: (to, from, next) => {
      const token = localStorage.getItem('token');
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
      {path: 'login', component: () => import('pages/public/Login.vue')},
      {path: 'register', component: () => import('pages/public/Register.vue')}
    ]
  }
];

// Always leave this as last one
if (process.env.MODE !== 'ssr') {
  routes.push({
    path: '*',
    component: () => import('pages/error/Error404.vue')
  })
}

export default routes
