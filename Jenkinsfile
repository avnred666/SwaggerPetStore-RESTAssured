pipeline {
  agent any
  stages {
    stage('Checkout') {
      steps {
        git(url: 'https://github.com/avnred666/SwaggerPetStore-RESTAssured', branch: 'main')
      }
    }

    stage('Run') {
      steps {
        bat 'mvn verify'
      }
    }

  }
}