#!/bin/bash

usage="Usage: infouser.sh param"
    if [ $# -ne 1 ]; then 
            echo $usage; exit 1
    else 
        user=$1
    fi

for it in `cat /etc/passwd | cut -d: -f1`; do
    if [ $it == $user ]; then
        home=`cat /etc/passwd | grep "^$user\>" | cut -d: -f6`
        echo "Home: " $home
        size=`du -sh $home | cut -d: -f1`
        echo "Home size: " $size
        processes=`ps -u $it | wc -l`
        echo "Active processes: " $processes
    fi
done
