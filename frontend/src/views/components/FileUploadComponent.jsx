export default function FileUploadComponent({
  files,
  setFiles,
  title,
  maxFiles,
  type="all"
}) {

  const handleFileTypes = (files, maxFiles, targetFiles) => {
    if (files.length === maxFiles) return;

    const allowedExtensions = type === "all"
      ? ["pdf", "png", "doc", "docx", "jpg", "jpeg", "webapp"]
      : ["png", "jpg", "webp", "jpeg"]
    const newFiles = Array.from(targetFiles);

    const validFiles = newFiles.filter((file) => {
      const extension = file.name.split(".").pop().toLowerCase();
      return allowedExtensions.includes(extension);
    });

    if (validFiles.length !== newFiles.length) {
      alert(
        `Некоторые файлы не были добавлены. Разрешены только форматы: ${allowedExtensions.join(", ")}`,
      );
    }

    const array = [...files, ...validFiles].slice(0, maxFiles);
    return array;
  };

  return (
    <div className="card p-4 form-section rounded-4">
      <h5>{title}</h5>

      <div className="mb-3">
        <input
          type="file"
          className="form-control"
          onChange={(e) => {
            setFiles(handleFileTypes(files, maxFiles, e.target.files));
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

      <div className="form-text">
        Максимальное количество файлов: {maxFiles}.
      </div>
    </div>
  );
}
