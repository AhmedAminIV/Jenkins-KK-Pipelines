pipeline {
    agent { label 'ststor01' }

    stages {
        stage('Deploy') {
            steps {
                git branch: "master",
                    credentialsId: 'ststor01',
                    url: "http://git.stratos.xfusioncorp.com/sarah/web_app.git"
                sh 'cp -r $WORKSPACE* /var/www/html'
                echo 'Deploying web_app code from /var/www/html'
                sh 'ls -l /var/www/html'
            }
        }
    }
}
