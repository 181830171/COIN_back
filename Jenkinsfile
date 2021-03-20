pipeline {
  agent any
  stages {
    stage('check out') {
      steps {
        echo 'check out'
        git(url: 'http://212.129.149.40/181250062_fabulousseciii/backend_coin.git', branch: 'master', changelog: true, poll: true, credentialsId: '5355acb9-7b22-405c-949a-96338f37c645')
      }
    }

    stage('build') {
      steps {
        echo 'build'
        sh 'mvn -B -DskipTests clean package'
      }
    }

    stage('test') {
      steps {
        echo 'test'
        sh 'mvn test'
      }
    }

  }
}