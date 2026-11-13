# 1. Check Maven compiler plugin configuration
if (Get-Content pom.xml | Select-String -Pattern "<source>21</source>" -Quiet) {
  Write-Host "✅ Maven compiler plugin is configured for Java 21"
} else {
  Write-Host "❌ Maven compiler plugin NOT configured for Java 21"
}

# 2. Compile project and check for errors
& mvn clean install > build.log 2>&1
if (Get-Content build.log | Select-String -Pattern "ERROR" -Quiet) {
  Write-Host "❌ Compilation errors found"
} else {
  Write-Host "✅ No compilation errors"
}

# 3. Check dependency compatibility
& mvn dependency:tree > dependencies.log 2>&1
if (Get-Content dependencies.log | Select-String -Pattern "deprecated" -Quiet) {
  Write-Host "❌ Deprecated dependencies found"
} else {
  Write-Host "✅ No deprecated dependencies found"
}

# 4. Static analysis with SpotBugs (if plugin is present)
if (Get-Content pom.xml | Select-String -Pattern "spotbug" -Quiet) {
  & mvn spotbug:spotbug > spotbugs.log 2>&1
  if ($LASTEXITCODE -eq 0) {
    Write-Host "✅ SpotBugs analysis completed"
  } else {
    Write-Host "❌ SpotBugs analysis failed"
  }
} else {
  Write-Host "ℹ️ SpotBugs plugin not found in pom.xml"
}

# 5. Final validation summary
Write-Host "\n--- VALIDATION SUMMARY ---"
if (Test-Path build.log) {
  Get-Content build.log | Where-Object { $_ -match "ERROR" }
}
if (Test-Path dependencies.log) {
  Get-Content dependencies.log | Where-Object { $_ -match "deprecated" }
}
if (Test-Path spotbugs.log) {
  Get-Content spotbugs.log | Where-Object { $_ -match "ERROR" }
}