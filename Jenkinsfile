'''
################# USER DEFINED VARIABLES###############################
'''
def CF_ORG = "columbus-apps"
def CF_SPACE = "mmp"
def JOB_NAME = "${env.JOB_NAME}".tokenize('/')[0]
def BRANCH = "${env.JOB_NAME}".tokenize('/')[1]
def CF_APPNAME ="user-registration"
'''
################# USER DEFINED VARIABLES###############################
'''


pipeline {
  agent any

    tools {
      jdk 'jdk'
      gradle "gradle5.6"
    }
  options {
    disableConcurrentBuilds()
    timeout(time: 1, unit: 'HOURS')
    skipDefaultCheckout(false)
    parallelsAlwaysFailFast()
    buildDiscarder(logRotator(numToKeepStr: '15'))
  }
    stages {
        stage('Initialize'){
          steps{
            echo"add steps to verify environment"
          }
        }

      stage('Run Unit Tests') {
            steps {
              sh "chmod +x gradlew "
             sh"./gradlew -Dtest.type=unit test"
            }
        }
        stage('Build artifact') {
            steps {
              sh"./gradlew build"
              sh "cp build/libs/*.jar ../../dist"
              sh"ls -alR"
            }
        }

                stage('Deploy to PCF') {
            when { branch 'master' }
            steps {
                withCredentials([string(credentialsId: 'CF_API', variable: 'CF_API'), string(credentialsId: 'CF_USER', variable: 'CF_USER'), string(credentialsId: 'CF_PASS', variable: 'CF_PASS')]) {
                    sh "cf login -a ${CF_API} -u ${CF_USER} -p ${CF_PASS} -o ${CF_ORG} -s ${CF_SPACE} "
                    sh "cf push -f ./manifest-prod.yml"
                }
            }
        }
            }
  post{
    success{
      echo"SUCCESSFUL BUILD"
    }
    failure{
      echo "BUILD FAILED"
    }
    always{
      junit allowEmptyResults: true, testResults: 'build/test-results/test/*.xml'
      deleteDir()
    }
  }
}
