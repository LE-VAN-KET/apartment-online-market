pipeline{
    agent any

    stages{
        stage('Prepare workspace') {
            steps {
                echo 'Prepare workspace'
                // Clean workspace
                step([$class: 'WsCleanup'])
                // Checkout git
                script {
                    def commit = checkout scm
                    env.BRANCH_NAME = commit.GIT_BRANCH.replace('origin/', '')
                }
            }
        }

        stage('Dependencies'){
            steps {
                echo 'Dependency stage'
                script {
                    sh "echo 'Downloading dependencies...'"
                    sh "./mvnw clean install -DskipTests=true"
                }
            }
        }

        stage('Testing') {
            steps {
                echo 'Test stage'
                script {
                    sh "echo 'JUnit testing...'"
                    sh "./mvnw test"
                    jacoco(execPattern: 'target/jacoco.exec')
                }
            }
        }

        stage('SonarQube Analysis') {
            agent {
                docker {
                    image 'jenkins/jnlp-agent-maven:jdk11'
                    args '-v /root/.m2:/root/.m2'
                }
            }
            environment {
                scannerHome = tool 'SonarQube'
            }
            steps {
                withSonarQubeEnv(installationName: 'SonarQube') {
                    sh """mvn clean verify sonar:sonar -Dsonar.projectKey=apartment-market-oline \
                    -Dsonar.host.url=http://146.190.105.184:10000 -Dsonar.login=sqp_d0eba2568a634e49fee0926eb2ebedb7cd0ef5dd
                    """
                }

                timeout(time: 60, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }

                script {
                    def sonar = waitForQualityGate()
                    if (sonar.status != 'OK') {
                        if (sonar.status == 'WARN') {
                            currentBuild.result = 'UNSTABLE'
                        } else {
                            error "Quality gate is broken"
                        }
                    }
                }
            }
        }

        stage("Deploy environment Dev"){
            when {
                expression {
                    return (env.BRANCH_NAME == 'origin/develop' | env.BRANCH_NAME == 'develop')
                }
            }
            steps{
                echo "========Build And Push image to test environment========"
                script {
                    sh "docker build -t vanket/apartment-online-market:latest ."
                    sh "docker login -u vanket -p dckr_pat_V1ZSZ0lJu4IESrxEJz_45ClFc60"
                    sh "docker tag vanket/apartment-online-market:latest vanket/apartment-online-market:1.0.0"
                    sh "docker push vanket/apartment-online-market:1.0.0"

                    echo "remove all image"
                    sh """docker rmi vanket/apartment-online-market:1.0.0 vanket/apartment-online-market:latest -f
                    """

                    echo "Login into server restart container"
                    echo "SSH remote to server to run docker-compose"
                    sh """ssh -i ~/.ssh/id_rsa_ggcloud khanhdpdx@34.142.222.244 docker pull \
                    vanket/apartment-online-market:1.0.0 && docker run -d --name KMS-BE  \
                    -p  vanket/apartment-online-market:1.0.0
                    """

                    echo "Exit remote server"
                }
            }
        }
    }
    post{
        always{
            echo "========always========"
        }
        aborted {
            echo "Sending message to Slack"
            echo "ABORTED"
        }
        success{
            echo "Sending message to Slack"
            echo "SUCCESS"
        }
        failure{
            echo "Sending message to Slack"
            echo "FAILURE"
        }
    }
}