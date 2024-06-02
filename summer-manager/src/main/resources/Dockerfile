FROM jdk:21.0.2
MAINTAINER gvp9132 <wjj910302@gmail.com>
#系统编码
ENV LANG=C.UTF-8 LC_ALL=C.UTF-8
WORKDIR /app

ADD build/libs/summer-manager-1.0.0-SNAPSHOT.jar /app/summer-manager.jar
#RUN ln -snf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo Asia/Shanghai > /etc/timezone
#启动容器时的进程
ENTRYPOINT ["java","-jar","/app/summer-manager.jar"]

#暴露8080端口
EXPOSE 8081