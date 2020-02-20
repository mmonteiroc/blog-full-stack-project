const routes = [
  {
    path: '/',
    component: () => import('layouts/PublicLayout.vue'),
    children: [
      {path: '', component: () => import('pages/Posts.vue')},
    ]
  },
  {
    path: '/private',
    component: () => import('layouts/PrivateLayout.vue'),
    children: [
      {path: "posts", component: () => import('pages/Error404.vue')}, //
      {path: "creation", component: () => import('pages/Form.vue')},
      {path: "updating", component: () => import('pages/Form.vue')},
      {path: "calculadora", component: () => import('pages/Calculadora.vue')}
    ]
  },
  {
    path: '/login',
    component: () => import('layouts/EmptyLayout.vue'),
    children: [
      {path: '', component: () => import('pages/Login.vue')}
    ]
  }
];

// Always leave this as last one
if (process.env.MODE !== 'ssr') {
  routes.push({
    path: '*',
    component: () => import('pages/Error404.vue')
  })
}

export default routes
