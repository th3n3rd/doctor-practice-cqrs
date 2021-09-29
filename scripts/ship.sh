#!/usr/bin/env bash

set -e

CURRENT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"

source "${CURRENT_DIR}/functions.sh"

info "Checking for the current branch"
CURRENT_BRANCH=$(git branch --show-current)
if [ $CURRENT_BRANCH != "main" ]; then
  error "You have to be on main to ship the changes"
  exit 1
fi

cd "$CURRENT_DIR/.."

info "Checking for uncommitted changes"
UNCOMMITTED_CHANGES=$(git status --porcelain | wc -l)
if [ $UNCOMMITTED_CHANGES -ne 0 ]; then
  git status
  error
  error "^^^You have some uncommitted changes"
  error
  exit 1
fi

info "Fetching latest changes (and rebasing on top) from the remote repository"
git pull -r

info "Validating the changes"
"${CURRENT_DIR}/acceptance.sh"

info "Publishing all changes into the remote repository"
git push

success "Shipped it!"


