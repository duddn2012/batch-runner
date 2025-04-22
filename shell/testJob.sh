#!/bin/bash

# 실행할 JAR 파일 경로
JAR_PATH="D:\Project\batch-runner\target\demo-0.0.1-SNAPSHOT.jar"

# 실행할 Job 이름
JOB_NAME="TestJob"

MODE="sync"

# 실행 시간 기준 파라미터
CURR_SECONDS=$(date +%s)
CURR_DATE=$(date +%Y%m%d)


# Job 실행
java -jar "$JAR_PATH" "$JOB_NAME" "$MODE" #"job.id=$CURR_SECONDS" "job.date=$CURR_DATE" > "$LOG_FILE" 2>&1

# 실행 결과 출력
echo "Job '$JOB_NAME' 실행 완료. 로그: $LOG_FILE"
