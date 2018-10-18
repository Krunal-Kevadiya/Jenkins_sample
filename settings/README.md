## Jenkins Application Documentation

<span style="color:#FE2E2E; font-size:20px;">1. Fastlane Installation</span>

Make sure you have the latest version of the Xcode command line tools installed:
- <span style="color:#FF0080; font-size:15px;">xcode-select --install</span>

Install _fastlane_ using
- <span style="color:#FF0080; font-size:15px;">[sudo] gem install fastlane -NV</span>

or alternatively using
- <span style="color:#FF0080; font-size:15px;">brew cask install fastlane</span>


<span style="color:#FE2E2E; font-size:20px;">2. Build generate & upload in fabric</span>
- <span style="color:#FF7401; font-size:15px;">Configuration</span>
    ```
    - Set your application package
    PACKAGE_NAME = "com.exaple"

    - Set you fabric api key
    FABRIC_API_TOKEN = "b7353e549e0f0d809cf77962a39ebbb551cc185f"

    - Set you fabric secret key
    FABRIC_BUILD_SECRET = "a16bf773020f43aeb3f24bbe2707516d9c5a1a3089d82fb25790cd52d472dd11"

    - Added your testing email when you have invite to test your application to internal
    TESTER_EMAIL = ['krunal@linkerlogic.com', 'r.d.thakrar@gmail.com', 'rcbrcb13@gmail.com', 'terrydan710@gmail.com']

    - Added client testing email when you have invite to test your application to client
    CLIENT_EMAIL = ['krunal@linkerlogic.com', 'r.d.thakrar@gmail.com', 'rcbrcb13@gmail.com', 'terrydan710@gmail.com']
    ```

- <span style="color:#2E64FE; font-size:15px;">Fastlane command to execute in terminal or CMD</span>
    ```
    There are single type of argument pass like below
    `versionChange : major, minor, patch, reset`
    here reset value is used for set default value of version code and name.

    in application display like
    - format like "Version code - ${VERSION_CODE}"
    Example = versionCode = 1
    - format like "Version Name - %d.%d.%d(%d)" "${VERSION_MAJOR}" "${VERSION_MINOR}" "${VERSION_PATCH}" "${VERSION_CODE}"
    Example = versionName = 1.0.0(1)
    ```

    1. For release in Developer
        ```
        fastlane devVariant versionChange:"patch"
        ```
    2. For release in QA
        ```
        fastlane qaVariant versionChange:"patch"
        ```
    3. For release in Production
        ```
        fastlane productionVariant versionChange:"patch"
        ```


<span style="color:#FE2E2E; font-size:20px;">3. Static Code Analysis Tool</span>

Here eight different type of Static Code Analysis tool integrated like below
```
    1.  CheckStyle
    2.  Lint
    3.  Findbugs
    4.  PMD
    5.  KtLint
    6.  Detekt
    7.  Spotless
    8.  Sonarqube
```
With execute side by side and generate report file when you have resolve tool error

Format :- like TXT, XML and JSON in your local settings folder.

Path :- App root dir -> settings -> reports -> tool type).

- <span style="color:#FF7401; font-size:15px;">Configuration</span>

    Also you have use those tool using single file import/apply in your build.gradle file.
    1. app level build.gradle file
        ```groovy
        apply from: '../settings/codequality/quality.gradle'
        ```
    2. Project level build.gradle file in
        ```groovy
        buildscript {
            apply from: './settings/dependencies.gradle'
            addRepos(repositories)

            dependencies {
                classpath deps.staticTools.sonarqube
                classpath deps.staticTools.jacoco
            }
        }

        plugins {
            id("com.diffplug.gradle.spotless").version("3.15.0")
        }

        allprojects {
            addRepos(repositories)
            addProjectSetting()
        }

        apply plugin: 'org.sonarqube'
        ```
- <span style="color:#2E64FE; font-size:15px;">How to run static code analysis tool</span>

    Run all tool excluding 'Sonarqube' using command like
    ```
    ./gradlew check
    ```
    Now you have run separately then use
    1. For JAVA File
        ```
        ./gradlew checkstyle
        ./gradlew lint
        ./gradlew findbugs
        ./gradlew pmd
        ```
    2. For KOTLIN File
        ```
        ./gradlew ktlint
        ./gradlew ktlintFormat
        ./gradlew detektcheck
        ./gradlew spotlessApply
        ./gradlew spotlessCheck
        ```
    3. Sonarqube
        ```
        ./gradlew sonarqube -Dsonar.host.url=http://localhost:9000
        ./gradlew sonarqube -Dsonar.host.url=http://172.16.1.109:9000
        ./gradlew sonarqube -Dsonar.host.url=http://ci.simformsolutions.com:9000
        ```
    Also it's configure using preBuild and assemble command like you build.gradle file
    ```
    preBuild.dependsOn('checkstyle')
    assemble.dependsOn('lint')
    check.dependsOn 'checkstyle', 'detekt', 'findbugs', 'lint', 'ktlint', 'ktlintFormat', 'pmd', 'spotlessApply', 'spotlessCheck'
    ```

<span style="color:#FE2E2E; font-size:20px;">4. Auto increment version code & name </span>

That part for used when you have run/execute fastlane command and update you version without forgot
- <span style="color:#FF7401; font-size:15px;">Configuration</span>
    1. app level build.gradle file
        ```groovy
        android {
            defaultConfig {
                applicationId readApplicationId()
                manifestPlaceholders = [crashlyticsKey: readCrashlyticsKey()]
            }

            flavorDimensions "mode"
            productFlavors {
                production {
                    dimension "mode"
                    applicationId readApplicationId()
                    versionCode readVersionCode(flavorFiles.production)
                    versionName readVersionName(flavorFiles.production)
                }

                qa {
                    dimension "mode"
                    applicationId readApplicationId()
                    versionCode readVersionCode(flavorFiles.qa)
                    versionName readVersionName(flavorFiles.qa)
                }

                development {
                    dimension "mode"
                    applicationId readApplicationId()
                    versionCode readVersionCode(flavorFiles.development)
                    versionName readVersionName(flavorFiles.development)
                }
            }
        }

        def readVersionName(def buildType = "buildVariant") {
            def version = readVersionFile(buildType)
            return "${version['VERSION_MAJOR']}.${version['VERSION_MINOR']}.${version['VERSION_PATCH']}(${version['VERSION_CODE']})"
        }

        def readVersionCode(def buildType = "buildVariant") {
            def version = readVersionFile(buildType)
            def build = version['VERSION_CODE'] as int
            return build
        }

        def readCrashlyticsKey() {
            def version = readVersionFile(flavorFiles.project)
            return "${version['FABRIC_API_TOKEN']}"
        }

        def readApplicationId() {
            def version = readVersionFile(flavorFiles.project)
            return "${version['APPLICATION_ID']}"
        }
        ```
    2. AndroidManifest.xml file
        ```xml
         <application>
                <!-- Fabric -->
                <meta-data
                    android:name="io.fabric.ApiKey"
                    android:value="${crashlyticsKey}" />
         </application>
        ```

----

This README.md is auto-generated and will be re-generated every time [fastlane](https://fastlane.tools) is run.
More information about fastlane can be found on [fastlane.tools](https://fastlane.tools).
The documentation of fastlane can be found on [docs.fastlane.tools](https://docs.fastlane.tools).
