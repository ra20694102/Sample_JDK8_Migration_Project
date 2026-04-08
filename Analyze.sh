#!/bin/bash

echo "🔍 Starting JDK Migration Analysis..."

# Clean old reports
rm -rf reports
mkdir -p reports

echo "📦 Detecting build tool..."

if [ -f "pom.xml" ]; then
  BUILD_TOOL="maven"
elif [ -f "build.gradle" ]; then
  BUILD_TOOL="gradle"
else
  echo "❌ No Maven or Gradle build file found"
  exit 1
fi

echo "⚙️ Build tool detected: $BUILD_TOOL"

# Compile project
if [ "$BUILD_TOOL" = "maven" ]; then
  mvn clean compile -q
elif [ "$BUILD_TOOL" = "gradle" ]; then
  gradle build -q
fi

# Run jdeps
echo "🔎 Running jdeps..."
jdeps -recursive -jdkinternals -cp target/classes > reports/jdeps.txt 2>&1

# Dependency tree
echo "🌳 Generating dependency tree..."

if [ "$BUILD_TOOL" = "maven" ]; then
  mvn dependency:tree -DoutputFile=reports/deps.txt -q
elif [ "$BUILD_TOOL" = "gradle" ]; then
  gradle dependencies > reports/deps.txt
fi

# Scan for risky imports
echo "🧪 Scanning for internal APIs..."
grep -R "sun\." src > reports/internal-apis.txt || true
grep -R "com\.sun\." src >> reports/internal-apis.txt || true

echo "📄 Reports generated in /reports folder"