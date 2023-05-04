<template>
  <div>
    <div>&nbsp;</div>
    <div class="footer">
      <div style="text-align: left">
        <img src="../assets/images/main_logo.png">
      </div>
      <div class="footer-text">
        <span>&copy;아이북(aebook) C201</span><br>
        <span>상호명: 아이북 | 사업자 등록 번호: 746-43-01140 | 사업자명: 이도연</span><br>
        <span>도서 API 제공 : 알라딘 인터넷서점( www.aladin.co.kr)</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'TheFooter'
}
</script>

<style scoped>
.footer {
    background-color: var(--main-yellow);
    padding: 20px;
    position: relative;
    margin-top: auto;
}

.footer img {
  width: 100px;
  height: 50px;
  margin-right: 10px;
  margin-left: 17px;
}

.footer-text {
  text-align: left;
}
</style>
