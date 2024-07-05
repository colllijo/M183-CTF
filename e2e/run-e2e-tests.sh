#!/bin/bash

set -ou pipefail

check_health() {
  curl -s localhost:8080/api/actuator/health | grep -q '"status":"UP"'

  echo $?
}

CUR_PATH=$PWD

SCRIPT=$(readlink -f "$0")
SCRIPT_PATH=$(dirname "$SCRIPT")

docker compose \
  --project-name ctf-e2e \
  --file "$SCRIPT_PATH/docker-compose.yaml" \
  up \
  --detach

while [ "$(check_health)" != '0' ]; do
  sleep 1
done

cd "$SCRIPT_PATH/../frontend" || exit 1

npm run cypress:run
EXIT_CODE=$?

docker compose \
  --project-name ctf-e2e \
  --file "$SCRIPT_PATH/docker-compose.yaml" \
  down

cd "$CUR_PATH" || exit $EXIT_CODE
exit $EXIT_CODE
