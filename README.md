# Running Tests with LambdaTest and Gitpod

1. **LambdaTest task**: [Link](https://drive.google.com/file/d/1us3iLKIu7O1DIL9q_6X-FMYvjA7oJRMM/view)
2. **Gitpod link**: [Link](https://gitpod.io/#https://github.com/Pomidorum1989/playwright-java)
3. **Run the lambdaTest cloud tests from CLI using the following command**:
   ```sh
   mvn clean test -DisLambdaTest=true -Pparallel-methods
4. **Run the test local from CLI using the following command**:
   ```sh
   mvn clean test -DbrowserName=chromium -DisLambdaTest=false -DisHeadless=true -Pparallel-classes
