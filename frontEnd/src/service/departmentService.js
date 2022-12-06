import axios from 'axios';
import authHeader from './auth-hreader'
/**
 * Api lấy ra danh sách toàn bộ phòng ban
 * */
class DepartmentApi {

    async getDepartment(params) {
        return await axios.get('http://localhost:8083/api/departmentss', {params, headers: authHeader()});
    }

    async deleteDepartment(id) {
        return await axios.delete('http://localhost:8083/api/department/' + id)
    }
    async updateDepartment(id, department) {

        return await axios.put(`http://localhost:8083/api/department/${id}`, department)
    }
    async createDepartment(department) {
        return await axios.post('http://localhost:8083/api/department', department)
    }
    async getFind(text) {
        return await axios.get(`http://localhost:8083/api/department/find?text=${text}`)
    }
}
export default DepartmentApi;