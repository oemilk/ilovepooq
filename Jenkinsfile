pipeline {
  agent {
    node {
      label 'worker'
    }

  }
  stages {
    stage('Source') {
      steps {
        git 'https://github.com/oemilk/ilovepooq'
      }
    }

  }
  environment {
    COMPLETED_MSG = 'Build done!'
  }
}