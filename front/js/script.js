import FetchUtil from './fetchUtil.js'

const todoVue = new Vue({
    el: '#app',

    /**
     * @property {Array} doList やることリスト
     * @property {doneList} doneList 完了したことリスト
     * @property {string} newTask 新規追加するタスク
     */
    data: {
        doList: [],
        doneList: [],
        newTask: '',
    },

    methods: {

        /**
         * 新規タスクを追加
         */
        addTask() {

            FetchUtil.post('http://localhost:8080/todo/create', 
                {task: {taskId: null, taskName: this.newTask, done: false}, message: ''}).then((response)=> {

                this.doList.push({taskId: response.taskId, taskName: this.newTask, done: false})
            })

        },

        /**
         * タスクを完了させる
         * 
         * @param {Object} task 
         */
        doneTask(task) {
            task.done = true

            FetchUtil.post('http://localhost:8080/todo/update', {task: task, message: ''}).then((response) => {

                this.doList = this.doList.filter((doTask) => {
                    return doTask.taskId !== task.taskId
                })

                this.doneList.push(task)
            })
        }

    },

    /**
     * APIを通してTodoリストを取得
     */
    mounted() {

        FetchUtil.get('http://localhost:8080/todo/').then((todoList) => {

            todoList.forEach((item) => {

                if (item.done) {
                    this.doneList.push(item)
                }

                if (!item.done) {
                    this.doList.push(item)
                }
            })

        })
    }
})
