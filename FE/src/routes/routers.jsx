//Pages
import Login from "~/pages/Login";
import Register from "~/pages/Register";
import Home from "~/pages/Home";
import Search from "~/pages/Search";
import BookDetail from "~/pages/BookDetail";
import Profile from "~/pages/Profile";
import PostStory from "~/pages/PostStory";
import UserManagement from "~/pages/UserManagement";
import Favorite from "~/pages/Favorite";
import Recent from "~/pages/Recent";

const routers = [
  { path: "/login", component: Login, layout: null },
  { path: "/register", component: Register, layout: null },
  { path: "/", component: Home },
  { path: "/search", component: Search, protected: true },
  { path: "/profile", component: Profile, protected: true },
  { path: "/books/:id", component: BookDetail, protected: true },
  { path: "/post-story", component: PostStory, protected: true },
  { path: "user-management", component: UserManagement, protected: true },
  { path: "/favorite", component: Favorite, protected: true },
  { path: "/Recent", component: Recent, protected: true },
];

export default routers;
