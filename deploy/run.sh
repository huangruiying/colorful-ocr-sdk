#!/bin/bash
# author Ryan.h
# ----------------
# java项目启动脚本
# ----------------

# 0.stop
docker stop regin-tools

sleep 3

# 1.clean
docker rm $(docker ps -a -q)

# 2.build
# docker build -t [REPOSITORY:TAG] .
docker build -t java_regin-tools:latest .
# docker run <-p [宿主机端口]:[容器端口]> <-d 表示后台启动> <-v [宿主机目录]:[容器目录]> <--name [启动的容器别名]> REPOSITORY:TAG
docker run -m 500m -p 8088:8088 -p 5005:5005 -d -v /var/tessdata/:/var/tessdata/ -v /var/ip2region/:/var/ip2region/ -v /var/logs/:/var/logs/ --name regin-tools java_regin-tools:latest 
#-v /usr/bin/:/usr/bin/ -v /usr/local/:/usr/local/ -v /usr/local/bin/:/usr/local/bin/ 
