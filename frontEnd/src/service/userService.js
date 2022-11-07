import axios from 'axios';
import authHeader from './auth-hreader'

class UserService{

    async getAllUser(params){
        return await axios.get('http://localhost:8083/api/get-user', {params, headers: authHeader()})
    }
    async getUser(id){
        return await axios.get(`http://localhost:8083/api/user/${id}`)
    }
    async getEmail() {
        return await axios.get('http://localhost:8083/api/getemail')
    }
    async getStaff() {
        return await axios.get(`http://localhost:8083/api/list`)
    }
    async getFind(text) {
        return await axios.get(`http://localhost:8083/api/users/find?text=${text}`)
    }
    async deleteUser(id) {
        return await axios.put(`http://localhost:8083/api/user-delete/${id}`)
    }
    async unlockUser(id) {
        return await axios.put(`http://localhost:8083/api/user-unlock/${id}`)
    }
    async updateUser(id, user) {
        return await axios.put(`http://localhost:8083/api/user-update/${id}`, user)
    }
    async createUser(user) {
        return await axios.post('http://localhost:8083/api/create-user', user)
    }
    async getCode() {
        return await axios.get('http://localhost:8083/api/getcode')
    }
    async getRole(id,user){
        return await axios.get(`http://localhost:8083/api/add-Role/${id}`, user)
    }
}

export default UserService;