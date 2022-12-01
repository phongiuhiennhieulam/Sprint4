import axios from 'axios';
import authHeader from './auth-hreader'

class StaffService {
    updateMoney (money,ids) {

        return  axios.put('http://localhost:8083/api/staff/update-money/' + money + '?ids=' + ids , {headers: authHeader()})
    }
    updateMoney2 (money,ids, email) {
        return  axios.post('http://localhost:8083/api/staff/update-money2/' + money + '?ids=' + ids + '&email=' + email, {headers: authHeader()})
    }
     getAll(params) {
        return  axios.get('http://localhost:8083/api/staffs', {params, headers: authHeader()})
    }
     getStaff(id) {
        return  axios.get(`http://localhost:8083/api/staff/${id}`, { headers: authHeader()})
    }
     getStaffByEmail(email) {
        return  axios.get(`http://localhost:8083/api/staff-name/${email}`)
    }
     getCode() {
        return  axios.get('http://localhost:8083/api/getcode')
    }
     getBirthdays() {
        return  axios.get('http://localhost:8083/api/birthdays')
    }
     getEmail() {
        return  axios.get('http://localhost:8083/api/getemail')
    }
     getEmail2(id) {
        return  axios.get(`http://localhost:8083/api/getemail2/${id}`)
    }
     getCode2(id) {
        return  axios.get(`http://localhost:8083/api/getcode2/${id}`)
    }
     getUser() {
        return  axios.get('http://localhost:8083/api/get-user', { headers: authHeader()})
    }
     getFind(text) {
        return  axios.get(`http://localhost:8083/api/staffs/=${text}`, { headers: authHeader()})
    }
     getWelfare(id) {
        return  axios.get(`http://localhost:8083/api/staff-show/${id}`, { headers: authHeader()}) ;
    }
     getWelfareRegister(id) {
        return  axios.get(`http://localhost:8083/api/staff-show-register/${id}`, { headers: authHeader()});
    }
     getGeneralWelfare() {
        return  axios.get('http://localhost:8083/api/general-welfanes');
    }
     getDepartments() {
        return  axios.get('http://localhost:8083/api/departments', );
    }
     deleteStaff(id) {
        return  axios.put(`http://localhost:8083/api/staff-delete/${id}`, { headers: authHeader()})
    }
     unlookStaff(id) {
        return  axios.put(`http://localhost:8083/api/staff-unlock/${id}`, { headers: authHeader()})
    }
     updateStaff(id, staff) {
        return  axios.put(`http://localhost:8083/api/staffs/${id}`, staff,  { headers: authHeader()})
    }
     createStaff2(staff) {
        return  axios.post('http://localhost:8083/api/staffs', staff, { headers: authHeader()})
    }
     getRegisterWelfare() {
        return  axios.get('http://localhost:8083/api/registers', { headers: authHeader()})
    }
      
     DeleteRegisterWelfare(id) {
        return  axios.put(`http://localhost:8083/api/register-delete/${id}`, { headers: authHeader()})
    }
deletes (ids) {
        return  axios.put(`http://localhost:8083/api/staff-deletes?ids=${ids}`, { headers: authHeader()})
    }
     getErorr () {
        return  axios.get('http://localhost:8083/api/staff-erorr', { headers: authHeader()})
    }
     getStaffByDepartment (id) {
        return  axios.get(`http://localhost:8083/api/list-department/${id}`, { headers: authHeader()})
    }
     getDepartment2 () {
        return  axios.get('http://localhost:8083/api/list-department/1', { headers: authHeader()})
    }
     SuccessRegisterWelfare(id) {
        return  axios.put(`http://localhost:8083/api/register/${id}`, { headers: authHeader()})
    }
     ReturnRegisterWelfare(id) {
        return  axios.put(`http://localhost:8083/api/return/${id}`, { headers: authHeader()})
    }
     GetStaffByWelfare(id) {
        return  axios.get(`http://localhost:8083/api/staffs-by-welfare/${id}`,  { headers: authHeader()})
    }   
     GetCost() {
        return  axios.get('http://localhost:8083/api/costs')
    } 

    async ImportExcel(file) {
        return await axios.post('http://localhost:8083/api/uploadExcel', file, { headers: authHeader() })
    }
   
   
}
export default new StaffService()
