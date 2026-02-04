#include "curlwrapper.h"

int main()
{
  Request req;
  req.type = POST;
  req.url = "http://localhost:8080/api/auth/sign_up";
  req.json_data = "{ \"username\": \"123@123.123\", \"password\": \"123\", \"firstName\": \"123\", \"lastName\": \"123\" }";
  send(&req);
  return 0;
}
