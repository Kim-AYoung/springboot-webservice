# 배포할 신규 버전 스프링 부트 프로젝트를 'stop.sh'에서 종료한 profile로 실행

#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh # import

REPOSITORY=/home/ec2-user/app/step3
PROJECT_NAME=springboot-webservice

echo "> Copy Build File"
cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo "> Deploy New Application"
JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

echo "> Add Execute Permissions To $JAR_NAME"
chmod +x $JAR_NAME

IDLE_PROFILE=$(find_idle_profile)
echo "> Execute $JAR_NAME (profile = $IDLE_PROFILE)"
nohup java -jar \
    -Dspring.config.location=classpath:/application.properties,classpath:/application-$IDLE_PROFILE.properties,/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-real-db.properties \
    -Dspring.profiles.active=$IDLE_PROFILE \
    $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &