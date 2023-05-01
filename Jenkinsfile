pipeline 
{
	agent any
	// tools {
	// 	gradle 'gradle 7.6.1'
	// }
	environment {
		PROJECT = 'ae-book'
		APP_SPRING_API = 'backend'
		APP_AI_API = 'ai'
		APP_BATCH_API = 'batch'
	}
	stages {
		//===================================== Environment =====================================
		stage('environment') {
			when {
				changeset "env-config/**/*"
			}
			steps {
				echo 'Environment Settings Start'
				sh 'docker-compose -f env-config/docker-compose-env.yml down'
				sh 'docker-compose -f env-config/docker-compose-env.yml up -d'
				echo 'Environment Settings End'
			}
		}
		//===================================== Build =====================================
		stage('build-spring-api') {
			when {
				anyOf {
					changeset "backend/**/*"
				}
			}
			steps {
				echo 'Build Start "${APP_SPRING_API}"'
				sh 'chmod +x ${APP_SPRING_API}/gradlew'
				sh '${APP_SPRING_API}/gradlew -p ${APP_SPRING_API} cleanQuerydslSourceDir'
				sh '''
					${APP_SPRING_API}/gradlew -p ${APP_SPRING_API} build -x test
					docker build -t back-spring-api-img ${APP_SPRING_API}/. --no-cache
				'''
				echo 'Build End "${APP_SPRING_API}"'
			}
			post {
				success {
					echo 'Back-spring-api container stop Start.'
					sh '''
					if (docker ps | grep "back-spring-api"); then docker stop back-spring-api;
					fi
					'''
					echo 'Back-spring-api container stop Success';
				}
			}
		}
		stage('build-front') {
			when {
				changeset "frontend/**/*"
			}
			steps {
				echo 'Build Start Front App'
				sh 'docker build -t front-img frontend/. --no-cache'
				echo 'Build End Front App'
			}
			post {
				success {
					echo 'Front container stop Start'
					sh '''
					if (docker ps | grep "front-app"); then docker stop front-app;
					fi
					'''
					echo 'Front container stop Success';
				}
			}
		}
		stage('build-batch') {
			when {
				anyOf {
					changeset "batch/**/*"
				}
			}
			steps {
				echo 'Build Start "${APP_BATCH_API}"'
				sh 'chmod +x ${APP_BATCH_API}/gradlew'
				sh '''
					${APP_BATCH_API}/gradlew -p ${APP_BATCH_API} build -x test
					docker build -t back-batch-img ${APP_BATCH_API}/. --no-cache
				'''
				echo 'Build End "${APP_BATCH_API}"'
			}
			post {
				success {
					echo 'Back-batch-api container stop Start.'
					sh '''
					if (docker ps | grep "back-batch-api"); then docker stop back-batch-api;
					fi
					'''
					echo 'Back-batch-api container stop Success';
				}
			}
		}
		stage('build-ai-api') {
			when {
				anyOf {
					changeset "ai/**/*"
				}
			}
			steps {
				echo 'Build Start "${APP_AI_API}"'
				sh 'chmod +x ${APP_AI_API}/gradlew'
				sh '''
					${APP_AI_API}/gradlew -p ${APP_AI_API} build -x test
					docker build -t back-ai-api-img ${APP_AI_API}/. --no-cache
				'''
				echo 'Build End "${APP_AI_API}"'
			}
			post {
				success {
					echo 'Back-ai-api container stop Start.'
					sh '''
					if (docker ps | grep "back-ai-api"); then docker stop back-ai-api;
					fi
					'''
					echo 'Back-ai-api container stop Success';
				}
			}
		}
		//===================================== Deploy (Docker run) =====================================
		stage('deploy-spring-api') {
			when {
				anyOf {
					changeset "backend/**/*"
				}
			}
			steps {
				echo 'Deploy Start "${APP_SPRING_API}"'
				sh 'docker run -it -d --rm -p 8082:8082 --name back-spring-api --network env-config_ae-book_network back-spring-api-img'
				echo 'Deploy End "${APP_SPRING_API}"'
			}
		}
		stage('deploy-front') {
			when {
				changeset "frontend/**/*"
			}
			steps {
				echo 'Deploy Start Front App'
				sh '''
					docker run -it -d --rm -p 3000:3000 --name front-app --network env-config_ae-book_network front-img 
				'''
				echo 'Deploy End Front App'
			}
		}
		// batch는 build 될 때마다 데이터가 들어가기 때문에 변경사항있다고 run하는건 아닌 듯...
		// stage('deploy-batch-api') {
		// 	when {
		// 		anyOf {
		// 			changeset "batch/**/*"
		// 		}
		// 	}
		// 	steps {
		// 		echo 'Deploy Start "${APP_BATCH_API}"'
		// 		sh 'docker run -it -d --rm -p 8084:8082 --name back-batch --network env-config_ae-book_network back-batch-img'
		// 		echo 'Deploy End "${APP_BATCH_API}"'
		// 	}
		// }
		stage('deploy-ai-api') {
			when {
				anyOf {
					changeset "ai/**/*"
				}
			}
			steps {
				echo 'Deploy Start "${APP_SPRING_API}"'
				sh 'docker run -it -d --rm -p 8086:8000 --name back-ai-api --network env-config_ae-book_network back-ai-api-img'
				echo 'Deploy End "${APP_SPRING_API}"'
			}
		}
	}
}
