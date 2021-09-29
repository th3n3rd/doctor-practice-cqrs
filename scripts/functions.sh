function quit() {
    error "Oops, something went wrong"
    return 1
}

function info() {
    printf "\e[34m$1\e[0m\n"
}

function error() {
    printf "\e[31m$1\e[0m\n"
}

function success() {
    printf "\e[32m$1\e[0m\n"
}
