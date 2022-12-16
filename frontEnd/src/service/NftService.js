import axios from 'axios';
import authHeader from './auth-hreader'

class NtfService {
    getNew () {
        return  axios.get('http://localhost:8083/api/nft-new', {headers: authHeader()})
    }
    getOld () {
        return  axios.get('http://localhost:8083/api/nft-old'  , {headers: authHeader()})
    }
    Seen() {
        return  axios.get('http://localhost:8083/api/seen' , {headers: authHeader()})
    }
     
   
}
export default new NtfService()
