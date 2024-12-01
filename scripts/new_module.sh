#!/bin/bash

create_module() {
  local module_type=$1
  local module_name=$2
  local base_dir

  if [[ "$module_type" == "common" ]]; then
    base_dir="common"
  elif [[ "$module_type" == "features" ]]; then
    base_dir="features"
  else
    echo "Invalid module type. Use 'common' or 'features'."
    exit 1
  fi

  local module_dir="$base_dir/$module_name"
  local settings_file="$base_dir/settings-$module_type.gradle.kts"

  if [[ -d "$module_dir" ]]; then
      echo "Error: Module $module_name already exists in $base_dir directory."
      exit 1
  fi

  mkdir -p "$module_dir/src/main/java/ru/kram/sandbox/$module_type/$module_name"
  mkdir -p "$module_dir/src/main/res"

  cat <<EOF > "$module_dir/build.gradle.kts"
plugins {
    id("ru.kram.sandbox.library")
}

android {
    namespace = "ru.kram.sandbox.$module_type.$module_name"
}

dependencies {

}
EOF

  cat <<EOF > "$module_dir/.gitignore"
/build
EOF

  if [[ -s "$settings_file" ]]; then
      echo "" >> "$settings_file"
    fi

    echo "include(\"$base_dir:$module_name\")" >> "$settings_file"

  echo "Module $module_name created in $base_dir directory."
}

if [[ $# -ne 2 ]]; then
  echo "Usage: $0 <common|features> <module_name>"
  exit 1
fi

create_module "$1" "$2"