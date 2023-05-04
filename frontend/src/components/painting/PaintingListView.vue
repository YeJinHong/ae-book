<template>
  <div>
    <h1>그림 목록</h1>
    <button @click="onClickList('LINE')" class="ae-btn">선화</button>
    <button @click="onClickList('COLOR')" class="ae-btn">그림</button>
    <router-link to="/painting/generate"><button class="ae-btn btn-red">선화 만들러 가기</button></router-link>
    <div class="painting-container">
      <list-item
          v-for="painting in paintingList"
          :key="painting.id"
          :item="painting"
        >
      </list-item>
    </div>
    <div class="pagination-container">
      <pagination :pageSetting="paintingPageSetting" @paging="paging"></pagination>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import ListItem from '../common/list/ListItem.vue'
import Pagination from '../common/Pagination.vue'
const paintingStore = 'paintingStore'

export default {
  name: 'PaintingListView',
  data () {
    return {
      request: null
    }
  },
  components: {
    ListItem,
    Pagination
  },
  computed: {
    ...mapState(paintingStore, ['paintingList', 'paintingPageSetting'])
  },
  methods: {
    ...mapActions(paintingStore, ['getPaintingList']),
    onClickList (type) {
      this.request = {
        type: type
      }
      this.getPaintingList(this.request)
    },
    paging (page) {
      this.request['page'] = page - 1
      this.getPaintingList(this.request)
    }
  }
}
</script>

<style scoped>

h1 {
  font-weight: 800;
  margin: 10px 0px;
}
.painting-container{
  margin: auto;
  margin-top: 30px;
  width: 1000px;
  display: flex;
  justify-content: space-evenly;
  align-items: center;
}

.pagination-container {
  display:flex;
  justify-content: center;
}
</style>
