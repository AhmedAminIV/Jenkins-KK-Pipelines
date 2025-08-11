// Jenkins chain builds
pipeline {
    agent any

    stages {
        stage('Restart Apache Service') {
            steps {
                script {
                    def servers = [
                        [host: 'stapp01', user: 'tony', sudoCredId: 'stapp01'],
                        [host: 'stapp02', user: 'steve', sudoCredId: 'stapp02'],
                        [host: 'stapp03', user: 'banner', sudoCredId: 'stapp03']
                    ]

                    sshagent(['jenkins-ssh-key']) {
                        for (srv in servers) {
                            withCredentials([string(credentialsId: srv.sudoCredId, variable: 'SUDO_PASS')]) {
                                sh """
                                ssh -o StrictHostKeyChecking=no ${srv.user}@${srv.host} 'bash -s' << EOF
                                    echo "$SUDO_PASS" | sudo -S systemctl enable httpd
                                    echo "$SUDO_PASS" | sudo -S systemctl restart httpd || true
                                """
                            }
                        }
                    }
                }
            }
        }
    }
}



https://8080-port-f5ly3zwyszbdnvux.labs.kodekloud.com/gitea-webhook/post/job=nautilus-app-deployment-trigger

pwd
ls -la
scp * natasha@ststor01:/var/www/html