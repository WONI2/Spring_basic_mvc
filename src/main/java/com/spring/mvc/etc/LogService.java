package com.spring.mvc.etc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j //로그 라이브러리
public class LogService {

    /*
    * - 로그 : 발생시간, 로그 레벨, 파일 저장
    * - 로그 라이브러리 : logback, log4j, slf4j...
    * -로그는 발생 시간이 가장 중요함.
    *
    * - 로그 레벨(내려갈수록 심각한 것)
    * 1. trace : 개발할 때 씀. 애플리케이션 실행흐름 세부정보, 디버깅목적
    * 2. debug : 변수값, 파라미터값 등 내부정보 출력. 디버깅목적
    * =======운영서버 ▼(3~6) ===============================
    * 3. info : 운영환경에서 일반적인 작동 정보들, 시스템 상태, 진행중인 작업 정보
    * ====================개발서버 환경 ▲(1~3)==========================
    * 4. warn : 잠재적인 문제상황을 경고.(현재시점에 에러가 난 것은 아님), 구성값이 예상범위를 벗어났거나 시스템 리소스 부족
    * 5. error : 예외가 발생하거나 기능이 실패했을 때 심각한 문제 상황
    * 6. fatal : 치명적 오류, 시스템이 지속될 수 없는 상황. 즉각 조치가 필요한 경우
    *
    * */

    public void showLog() {
//        System.out.println("hello");

        log.trace("trace");
        log.debug("debug");
        log.info("info");
        log.warn("warn");
        log.error("error");

    }

}
