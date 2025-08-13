// 在 utils 文件夹下创建 util.js 文件

export function debounce(fn, interval) {
  let canRun = true;
  return function() {
    if (!canRun) {
      return;
    }
    canRun = false;
    fn.apply(this, arguments);
    setTimeout(() => {
      canRun = true;
    }, interval);
  };
}

// 节流函数
// export function debounce(fn, interval) {
//   let canRun = true;
//   return function() {
//     if (!canRun) {
//       return;
//     }
//     canRun = false;
//     setTimeout(() => {
//       fn.apply(this, arguments);
//       canRun = true;
//     }, interval);
//   };
// }