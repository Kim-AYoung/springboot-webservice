# 기존 Nginx에 연결되어 있진 않지만, 실행 중이던 스프링 부트 종료

#!/usr/bin/env bash

ABSPATH=$(readlink -f $0) # 현재 stop.sh가 속해 있는 경로 찾기
ABSDIR=$(dirname $ABSPATH) # 해당 경로에서 diretory가 아닌 접미사는 제거하기
source ${ABSDIR}/profile.sh # import

IDLE_PORT=$(find_idle_port)

echo "> $IDLE_PORT 에서 구동중인 애플리케이션 pid 확인"
IDLE_PID=$(lsof -ti tcp:${IDLE_PORT})

if [ -z ${IDLE_PID} ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $IDLE_PID"
  kill -15 ${IDLE_PID}
  sleep 5
fi
