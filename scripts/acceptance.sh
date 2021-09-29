#!/bin/bash

set -Ee

trap quit ERR
trap teardown EXIT

CURRENT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"

source "${CURRENT_DIR}/functions.sh"

function teardown() {
    info "Tear down infrastructure"
    docker-compose down
}

cd "$CURRENT_DIR/.."

info "Set up clean infrastructure"
docker-compose rm -f
docker-compose up -d

info "Running the build including linter and tests"
./gradlew clean build

success "Build validated!"
