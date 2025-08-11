// Jenkins Multistage Pipeline
pipeline {
    agent {
        label 'ststor01' // This must match the Jenkins node label for App Server 3
    }

    stages {
		stage('Deploy') {
            steps {
                echo "Deploying code to /var/www/html on Storage Server..."
                sh '''
                cd /var/www/html
                git pull origin master
                '''
            }
        }

        stage('Test') {
            steps {
                echo "Testing application via LBR..."
                sh '''
                curl --fail --silent --show-error http://stlb01:8091   #add /web if want to test the fail
                '''
            }
        }    
    }

    post {
        failure {
            echo "Pipeline failed! Either deployment or test failed."
        }
    }
}
