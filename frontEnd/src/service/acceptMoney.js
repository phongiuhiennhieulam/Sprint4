import axios from 'axios';
import authHeader from './auth-hreader'

class AcceptMoneyService{
    async getAllMoneyUp(params) {
        return await axios.get('http://localhost:8083/api/getList/money', {params, headers: authHeader()},);
    }
    async getAllMoney(params) {
        return await axios.get('http://localhost:8083/api/getPage/money', {params, headers: authHeader()});
    }
    async getmoneyAccept(params) {
        return await axios.get('http://localhost:8083/api/getList/moneyAccept', {params, headers: authHeader()});
    }
    async getmoneyCancel(params) {
        return await axios.get('http://localhost:8083/api/getList/moneyCancel', {params, headers    : authHeader()});
    }
    async getmoneyWaiting(params) {
        return await axios.get('http://localhost:8083/api/getList/moneyWaiting', {params, headers: authHeader()});
    }
    async Sort(params) {
        return await axios.get('http://localhost:8083/api/sort-NameAdmin', {params, headers: authHeader()});
    }
    async getNewStaffs(params) {
        return await axios.get('http://localhost:8083/api/list-register', {params});
    }
    //xét duyệt tiền
    async Cancelmoney(id) {
        return await axios.put(`http://localhost:8083/api/cancel-money/${id}`);
    }
    async Returnmoney(id) {
        return await axios.put(`http://localhost:8083/api/return-money/${id}`);
    }
    async Acceptmoney(id) {
        return await axios.put(`http://localhost:8083/api/accpet-money/${id}`);
    }
    async AcceptAll(ids) {
        return await axios.put(`http://localhost:8083/api/accept-all?ids=${ids}`);
    }
    async CancelAll(ids) {
        return await axios.put(`http://localhost:8083/api/cancel-all?ids=${ids}`);
    }
    async ReturnAll(ids) {
        return await axios.put(`http://localhost:8083/api/return-all?ids=${ids}`);
    }
    //xét duyệt nhân viên
    async AcceptStaff(id,money,moneyId) {
        return await axios.put(`http://localhost:8083/api/accept-register/${id}/${money}/${moneyId}`);
    }
    async CancelStaff(id,moneyId) {
        return await axios.put(`http://localhost:8083/api/cancel-register/${id}/${moneyId}`);
    }
    async AcceptAllStaff(ids) {
        return await axios.put(`http://localhost:8083/api/accept-all-register?ids=${ids}`);
    }
    async CancelAllStaff(ids) {
        return await axios.put(`http://localhost:8083/api/cancel-all-register?ids=${ids}`);
    }
}
export default AcceptMoneyService;