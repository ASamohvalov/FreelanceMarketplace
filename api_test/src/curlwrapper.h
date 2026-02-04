typedef struct
{
  const char *data;
  int code;
} Response;

typedef enum
{
  POST, GET, PUT, PATCH, DELETE
} RequestType;

typedef struct
{
  const char *url;
  RequestType type;
  const char *json_data;
} Request;

Response send(Request *);
