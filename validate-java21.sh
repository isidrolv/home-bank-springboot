#!/bin/bash

# 1. Check Maven compiler plugin configuration
if grep -q "<source>21</source>" pom.xml && grep -q "<target>21</target>" pom.xml; then
  echo "✅ Maven compiler plugin is configured for Java 21"
else
  echo "❌ Maven compiler plugin NOT configured for Java 21"
fi

# 2. Compile project and check for errors
mvn clean install > build.log 2>&1
if grep -i "ERROR" build.log; then
  echo "❌ Compilation errors found"
else
  echo "✅ No compilation errors"
fi

# 3. Check dependency compatibility
mvn dependency:tree > dependencies.log 2>&1
if grep -i "deprecated" dependencies.log; then
  echo "❌ Deprecated dependencies found"
else
  echo "✅ No deprecated dependencies found"
fi

# 4. Static analysis with SpotBugs (if plugin is present)
if grep -q "spotbug" pom.xml; then
  mvn spotbug:spotbug > spotbugs.log 2>&1
  if [ $? -eq 0 ]; then
    echo "✅ SpotBugs analysis completed"
  else
    echo "❌ SpotBugs analysis failed"
  fi
else
  echo "ℹ️ SpotBugs plugin not found in pom.xml"
fi

# 5. Final validation summary
echo -e "\n--- VALIDATION SUMMARY ---"
if [ -f build.log ]; then
grep -i "ERROR" build.log
fi
if [ -f dependencies.log ]; then
grep -i "deprecated" dependencies.log
fi
if [ -f spotbugs.log ]; then
grep -i "ERROR" spotbugs.log
fi