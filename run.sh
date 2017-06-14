#!/bin/sh

set -x

PID=$( cat ./run.pid )

if [ "$PID" != "" ]
then
    kill -ABRT $PID
    > ./run.pid
fi

nohup java -jar test-springboot-firebase.jar > ./run.log &

jps -l | grep test-springboot-firebase.jar | cut -d' ' -f1 > ./run.pid
