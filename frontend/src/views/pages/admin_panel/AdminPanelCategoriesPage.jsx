import { useNavigate } from "react-router-dom";
import "./css/admin_pages.css";
import { useEffect, useState } from "react";
import {
  createCategoryRequest,
  createSubcategoryRequest,
  deleteCategoryRequest,
  deleteSubcategoryRequest,
  getAllCategoryInfo,
} from "../../../logic/requests/service/categoryRequest";

export default function AdminPanelCategoriesPage() {
  const navigate = useNavigate();
  const [categories, setCategories] = useState([]);
  const [error, setError] = useState("");

  const [newCategory, setNewCategory] = useState("");
  const [newSubcategories, setNewSubcategories] = useState({});

  useEffect(() => {
    (async () => {
      const response = await getAllCategoryInfo();
      if (response.status !== 200) {
        navigate(`/error?code=${response.status}`);
        return;
      }

      setCategories(response.data);
    })();
  }, [navigate]);

  const createCategory = async () => {
    if (newCategory.length === 0 || newCategory.length > 50) {
      setError("Название категории не может быть пустым или больше 50 символов");
      return;
    }

    const response = await createCategoryRequest(newCategory);
    if (response.status === 400) {
      setError("Название категории уже занято");
      return;
    } else if (response.status !== 200) {
      navigate(`/error?code=${response.status}`);
      return;
    }

    setCategories([
      ...categories,
      {
        name: newCategory,
        id: response.data.id,
        subcategories: [],
      },
    ]);
  };

  const createSubcategory = async (catId) => {
    if (!newSubcategories[catId] || newSubcategories[catId].length === 0 || newSubcategories[catId].length > 50) {
      setError("Название подкатегории не может быть пустым или больше 50 символов");
      return;
    }

    const response = await createSubcategoryRequest(newSubcategories[catId], catId);
    if (response.status === 400) {
      setError("Название подкатегории уже занято");
      return;
    } else if (response.status !== 200) {
      navigate(`/error?code=${response.status}`);
      return;
    }

    setCategories(categories.map(cat => {
      return {
        ...cat,
        subcategories: [
          ...cat.subcategories,
          {
            name: newSubcategories[catId],
            id: response.data.id,
          }
        ]
      };
    }));

    setNewSubcategories(prev => ({ ...prev, [catId]: "" }));
  };

  const deleteCategory = async (id) => {
    if (!confirm("Вы уверены что хотите удалить категорию")) {
      return;
    }

    const response = await deleteCategoryRequest(id);
    if (response.status === 400) {
      setError("Категория используется в услугах, удаление недоступно");
      return;
    } else if (response.status !== 200) {
      navigate(`/error?code=${response.status}`);
      return;
    }

    setCategories(prev => prev.filter(cat => cat.id !== id));
  };

  const deleteSubcategory = async (id) => {
    if (!confirm("Вы уверены что хотите удалить подкатегорию")) {
      return;
    }

    const response = await deleteSubcategoryRequest(id);
    if (response.status === 400) {
      setError("Подкатегория используется в услугах, удаление недоступно");
      return;
    } else if (response.status !== 200) {
      navigate(`/error?code=${response.status}`);
      return;
    }

    setCategories(prev => prev.map(cat => {
      const hasSub = cat.subcategories.some(sub => sub.id === id);

      if (!hasSub) return cat;

      return {
        ...cat,
        subcategories: cat.subcategories.filter(sub => sub.id !== id)
      };
    }));

  };

  return (
    <main className="col-10 p-4 admin_main-flex">
      {error && (
        <div className="alert alert-danger" role="alert">
          {error}
        </div>
      )}
      <div className="admin-card mb-4">
        <h5 className="mb-3">Добавить категорию</h5>

        <input
          className="form-control mb-2"
          placeholder="Название категории"
          value={newCategory}
          onChange={(e) => setNewCategory(e.target.value)}
        />

        <button className="btn btn-primary" onClick={createCategory}>
          Добавить
        </button>
      </div>

      <div className="admin-card admin_div-scrolling">
        <h5 className="mb-3">Список категорий</h5>

        <div className="accordion">
          {categories.map((category, idx) => (
            <div
              className="accordion-item border rounded-top mb-4"
              id={`catAccordion${idx}`}
              key={idx}
            >
              <h2 className="accordion-header">
                <button
                  className="accordion-button"
                  data-bs-toggle="collapse"
                  data-bs-target={`#cat${idx}`}
                >
                  {category.name}
                </button>
              </h2>

              <div
                className="accordion-collapse collapse show"
                id={`cat${idx}`}
              >
                <div className="accordion-body">
                  {category.subcategories.map((subcategory, idx) => (
                    <div
                      className="d-flex justify-content-between mb-2 border-bottom p-1"
                      key={idx}
                    >
                      <span>{subcategory.name}</span>
                      <button className="btn btn-sm btn-dark" onClick={() => deleteSubcategory(subcategory.id)}>
                        <i className="bi bi-trash3-fill"></i>
                      </button>
                    </div>
                  ))}

                  <div className="d-flex gap-2 mt-3">
                    <input
                      className="form-control"
                      placeholder="Новая подкатегория"
                      value={newSubcategories[category.id] || ""}
                      onChange={(e) => {
                        setNewSubcategories((prev) => ({
                          ...prev,
                          [category.id]: e.target.value,
                        }));
                      }}
                    />
                    <button
                      className="btn btn-primary"
                      onClick={() => createSubcategory(category.id)}
                    >
                      +
                    </button>
                  </div>

                  <button className="btn btn-outline-dark mt-3" onClick={() => deleteCategory(category.id)}>Удалить</button>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </main>
  );
}
