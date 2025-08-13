<template>
  <div class="container">
    <button @click="exportPage" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
      导出页面
    </button>
    <div ref="content" class="mt-4 p-4 border border-gray-300 rounded">
      <h1 class="text-2xl font-bold mb-2">测试1111</h1>
      <p class="mb-2">测试1111</p>

    </div>
  </div>
</template>

<script>
import html2canvas from 'html2canvas';
import jsPDF from 'jspdf';

export default {
  name: 'ExportPage',
  methods: {
    exportPage() {
      const content = this.$refs.content;

      // 导出为PDF
      html2canvas(content).then(canvas => {
        const imgData = canvas.toDataURL('image/png');
        const pdf = new jsPDF('p', 'mm', 'a4');
        const imgWidth = 210;
        const imgHeight = canvas.height * imgWidth / canvas.width;

        pdf.addImage(imgData, 'PNG', 0, 0, imgWidth, imgHeight);
        pdf.save('页面导出.pdf');
      });

      // 也可以导出为图片
      // html2canvas(content).then(canvas => {
      //   const link = document.createElement('a');
      //   link.download = '页面导出.png';
      //   link.href = canvas.toDataURL('image/png');
      //   link.click();
      // });
    }
  }
}
</script>

<style scoped>
.container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}
</style>
