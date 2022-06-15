# start, stop, switch, health 스크립트 파일에서 공용으로 사용할 'profile'과 port 체크 로직

#!/usr/bin/env bash

# 쉬고 있는 profile 찾기 : real1이 사용 중이면 real2가 쉬고, real2가 사용 중이면 real이 쉼
function find_idle_profile() {
    # 현재 Nginx가 바라보고 있는 스프링 부트가 정상적으로 수행 중인지 확인하고 응답 값을 HttpStatus로 받음
    RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://locahost/profile)

    if [ ${RESPONSE_CODE} -ge 400 ] # code가 400 이상이면 즉, 오류가 발생한다면 real2를 현재 profile로 사용
    then
        CURRENT_PROFILE=real2
    else
        CURRENT_PROFILE=$(curl -s http://localhost/profile)
    fi

    if [ ${CURRENT_PROFILE} == real1 ]
    then
        IDLE_PROFILE=real2
    else
        IDLE_PROFILE=real1
    fi

    echo "${IDLE_PROFILE}"
}

# 쉬고 있는 profile의 port 찾음
# 쉬고 있는 profile을 스프링 부트 프로젝트와 연결하기 위해
function find_idle_port() {
    IDLE_PROFILE=$(find_idle_profile) # bash라는 스크립트는 값을 반환하는 기능이 없기 때문에 다음과 같이 사용

    if [ ${IDLE_PROFILE} == real1 ]
    then
        echo "8081"
    else
        echo "8082"
    fi
}