import { useParams } from "react-router-dom";
import HeaderComponent from "../../components/HeaderComponent";
import "./css/service_page.css";
import FooterComponent from "../../components/FooterComponent";

export default function ServicePage() {
  const { id } = useParams();

  return (
    <>
      <HeaderComponent />
      <div class="container my-4">
        <nav class="mb-3 text-muted small">
          Services / Web / WordPress
        </nav>

        <h2 class="fw-bold mb-2">WordPress Website Development</h2>

        <div class="d-flex align-items-center gap-3 mb-4">
          <span class="text-warning">★★★★★</span>
          <span>4.9 (128 reviews)</span>
          <span class="badge bg-light text-dark">WordPress</span>
          <span class="badge bg-light text-dark">Landing</span>
        </div>

        <div class="row">
          <div class="col-lg-8">
            <div class="service-collage">
              <div class="collage-main"></div>
              <div class="collage-side">
                <div class="collage-item"></div>
                <div class="collage-item"></div>
                <div class="collage-item"></div>
                <div class="collage-item"></div>
              </div>
            </div>

            <div class="card p-4 mb-4 rounded-4">
              <h4 class="mb-3">About this service</h4>

              <p>
                I will create a modern, fast and fully responsive WordPress website
                tailored specifically to your business goals. The website will be
                optimized for performance, SEO and usability.
              </p>

              <p>
                This service is perfect for entrepreneurs, startups, small businesses
                and personal brands who want a professional online presence that
                converts visitors into customers.
              </p>

              <h5 class="mt-4">What you will get</h5>
              <ul>
                <li>Custom WordPress theme (no templates)</li>
                <li>Responsive design (mobile, tablet, desktop)</li>
                <li>SEO optimization</li>
                <li>Fast loading speed</li>
                <li>Easy admin panel</li>
              </ul>

              <h5 class="mt-4">Why choose me</h5>
              <ul>
                <li>5+ years of experience</li>
                <li>100+ completed projects</li>
                <li>Clear communication</li>
                <li>On-time delivery</li>
              </ul>

              <h5 class="mt-4">What I need from you</h5>
              <ul>
                <li>Website goals and references</li>
                <li>Text content (or request copywriting)</li>
                <li>Logo and brand colors</li>
              </ul>
            </div>

            <div class="card p-4 mb-4 rounded-4">
              <h4 class="mb-3">Reviews</h4>

              <div class="review">
                <div class="d-flex justify-content-between">
                  <strong>Alex</strong>
                  <span class="text-warning">★★★★★</span>
                </div>
                <p class="mt-2">
                  Amazing experience. The website looks clean and works perfectly.
                </p>
              </div>

              <div class="review">
                <div class="d-flex justify-content-between">
                  <strong>Maria</strong>
                  <span class="text-warning">★★★★☆</span>
                </div>
                <p class="mt-2">
                  Fast delivery and good communication. Would order again.
                </p>
              </div>
            </div>
          </div>
          <div class="col-lg-4">

            <div class="card p-4 service-sidebar rounded-4">
              <div class="price mb-3">1999 ₽</div>

              <button class="btn btn-primary w-100 mb-3">
                Order service
              </button>

              <button
                class="btn btn-primary w-100 mb-3"
                onClick={() => alert("kadjkad")}
              >
                Оставить отклик на обсуждение
              </button>
              <hr/>

              <div class="d-flex align-items-center gap-3">
                <div class="avatar">CJ</div>
                <div>
                  <strong>Cris James</strong>
                  <div class="text-muted small">Web Developer</div>
                </div>
              </div>

              <ul class="list-unstyled mt-3 small">
                <li>✔ Response time: 1 hour</li>
                <li>✔ Delivery: 3 days</li>
                <li>✔ Revisions: 2</li>
              </ul>
            </div>

          </div>
        </div>

        <h4 class="mt-5 mb-4">More services by this seller</h4>

        <div class="row g-4">
          <div class="col-md-4 col-lg-3">
            <div class="service-card">
              <div class="service-card-img"></div>
              <div class="service-card-body">
                <strong>Landing page design</strong>
                <div class="text-muted small">from 1499 ₽</div>
              </div>
            </div>
          </div>

          <div class="col-md-4 col-lg-3">
            <div class="service-card">
              <div class="service-card-img"></div>
              <div class="service-card-body">
                <strong>WooCommerce setup</strong>
                <div class="text-muted small">from 2499 ₽</div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <FooterComponent />
    </>
  );
}
