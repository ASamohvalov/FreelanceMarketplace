import { useEffect, useState } from "react";
import ReactMarkdown from "react-markdown";
import { useNavigate, useSearchParams } from "react-router-dom";
import { getUserData, hasRole, isAuth } from "../../../logic/jwt";
import {
  getCurrentPointRateRequest
} from "../../../logic/requests/payment/accountRequest";
import { getAllCategoryInfo } from "../../../logic/requests/service/categoryRequest";
import {
  createServiceRequest,
  editServiceRequest,
  getServiceByIdRequest,
} from "../../../logic/requests/service/serviceRequest";
import LoadingInput from "../../components/elements/LoadingInput";
import NavLocation from "../../components/elements/NavLocation";
import FileUploadComponent from "../../components/FileUploadComponent";
import ServiceCardComponent from "../../components/service/ServiceCardComponent";
import "./css/create_service_page.css";
import { fromKopeck } from "../../../logic/lang";

export default function CreateServicePage({ isEdit = false }) {
  const navigate = useNavigate();
  const [error, setError] = useState(null);
  const [searchParams] = useSearchParams();

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
  const [submitDisable, setSubmitDisable] = useState(false);

  const [freeServiceMode, setFreeServiceMode] = useState(false);
  const [currentPointRate, setCurrentPointRate] = useState(null);

  useEffect(() => {
    if (!isAuth() || !hasRole("ROLE_FREELANCER")) {
      navigate(-1);
      return;
    }

    document.title = isEdit ? "Редактировние услуги" : "Создание услуги";

    if (isEdit && !searchParams.get("id")) {
      navigate(-1);
      return;
    }

    (async () => {
      const response = await getAllCategoryInfo();
      if (response.status !== 200) {
        navigate(`/error?code=${response.status}`);
        return;
      }
      setCategories(response.data);

      if (isEdit) {
        const serviceInfoResponse = await getServiceByIdRequest(
          searchParams.get("id"),
        );
        if (response.status !== 200) {
          navigate(`/error?code=${response.status}`);
          return;
        }
        const category = response.data.find(
          (c) => c.id === serviceInfoResponse.data.categoryId,
        );
        const subcategory = category.subcategories.find(
          (sc) => sc.id === serviceInfoResponse.data.subcategoryId,
        );

        setTitle(serviceInfoResponse.data.title);
        setDescription(serviceInfoResponse.data.description);
        setSelectedCategory(category.id);
        setSelectedSubcategory(subcategory.id);
        setPrice(Math.floor(serviceInfoResponse.data.price / 100));
        setFreeServiceMode(serviceInfoResponse.data.type === "FREE");
      } else {
        setSelectedCategory(response.data[0].id);
        setSelectedSubcategory(response.data[0].subcategories[0].id);
      }
    })();
  }, [navigate, isEdit, searchParams]);

  async function handleSubmit(event) {
    event.preventDefault();

    if (title.length < 10) {
      setError("Ошибка: минимальная длина заголовка - 10 символов");
      return;
    }
    if (!freeServiceMode && price < 20) {
      setError("Ошибка: минимальная цена - 20 рублей");
      return;
    }
    if (description.length < 100) {
      setError("Ошибка: минимальная длина описания - 100 символов");
      return;
    }
    if (!titleImage) {
      setError("Ошибка: основное изображение обязательно!");
      return;
    }
    if (images.length === 0 || images.length > 4) {
      setError("Ошибка: в галлереи должно быть от 1 до 4 изображений");
      return;
    }

    setSubmitDisable(true);
    const data = {
      title: title,
      titleImage: titleImage,
      description: description,
      price: price * 100,
      subcategoryId: selectedSubcategory,
      images: images,
      type: freeServiceMode ? "FREE" : "USUAL",
    };
    const response = isEdit
      ? await editServiceRequest(searchParams.get("id"), data)
      : await createServiceRequest(data);
    if (response.status !== 200) {
      navigate("/error");
      return;
    }
    const da = categories.filter(
      (category) => category.id === selectedCategory,
    )[0];
    navigate(`/${isEdit ? "update" : "create"}-service/success`, {
      state: {
        serviceId: response.data.id,
        serviceName: title,
        category: da, // todo
        selectedSubcategory: da.subcategories.filter(
          (item) => item.id === selectedSubcategory,
        )[0], //todo
        price: price * 100,
      },
    });
  }

  const changeServiceMode = async () => {
    if (!freeServiceMode) {
      if (currentPointRate === null) {
        const response = await getCurrentPointRateRequest();
        if (response.status !== 200) {
          navigate("/error");
          return;
        }
        setCurrentPointRate(response.data.rate)
      }
      setPrice(0);
      setFreeServiceMode(true);
    } else {
      setPrice(599);
      setFreeServiceMode(false);
    }
  };

  return (
    <div className="container my-4">
      <NavLocation>
        Услуги / {isEdit ? "Редактирование" : "Создание новой"}
      </NavLocation>
      <h2 className="fw-bold mb-2">
        {isEdit ? "Редактирование услуги" : "Создание новой услуги"}
      </h2>
      <p className="text-muted mb-3">
        Заполните приведенную ниже информацию, чтобы клиенты могли легко найти и
        понять суть вашего сервиса.
      </p>
      <div className="form-check form-switch mb-3">
        <input
          className="form-check-input"
          type="checkbox"
          id="freeServiceInput"
          onChange={changeServiceMode}
          checked={freeServiceMode}
        />
        <label
          className="form-check-label small text-muted"
          htmlFor="freeServiceInput"
        >
          Бесплатная услуга
        </label>
      </div>

      <div className="row">
        <div className="col-lg-8 mb-4">
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
            </div>

            <div className="row">
              <div className="col-md-6 mb-3">
                <label className="form-label text-secondary">Категория</label>
                {categories.length === 0 ? (
                  <LoadingInput />
                ) : (
                  <select
                    className="form-select"
                    value={selectedCategory.id}
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
                    value={selectedSubcategory.id}
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
              <a
                href="https://www.markdownlang.com/ru/cheatsheet/"
                target="_blank"
              >
                <small className="h6 text-decoration-none">
                  (в формате MarkDown)
                </small>
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
              <ReactMarkdown>{description}</ReactMarkdown>
            </div>
          </div>

          {!freeServiceMode && (
            <div className="card p-4 form-section rounded-4">
              <h5>
                Цена <small>(в рублях)</small>
              </h5>

              <div className="row">
                <div className="mb-3">
                  <input
                    type="number"
                    className="form-control"
                    value={price}
                    onChange={(e) =>
                      e.target.value <= 999999 && setPrice(e.target.value)
                    }
                  />
                </div>
              </div>
            </div>
          )}

          <div className="card p-4 form-section rounded-4">
            <h5>Основное изображение</h5>

            <div className="mb-3">
              <input
                type="file"
                className="form-control"
                onChange={(e) => {
                  setTitleImage(e.target.files[0]);
                }}
              />
            </div>

            <div className="form-text"></div>
          </div>

          <FileUploadComponent
            title={"Галлерея"}
            files={images}
            setFiles={setImages}
            maxFiles={5}
            type={"images"}
          />

          {error && <div className="alert alert-danger">{error}</div>}

          <div className="card p-4 rounded-4">
            <button
              className="btn btn-primary btn-lg w-100"
              onClick={handleSubmit}
              disabled={submitDisable}
            >
              {isEdit ? "Редактировать услугу" : "Создать услугу"}
            </button>
          </div>
        </div>

        <div className="col-lg-4">
          <div className="hint-card">
            {freeServiceMode && (
              <div className="card p-4 mb-4 rounded-4">
                <h6>Информация о бесплатной услуге</h6>
                <ul className="small mt-2">
                  <li>
                    За выполнение услуги вам начислится 1 балл ({fromKopeck(currentPointRate)} по текущему
                    курсу)
                  </li>
                  <li>За бесплатную услугу заказчик ничего не платит</li>
                  <li>
                    Основная задача - получение опыта во взаимодействии с
                    заказчиком и разработке коммерческого проекта
                  </li>
                </ul>
              </div>
            )}

            <div className="card p-4 mb-4 rounded-4">
              <h6>Советы по написанию</h6>
              <ul className="small mt-2">
                <li>Используйте понятный и простой язык</li>
                <li>Добавляйте реальные примеры в галерею</li>
                <li>Конкурентные цены привлекают первых клиентов</li>
              </ul>
            </div>

            <div className="preview-card">
              <ServiceCardComponent
                title={title !== "" ? title : "Service preview"}
                freelancerName={
                  getUserData()?.firstName + " " + getUserData()?.lastName
                }
                price={price * 100}
                image={titleImage}
                isPreview={true}
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
