export default {
   namespaced: true,
  state: {
    groupmodel:false,
    count:false,
    userIds:null,
    Taskmode:false
  },
  mutations: {
    TaskmodeClick(state,value){
      state.Taskmode = value;
    },
    increment(state,value) {
      state.userIds = value;
    },
    handleClassMode(state,value){
       state.count = value;
    },
    groupmodelClick(state,value){
       state.groupmodel = value;
    },
  },
};
