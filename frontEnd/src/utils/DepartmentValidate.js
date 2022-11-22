import { required } from 'vee-validate/dist/rules'
import { extend } from 'vee-validate'

extend('required', {
  ...required,
  message: 'không được để trống'
})
export default {
  data() {
    return {
      addDepartment: {
        name: 'Tên phòng ban ',
      }
    }
  },
  methods: {
    messageError(fieldName, error) {
      if (error === undefined) {
        return undefined
      }
      return fieldName + error
    }
  }
}
