//Pages
import Login from "~/pages/Login";
import Register from "~/pages/Register";
import Home from "~/pages/Home";
import Search from "~/pages/Search";
import BookDetail from "~/pages/BookDetail";
import Setting from "~/pages/Setting";
import Profile from "~/pages/Profile";
import Chapter from "~/pages/Chapter";
import PostStory from "~/pages/PostStory";
import UserManagement from "~/pages/UserManagement";

const routers = [
  { path: "/login", component: Login, layout: null },
  { path: "/register", component: Register, layout: null },
  { path: "/", component: Home },

  { path: "/search", component: Search, protected: true },
  { path: "/setting", component: Setting, protected: true },
  { path: "/profile", component: Profile, protected: true },
  { path: "/books/:isbn13", component: BookDetail, protected: true },
  { path: "/books/:isbn13/:chapter", component: Chapter, protected: true },
  { path: "/post-story", component: PostStory, protected: true },
  { path: "user-management", component: UserManagement, protected: true },
];

export default routers;
