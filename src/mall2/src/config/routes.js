
//基础页面
import Home from '../pages/Home/';
import ProductDetail from '../pages/mall/ProductDetail/';
import ProductList from '../pages/mall/ProductList/';
import Shopcart from '../pages/mall/Shopcart/';
import Order from '../pages/mall/Order/';
import Login from '../pages/account/Login/';
import Register from '../pages/account/Register/';

//默认 layout 采用 MainLayout
const routes = [
  {
    path: '/',
    title: 'MenuHome',
    icon: 'home',
    component: Home
  },

  {
    path: '/mall/product/detail/:id',
    title: 'ProductDetail',
    menu: false,
    component: ProductDetail
  },
  {
    path: '/mall/product/list/:id',
    title: 'ProductList',
    menu: false,
    component: ProductList
  },
  {
    path: '/mall/shopcart',
    title: 'Shopcart',
    menu: false,
    component: Shopcart
  },
  {
    path: '/mall/order',
    title: 'Order',
    menu: false,
    component: Order
  },
  {
    title: 'v_account',
    menu: false,
    icon: 'user',
    routes: [
      {
        path: '/account/login',
        isSimpleLayout: true,
        title: 'MenuLogin',
        component: Login
      },
      {
        path: '/account/register',
        simpleLayout: true,
        title: 'MenuRegister',
        component: Register
      }
    ]
  }
];

export default routes;