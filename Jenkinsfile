node {
    stage 'setup'
    def mainBranch = "develop"
    def mainBuildFlavor = ""
    def mainBuildType = "Debug"
    def mainBuildVariant = mainBuildFlavor + mainBuildType
    def buildVariants = ["Debug"]
    checkout scm
    properties([
        buildDiscarder(logRotator(numToKeepStr: '50')),
        disableConcurrentBuilds()
    ])
    def revisionHash = sh(script: "git rev-parse --short HEAD", returnStdout: true).trim()
    def revCount = sh(script: "git rev-list $revisionHash..origin/$mainBranch --count", returnStdout: true)
            .trim().toInteger()
    if (revCount > 0) {
        echo "branch is behind dev, marking build as UNSTABLE"
        currentBuild.result = "UNSTABLE"
    }
    sh "chmod +x ./gradlew"
    gradleNoDaemon('clean')
    stage 'test'
    gradleNoDaemon("test${mainBuildVariant}")
    step([$class: 'JUnitResultArchiver', testResults: '**/app/build/test-results/**/TEST-*.xml'])
    stage 'assemble'
    def assembleCommand = ""
    for (String variant : buildVariants) {
        assembleCommand += "assemble$variant "
    }
    gradleNoDaemon(assembleCommand)
    archive '**/app/build/outputs/apk/app*.apk'
    stage 'analysis'
    gradleNoDaemon(":app:lint${mainBuildVariant} checkstyle findbugsXml pmdMain pmdTest cpdCheck")
    sh 'cloc --by-file --xml --out=app/build/reports/cloc.xml app/src'
    stage 'publish analysis'
    step([$class: 'LintPublisher', pattern: '**/lint-results*.xml'])
    step([$class: 'hudson.plugins.checkstyle.CheckStylePublisher', pattern: '**/app/build/reports/checkstyle/*.xml'])
    step([$class: 'FindBugsPublisher', pattern: '**/app/build/reports/findbugs/*.xml'])
    step([$class: 'PmdPublisher', pattern: '**/app/build/reports/pmd/*.xml'])
    step([$class: 'DryPublisher', pattern: '**/app/build/reports/cpd/*.xml'])
    step([$class          : 'JacocoPublisher', classPattern: 'app/build/intermediates/classes/debug',
          exclusionPattern: '**/R.class, **/R$*.class, **/BuildConfig.*, **/Manifest*.*, '
                  // Misc library specific
                  + '**/*$ViewInjector*.*, **/*_*, **/Dagger*, '
                  + 'io/realm/**, **/com/viewpagerindicator/*, **/*$ViewBinder*.*, '
                  // Android specific
                  + '**/*LinearLayout.*, **/*FrameLayout.*, **/*RelativeLayout.*, **/*ImageView.*, '
                  + '**/*ConstraintLayout.*, **/*GridLayout.*, **/*TableLayout.*, **/*ListView.*, '
                  + '**/*ScrollView.*, **/*WebView.*, **/*SearchView.*, **/*VideoView.*, **/*SurfaceView.*, '
                  + '**/*NestedScrollView.*, **/*Toolbar.*, **/*CardView.*, **/*FloatingActionButton.*, '
                  + '**/*RecyclerView.*, **/*ProgressBar.*, **/*Button.*, **/*TabLayout.*, '
                  + '**/*ViewGroup.*, **/*CoordinatorLayout.*, **/*Spinner.*, **/*CheckBox.*, '
                  + '**/*TextView.*, **/*RadioButton.*, **/*SeekBar.*, '
                  + '**/*Activity.*, **/*Fragment.*, **/*Screen*, **/*Application.*, '
                  + '**/*Adapter*.*, **/*ViewHolder*.*, **/*Binder.*, **/*BinderImpl.*, '
                  + '**/*ItemDecorator.*, **/*ItemDecoration.*, '
                  + '**/*Service.*, '
                  // Hax
                  + '**/*ExtensionKt*, '
                  + '**/*Activity$*, **/*Fragment$*, **/*Application$*, '
                  + '**/*Adapter$*, **/*ViewHolder$*, **/*Binder$*, **/*BinderImpl$*, '
                  + '**/*Companion$CREATOR*, '
                  + '**/*ProgressBar$*, **/*TabLayout$*, **/*RecyclerView$*, '
                  + '**/*ItemDecorator$*, **/*ItemDecoration$*, '
                  // Project exclusive
                  + '**/*View$*,'
                  + '**/*PreferencesConstants*.*, **/*Factory*.*, '
                  + '**/*Article*.*, **/Source.*, **/ArticlesResponse.*, **/SourcesResponse.*,'
                  + '**/*NotificationSubscriber.*,'
                  + '**/*NotificationProvider.*, **/SnackbarProvider.*,'
                  + '**/*NetworkManager.*, **/*ImageManager.*,'
                  + '**/*AuthorizationInterceptor.*, ',
          execPattern     : 'app/build/jacoco/**.exec',
          inclusionPattern: '**/*.class'])
}

void gradleNoDaemon(String tasks) {
    gradle(tasks, '-Dorg.gradle.daemon=false')
}

void gradle(String tasks, String switches = null) {
    String gradleCommand = "";
    gradleCommand += './gradlew '
    gradleCommand += tasks
    if (switches != null) {
        gradleCommand += ' '
        gradleCommand += switches
    }
    sh gradleCommand.toString()
}