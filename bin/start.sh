#!/bin/bash
BASE_DIR=$(cd `dirname $0`; pwd)
BASE_DIR=`dirname "$BASE_DIR"`
cd $BASE_DIR

if [ ! -d temp ]; then
	mkdir temp
fi

. $BASE_DIR/bin/serverEnv.sh

module="note"
logDir="/opt/note/logs"
jarName="note-0.0.1-SNAPSHOT.jar"


BOLTDOGPIDFILE="temp/$module.pid"
if [ -f "$BOLTDOGPIDFILE" ]; then
  if kill -0 `cat "$BOLTDOGPIDFILE"` > /dev/null 2>&1; then
	 echo already running as process `cat "$BOLTDOGPIDFILE"`. 
	 exit 0
  fi
fi

#start jvm
$JAVA $JAVA_OPTS -Dlog.rootDir=$logDir -Dlog.module=$module -D$module -Dlogback.configurationFile=$LOGBACK_CONFIGFILE \
-Dbase.dir=$BASE_DIR -cp $CLASS_PATH -jar ./module/$jarName >/dev/null 2>&1 &

if [ $? -eq 0 ]
	then
		if /bin/echo -n $! > "$BOLTDOGPIDFILE"
	then
		sleep 1
		echo STARTED
	else
		echo FAILED TO WRITE PID
		exit 1
	fi
else
  echo SERVER DID NOT START
  exit 1
fi