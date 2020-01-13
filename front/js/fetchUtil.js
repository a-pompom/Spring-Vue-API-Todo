export default class FetchUtil {

    /**
     * APIからリソースを取得
     * 
     * @param {string} url リソース取得先URL
     * 
     * @return {Promise} responseJSON レスポンスをJSONに整形するPromiseオブジェクト
     */
    static async get(url) {

        const response = await fetch(url)
        const responseJSON = await response.json()

        return responseJSON
    }

    /**
     * APIへPOSTリクエストを送信
     * 
     * @param {string} url POSTリクエスト送信先URL
     * @param {Objcat} body 送信するリクエストボディ
     * 
     * @return {Promise} reponseJSON レスポンスをJSONに整形するPromiseオブジェクト
     */
    static async post(url, body) {

        const options = {
            method: 'POST',
            body: JSON.stringify(body),
            headers: new Headers({'Accept': 'application/json','content-type': 'application/json'})
        }

        const response = await fetch(url, options)
        const responseJSON = await response.json()

        return responseJSON
    }

}