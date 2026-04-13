export default function FileUploadComponent({ files, setFiles, title, maxFiles }) {
  return (
    <div className="card p-4 form-section rounded-4">
      <h5>{title}</h5>

      <div className="mb-3">
        <input
          type="file"
          className="form-control"
          onChange={(e) => {
            if (files.length === maxFiles) {
              return;
            }
            const array = [...files];
            array.push(e.target.files[0]);
            setFiles(array);
          }}
        />
      </div>

      <div className="mb-3">
        <div id="file-list">
          {files.map((image, idx) => (
            <div className="file-row" key={idx}>
              <span className="file-name">{image.name}</span>
              <span
                className="icon remove"
                onClick={() => {
                  const array = [...files];
                  array.splice(idx, 1);
                  setFiles(array);
                }}
              >
                ✕
              </span>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
