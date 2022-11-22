import axios from 'axios';
import authHeader from './auth-hreader'

class UserService{

    async getAUser(params){
        return await axios.get('http://localhost:8083/api/get-user', {params})
    }
    async getAllUser(params){
        return await axios.get('http://localhost:8083/api/users', {params, headers: authHeader()})
    }
    async getUser(id){
        return await axios.get(`http://localhost:8083/api/user/${id}`)
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
    async getRole(idUser,roles){
        return await axios.put(`http://localhost:8083/api/add-Role/${idUser}`, roles)
    }
}

export default UserService;