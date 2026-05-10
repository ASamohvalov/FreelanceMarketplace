import { useState } from "react";
import { getAvatarUrl } from "../../../logic/image";
import { useNavigate } from "react-router-dom";
import { useEffect } from "react";

export default function Avatar({userId, className}) {
  const navigate = useNavigate();

  const [noAvatar, setNoAvatar] = useState(false);

  useEffect(() => {
    setNoAvatar(false);
  }, [userId]);

  const style = { cursor: "pointer" };
  const url = userId ? getAvatarUrl(userId) : null;

  if (url && !noAvatar) {
    return (
      <img
        className={className}
        src={url}
        alt="Avatar"
        style={style}
        onClick={() => navigate("/profile/" + userId)}
        onError={(e) => {
          e.target.style.display = 'none'
          setNoAvatar(true);
        }}
      />
    );
  }

  return (
    <div
      className={className}
      style={style}
      onClick={() => navigate("/profile/" + userId)}
    />
  );
}
