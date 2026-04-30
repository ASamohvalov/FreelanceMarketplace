import { useParams } from "react-router-dom";
import OrderCard from "../../components/order/OrderCard";
import "./css/order_page.css";

export default function OrderPage() {
  const { id } = useParams();

  return (
    <main style={{ minHeight: "90vh" }}>
      <div class="container mt-5 mb-5">
        <div class="row g-4">
          <div class="col-lg-8">
            <OrderCard />
            <div class="order-card_box mb-4">
              <h6 class="fw-semibold mb-3">Действия</h6>

              <div class="d-flex flex-wrap gap-2">
                <button class="btn btn-primary">
                  <i class="bi bi-chat-dots me-1"></i>
                  Перейти в чат
                </button>

                <button class="btn btn-success">
                  <i class="bi bi-check-circle me-1"></i>
                  Сдать работу
                </button>

                <button class="btn btn-outline-secondary">
                  <i class="bi bi-star me-1"></i>
                  Оставить отзыв
                </button>

                <button class="btn btn-outline-danger">
                  <i class="bi bi-x-circle me-1"></i>
                  Отменить заказ
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>
  );
}
