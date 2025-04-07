pipeline {
    agent any
    tools {
        jdk 'JDK17'       // Matches the name you configured in Jenkins
        maven 'Maven 3.9.6'
    }
    environment {
        // Store SonarCloud token in Jenkins Credentials (ID: 'sonarcloud-token')
        SONAR_TOKEN = credentials('sonarcloud-token') 
    }
    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', 
                url: 'https://github.com/komal-30/ev-charging-system.git'
            }
        }
        stage('Build') {
            steps {
                bat 'mvn clean package'  // Builds the .jar file
            }
        }
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarCloud') {  // Matches Jenkins SonarQube server name
                    bat """
                        mvn sonar:sonar ^
                        -Dsonar.projectKey=ev-charging-system ^
                        -Dsonar.organization=your-sonarcloud-org ^
                        -Dsonar.login=%SONAR_TOKEN% ^
                        -Dsonar.host.url=https://sonarcloud.io
                    """
                }
            }
        }
        stage('Archive Artifact') {
            steps {
                archiveArtifacts 'target/*.jar'  // Saves the JAR for deployment
            }
        }
    }
    post {
        success {
            bat 'echo SonarQube analysis completed. View results at: https://sonarcloud.io/dashboard?id=ev-charging-system'
        }
    }
}