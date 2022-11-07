import axios from 'axios';

/**
 * Api lấy ra danh sách toàn bộ phòng ban
 * */
class DepartmentApi {

    async getDepartment() {
        return await axios.get('http://localhost:8083/api/departments');
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