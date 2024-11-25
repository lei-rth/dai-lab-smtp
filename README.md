# SMTP Pranker

## Table of contents
- [SMTP Pranker](#smtp-pranker)
  - [Table of contents](#table-of-contents)
  - [Setting up non persistent demo](#setting-up-non-persistent-demo)
    - [Setup your Environment file](#setup-your-environment-file)
    - [Execute demo](#execute-demo)
  - [References](#references)
## Setting up non persistent demo

This will bring up a demo instance

Clone the code

```bash
git clone git@github.com:lei-rth/dai-lab-smtp.git && cd dai-lab-smtp
```

Deploy the code

```bash
# Copy default config
cp -n .env.example .env

# Start the stack
make dev
```

Mock server is available at http://localhost:1080

### Setup your Environment file

Following variable setup in your [.env](.env) file will setup the demo environment for you

```ini
VICTIMS_LIST=victims.txt
MESSAGES_LIST=messages.txt
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
