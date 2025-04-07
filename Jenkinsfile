pipeline {
    agent any
    tools {
        jdk 'JDK17'
        maven 'Maven 3.9.6'
    }
    environment {
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
                // SECURE CHANGE: Wrap Maven build with credentials
                withCredentials([string(credentialsId: 'db-password-secret', variable: 'DB_PASSWORD')]) {
                    bat """
                        mvn clean package ^
                        -Dspring.datasource.password=%DB_PASSWORD%
                    """
                }
            }
        }
        
        stage('SonarQube Analysis') {
    		steps {
        		withCredentials([string(credentialsId: 'sonarcloud-token', variable: 'SONAR_TOKEN')]) {
		            bat """
		                mvn sonar:sonar ^
		                -Dsonar.projectKey=ev-charging-system ^
		                -Dsonar.organization=komal-30 ^
		                -Dsonar.host.url=https://sonarcloud.io ^
		                -Dsonar.login=%SONAR_TOKEN%
		            """
        }
    }
}
        
        stage('Archive Artifact') {
            steps {
                archiveArtifacts 'target/*.jar'
            }
        }
    }
    post {
        success {
            bat 'echo SonarQube analysis completed. View results at: https://sonarcloud.io/dashboard?id=ev-charging-system'
        }
    }
}