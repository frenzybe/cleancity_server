pipeline {
    agent any
    stages {
        stage('Build Docker Image'){
            steps {
                script {
                    sh 'docker build -t cleancity_server .'
                }
            }
        }
    }
}