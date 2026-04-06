import { useEffect } from "react";
import HeaderComponent from "../../components/HeaderComponent";
import { useState } from "react";
import LoadingComponent from "../../components/LoadingComponent";
import { getAllServicesRequest } from "../../../logic/requests/service/serviceRequest";
import { useNavigate, useSearchParams } from "react-router-dom";
import ServicesListComponent from "../../components/service/ServicesListComponent";
import FooterComponent from "../../components/FooterComponent";
import "./css/services_page.css";
import { Filters } from "../../components/services/Filters";

export default function ServicesPage({func}) {
    const navigate = useNavigate();
    const [services, setServices] = useState([]);
    // const [images, setImages] = useState([]);
    const [loading, setLoading] = useState(true);
    const [searchParams, setSearchParams] = useSearchParams();
    const [filters, setFilters] = useState({
        title: searchParams.get("search") || "",
        price: 0,
        maxPrice: 0,
        active: searchParams.get("search") || false,
    });

    useEffect(() => {
        document.title = "Услуги";

        (async () => {
            const result = await func();
            if (result.status !== 200) {
                navigate("/error");
                return;
            }
            setServices(result.data);
            const maxPrice = Math.max(...result.data.map((service) => service.price));
            setFilters((prev) => ({
                ...prev,
                price: maxPrice,
                maxPrice: maxPrice,
            }));
            setLoading(false);
        })();
    }, [navigate, func]);

    return (
        <>
            <main style={{ minHeight: "80vh", marginLeft:80 }}>
                <div className="container mt-4 mb-4">
                    <div className="row align-items-start">
                        <div
                            className="col-lg-3 mb-4 position-sticky"
                            style={{ top: "82px" }}
                        >
                            <Filters
                                services={[services, setServices]}
                                filters={filters}
                                setFilters={setFilters}
                            />
                        </div>

                        {loading ? (
                            <LoadingComponent />
                        ) : (
                            <ServicesListComponent
                                services={services.filter((service) => {
                                    return (
                                        service.title
                                            .toLowerCase()
                                            .includes(
                                                filters.title.toLowerCase(),
                                            ) && service.price <= filters.price
                                    );
                                })}
                            />
                        )}
                    </div>
                </div>
            </main>
        </>
    );
}
