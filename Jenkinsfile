pipeline {
  agent {
    label 'master'
  }
  stages {
    stage('checkout') { 
        steps {
            checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/ARK21/QueueHandler.git']]]) 
            } 
        } 
    } 
}
