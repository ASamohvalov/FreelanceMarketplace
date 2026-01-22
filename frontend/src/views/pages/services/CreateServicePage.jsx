import { useEffect } from "react";
import HeaderComponent from "../../components/HeaderComponent";
import { useState } from "react";
import { useRef } from "react";
import { hasRole, isAuth } from "../../../logic/jwt";
import { useNavigate } from "react-router-dom";
import { createServiceRequest } from "../../../logic/requests/service/serviceRequest";

export default function CreateServicePage() {
  const navigate = useNavigate();
  const [error, setError] = useState(null);

  const title = useRef(null);
  const description = useRef(null);
  const price = useRef(null);
  const image = useRef(null);

  useEffect(() => {
    if (!isAuth() || !hasRole("ROLE_FREELANCER")) {
      navigate(-1);
      return;
    }
    document.title = "Create service";
  });

  async function handleSubmit(event) {
    event.preventDefault();
    if (!title.current.value ||
        !description.current.value ||
        !price.current.value ||
        !image.current.files) {
      setError("all fields are required");
      return;
    }
    const response = await createServiceRequest(
      title.current.value,
      image.current.files[0],
      description.current.value,
      price.current.value
    );
    if (response.status !== 200) {
      setError("some error with saving");
    }
  }

  return (
    <>
      <HeaderComponent />

      <div className="mx-auto" style={{ width: "500px", marginTop: "200px" }}>
        <div
          className={`mb-2 bg-danger p-4 border border-danger rounded shadow ${error ? "visible" : "invisible"}`}
        >
          { error }
        </div>
        <div className="shadow w-100 bg-dark rounded p-4 text-light">
          <div className="text-center mb-3 h4">Create service</div>
          <form onSubmit={ handleSubmit } className="mb-4">
            <label htmlFor="title-input">Image</label>
            <input
              className="form-control mb-3"
              id="title-input"
              type="file"
              ref={ image }
            />
            <label htmlFor="title-input">Title</label>
            <input
              className="form-control mb-3"
              id="title-input"
              type="text"
              ref={ title }
            />
            <label htmlFor="description-input">Description</label>
            <input
              className="form-control mb-3"
              id="description-input"
              type="text"
              ref={ description }
            />
            <label htmlFor="price-input">Price</label>
            <input
              className="form-control mb-3"
              id="price-input"
              type="number"
              ref={ price }
            />
            <button className="btn btn-primary" type="submit">
              Submit
            </button>
          </form>
        </div>
      </div>
    </>
  );
}
