<template>
  <div class="container">
    <canvas id="canvas"
      ref="canvas"
      @mousemove="onMove"
      @mousedown="startPainting"
      @mouseup="cancelPainting"
      @mouseleave="cancelPainting"
    ></canvas>
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
      <button>종료</button>
      <button>저장</button>
    </div>
  </div>
</template>

<script>

export default {
  name: 'PaintingBoardView',
  data () {
    return {
      canvas: Object,
      ctx: Object,
      isPainting: false,
      mode: 'brush',
      colorOptions1: ['#ff0000', '#ff8c00', '#ffff00', '#008000'],
      colorOptions2: ['#0000ff', '#800080', '#000080', '#000000']
    }
  },
  mounted () {
    this.canvas = this.$refs.canvas
    this.ctx = this.canvas.getContext('2d')
    this.canvas.width = 800
    this.canvas.height = 500
    this.ctx.lineWidth = 10
  },
  methods: {
    onMove (event) {
      if (this.isPainting) {
        if (this.mode === 'brush') {
          this.ctx.lineTo(event.offsetX, event.offsetY)
          this.ctx.stroke()
        } else if (this.mode === 'eraser') {
          this.ctx.clearRect(event.offsetX - 5, event.offsetY - 5, this.ctx.lineWidth, this.ctx.lineWidth)
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
      this.ctx.clearRect(0, 0, 800, 500)
      this.ctx.beginPath()
    }
  }
}
</script>
<style scoped>
.container {
  padding: 0 100px;
}

#canvas {
    border-radius: 15px;
    box-shadow: 0px 1px 10px 0.1px rgba(0, 0, 0, 0.3);
    width: 800px;
    height:  500px;
    float: left;
}

/* palette */
#palette {
  display: flex;
  flex-direction: row;
  background-color: pink;
  width: 200px;
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

</style>
