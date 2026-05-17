import { useEffect, useState } from "react";
import { fromKopeck } from "../../../logic/lang";

export const Filters = ({ filters, variant = "заказы", setFilters }) => {
  const [temp, setTemp] = useState({
    title: filters.title,
    price: filters.price,
  });

  useEffect(() => {
    const a = () =>
      setTemp({
        title: filters.title,
        price: filters.price,
      });
    a();
  }, [filters]);

  return (
    <div>
      <form
        className="service-page-sidebar shadow-sm border"
        onSubmit={(e) => {
          e.preventDefault();
          setFilters((prev) => ({
            ...prev,
            title: temp.title,
            price: temp.price,
            active: true,
          }));
        }}
      >
        <h5 className="mb-3">Фильтры</h5>

        <div className="mb-3">
          <label className="form-label">Название</label>
          <input
            type="text"
            value={temp.title}
            onChange={(e) => setTemp({ ...temp, title: e.target.value })}
            className="form-control"
            placeholder="Поиск..."
          />
        </div>

        <div className="mb-3">
          <label className="form-label">Цена до: {fromKopeck(temp.price)}</label>
          <input
            type="range"
            min={0}
            max={
              variant === "заказы"
                ? filters.maxPrice
                : filters.maxFreelancerPrice
            }
            value={temp.price}
            onChange={(e) => setTemp({ ...temp, price: e.target.value })}
            className="form-range"
          />
        </div>

        <div className="mb-3">
          <label className="form-label">Тип услуги</label>
          <select className="form-select">
            <option>Все</option>
            <option>Frontend</option>
            <option>Backend</option>
            <option>Design</option>
          </select>
        </div>

        <button className="btn btn-main w-100" type="submit">
          Применить
        </button>
        <button
          className="btn btn-secondary w-100 mt-2"
          disabled={!filters.active}
          type="button"
          onClick={() => {
            setFilters((prev) => ({
              ...prev,
              title: "",
              price: prev.maxPrice,
              active: false,
            }));
          }}
        >
          Сбросить
        </button>
      </form>
    </div>
  );
};
