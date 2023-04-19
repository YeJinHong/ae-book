<template>
    <div>
        <b-navbar toggleable="lg" type="dark" variant="light" class="nav">
            <b-navbar-brand href="#">
                <router-link to="/">
                    아이북
                </router-link>
            </b-navbar-brand>

            <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

            <b-collapse id="nav-collapse" is-nav>
                <b-navbar-nav>
                    <b-nav-item>
                        <router-link to="/story">
                                동화책 만들기
                        </router-link>
                    </b-nav-item>
                    <b-nav-item>
                        <router-link to="/painting">
                                색칠공부
                        </router-link>
                    </b-nav-item>
                    <b-nav-item>
                        <router-link to="/searchbookbypicture">
                                사진으로 책 검색
                        </router-link>
                    </b-nav-item>
                    <b-nav-item>
                        <router-link to="/searchbook">
                                제목으로 책 검색
                        </router-link>
                    </b-nav-item>
                </b-navbar-nav>
                <!-- TODO : 로그인 여부(세션에 JWT 정보 확인)에 따라(v-if) 로그인/마이페이지로 출력 바꾸기-->
                <b-navbar-nav class="ml-auto">
                    <b-nav-form>
                        <!-- TODO : 로그인 모달창 작성 및 연결 -->
                        <b-button size="sm">로그인</b-button>
                    </b-nav-form>
                </b-navbar-nav>
            </b-collapse>
        </b-navbar>
    </div>
</template>

<script>
// TODO: 메인페이지 Navbar 배치 및 디자인 변경
export default {
  name: 'HeaderNavbarMain'
}
</script>

<style scoped>

</style>
