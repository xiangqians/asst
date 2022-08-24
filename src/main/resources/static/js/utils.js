function UtilsModule() {

    let obj = {};

    /**
     * 工具类
     * @constructor
     */
    obj.Utils = function () {
    }

    /**
     * 双击事件处理器
     * @constructor
     */
    obj.Utils.DoubleClickEvent = function () {
        this.clickCount = 0;
        this.prevTimestamp = 0;
        this.curTimestamp = 0;
    }
    /**
     * 点击
     * @param callback 双击事件回调函数
     * @param interval 双击间隔，单位：ms
     */
    obj.Utils.DoubleClickEvent.prototype.click = function (callback, interval) {
        if (interval == undefined) {
            interval = 300;
        }
        const self = this;

        // setTimeout
        // _self.clickCount++;
        // setTimeout(() => {
        //     if (_self.clickCount === 2) {
        //         callback();
        //     }
        //     _self.clickCount = 0;
        // }, interval);

        // timestamp
        self.prevTimestamp = self.curTimestamp;
        self.curTimestamp = new Date().getTime();
        if (self.curTimestamp - self.prevTimestamp < interval) {
            self.prevTimestamp = 0;
            callback();
        }

    }

    /**
     * 获取请求参数
     * @returns {null|Map<any, any>}
     */
    obj.Utils.getRequestParams = function () {
        let url = location.href;
        // console.log('url', url);
        let index = url.indexOf("?");
        if (index <= 0) {
            return new Map();
        }

        let map = new Map();
        let params = url.substring(index + 1, url.length).split('&');
        for (let i = 0, length = params.length; i < length; i++) {
            let nv = params[i].split('=');
            let name = nv[0].trim();
            let value = nv.length == 2 ? nv[1].trim() : null;
            map.set(name, value);
        }
        return map;
    }

    /**
     * 存储器
     * @constructor
     */
    obj.Utils.Storage = function () {
    }

    obj.Utils.Storage.set = function (name, value) {
        obj.Utils.Storage._storageStrategy.set(name, value);
    }

    obj.Utils.Storage.get = function (name) {
        return obj.Utils.Storage._storageStrategy.get(name);
    }

    // session Storage
    // sessionStorage和localStorage的区别是：
    // 1、localStorage没有过期时间
    // 2、sessionStorage针对一个session进行数据存储，生命周期与session相同，当用户关闭浏览器后，数据将被删除。
    obj.Utils.Storage._sessionStorage = function () {
    }

    obj.Utils.Storage._sessionStorage.set = function (name, value) {
        sessionStorage.setItem(name, value)
    }

    obj.Utils.Storage._sessionStorage.get = function (name) {
        return sessionStorage.getItem(name);
    }

    // cookie Storage
    obj.Utils.Storage._cookieStorage = function () {
    }

    obj.Utils.Storage._cookieStorage.set = function (name, value) {
        document.cookie = name + "=" + value;
    }

    obj.Utils.Storage._cookieStorage.get = function (name) {
        let cookie = document.cookie;
        let array = cookie.split(';');
        for (let i = 0, length = array.length; i < length; i++) {
            let kv = array[i].split('=');
            if (name === kv[0].trim()) {
                return kv.length >= 2 ? kv[1].trim() : null;
            }
        }
        return null;
    }

    // 设置存储策略
    obj.Utils.Storage._storageStrategy = obj.Utils.Storage._sessionStorage;

    /**
     * Http
     * @constructor
     */
    obj.Utils.Http = function () {
    }

    /**
     * ajax
     * @param url
     * @param data
     * @param type post,delete,put,get
     * @param success
     * @param error
     * @private
     */
    obj.Utils.Http._ajax = function (url, data, type, success, error) {
        $.ajax({
            url: url,
            data: data,
            type: type,
            dataType: "json",
            async: false,
            headers: { 'Content-Type': 'application/json;charset=utf-8' }, //接口json格式
            success: function (resp) {
                if (resp.statusCode !== 200) {
                    if (error) {
                        error(resp);
                    } else {
                        alert(resp.message);
                    }
                    return;
                }

                if (success) {
                    success(resp);
                }
            },
            error: function (resp) {
                if (error) {
                    error(resp);
                    return;
                }
                alert(resp.message);
            }
        });
    }

    /**
     * POST
     * @param url
     * @param data
     * @param success
     * @param error
     */
    obj.Utils.Http.post = function (url, data, success, error) {
        obj.Utils.Http._ajax(url, JSON.stringify(data), 'post', success, error);
    }

    /**
     * DELETE
     * @param url
     * @param data
     * @param success
     * @param error
     */
    obj.Utils.Http.delete = function (url, data, success, error) {
        obj.Utils.Http._ajax(url, JSON.stringify(data), 'delete', success, error);
    }

    /**
     * PUT
     * @param url
     * @param data
     * @param success
     * @param error
     */
    obj.Utils.Http.put = function (url, data, success, error) {
        obj.Utils.Http._ajax(url, JSON.stringify(data), 'put', success, error);
    }

    /**
     * GET
     * @param url
     * @param data
     * @param success
     * @param error
     */
    obj.Utils.Http.get = function (url, data, success, error) {
        obj.Utils.Http._ajax(url, data, 'get', success, error);
    }

    /**
     * Object
     * @constructor
     */
    obj.Utils.Object = function () {
    }

    /**
     * 寄生组合式继承
     * 继承父类属性；覆盖父类方法。
     * @param subType 子类类型
     * @param superType 父类类型
     */
    obj.Utils.Object.inherit = function (subType, superType) {
        // 在 new Fn() 的时候将构造函数指向子类
        function Fn() {
            // 修复实例
            this.constructor = subType
        }

        // new Fn()实例的原型继承了父类函数的原型
        Fn.prototype = superType.prototype;

        // 将子类的原型指向父类原型的一个副本
        subType.prototype = new Fn();
    };

    return obj;
}
