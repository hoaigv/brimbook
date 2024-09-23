//Pages
import Login from "~/pages/Login";
import Register from "~/pages/Register";
import Home from "~/pages/Home";
import Search from "~/pages/Search";
import BookDetail from "~/pages/BookDetail";
import Setting from "~/pages/Setting";
import Profile from "~/pages/Profile";
import Chapter from "~/pages/Chapter";

const routers = [
  { path: "/login", component: Login, layout: null },
  { path: "/register", component: Register, layout: null },
  { path: "/", component: Home },
  { path: "/search", component: Search },
  { path: "/setting", component: Setting },
  { path: "/profile", component: Profile },
  { path: "/books/:isbn13", component: BookDetail },
  { path: "/books/:isbn13/:chapter", component: Chapter },
];

export default routers;
