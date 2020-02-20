const routes = [
  {
    path: '/',
    component: () => import('layouts/PublicLayout.vue'),
    children: [
      {path: '', component: () => import('pages/public/Posts.vue')},
    ]
  },
  {
    path: '/private',
    component: () => import('layouts/PrivateLayout.vue'),
    children: [
      {path: "posts", component: () => import('pages/error/Error404.vue')},
      {path: "creation", component: () => import('pages/private/Form.vue')},
      {path: "updating", component: () => import('pages/private/Form.vue')},
      {path: "calculadora", component: () => import('pages/private/Calculadora.vue')}
    ]
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
