#!/usr/bin/env bash
host=172.24.9.72
user=root
pass=root
database=db_dashboard



mysqldump -h${host} -u${user} -p${pass} --databases ${database} > ./${database}.sql
