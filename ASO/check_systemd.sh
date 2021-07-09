#!/bin/bash

usage="Usage: check_systemd.sh"
    if [ $# -ne 0 ]; then 
            echo $usage; exit 1
    fi
howmany=`systemctl --failed | grep ".service" | awk -F "loaded" '{print $1}' | wc -l`
if [  $howmany == 0 ]; then 
    echo "Felicitats, tots els serveis estan correctes"
else 
    echo "Hi ha $howmany serveis fallits: "
fi
for service in `systemctl --failed | grep ".service" | awk -F "loaded" '{print $1}'`; do
    printf "$service "
done
echo ""