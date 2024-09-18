import { config } from "~/config";

//Layouts
import { HeaderOnly } from "~/layouts";

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

const publicRouter = [
    { path: config.routes.login, component: Login, layout: null },
    { path: config.routes.home, component: Home },
    { path: config.routes.search, component: Search },
    {
        path: config.routes.bookDetail,
        component: BookDetail,
        layout: HeaderOnly,
    },
    { path: config.routes.recent, component: Recent },
    { path: config.routes.topRated, component: TopRated },
    { path: config.routes.favorite, component: Favorite },
    { path: config.routes.setting, component: Setting },
    { path: config.routes.store, component: Store },
    { path: config.routes.notification, component: Notification },
    { path: config.routes.profile, component: Profile },
    { path: config.routes.chapter, component: Chapter, layout: HeaderOnly },
];

const privateRouter = [];

export { publicRouter, privateRouter };
