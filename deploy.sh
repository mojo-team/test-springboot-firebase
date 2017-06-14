#!/bin/sh

set -x

find .

sshpass -e scp -vv target/*.jar vivatech@vivatech.pintade.org:/home/vivatech/
sshpass -e scp -vv run.sh vivatech@vivatech.pintade.org:/home/vivatech/
sshpass -e ssh -vv vivatech@vivatech.pintade.org ./run.sh
