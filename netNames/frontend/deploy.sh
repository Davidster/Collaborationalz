#!/usr/bin/env bash

eval $(assume-role david)

aws s3 sync ./ s3://conuhacks
