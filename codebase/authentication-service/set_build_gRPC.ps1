# set_build_gRPC.ps1
# 1. Set JAVA_HOME tạm thời cho session này
$env:JAVA_HOME="D:\vunh\Sharyo_dev\java\17"
$env:PATH="$env:JAVA_HOME\bin;D:\vunh\apache-maven-3.9.11\bin;$env:PATH"

# 2. Chạy Maven clean compile trong module authentication-service
cd "D:\vunh\microservice\codebase\authentication-service"
mvn.cmd clean compile
