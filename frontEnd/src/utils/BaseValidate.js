import { ext, regex, required } from 'vee-validate/dist/rules'
import { extend } from 'vee-validate'

extend('required', {
  ...required,
  message: ' bắt buộc phải nhập mật khẩu'
})
extend('ext', {
  ...ext,
  message: ' phải chứa chữ hoa và số'
})
extend('regex', {
  ...regex,
  message: ' phải chứa chữ hoa và số'
})
extend('max', {

  validate(value, args) {
    return value.toString().length <= args.length
  },
  params: ['length'],
  message: ` không vượt quá {length} ký tự`
})

export default {
  data() {
    return {
      loginFields: {
        username: 'Tên đăng nhập',
        password: 'Mật khẩu'
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
