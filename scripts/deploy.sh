REPOSITORY=/home/ubuntu

echo "> 빌드 파일 복사"
cp $REPOSITORY/build/libs/*SNAPSHOT.war $REPOSITORY/

echo "> 프로세스 아이디 확인"
CURRENT_PID=$(pgrep -f *.war)

if [ -z $CURRENT_PID ]
then
  echo "> 실행중인 프로세스 없음"
else
  echo "> kill -9 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> 새 애플리케이션 배포"
JAR_NAME=$(ls -tr $REPOSITORY/ | grep war | tail -n 1)
nohup java -jar $REPOSITORY/$JAR_NAME --spring.profiles.active=dev > /dev/null 2> /dev/null < /dev/null &
