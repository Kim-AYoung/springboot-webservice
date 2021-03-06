# Nginx가 바라보는 스프링 부트를 최신 버전으로 변경

#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

function switch_proxy() {
    IDLE_PORT=$(find_idle_port)

    echo "> 전환할 Port: $IDLE_PORT"
    echo "> Port 전환"
    echo "set \$service_url http://127.0.0.1:${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc # service_url 덮어쓰기

    echo "> Nginx Reload"
    sudo service nginx reload # Nginx 설정을 다시 불러옴, 끊기는 현상 없음, 중요한 설정은 restart 사용
}
