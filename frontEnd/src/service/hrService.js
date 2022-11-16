import axios from 'axios';
import authHeader from './auth-hreader'

class StaffService {
    async updateMoney (money,ids) {
        return await axios.put('http://localhost:8083/api/staff/update-money/' + money + '?ids=' + ids, {headers: authHeader()})
    }
    async getAll(params) {
        return await axios.get('http://localhost:8083/api/staffs', {params, headers: authHeader()})
    }
    async getStaff(id) {
        return await axios.get(`http://localhost:8083/api/staff/${id}`, { headers: authHeader()})
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
    async getEmail2(id) {
        return await axios.get(`http://localhost:8083/api/getemail2/${id}`)
    }
    async getCode2(id) {
        return await axios.get(`http://localhost:8083/api/getcode2/${id}`)
    }
    async getUser() {
        return await axios.get('http://localhost:8083/api/get-user', { headers: authHeader()})
    }
    async getFind(text) {
        return await axios.get(`http://localhost:8083/api/staffs/find?text=${text}`, { headers: authHeader()})
    }
    async getWelfare(id) {
        return await axios.get(`http://localhost:8083/api/staff-show/${id}`, { headers: authHeader()}) ;
    }
    async getWelfareRegister(id) {
        return await axios.get(`http://localhost:8083/api/staff-show-register/${id}`, { headers: authHeader()});
    }
    async getGeneralWelfare() {
        return await axios.get('http://localhost:8083/api/general-welfanes');
    }
    async getDepartments() {
        return await axios.get('http://localhost:8083/api/departments', );
    }
    async deleteStaff(id) {
        return await axios.put(`http://localhost:8083/api/staff-delete/${id}`, { headers: authHeader()})
    }
    async unlookStaff(id) {
        return await axios.put(`http://localhost:8083/api/staff-unlock/${id}`, { headers: authHeader()})
    }
    async updateStaff(id, staff) {
        return await axios.put(`http://localhost:8083/api/staffs/${id}`, staff,  { headers: authHeader()})
    }
    async createStaff2(staff) {
        return await axios.post('http://localhost:8083/api/staffs', staff, { headers: authHeader()})
    }
    async getRegisterWelfare() {
        return await axios.get('http://localhost:8083/api/registers', { headers: authHeader()})
    }
      
    async DeleteRegisterWelfare(id) {
        return await axios.put(`http://localhost:8083/api/register-delete/${id}`, { headers: authHeader()})
    }
    async deletes (ids) {
        return await axios.put(`http://localhost:8083/api/staff-deletes?ids=${ids}`, { headers: authHeader()})
    }
    async getErorr () {
        return await axios.get('http://localhost:8083/api/staff-erorr', { headers: authHeader()})
    }
    async getStaffByDepartment (id) {
        return await axios.get(`http://localhost:8083/api/list-department/${id}`, { headers: authHeader()})
    }
    async getDepartment2 () {
        return await axios.get('http://localhost:8083/api/list-department/1', { headers: authHeader()})
    }
    async SuccessRegisterWelfare(id) {
        return await axios.put(`http://localhost:8083/api/register/${id}`, { headers: authHeader()})
    }
    async ReturnRegisterWelfare(id) {
        return await axios.put(`http://localhost:8083/api/return/${id}`, { headers: authHeader()})
    }
    async GetStaffByWelfare(id) {
        return await axios.get(`http://localhost:8083/api/staffs-by-welfare/${id}`,  { headers: authHeader()})
    }   
    async GetCost() {
        return await axios.get('http://localhost:8083/api/costs')
    } 
   
   
}
export default new StaffService()