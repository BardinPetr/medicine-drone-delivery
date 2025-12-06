#!/bin/bash

# This script is used to publish new version of frontend api library
# Process:
#   1. build and launch app
#   2. update local openapi spec file
#   3. run openapi generator
#   4. build library with npm
#   5. publish library to npm

OPENAPI_URL="http://localhost:8080/openapi.yaml"
OPENAPI_OUTPUT="openapi.yaml"
MAX_WAIT_SECONDS=60
CHECK_INTERVAL=1

./gradlew bootJar -x test

java -jar ./build/libs/meddelivery.jar &
APP_PID=$!

cleanup() {
    echo "Stopping application (PID: ${APP_PID})..."
    kill ${APP_PID} 2>/dev/null || true
    wait ${APP_PID} 2>/dev/null || true
}
trap cleanup EXIT

while [[ ${ELAPSED} -lt ${MAX_WAIT_SECONDS} ]]; do
  if wget -q --tries=1 --timeout=10 "${OPENAPI_URL}" -O "${OPENAPI_OUTPUT}"; then
      echo "Downloaded OpenAPI OK"
      break
  fi
  if ! kill -0 ${APP_PID} 2>/dev/null; then
      echo "Application process died"
      exit 1
  fi
  sleep ${CHECK_INTERVAL}
  ELAPSED=$((ELAPSED + CHECK_INTERVAL))
done

kill -9 ${APP_PID} 2>/dev/null || true

rm -rf build/fe-lib/dist

./gradlew openApiGenerate

cd build/fe-lib
npm i
npm run build
npm publish ./dist
