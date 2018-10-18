#!/usr/bin/env groovy

def teamName = 'Krunal-Kevadiya'
def repoName = 'Jenkins_sample'

def buildHotfixBranch() {
    echo "Hotfix branch"
}

def buildReleaseBranch() {
    echo "Release branch"
}

def buildFeatureBranch() {
    echo "Feature branch"
}

def buildDevelopBranch() {
    echo "Develop branch"
}

def buildMasterBranch() {
    echo "Master branch"
     stage('Check') {
         sh "./gradlew check"
     }
     publishHTML(target: [
           allowMissing: false,
           alwaysLinkToLastBuild: true,
           keepAll: true,
           reportDir: "settings/reports/checkstyle",
           reportFiles: 'checkstyle.html',
           reportName: 'Checkstyle HTML Report'
     ])
     publishHTML(target: [
            allowMissing: false,
            alwaysLinkToLastBuild: true,
            keepAll: true,
            reportDir: "settings/reports/lint",
            reportFiles: 'lints.html',
            reportName: 'Lint HTML Report'
      ])
      publishHTML(target: [
          allowMissing: false,
          alwaysLinkToLastBuild: true,
          keepAll: true,
          reportDir: "settings/reports/ktlint",
          reportFiles: 'ktlint.xml',
          reportName: 'KtLint XML Report'
      ])
    publishHTML(target: [
          allowMissing: false,
          alwaysLinkToLastBuild: true,
          keepAll: true,
          reportDir: "settings/reports/ktlint/format",
          reportFiles: 'ktlint.xml',
          reportName: 'KtLint Format XML Report'
    ])
}

node {
    stage('Checkout') {
        checkout([
            $class: 'GitSCM',
            branches: [[name: env.BRANCH_NAME]],
            doGenerateSubmoduleConfigurations: false,
            extensions: [],
            submoduleCfg: [],
            userRemoteConfigs: [[
                url: "https://github.com/${teamName}/${repoName}.git"
            ]]
        ])
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
