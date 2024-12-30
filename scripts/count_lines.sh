#!/bin/bash

PROJECT_DIR=$(pwd)
EXTENSIONS=("kt" "java" "aidl")
EXCLUDE_DIRS=("build" "generated" ".idea" ".gradle")

EXCLUDE_PARAMS=()
for DIR in "${EXCLUDE_DIRS[@]}"; do
  EXCLUDE_PARAMS+=(-path "*/$DIR/*" -prune -o)
done

TOTAL_LINES=0

for EXT in "${EXTENSIONS[@]}"; do
  FILES=$(find "$PROJECT_DIR" "${EXCLUDE_PARAMS[@]}" -name "*.$EXT" -type f -print)
  LINES=$(echo "$FILES" | xargs cat 2>/dev/null | wc -l)
  printf "%-5s - %d\n" "$EXT" "$LINES"
  TOTAL_LINES=$((TOTAL_LINES + LINES))
done

printf "Total - %d\n" "$TOTAL_LINES"