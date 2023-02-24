REPOSITORY=/home/ubuntu
 
cd $REPOSITORY
 
JAR_NAME=$(ls $REPOSITORY/build/libs | grep '*SNAPSHOT.war' | tail -n 1)
JAR_PATH=$REPOSITORY/$JAR_NAME
 
CURRENT_PID=$(pgrep -f *.war)
 
if [ -z $CURRENT_PID ]
then
  echo "> Nothing to end."
else
  echo "> kill -9 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi
 
echo "> $JAR_PATH deploy"
nohup java -jar $JAR_PATH --spring.profiles.active=dev > /dev/null 2> /dev/null < /dev/null &
