def CONTAINER_NAME="projeto-referencia-sb"
def HTTP_PORT_HOST="8889"
def HTTP_PORT_CONTAINER="8080"
def DOCKER_HUB_USER
def DOCKER_HUB_PASSWORD
def CONTAINER_TAG

node {

    stage('Initialize') {
        def dockerHome = tool 'myDocker'
        def mavenHome  = tool 'myMaven'
        env.PATH = "${dockerHome}/bin:${mavenHome}/bin:${env.PATH}"
        withCredentials([usernamePassword(credentialsId: 'dockerHubAccount', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
            DOCKER_HUB_USER = USERNAME
            DOCKER_HUB_PASSWORD = PASSWORD
        }
    }

    stage('Checkout') {
        checkout scm
    }

    stage('Tests') {
        sh "mvn clean test"
    }

    stage('Scripts'){
        sh "mvn flyway:migrate -Dflyway.url=jdbc:postgresql://${env.DATABASE_HOST}/${env.DATABASE_NAME} -Dflyway.user=${env.DATABASE_USER} -Dflyway.password=${env.DATABASE_PASSWORD}"
    }

    stage('Build App'){
        sh "mvn install -DskipTests"
    }

    stage('Image Build'){
        CONTAINER_TAG = readMavenPom().getVersion()
        imagePrune(CONTAINER_NAME)
        imageBuild(CONTAINER_NAME, CONTAINER_TAG, DOCKER_HUB_USER)
    }

    stage('Image Register'){
        pushToImage(CONTAINER_NAME, CONTAINER_TAG, DOCKER_HUB_USER, DOCKER_HUB_PASSWORD)
    }

    stage('Run App'){
        runApp(CONTAINER_NAME, CONTAINER_TAG, DOCKER_HUB_USER, HTTP_PORT_HOST, HTTP_PORT_CONTAINER)
    }

}

def imagePrune(containerName){
    try {
        sh "docker image prune -f"
        sh "docker stop $containerName"
    } catch(error){}
}

def imageBuild(containerName, tag, dockerHubUser){
    sh "docker build -t $dockerHubUser/$containerName:$tag --pull --no-cache ."
    echo "Image build complete"
}

def pushToImage(containerName, tag, dockerHubUser, dockerPassword){
    sh "docker login -u $dockerHubUser -p $dockerPassword"
    sh "docker push $dockerHubUser/$containerName:$tag"
    echo "Image push complete"
}

def runApp(containerName, tag, dockerHubUser, httpPortHost, httpPortContainer){
    sh "docker run -d --rm -p $httpPortHost:$httpPortContainer --name $containerName --network dashboard_monitor-net -e DATABASE_HOST=${env.DATABASE_HOST} -e DATABASE_NAME=${env.DATABASE_NAME} -e DATABASE_USER=${env.DATABASE_USER} -e DATABASE_PASSWORD=${env.DATABASE_PASSWORD} -e LOG_LEVEL=${env.LOG_LEVEL} -e SHOW_SQL=${env.SHOW_SQL} -e PROFILE=${env.PROFILE} $dockerHubUser/$containerName:$tag"
    echo "Application started on port: ${httpPortHost} (http)"
}
