#!/bin/bash
while true; do
    curl http://localhost:8080/rest/process-definition/key/Bewerbung/start -d "{}" -H "Content-Type: application/json"
    sleep 1
    echo "done"
done
