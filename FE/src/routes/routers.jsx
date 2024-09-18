//Layouts
import { HeaderOnly } from "~/layouts/HeaderOnly";

//Pages
import Login from "~/pages/Login";
import Home from "~/pages/Home";
import Search from "~/pages/Search";
import BookDetail from "~/pages/BookDetail";
import Recent from "~/pages/Recent";
import TopRated from "~/pages/TopRated";
import Favorite from "~/pages/Favorite";
import Setting from "~/pages/Setting";
import Store from "~/pages/Store";
import Notification from "~/pages/Notification";
import Profile from "~/pages/Profile";
import Chapter from "~/pages/Chapter";

const routers = [
    { path: "/login", component: Login, layout: null },
    { path: "/", component: Home },
    { path: "/search", component: Search },
    { path: "/books/:isbn13", component: BookDetail, layout: HeaderOnly },
    { path: "/recent", component: Recent },
    { path: "/top-rated", component: TopRated },
    { path: "/favorite", component: Favorite },
    { path: "/setting", component: Setting },
    { path: "/store", component: Store },
    { path: "/notification", component: Notification },
    { path: "/profile", component: Profile },
    { path: "/books/:isbn13/:chapter", component: Chapter, layout: HeaderOnly },
];

export default routers;
