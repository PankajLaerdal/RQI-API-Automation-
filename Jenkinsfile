pipeline {
    agent any

    parameters {
        string(name: 'ENV', defaultValue: 'qa', description: 'Environment')
        string(name: 'TAGS', defaultValue: '@course', description: 'Cucumber Tags')
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/PankajLaerdal/RQI-API-Automation-.git'
                    
            }
        }

        stage('Run Tests') {
            steps {
                bat "mvn clean test -Denv=%ENV% -Dcucumber.filter.tags=%TAGS%"
            }
        }
    }
}
