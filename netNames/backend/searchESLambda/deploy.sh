#!/usr/bin/env bash

npm run build

eval $(assume-role david)

aws lambda update-function-code --function-name SearchES --zip-file fileb://dist/deploymentPackage.zip
