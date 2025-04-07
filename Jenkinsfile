pipeline {
    agent any
    tools {
        jdk 'JDK17'       // Matches the name you configured in Jenkins
        maven 'Maven 3.9.6'
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
        stage('Archive Artifact') {
            steps {
                archiveArtifacts 'target/*.jar'  // Saves the JAR for deployment
            }
        }
    }
}