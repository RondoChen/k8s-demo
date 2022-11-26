import cookie from 'react-cookies';

export default class Util {
    static async request(method, service, url, params) {
        //localDev
        const lUrl = '/api/' + service + url;
        params = params || {};

        const ticket = cookie.load('ticket');
        const tenant_id = cookie.load('tenant_id');
        if (ticket) {
            params['ticket'] = ticket;
        }
        if (tenant_id) {
            params['tenant_id'] = tenant_id;
        }
        if (method === "GET") {
            let fullUrl = params && Object.keys(params).map(key => key + '=' + params[key]).join('&');
            if (lUrl.indexOf('?') > 0) {
                fullUrl = lUrl + "&" + fullUrl;
            } else {
                fullUrl = lUrl + "?" + fullUrl;
            }
            const response = await fetch(fullUrl);
            return await response.json();

        } else {
            const response_1 = await fetch(lUrl, {
                method: method,
                body: JSON.stringify(params),
                headers: new Headers({
                    'Content-Type': 'application/json'
                })
            });
            return await response_1.json();
        }

    }

    static async getEve(service, url, params) {
        return await Util.request("GET", service, url, params);
    }
    static async postEve(service, url, params) {
        return await Util.request("POST", service, url, params);
    }
    static async putEve(service, url, params) {
        return await Util.request("PUT", service, url, params);
    }
    static async deleteEve(service, url, params) {
        return await Util.request("DELETE", service, url, params);
    }


    static parseQuery() {
        var query = window.location.search.substring(1);
        var vars = query.split('&');
        var params = {}
        for (var i = 0; i < vars.length; i++) {
            var pair = vars[i].split('=');
            var key = decodeURIComponent(pair[0]), value = decodeURIComponent(pair[1]);
            if (params.hasOwnProperty(key)) {
                if (Array.isArray(params[key])) {
                    params[key].push(value);
                } else {
                    params[key] = [params[key], value];
                }
            } else {
                params[key] = value;
            }
        }
        return params;
    }

    static setCookie(key, value, expireSecond) {
        const expires = new Date(1000 * expireSecond);
        // console.log(expires, Date.now(), 123);
        cookie.save(key, value, { path: '/', expires });
    }
    static getCookie(key) {
        return cookie.load(key);
    }
    static removeCookie(key) {
        cookie.remove(key, { path: '/' });
    }
    static getLang() {
        let xlang = cookie.load("__lang") || "zh-CN";
        // if (!xlang) {
        //     xlang = navigator.languages !== undefined ? navigator.languages[0] : navigator.language || "zh-CN";
        //     Util.setCookie("__lang", xlang, 9587047844);
        // }
        return xlang;
    }

    static setLang(xlang) {
        Util.setCookie("__lang", xlang, 9587047844);
    }
    static getUser() {
        return {
            nick: cookie.load("nick"),
            open_id: cookie.load("open_id"),
            email: cookie.load("email"),
            mobile: cookie.load("mobile"),
            avatar: cookie.load("avatar"),
            ticket: cookie.load("ticket"),
        }
    }
    /**
     * 把含有 %s 字符串格式化，如 "abc %s %s", 仅支持 %s
     * @param {}} string 
     * @param  {...any} args 
     */
    static formatString(oriString, ...args) {
        let i = 0;
        return oriString.replace(/(%s)/ig, () => {
            return args[i++] || "";
        });
    }
}
// const util = {
//     request: (method, service, url, params) => {
//         //localDev
//         const lUrl = '/api/' + service + url;
//         if (method === "GET") {
//             let fullUrl = params && Object.keys(params).map(key => key + '=' + params[key]).join('&');
//             if (lUrl.indexOf('?') > 0) {
//                 fullUrl = lUrl + "&" + fullUrl;
//             } else {
//                 fullUrl = lUrl + "?" + fullUrl;
//             }
//             return fetch(fullUrl).then(response => response.json());

//         } else {
//             return fetch(lUrl, {
//                 method: method,
//                 body: JSON.stringify(params),
//                 headers: new Headers({
//                     'Content-Type': 'application/json'
//                 })
//             }).then(response => response.json());
//         }
//     },
//     postEve: (service, url, params) => {
//         return util.request("POST", service, url, params);
//     },
//     getEve: (service, url, params) => {
//         return util.request("GET", service, url, params);
//     },
//     putEve: (service, url, params) => {
//         return util.request("PUT", service, url, params);
//     },
//     deleteEve: (service, url, params) => {
//         return util.request("DELETE", service, url, params);
//     },
//     parseQuery: () => {
//         var query = window.location.search.substring(1);
//         var vars = query.split('&');
//         var params = {}
//         for (var i = 0; i < vars.length; i++) {
//             var pair = vars[i].split('=');
//             var key = decodeURIComponent(pair[0]), value = decodeURIComponent(pair[1]);
//             if (params.hasOwnProperty(key)) {
//                 if (Array.isArray(params[key])) {
//                     params[key].push(value);
//                 } else {
//                     params[key] = [params[key], value];
//                 }
//             } else {
//                 params[key] = value;
//             }
//         }
//         return params;
//     }
// };
// module.exports = util;
// export default eve;