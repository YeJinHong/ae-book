<template>
  <div>
    <div>&nbsp;</div>
    <div class="footer">
      <img src="../assets/images/aebook_logo.png">
      <div class="footer-text">
        <span>&copy;아이북(aebook) C201</span><br>
        <span>상호명: 아이북 | 사업자 등록 번호: 746-43-01140 | 사업자명: 이도연</span>
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
  display: flex;
  align-items: center;
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
