#!/bin/bash

usage="Usage: user-stats.sh param"
    if [ $# -ne 0 ]; then 
            echo $usage; exit 1
    fi
echo "Resum de logins: "
for user in `cat /etc/passwd | cut -d: -f1`; do
    count=`last $user | wc -l`
    totalhours=0
    totalminutes=0
    start=2
    count=$((count-1)); 
    for (( i=$start; i<$count; i++)); do
        hours=`last -n $i $user | cut -d: -f4 | cut -c 6,7`
        #totalhours=$((totalhours+hours))
        minutes=`last -n $i $user | cut -d: -f5 | cut -c 1,2`
        #totalminutes=$((totalminutes+minutes))
    done
    time=$((totalhours+totalminutes))
    count=$((count-1)); 
    echo "Usuari $user : temps total de login $time, nombre total de logins: $count"
    totalusage=0
    for pcpu in `ps -u $user -o pcpu`; do
        if [ $pcpu != "%CPU" ]; then 
            result=$(bc -l <<<"${totalusage}+${pcpu}")
            totalusage=$result
        fi
    done
    processes=`ps -u $user | wc -l`
    echo "Active processes for $user: $processes"
    echo "Total CPU usage for $user: $totalusage" 
done