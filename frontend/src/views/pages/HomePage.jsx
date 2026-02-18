import HeaderComponent from "../components/HeaderComponent";
import FooterComponent from "../components/FooterComponent";
import './css/home_page.css';

export default function HomePage() {
  return (
    <>
      <HeaderComponent />
      <section class="hero text-center">
        <div class="container">
          <h1 class="display-5 fw-bold">Find the Perfect Freelancer for Your Project</h1>
          <p class="lead mt-3">Web development, design, marketing and more — all in one place.</p>

          <div class="hero-search input-group">
            <input type="text" class="form-control form-control-lg" placeholder="Search for services..." />
            <button class="btn btn-light btn-lg">Search</button>
          </div>
        </div>
      </section>

      <section class="py-5">
        <div class="container">
          <h2 class="text-center mb-5">Popular Categories</h2>

          <div class="row g-4">

            <div class="col-md-3">
              <div class="category-card">
                <h5>Development & IT</h5>
                <small class="text-muted">1200+ services</small>
              </div>
            </div>

            <div class="col-md-3">
              <div class="category-card">
                <h5>Design & Creative</h5>
                <small class="text-muted">980+ services</small>
              </div>
            </div>

            <div class="col-md-3">
              <div class="category-card">
                <h5>Marketing & Sales</h5>
                <small class="text-muted">760+ services</small>
              </div>
            </div>

            <div class="col-md-3">
              <div class="category-card">
                <h5>Writing & Content</h5>
                <small class="text-muted">640+ services</small>
              </div>
            </div>

          </div>
        </div>
      </section>

      <section class="py-5 bg-light">
        <div class="container">
          <h2 class="text-center mb-5">Featured Services</h2>

          <div class="row g-4">

            <div class="col-md-4">
              <div class="service-card">
                <div class="service-img"></div>
                <div class="p-4">
                  <strong>I will build your WordPress website</strong>
                  <div class="text-muted small mt-2">Cris James</div>
                  <div class="mt-3 fw-bold">From 1999 ₽</div>
                </div>
              </div>
            </div>

            <div class="col-md-4">
              <div class="service-card">
                <div class="service-img"></div>
                <div class="p-4">
                  <strong>I will design a modern logo</strong>
                  <div class="text-muted small mt-2">Anna Lee</div>
                  <div class="mt-3 fw-bold">From 999 ₽</div>
                </div>
              </div>
            </div>

            <div class="col-md-4">
              <div class="service-card">
                <div class="service-img"></div>
                <div class="p-4">
                  <strong>I will create a marketing strategy</strong>
                  <div class="text-muted small mt-2">Michael Scott</div>
                  <div class="mt-3 fw-bold">From 2499 ₽</div>
                </div>
              </div>
            </div>

          </div>
        </div>
      </section>

      <section class="py-5">
        <div class="container">
          <h2 class="text-center mb-5">How It Works</h2>

          <div class="row g-4">

            <div class="col-md-4">
              <div class="step-card">
                <h4>1️⃣ Search</h4>
                <p class="text-muted mt-3">Browse thousands of services and find what you need.</p>
              </div>
            </div>

            <div class="col-md-4">
              <div class="step-card">
                <h4>2️⃣ Hire</h4>
                <p class="text-muted mt-3">Choose the best freelancer and place your order.</p>
              </div>
            </div>

            <div class="col-md-4">
              <div class="step-card">
                <h4>3️⃣ Get Results</h4>
                <p class="text-muted mt-3">Receive high-quality work delivered on time.</p>
              </div>
            </div>

          </div>
        </div>
      </section>

      <section class="cta">
        <div class="container">
          <h2>Start Selling Your Services Today</h2>
          <p class="mt-3">Join thousands of freelancers earning online.</p>
          <a href="#" class="btn btn-light btn-lg mt-3">Become a Freelancer</a>
        </div>
      </section>

      <FooterComponent />
    </>
  );
}
