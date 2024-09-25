import { Navigate } from "react-router-dom";

const PrivateRoute = ({ children }) => {
  const userToken = localStorage.getItem("userToken");

  if (!userToken) {
    return <Navigate to="/login" replace />;
  }

  return children;
};

export default PrivateRoute;
