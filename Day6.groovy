// Jenkins MR jobs

pipeline {
    agent {
        label 'stapp03' // This must match the Jenkins node label for App Server 3
    }

    stages {
        stage('Build') {
            steps {
                script {
                    // Clone the repo
                    sh 'git clone -b master http://git.stratos.xfusioncorp.com/sarah/mr_job.git'
                    dir('mr_job') {
                        // Build Docker image
                        sh 'docker build -t stregi01.stratos.xfusioncorp.com:5000/nginx:latest .'
                        // Push to registry
                        sh 'docker push stregi01.stratos.xfusioncorp.com:5000/nginx:latest'
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    // Remove existing container if exists
                    sh 'docker rm -f nginx-app || true'
                    // Run container on port 8080
                    sh 'docker run -d --name nginx-app -p 8080:80 stregi01.stratos.xfusioncorp.com:5000/nginx:latest'
                }
            }
        }
    }
}
