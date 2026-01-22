import HeaderComponent from "../../components/HeaderComponent";

export default function ErrorPage({ message }) {

  return (
    <>
      <HeaderComponent />
      <main>
        <div
          className={`mb-2 bg-danger p-4 border border-danger rounded shadow"}`}
        >
          { message }
        </div>
      </main>
    </>
  );
}
