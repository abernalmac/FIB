#!/bin/bash
max=0
usage="Usage: ocupacio.sh param"
    if [ $# -ne 1 ]; then 
            echo $usage; exit 1
    else 
        max=$1
    fi

for user in `cat /etc/passwd | cut -d: -f1`; do
    home=`cat /etc/passwd | grep "^$user\>" | cut -d: -f6`
    du -sh $home --threshold=$max
done