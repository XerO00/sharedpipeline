def call(Map config) {
    pipeline {
        agent any
        environment {
            REPO_NAME = "${config.REPO}"       
            BRANCH =   "${config.BRANCH}"       
        }
        stages {
            stage('Checkout') {
                steps {
                    git branch: '${BRANCH}',
                    // credentialsId: 'git_mirror',
                    // url: 'ssh://git@xwtcvpgit:7999/dat/tdg-meterevent-api.git'
                    echo 'Git clone success'
                    // sh 'chmod +rx scripts/tdg-meterevent-api_initialization.sh'
                    // sh './scripts/tdg-meterevent-api_initialization.sh'
                    echo "Installed packages successfully"
                }
            }
            stage('Build and Deploy') {
                steps {
                    script {
                        if (config.ENVIRONMENT == 'sand') {
                            // withCredentials([usernamePassword(credentialsId: '', passwordVariable: 'AWS_SECRET_ACCESS_KEY', usernameVariable: 'AWS_ACCESS_KEY_ID')]) {
                            echo 'SAM Build and deploy in sand started'
                            build_and_deploy()
                            echo 'SAM Build and deploy in sand successful'
                        }                           
                        else if (config.ENVIRONMENT == 'dev') withCredentials([usernamePassword(credentialsId: 'aws-api-dev', passwordVariable: 'AWS_SECRET_ACCESS_KEY', usernameVariable: 'AWS_ACCESS_KEY_ID')]) {
                            echo 'SAM Build and deploy in dev started'
                            build_and_deploy()
                            echo 'SAM Build and deploy in dev successful'
                        }
                        else if (config.ENVIRONMENT == 'test') withCredentials([usernamePassword(credentialsId: 'aws-api-test', passwordVariable: 'AWS_SECRET_ACCESS_KEY', usernameVariable: 'AWS_ACCESS_KEY_ID')]) {
                            echo 'SAM Build and deploy in test started'
                            build_and_deploy()
                            echo 'SAM Build and deploy in test successful'
                        }
                        else if (config.ENVIRONMENT == 'prod') withCredentials([usernamePassword(credentialsId: 'aws-api-prod', passwordVariable: 'AWS_SECRET_ACCESS_KEY', usernameVariable: 'AWS_ACCESS_KEY_ID')]) {
                            echo 'SAM Build and deploy in prod started'
                            build_and_deploy()
                            echo 'SAM Build and deploy in prod successful'
                        }
                    }
                }
            }
            stage('notify') {
                steps{
                    echo "https://hub.docker.com/r/$REPO_NAME"
                }
            }
        }
    }
}

def build_and_deploy() {
    echo 'SAM Build and deploy in dev successful'
}
