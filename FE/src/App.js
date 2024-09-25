import { Fragment } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import PrivateRoute from "./components/PrivateRoute";

//Router
import routers from "~/routes/routers";

//Layout
import { HeaderOnly } from "./layouts/HeaderOnly";

export default function App() {
  return (
    <Router>
      <div className="App">
        <Routes>
          {routers.map((route, index) => {
            const Page = route.component;

            let Layout = HeaderOnly;
            if (route.layout) {
              Layout = route.layout;
            } else if (route.layout === null) {
              Layout = Fragment;
            }

            return (
              <Route
                key={index}
                path={route.path}
                element={
                  route.protected ? (
                    <PrivateRoute>
                      <Layout>
                        <Page />
                      </Layout>
                    </PrivateRoute>
                  ) : (
                    <Layout>
                      <Page />
                    </Layout>
                  )
                }
              />
            );
          })}
        </Routes>
      </div>
    </Router>
  );
}
