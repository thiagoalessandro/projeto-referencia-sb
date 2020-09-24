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
        CONTAINER_TAG = readMavenPom().getVersion()
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
        sh "mvn flyway:migrate -Dflyway.url=jdbc:postgresql://intelector.com.br:5432/db -Dflyway.user=admin -Dflyway.password=Thiag0@0703"
    }

    stage('Build App'){
        sh "mvn install"
    }

    stage('Image Build'){
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
    sh "docker run -d --rm -p $httpPortHost:$httpPortContainer --name $containerName $dockerHubUser/$containerName:$tag"
    echo "Application started on port: ${httpPortHost} (http)"
}
