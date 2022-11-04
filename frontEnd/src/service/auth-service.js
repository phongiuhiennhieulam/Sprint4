import axios from 'axios'

const API_URL = 'http://localhost:8083/api/auth/'

class AuthService {
  login (user) {
    return axios
      .post(API_URL + 'signin', {
        userName: user.userName,
        passWord: user.passWord
      })
      .then(response => {
        console.log(response)
        if (response.data.token) {
          localStorage.setItem('user', JSON.stringify(response.data))
        }

        return response.data
      })
  }

  logout () {
    localStorage.removeItem('user')
  }

  register (user) {
    return axios.post(API_URL + 'signup', {
      userName: user.userName,
      passWord: user.passWord
    })
  }
}

export default new AuthService()
