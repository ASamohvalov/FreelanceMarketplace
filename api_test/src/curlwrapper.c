#include "curlwrapper.h"

#include <stdio.h>
#include <stdlib.h>

const char *perfom(const char* cmd)
{

}

Response send(Request *req)
{
  char cmd[256];
  if (req->type == POST) {
    snprintf(cmd, sizeof(cmd), "curl -d %s", req->url);
    int res = system(cmd);
    printf("%d\n", res);
  } else if (req->type == GET) {
    snprintf(cmd, sizeof(cmd), "curl %s", req->url);
    int res = system(cmd);
    printf("%d\n", res);
  }
}
