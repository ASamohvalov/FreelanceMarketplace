import { useEffect } from "react";
import HeaderComponent from "../../components/HeaderComponent";
import { useState } from "react";
import { useRef } from "react";
import { getUserData, hasRole, isAuth } from "../../../logic/jwt";
import { useNavigate } from "react-router-dom";
import { getAllCategoryInfo } from "../../../logic/requests/service/categoryRequest";
import "./css/create_service_page.css";
import LoadingInput from "../../components/elements/LoadingInput";
import { IMAGE_UPLOADING_TYPE } from "../../../env";
import { createServiceRequest } from "../../../logic/requests/service/serviceRequest";
import FooterComponent from "../../components/FooterComponent";
import NavLocation from "../../components/elements/NavLocation";
import ReactMarkdown from "react-markdown";
import ServiceCardComponent from "../../components/service/ServiceCardComponent";

export default function CreateServicePage() {
  const navigate = useNavigate();
  const [error, setError] = useState(null);

  // response data
  const [categories, setCategories] = useState([]);

  // fields
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [selectedCategory, setSelectedCategory] = useState("");
  const [selectedSubcategory, setSelectedSubcategory] = useState("");
  const [price, setPrice] = useState(599);
  const [titleImage, setTitleImage] = useState(null);
  const [images, setImages] = useState([]);

  // field references
  const deadlineDays = useRef(null);
  const revisionsCount = useRef(null);

  useEffect(() => {
    if (!isAuth() || !hasRole("ROLE_FREELANCER")) {
      navigate(-1);
      return;
    }
    document.title = "Create service";

    (async () => {
      const response = await getAllCategoryInfo();
      if (response.status !== 200) {
        console.log("logic error");
        navigate("/error");
        return;
      }
      setCategories(response.data);
      setSelectedCategory(response.data[0].id);
    })();
  }, [navigate]);

  async function handleSubmit(event) {
    event.preventDefault();

    var err = false;
    if (title.length < 10) {
      setError({ title: "Error: min title length is 10 symbols" });
      err = true;
    }
    if (price < 20) {
      setError({ price: "Error: price min is 20 rubles" });
      err = true;
    }
    if (description.length < 100) {
      setError({ description: "Error: min description length is 100 symbols" });
      err = true;
    }
    if (err) return;

    const response = await createServiceRequest({
      title: title,
      titleImage: titleImage,
      description: description,
      price: price,
      deadlineDays: Number(deadlineDays.current.value),
      revisionsCount: Number(revisionsCount.current.value),
      subcategoryId: selectedSubcategory,
      images: images,
    });
    if (response.status !== 200) {
      navigate("/error");
      return;
    }
  }

  return (
    <>
      <HeaderComponent />

      <main>
        <div className="container my-4">
          <NavLocation>Services / Create new</NavLocation>
          <h2 className="fw-bold mb-2">Создание новой услуги</h2>
          <p className="text-muted mb-4">
            Заполните приведенную ниже информацию, чтобы клиенты могли легко
            найти и понять суть вашего сервиса.
          </p>

          <div className="row">
            <div className="col-lg-8">
              <div className="card p-4 form-section rounded-4">
                <h5>Основная информация</h5>

                <div className="mb-3">
                  <label className="form-label text-secondary">
                    Заголовок услуги
                  </label>
                  <input
                    type="text"
                    className="form-control"
                    value={title}
                    onChange={(e) =>
                      e.target.value.length <= 50 && setTitle(e.target.value)
                    }
                    placeholder="I will create a WordPress website for your business"
                  />
                  <div className="form-text">
                    <span className="text-danger">{error?.title}</span>
                  </div>
                </div>

                <div className="row">
                  <div className="col-md-6 mb-3">
                    <label className="form-label text-secondary">
                      Категория
                    </label>
                    {categories.length === 0 ? (
                      <LoadingInput />
                    ) : (
                      <select
                        className="form-select"
                        value={selectedCategory}
                        onChange={(e) => {
                          setSelectedCategory(e.target.value);
                          setSelectedSubcategory("");
                        }}
                      >
                        {categories.map((category) => (
                          <option key={category.id} value={category.id}>
                            {category.name}
                          </option>
                        ))}
                      </select>
                    )}
                  </div>

                  <div className="col-md-6 mb-3">
                    <label className="form-label text-secondary">
                      Подкатегория
                    </label>
                    {categories.length === 0 ? (
                      <LoadingInput />
                    ) : (
                      <select
                        className="form-select"
                        value={selectedSubcategory}
                        onChange={(e) => setSelectedSubcategory(e.target.value)}
                      >
                        {(() => {
                          const element = categories.find(
                            (category) => category.id === selectedCategory,
                          );
                          return element?.subcategories.map((category) => (
                            <option key={category.id} value={category.id}>
                              {category.name}
                            </option>
                          ));
                        })()}
                      </select>
                    )}
                  </div>
                </div>
              </div>

              <div className="card p-4 form-section rounded-4">
                <h5>
                  Описание{" "}
                  <a href="https://www.markdownlang.com/ru/cheatsheet/" target="_blank">
                    <small className="h6 text-decoration-none">(в формате MarkDown)</small>
                  </a>
                </h5>

                <div className="mb-3">
                  <textarea
                    className="form-control"
                    rows="7"
                    placeholder="Опишите, что вы предлагаете, для кого это предназначено и что получит клиент..."
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                  ></textarea>
                </div>

                <div className="border-top p-1 mt-1">
                  <ReactMarkdown>
                    {description}
                  </ReactMarkdown>
                </div>

                <div className="form-text">
                  <span className="text-danger">{error?.description}</span>
                </div>
              </div>

              <div className="card p-4 form-section rounded-4">
                <h5>Оценивание</h5>

                <div className="row">
                  <div className="col-md-4 mb-3">
                    <label className="form-label">Цена</label>
                    <input
                      type="number"
                      className="form-control"
                      value={price}
                      onChange={(e) =>
                        e.target.value <= 999999 && setPrice(e.target.value)
                      }
                    />
                  </div>

                  <div className="col-md-4 mb-3">
                    <label className="form-label">Время выполнения (дни)</label>
                    <input
                      type="number"
                      className="form-control"
                      placeholder="3"
                      min="1"
                      ref={deadlineDays}
                    />
                  </div>

                  <div className="col-md-4 mb-3">
                    <label className="form-label">Правки (количество)</label>
                    <input
                      type="number"
                      className="form-control"
                      placeholder="2"
                      min="1"
                      ref={revisionsCount}
                    />
                  </div>
                </div>

                <div className="form-text">
                  <span className="text-danger">{error?.price}</span>
                </div>
              </div>

              <div className="card p-4 form-section rounded-4">
                <h5>Основное изображение</h5>

                <div className="mb-3">
                  <input
                    type="file"
                    className="form-control"
                    onChange={(e) => {
                      setTitleImage(e.target.files[0]);
                      /*
                      if (e.target.files && IMAGE_UPLOADING_TYPE.includes(e.target.files.type)) {
                        setTitleImage(e.target.files[0]);
                      }
                      */
                    }}
                  />
                </div>

                <div className="form-text"></div>
              </div>

              <div className="card p-4 form-section rounded-4">
                <h5>Галерея</h5>

                <div className="mb-3">
                  {/* TODO, file delete in input */}
                  <input
                    type="file"
                    className="form-control"
                    onChange={(e) => {
                      if (images.length === 4) {
                        return;
                      }
                      const array = [...images];
                      array.push(e.target.files[0]);
                      setImages(array);
                    }}
                  />
                </div>

                <div className="mb-3">
                  <div id="file-list">
                    {images.map((image, idx) => (
                      <div className="file-row" key={idx}>
                        <span className="file-name">{image.name}</span>
                        <span
                          className="icon remove"
                          onClick={() => {
                            const array = [...images];
                            array.splice(idx, 1);
                            setImages(array);
                          }}
                        >
                          ✕
                        </span>
                      </div>
                    ))}
                  </div>
                </div>

                <div className="form-text">
                  Upload images that represent your service (up to 5).
                </div>
              </div>

              <div className="card p-4 rounded-4">
                <button
                  className="btn btn-primary btn-lg w-100"
                  onClick={handleSubmit}
                >
                  Create service
                </button>
              </div>
            </div>

            <div className="col-lg-4">
              <div className="hint-card">
                <div className="card p-4 mb-4 rounded-4">
                  <h6>Советы по написанию</h6>
                  <ul className="small mt-2">
                    <li>Используйте понятный и простой язык</li>
                    <li>Добавляйте реальные примеры в галерею</li>
                    <li>Конкурентные цены привлекают первых клиентов</li>
                    <li>Быстрая доставка повышает конверсию</li>
                  </ul>
                </div>

                <div className="preview-card">

                  <ServiceCardComponent
                    title={title !== "" ? title : "Service preview"}
                    freelancerName={getUserData()?.firstName + " " + getUserData()?.lastName}
                    price={price}
                    image={titleImage}
                    isPreview={true}
                  />
                </div>
              </div>
            </div>
          </div>
        </div>
      </main>

      <FooterComponent />
    </>
  );
}
