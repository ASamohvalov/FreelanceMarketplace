import HeaderComponent from "../components/HeaderComponent";
import FooterComponent from "../components/FooterComponent";
import './css/home_page.css';

export default function HomePage() {
  return (
    <>
      <HeaderComponent />
      <section className="hero text-center">
        <div className="container">
          <h1 className="display-5 fw-bold">Find the Perfect Freelancer for Your Project</h1>
          <p className="lead mt-3">Web development, design, marketing and more — all in one place.</p>

          <div className="hero-search input-group">
            <input type="text" className="form-control form-control-lg" placeholder="Search for services..." />
            <button className="btn btn-light btn-lg">Search</button>
          </div>
        </div>
      </section>

      <section className="py-5">
        <div className="container">
          <h2 className="text-center mb-5">Popular Categories</h2>

          <div className="row g-4">

            <div className="col-md-3">
              <div className="category-card">
                <h5>Development & IT</h5>
                <small className="text-muted">1200+ services</small>
              </div>
            </div>

            <div className="col-md-3">
              <div className="category-card">
                <h5>Design & Creative</h5>
                <small className="text-muted">980+ services</small>
              </div>
            </div>

            <div className="col-md-3">
              <div className="category-card">
                <h5>Marketing & Sales</h5>
                <small className="text-muted">760+ services</small>
              </div>
            </div>

            <div className="col-md-3">
              <div className="category-card">
                <h5>Writing & Content</h5>
                <small className="text-muted">640+ services</small>
              </div>
            </div>

          </div>
        </div>
      </section>

      <section className="py-5 bg-light">
        <div className="container">
          <h2 className="text-center mb-5">Featured Services</h2>

          <div className="row g-4">

            <div className="col-md-4">
              <div className="service-card">
                <div className="service-img"></div>
                <div className="p-4">
                  <strong>I will build your WordPress website</strong>
                  <div className="text-muted small mt-2">Cris James</div>
                  <div className="mt-3 fw-bold">From 1999 ₽</div>
                </div>
              </div>
            </div>

            <div className="col-md-4">
              <div className="service-card">
                <div className="service-img"></div>
                <div className="p-4">
                  <strong>I will design a modern logo</strong>
                  <div className="text-muted small mt-2">Anna Lee</div>
                  <div className="mt-3 fw-bold">From 999 ₽</div>
                </div>
              </div>
            </div>

            <div className="col-md-4">
              <div className="service-card">
                <div className="service-img"></div>
                <div className="p-4">
                  <strong>I will create a marketing strategy</strong>
                  <div className="text-muted small mt-2">Michael Scott</div>
                  <div className="mt-3 fw-bold">From 2499 ₽</div>
                </div>
              </div>
            </div>

          </div>
        </div>
      </section>

      <section className="py-5">
        <div className="container">
          <h2 className="text-center mb-5">How It Works</h2>

          <div className="row g-4">

            <div className="col-md-4">
              <div className="step-card">
                <h4>1️⃣ Search</h4>
                <p className="text-muted mt-3">Browse thousands of services and find what you need.</p>
              </div>
            </div>

            <div className="col-md-4">
              <div className="step-card">
                <h4>2️⃣ Hire</h4>
                <p className="text-muted mt-3">Choose the best freelancer and place your order.</p>
              </div>
            </div>

            <div className="col-md-4">
              <div className="step-card">
                <h4>3️⃣ Get Results</h4>
                <p className="text-muted mt-3">Receive high-quality work delivered on time.</p>
              </div>
            </div>

          </div>
        </div>
      </section>

      <section className="cta">
        <div className="container">
          <h2>Start Selling Your Services Today</h2>
          <p className="mt-3">Join thousands of freelancers earning online.</p>
          <a href="#" className="btn btn-light btn-lg mt-3">Become a Freelancer</a>
        </div>
      </section>

      <FooterComponent />
    </>
  );
}
