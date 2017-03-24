#!/usr/bin/env bash

if [ -x "$BASE_DIR/jdk/bin/java" ]; then
  JAVA="$BASE_DIR/jdk/bin/java"
elif [ "$JAVA_HOME" != "" ]; then
  JAVA="$JAVA_HOME/bin/java"
else
  JAVA=java
fi


LANG=en_US.UTF-8
#set classpath
CLASS_PATH="$BASE_DIR/module/*:$BASE_DIR/lib/*"
#set logback
LOGBACK_CONFIGFILE="$BASE_DIR/conf/logback.xml"
#set mainclass
MAIN_CLASS="uyun.boltdog.api.rest.Start"
#start jvm
if [ -z "$JAVA_OPTS" ]; then
	JAVA_OPTS="-Xms128m -Xmx1024m"
fi