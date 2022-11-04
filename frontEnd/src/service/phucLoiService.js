import axios from 'axios';

/**
 * Api lấy ra danh sách toàn bộ phúc lợi
 * */
class WelfareApi {

    async getAllWelfare() {
        return await axios.get('http://localhost:8083/api/welfares');
    }

    async deleteWelfare(id) {
        return await axios.delete('http://localhost:8083/api/welfare/' + id)
    }
    async updateWelfare(id, object) {

        return await axios.put('http://localhost:8083/api/welfare/' + id, object)
    }
    async createWelfare(object) {
        return await axios.post('http://localhost:8083/api/welfare', object)
    }
    async registerWelfare(object) {
        return await axios.post('http://localhost:8083/api/regis_welfare', object)
    }
    async findID(username) {
        return await axios.get('http://localhost:8083/api/find_id/'+username)
    }
    async getAllGeneralWelfare() {
        return await axios.get('http://localhost:8083/api/general-welfanes');
    }
    async deleteGeneralWelfare(id) {
        return await axios.delete('http://localhost:8083/api/general-welfane/'+id);
    }
    async updateGeneralWelfare(id, object) {
        return await axios.put('http://localhost:8083/api/general-welfane/'+ id, object);
    }
    async createGeneralWelfare(object) {
        return await axios.post('http://localhost:8083/api/general-welfane',object);
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
    

    
}
export default WelfareApi;
