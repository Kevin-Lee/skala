#!/bin/bash -e

echo "======================================================"
echo "Build packages"
echo "------------------------------------------------------"

sbt clean
sbt writeVersion

echo ""
echo "======================================================"
echo "Run: sbt clean +packageBin +packageSrc +packageDoc "
echo "------------------------------------------------------"
if sbt clean +packageBin +packageSrc +packageDoc ; then
  echo "Done: sbt clean +packageBin +packageSrc +packageDoc "
  echo "======================================================"
else
  echo "Failed: sbt clean +packageBin +packageSrc +packageDoc " 1>&2
  echo "======================================================"
  exit 1
fi

echo ""
echo "======================================================"
echo "Building Packages: Done"
echo "======================================================"

sbt writeVersion
