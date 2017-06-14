#!/bin/sh

env

set -x

sshpass -e scp target/*.jar vivatech@vivatech.pintade.org:/home/vivatech/
sshpass -e scp run.sh vivatech@vivatech.pintade.org:/home/vivatech/
sshpass -e ssh vivatech@vivatech.pintade.org ./run.sh
