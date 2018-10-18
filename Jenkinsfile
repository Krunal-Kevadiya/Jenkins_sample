#!/usr/bin/env groovy

def buildFeatureBranch() {
    echo "Feature branch"
}

def buildDevelopBranch() {
    echo "Develop branch"
}

def buildReleaseBranch() {
    echo "Release branch"
}

def buildMasterBranch() {
    echo "Master branch"
}

def buildHotfixBranch() {
    echo "Hotfix branch"
}

def teamName = 'KevadiyaKrunalK'
def repoName = 'jenkinsdemo'

node {

    stage('Checkout') {
            checkout([$class: 'GitSCM', branches: [[name: '*/master']],
            userRemoteConfigs: [[url: 'ssh://git@bitbucket.org/KevadiyaKrunalK/jenkinsdemo.git']],
            browser: [$class: 'BitbucketWeb', repoUrl: 'https://bitbucket.org/KevadiyaKrunalK/jenkinsdemo']]
            )
        }

    def name = env.BRANCH_NAME
    if (name.startsWith('feature/')) {
        buildFeatureBranch()
    } else if (name == 'develop') {
        buildDevelopBranch()
    } else if (name.startsWith('release/')) {
        buildReleaseBranch()
    } else if (name == 'master') {
        buildMasterBranch()
    } else if (name.startsWith('hotfix/')) {
        buildHotfixBranch()
    } else {
        error "Don't know what to do with this branch: ${name}"
    }
}
