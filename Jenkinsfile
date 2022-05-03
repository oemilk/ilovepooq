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

    stage('Build') {
      steps {
        tool(name: 'gradle7.4.2', type: 'gradle')
        sh 'gradle build'
      }
    }

  }
  environment {
    COMPLETED_MSG = 'Build done!'
  }
}