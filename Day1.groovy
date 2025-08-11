pipeline {
    agent { label 'App Server 3' }

    environment {
        IMAGE_NAME = "stregi01.stratos.xfusioncorp.com:5000/nginx:latest"
    }

    stages {
        stage('Build') {
            steps {
                git url: 'http://git.stratos.xfusioncorp.com/sarah/web.git', branch: 'master'
                script {
                    sh "docker build -t ${IMAGE_NAME} ."
                    sh "docker push ${IMAGE_NAME}"
                }
            }
        }
    }
}

