// Jenkins Setup Node App
pipeline {
    agent {
        label 'stapp03' // This must match the Jenkins node label for App Server 3
    }

    stages {
		stage('Build') {
            steps {
                echo "Cloning code from git"
                sh '''
                mkdir -p /tmp/node-app
                rm -rf /tmp/node-app/web
                cd /tmp/node-app
                git clone -b master http://git.stratos.xfusioncorp.com/sarah/web.git
                '''
                echo "Building the image using the DockerFile"
                sh '''
                cd /tmp/node-app/web
                ls -l
                docker build -t stregi01.stratos.xfusioncorp.com:5000/node-app:latest .
                docker images
                docker push stregi01.stratos.xfusioncorp.com:5000/node-app:latest
                '''
            }
        }

        stage('Deploy') {
            steps {
                script {
                    // Remove existing container if exists
                    sh 'docker rm -f node-app || true'
                    // Run container on port 8080
                    sh 'docker run -d --name node-app -p 8080:8080 stregi01.stratos.xfusioncorp.com:5000/node-app:latest'
                }
            }
        }    
    }

}
