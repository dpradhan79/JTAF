if exist target (
rd /s/q target
)

if exist test-output (
rd /s/q test-output
)
  
mvn clean install test