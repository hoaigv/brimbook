import { Fragment, useState, useEffect } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import PrivateRoute from "./components/PrivateRoute";
import Loading from "./components/Loading";

//Router
import routers from "~/routes/routers";

//Layout
import { HeaderOnly } from "./layouts/HeaderOnly";

export default function App() {
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    // Giả lập thời gian tải
    const timer = setTimeout(() => {
      setIsLoading(false);
    }, 1800);

    return () => clearTimeout(timer);
  }, []);

  // if (isLoading) {
  //   return <Loading />;
  // }

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
