export default function FooterComponent() {
  return (
    <footer>
      <div className="container">
        <div className="row">
          <div className="col-md-4">
            <h5>Freelance Market</h5>
            <p className="small mt-3">
              Connecting businesses with top freelance talent worldwide.
            </p>
          </div>

          <div className="col-md-4">
            <h6>Company</h6>
            <ul className="list-unstyled small mt-3">
              <li>
                <a href="#">About</a>
              </li>
              <li>
                <a href="#">Careers</a>
              </li>
              <li>
                <a href="#">Support</a>
              </li>
            </ul>
          </div>

          <div className="col-md-4">
            <h6>Legal</h6>
            <ul className="list-unstyled small mt-3">
              <li>
                <a href="#">Terms</a>
              </li>
              <li>
                <a href="#">Privacy</a>
              </li>
            </ul>
          </div>
        </div>

        <hr className="bg-secondary mt-4" />
        <div className="text-center small">
          © 2025 Freelance Market. All rights reserved.
        </div>
      </div>
    </footer>
  );
}
