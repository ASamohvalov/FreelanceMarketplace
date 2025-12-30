cmake_minimum_required(VERSION 3.23)

project(api_test)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

add_executable(api_test
  src/main.c
  src/curlwrapper.c src/curlwrapper.h
)
