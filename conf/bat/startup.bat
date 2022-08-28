@title Asst
@echo on
java -Dfile.encoding=utf-8 -Xss4096K -Xms512M -Xmx512M -jar asst-2022.8.jar --server.address=127.0.0.1 --server.port=8080 --server.servlet.context-path=/
pause
