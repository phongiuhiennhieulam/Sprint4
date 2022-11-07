import axios from 'axios';
import authHeader from './auth-hreader'
/**
 * Api lấy ra danh sách toàn bộ nhân viên
 * */
class StaffService {

    async getAll(params) {
        return await axios.get('http://localhost:8083/api/staffs', {params, headers: authHeader()})
    }
    async getStaff(id) {
        return await axios.get(`http://localhost:8083/api/staff/${id}`)
    }
    async getStaffByEmail(email) {
        return await axios.get(`http://localhost:8083/api/staff-name/${email}`)
    }
    async getCode() {
        return await axios.get('http://localhost:8083/api/getcode')
    }
    async getBirthdays() {
        return await axios.get('http://localhost:8083/api/birthdays')
    }
    async getEmail() {
        return await axios.get('http://localhost:8083/api/getemail')
    }
    async getUser() {
        return await axios.get('http://localhost:8083/api/get-user', { headers: authHeader()})
    }
    async getFind(text) {
        return await axios.get(`http://localhost:8083/api/staffs/find?text=${text}`)
    }
    async getWelfare(id) {
        return await axios.get(`http://localhost:8083/api/staff-show/${id}`);
    }
    async getWelfareRegister(id) {
        return await axios.get(`http://localhost:8083/api/staff-show-register/${id}`);
    }
    async getGeneralWelfare() {
        return await axios.get('http://localhost:8083/api/general-welfanes');
    }
    async getDepartments() {
        return await axios.get('http://localhost:8083/api/departments');
    }

    async deleteStaff(id) {
        return await axios.put(`http://localhost:8083/api/staff-delete/${id}`)
    }
    async unlookStaff(id) {
        return await axios.put(`http://localhost:8083/api/staff-unlock/${id}`)
    }
    async updateStaff(id, staff) {
        return await axios.put(`http://localhost:8083/api/staff/${id}`, staff)
    }
    async createStaff(staff) {
        return await axios.post('http://localhost:8083/api/staff', staff)
    }
    async createStaff2(staff) {
        return await axios.post('http://localhost:8083/api/staffs', staff)
    }
    async getRegisterWelfare() {
        return await axios.get('http://localhost:8083/api/registers')
    }
    async SuccessRegisterWelfare(id) {
        return await axios.put(`http://localhost:8083/api/register/${id}`)
    }
    async DeleteRegisterWelfare(id) {
        return await axios.put(`http://localhost:8083/api/register-delete/${id}`)
    }
    async updateMoney (ids) {
        return await axios.put(`http://localhost:8083/api/staff/update-money?ids=${ids}`)
    }
    async deletes (ids) {
        return await axios.put(`http://localhost:8083/api/staff/deletes?ids=${ids}`)
    }
    async getErorr () {
        return await axios.get('http://localhost:8083/api/staff-erorr')
    }
    async getStaffByDepartment (id) {
        return await axios.get(`http://localhost:8083/api/list-department/${id}`)
    }
    async getDepartment2 () {
        return await axios.get('http://localhost:8083/api/list-department/1')
    }
   
}
export default new StaffService()