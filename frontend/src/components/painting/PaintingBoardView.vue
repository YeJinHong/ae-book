<template>
  <div class="container">
    <!-- 왼쪽 -->
    <div class="container-left">
      <label class="title">제목 : </label>
      <input type="text" v-model="title">
      <canvas id="canvas"
        ref="canvas"
        @mousemove="onMove"
        @mousedown="startPainting"
        @mouseup="cancelPainting"
        @mouseleave="cancelPainting"
        @touchmove="onMove"
        @touchstart="startPainting"
        @touchend="cancelPainting"
        @touchcancel ="cancelPainting"
      ></canvas>
    </div>
    <!-- 왼쪽 -->
    <!-- 오른쪽 -->
    <div class="container-right">
    <div id="palette">
      <div class="left">
        <div
          v-for="(color, index) in colorOptions1"
          :key="index"
          :style="{ backgroundColor: color }"
          @click="onColorClick(color)"
          class="color-option"
        ></div>
      </div>
      <div class="right">
        <div
          v-for="(color, index) in colorOptions2"
          :key="index"
          :style="{ backgroundColor: color }"
          @click="onColorClick(color)"
          class="color-option"
        ></div>
      </div>
    </div>
    <div class="tools">
      <input id="line-width" @change="onLineWidthChange" type="range" min="5" max="20" value="10">
      <button id="reset-btn" @click="onResetClick">초기화</button>
      <button id="eraser-btn" @click="onEraserClick">지우개</button>
      <button id="brush-btn" @click="onBrushClick">브러쉬</button>
    </div>
    <div>
      <button class="ae-btn btn-navy">종료</button>
      <button @click="onSaveClick" class="ae-btn btn-red">저장</button>
    </div>
  </div>
  <!-- 오른쪽 -->
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
const paintingStore = 'paintingStore'

export default {
  name: 'PaintingBoardView',
  data () {
    return {
      canvas: Object,
      ctx: Object,
      isPainting: false,
      mode: 'brush',
      colorOptions1: ['#ff0000', '#ff8c00', '#ffff00', '#008000'],
      colorOptions2: ['#0000ff', '#800080', '#000080', '#000000'],
      title: '',
      color: ''
    }
  },
  computed: {
    ...mapState(paintingStore, ['sketch'])
  },
  mounted () {
    this.canvas = this.$refs.canvas
    this.ctx = this.canvas.getContext('2d')
    this.canvas.width = 700
    this.canvas.height = 500
    this.ctx.lineWidth = 10
    // base64 문자열 이미지 로드
    const image = new Image()
    image.src = this.sketch
    this.ctx.drawImage(image, 0, 0, this.canvas.width, this.canvas.height)
  },
  methods: {
    ...mapActions(paintingStore, ['savePainting']),
    onMove (event) {
      if (this.isPainting) {
        if (this.mode === 'brush') {
          this.ctx.strokeStyle = this.color
          this.ctx.fillStyle = this.color
          this.ctx.lineTo(event.offsetX, event.offsetY)
          this.ctx.stroke()
        } else if (this.mode === 'eraser') {
          // this.ctx.clearRect(event.offsetX - 5, event.offsetY - 5, this.ctx.lineWidth, this.ctx.lineWidth)
          this.ctx.strokeStyle = 'white'
          this.ctx.fillStyle = 'white'
          this.ctx.lineTo(event.offsetX, event.offsetY)
          this.ctx.stroke()
        }
      }
      this.ctx.beginPath()
      this.ctx.moveTo(event.offsetX, event.offsetY)
    },
    startPainting () {
      this.isPainting = true
    },
    cancelPainting () {
      this.isPainting = false
      this.ctx.beginPath()
    },
    onLineWidthChange (event) {
      this.ctx.lineWidth = event.target.value
    },
    onColorClick (color) {
      this.color = color
      this.ctx.strokeStyle = color
      this.ctx.fillStyle = color
    },
    onBrushClick () {
      this.mode = 'brush'
    },
    onEraserClick () {
      this.mode = 'eraser'
    },
    onResetClick () {
      // 채울 스타일을 적용
      this.ctx.fillStyle = 'white'
      // 캔버스 크기의 사각형으로 채우기
      this.ctx.fillRect(0, 0, this.canvas.width, this.canvas.height)
      this.ctx.beginPath()
    },
    canvasToFile (canvas) {
      // canvas -> dataURL
      let imgBase64 = canvas.toDataURL('image/png')

      const byteString = atob(imgBase64.split(',')[1])
      const ab = new ArrayBuffer(byteString.length)
      const ia = new Uint8Array(ab)
      for (let i = 0; i < byteString.length; i++) {
        ia[i] = byteString.charCodeAt(i)
      }
      const blob = new Blob([ab], { type: 'image/png' })

      // blob -> file
      const paintingFile = new File([blob], 'painting_' + new Date().getMilliseconds() + '.png', { type: 'image/png' })

      return paintingFile
    },
    onSaveClick () {
      // 제목을 썼는지 체크
      if (this.title.trim() === '') {
        alert('제목을 입력해주세요.')
        return
      }
      const paintingFile = this.canvasToFile(this.canvas)

      let data = {
        title: this.title,
        type: 'COLOR'
      }

      let formData = new FormData()
      formData.append('paintingFile', paintingFile)
      formData.append('data', new Blob([JSON.stringify(data)], {type: 'application/json'}))

      this.savePainting(formData)
        .then(
          alert('그림 저장에 성공했습니다.')
        )
        .catch(error => {
          alert('그림 저장에 실패했습니다.' + error)
        })
    }
  }
}
</script>
<style scoped>
.container {
  padding: 0 100px;
  display: flex;
  flex-direction: row;
}

.container-left {
  width: 80%;
  margin-right: 30px;
}

.container-right {
  width: 20%;
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
}

#canvas {
    border-radius: 15px;
    box-shadow: 0px 1px 10px 0.1px rgba(0, 0, 0, 0.3);
    width: 700px;
    height:  500px;
    float: left;
}

/* palette */
#palette {
  display: flex;
  flex-direction: row;
  justify-content: center;
}

.left, .right {
  display: flex;
  flex-direction: column;
}

.color-option {
  width: 40px;
  height: 40px;
  margin: 5px;
  box-shadow: 1px 3px 3px 0.1px rgba(0, 0, 0, 0.3);
  border-radius: 50%;
  cursor: pointer;
}

.title {
  font-size: 35px;
  font-weight: 800;
}

</style>
