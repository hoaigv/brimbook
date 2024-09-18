//Layouts
import { HeaderOnly } from '~/layouts/HeaderOnly';

//Pages
import Login from '~/pages/Login';
import Home from '~/pages/Home';
import Search from '~/pages/Search';
import BookDetail from '~/pages/BookDetail';
import Setting from '~/pages/Setting';
import Profile from '~/pages/Profile';
import Chapter from '~/pages/Chapter';

const routers = [
  { path: '/login', component: Login, layout: null },
  { path: '/', component: Home },
  { path: '/search', component: Search },
  { path: '/setting', component: Setting },
  { path: '/profile', component: Profile },
  { path: '/books/:isbn13', component: BookDetail, layout: HeaderOnly },
  { path: '/books/:isbn13/:chapter', component: Chapter, layout: HeaderOnly },
];

export default routers;
