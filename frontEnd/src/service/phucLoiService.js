import axios from 'axios';

/**
 * Api lấy ra danh sách toàn bộ phúc lợi
 * */
class WelfareApi {

    async getAllWelfare(year) {
        return await axios.get('http://localhost:8083/api/welfares/'+year);
    }
    async getAllWelfareByStatus() {
        return await axios.get('http://localhost:8083/api/welfares-user');
    }
    async getAllWelfareWaitToUpdate() {
        return await axios.get('http://localhost:8083/api/get-welfare-waiting-update');
    }

    async deleteWelfare(id) {
        return await axios.delete('http://localhost:8083/api/welfare/' + id)
    }
    async updateWelfare(id,status, object) {

        return await axios.put('http://localhost:8083/api/welfare-approval/' + id + '/'+status, object)
    }
    async createWelfare(status,object) {
        return await axios.post('http://localhost:8083/api/welfare-approval/'+status, object)
    }
    async acceptCreateWelfare(object) {
        return await axios.post('http://localhost:8083/api/welfare', object)
    }
    async acceptUpdateWelfare(id,object) {
        return await axios.put('http://localhost:8083/api/welfare/'+id, object)
    }
    async registerWelfare(object) {
        return await axios.post('http://localhost:8083/api/regis_welfare', object)
    }
    async findID(username) {
        return await axios.get('http://localhost:8083/api/find_id/'+username)
    }
    async getAllGeneralWelfare(year) {
        return await axios.get('http://localhost:8083/api/general-welfanes/'+year);
    }
    async deleteGeneralWelfare(id) {
        return await axios.delete('http://localhost:8083/api/general-welfane/'+id);
    }
    async updateGeneralWelfare(id, object) {
        return await axios.put('http://localhost:8083/api/general-welfane/'+ id, object);
    }
    async createGeneralWelfare(object) {
        return await axios.post('http://localhost:8083/api/general-welfane-approval',object);
    }
    async getAllWelfareByUser(id) {
        return await axios.get('http://localhost:8083/api/staff-show-all/'+id);
    }
    async getTotalMoney(username) {
        return await axios.get('http://localhost:8083/api/find_money/'+username);
    }
    async getTotalMoneyGeneralWelfare() {
        return await axios.get('http://localhost:8083/api/get_all_general_money');
    }
    async getAllWelfareWithQuantity(id) {
        return await axios.get('http://localhost:8083/api/getRegisterWelfare/'+id);
    }
    async getCurrentWelfareWithQuantity(id) {
        return await axios.get('http://localhost:8083/api/getCurrentRegisterWelfare/'+id);
    }
    async getOnlyOneWelfareOfUser(id) {
        return await axios.get('http://localhost:8083/api/selectOnlyOneWelfare/'+id);
    }
    
    async getAcceptWelfareOfUser(id) {
        return await axios.get('http://localhost:8083/api/getAcceptWelfare/'+id);
    }
    async getHistoryAcceptWelfareOfUser() {
        return await axios.get('http://localhost:8083/api/get-history-accept-welfare');
    }

    async getWelfare(id) {
        return await axios.get(`http://localhost:8083/api/getWelfare/${id}`);
    }
    async changeStatusWelfare(object){
        return await axios.put('http://localhost:8083/api/status-welfare', object);

    }
    async getStatusAcceptWelfareOfUser(id) {
        return await axios.get('http://localhost:8083/api/get-welfare-status/'+id);
    }
    async getAllWelfareUpdate() {
        return await axios.get('http://localhost:8083/api/get-welfare-update-approval');
    }
    async denyWelfareUpdate(id) {
        return await axios.put('http://localhost:8083/api/deny-welfare-update-approval/'+id);
    }
    async acceptAllWelfare(list) {
        return await axios.post('http://localhost:8083/api/accept-all-welfare',list);
    }
    async denyAllWelfare(ids) {
        return await axios.put(`http://localhost:8083/api/deny-all-welfare?ids=${ids}`);
    }
    

    
}
export default WelfareApi;
