import axios from 'axios';
import { useEffect, useState } from 'react';

export default function FileItemComponent({ fileId, idx, getReportFileUrl }) {
  const [extension, setExtension] = useState('...');
  const [isImage, setIsImage] = useState(false);
  const fileUrl = getReportFileUrl(fileId);

  useEffect(() => {
    const getFriendlyExtension = (contentType) => {
      if (!contentType) return 'file';

      const mimeMap = {
        'application/vnd.openxmlformats-officedocument.wordprocessingml.document': 'docx',
        'application/msword': 'doc',
        'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet': 'xlsx',
        'application/vnd.ms-excel': 'xls',
        'application/pdf': 'pdf',
        'image/jpeg': 'jpg',
        'image/png': 'png',
        'image/gif': 'gif',
        'image/webp': 'webp'
      };

      if (mimeMap[contentType]) {
        return mimeMap[contentType];
      }

      return contentType.split('/').pop().split(';')[0];
    };

    const fetchFileType = async () => {
      try {
        const response = await axios.head(fileUrl);
        const contentType = response.headers['content-type'];
        const ext = getFriendlyExtension(contentType);
        setExtension(ext);
      } catch (err) {
        console.error("Не удалось определить тип файла", err);
        setExtension('file');
      }
    };

    fetchFileType();
  }, [fileUrl]);

  const handleDownload = async () => {
    try {
      const response = await axios.get(fileUrl, {
        responseType: 'blob',
      });

      const blob = new Blob([response.data]);
      const url = window.URL.createObjectURL(blob);

      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', `${idx + 1}.${extension}`);
      document.body.appendChild(link);
      link.click();

      link.remove();
      window.URL.revokeObjectURL(url);
    } catch (err) {
      console.error("Ошибка при скачивании:", err);
    }
  };

  return (
    <div className="order-report-modal-window_file-item d-flex align-items-center justify-content-between mb-2">
      <div>
        <i className={`bi ${isImage ? "bi-image" : "bi-file-earmark-text"} me-2`}></i>
        {idx + 1}.{extension}
      </div>
      <button
        className="btn btn-sm btn-outline-primary"
        onClick={handleDownload}
      >
        Скачать
      </button>
    </div>
  );
};
