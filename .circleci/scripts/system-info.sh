#!/bin/bash
echo "CPU Info"
cat /proc/cpuinfo
echo ""

echo "Mem Info"
cat /proc/meminfo
echo ""

echo "System Info"
uname -a
echo ""

echo "ULimits"
ulimit -a
echo ""

echo "Executing user"
whoami
echo ""

echo "Disk space"
df -h
echo ""

echo "Java Info"
java -version 2>&1
echo ""

echo "Maven Info"
mvn -version

echo "Current working dir"
pwd
echo ""

echo "Current working dir content"
ls -l 
echo ""