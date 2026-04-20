function getInitials(name = "", surname = "") {
  return `${name.charAt(0)}${surname.charAt(0)}`.toUpperCase();
}

export default function AvatarComponent({ name, surname, src, size = 40 }) {
  if (src) {
    return (
      <img
        src={src}
        alt={`${name} ${surname}`}
        style={{
          width: size,
          height: size,
          borderRadius: "50%",
          objectFit: "cover",
        }}
      />
    );
  }

  return (
    <div
      style={{
        width: size,
        height: size,
        borderRadius: "50%",
        backgroundColor: "#5c6bc0",
        color: "white",
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        fontWeight: "bold",
      }}
    >
      {getInitials(name, surname)}
    </div>
  );
};
