#!/bin/sh

PID=$( cat ./run.pid )

kill -11 $PID

nohup java -jar test-springboot-firebase.jar &

echo $! > ./run.pid
