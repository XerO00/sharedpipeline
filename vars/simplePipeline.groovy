def call() {
    pipeline {
        agent any
        environment {
            MODULE='m4'
        }
        stages {
            stage('Verify') {                  
                steps {
                    echo "Module: ${MODULE}"
		    echo "adding test line"	
		    sh 'git version'
                }
            }
        }
    }
}
