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
		APP_BATCH = 'batch'
	}
	stages {
		//===================================== Environment =====================================
		// docker-compose 대신 k8s 사용 고려 중
		// stage('environment') {
		// 	when {
		// 		changeset "env-config/**/*"
		// 	}
		// 	steps {
		// 		echo 'Environment Settings Start'
		// 		sh 'docker-compose -f env-config/docker-compose-env.yml down'
		// 		sh 'docker-compose -f env-config/docker-compose-env.yml up -d'
		// 		echo 'Environment Settings End'
		// 	}
		// }
		//===================================== Front app =====================================
		stage('build-front') {
			when {
				changeset "frontend/**/*"
			}
			steps {
				echo 'Build Start Front App'
				sh 'docker build -t front-vue-img frontend/. --no-cache'
				echo 'Build End Front App'
			}
			post {
				success {
					echo 'Front-vue-img docker push Start'
					sh '''
						docker tag front-vue-img:latest teepij/front-vue-img:latest
						docker push teepij/front-vue-img
					'''
					echo 'Front-vue-img docker push Success';
				}
			}
		}
		
		// stage('deploy-front') {
		// 	when {
		// 		changeset "frontend/**/*"
		// 	}
		// 	steps {
		// 		echo 'Deploy Start Front App'
		// 		sh '''
		// 			if (docker ps | grep "front-vue"); then docker stop front-vue;
		// 			fi
		// 			docker run -it -d --rm -p 3000:3000 --name front-vue --network env-config_ae-book_network front-vue-img
		// 		'''
		// 		echo 'Deploy End Front App'
		// 	}
		// }
		//===================================== Spring API =====================================
		stage('build-spring-api') {
			when {
				anyOf {
					changeset "backend/**/*"
				}
			}
			steps {
				echo 'Build Start "${APP_SPRING_API}"'
				sh '''
					chmod +x ${APP_SPRING_API}/gradlew
					${APP_SPRING_API}/gradlew -p ${APP_SPRING_API} cleanQuerydslSourceDir		
					${APP_SPRING_API}/gradlew -p ${APP_SPRING_API} build -x test
					docker build -t back-spring-img ${APP_SPRING_API}/. --no-cache
				'''
				echo 'Build End "${APP_SPRING_API}"'
			}
			post {
				success {
					echo 'Back-spring-img docker push Start'
					sh '''
						docker tag back-spring-img:latest teepij/back-spring-img:latest
						docker push teepij/back-spring-img
					'''
					echo 'Back-spring-img docker push Success';
				}
			}
		}
		// stage('deploy-spring-api') {
		// 	when {
		// 		anyOf {
		// 			changeset "backend/**/*"
		// 		}
		// 	}
		// 	steps {
		// 		echo 'Deploy Start "${APP_SPRING_API}"'
		// 		sh '''
		// 			if (docker ps | grep "back-spring"); then docker stop back-spring;
		// 			fi
		// 			docker run -it -d --rm -p 8082:8082 --name back-spring --network env-config_ae-book_network back-spring-img
		// 			'''
		// 		echo 'Deploy End "${APP_SPRING_API}"'
		// 	}
		// }
		//===================================== Batch =====================================
		stage('build-batch') {
			when {
				anyOf {
					changeset "batch/**/*"
				}
			}
			steps {
				echo 'Build Start "${APP_BATCH}"'
				sh '''
				    chmod +x ${APP_BATCH}/gradlew
					${APP_BATCH}/gradlew -p ${APP_BATCH} build -x test
					docker build -t back-batch-img ${APP_BATCH}/. --no-cache
				'''
				echo 'Build End "${APP_BATCH}"'
			}
			post {
				success {
					echo 'Back-batch-img docker push Start'
					sh '''
						docker tag back-batch-img:latest teepij/back-batch-img:latest
						docker push teepij/back-batch-img
					'''
					echo 'Back-batch-img docker push Success';
				}
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
		// 		echo 'Deploy Start "${APP_BATCH}"'
		// 		sh '''
		// 			if (docker ps | grep "back-batch"); then docker stop back-batch;
		// 			fi
		// 			docker run -it -d --rm -p 8084:8082 --name back-batch --network env-config_ae-book_network back-batch-img
		// 			'''
		// 		echo 'Deploy End "${APP_BATCH}"'
		// 	}
		// }
		//===================================== AI =====================================
		stage('build-ai-api') {
			when {
				anyOf {
					changeset "ai/**/*"
				}
			}
			steps {
				echo 'Build Start "${APP_AI_API}"'
				sh '''
					docker build -t back-fast-img ${APP_AI_API}/. --no-cache
				'''
				echo 'Build End "${APP_AI_API}"'
			}
			post {
				success {
					echo 'Back-fast-img docker push Start'
					sh '''
						docker tag back-fast-img:latest teepij/back-fast-img:latest
						docker push teepij/back-fast-img
					'''
					echo 'Back-fast-img docker push Success';
				}
			}
		}
		// stage('deploy-ai-api') {
		// 	when {
		// 		anyOf {
		// 			changeset "ai/**/*"
		// 		}
		// 	}
		// 	steps {
		// 		echo 'Deploy Start "${APP_SPRING_API}"'
		// 		sh '''
		// 			if (docker ps | grep "back-fast"); then docker stop back-fast;
		// 			fi
		// 			docker run -it -d --rm -p 8000:8000 --name back-fast --network env-config_ae-book_network back-fast-img
		// 			'''
		// 		echo 'Deploy End "${APP_SPRING_API}"'
		// 	}
		// }
	}
}
