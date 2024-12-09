# SMTP Pranker

## Table of contents
- [SMTP Pranker](#smtp-pranker)
  - [Table of contents](#table-of-contents)
  - [Requirements](#requirements)
  - [Getting started](#getting-started)
    - [Clone the code](#clone-the-code)
    - [Deploy the code](#deploy-the-code)
    - [Setup your Environment file](#setup-your-environment-file)
    - [Execute demo](#execute-demo)
  - [References](#references)

## Requirements

* A Linux distribution or WSL
* Git
* Docker and Docker Compose
* Java 21
* Maven

## Getting started
This will bring up a demo instance

### Clone the code

```bash
git clone git@github.com:lei-rth/dai-lab-smtp.git && cd dai-lab-smtp
```

### Deploy the code

```bash
# Install "make" if not already installed
sudo apt-get install make 

# Copy default configuration
cp -n .env.example .env


# Change the SMTP host and port in the .env file if needed
SMTP_HOST=localhost # by default
SMTP_PORT=1025 # by default

# Start the stack 
make dev
```

Mock server is available at http://localhost:1080

### Setup your Environment file

Following variable setup in your [.env](.env) file will setup the demo environment for you

```ini
VICTIMS_LIST=victims.json
MESSAGES_LIST=messages.json
NUMBER_GROUPS=2
```

### Execute demo

```bash
make build run
```

References
----------

* The [SMTP RFC](<https://tools.ietf.org/html/rfc5321#appendix-D>), and in particular the [example scenario](<https://tools.ietf.org/html/rfc5321#appendix-D>)
* The [mailtrap](<https://mailtrap.io/>) online service for testing SMTP
