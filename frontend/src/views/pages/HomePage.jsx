import { Link } from "react-router-dom";
import HeaderComponent from "../components/HeaderComponent";

export default function HomePage() {
  return (
    <>
      <HeaderComponent />
      <Link to="/create_service">create service</Link>
    </>
  );
}
