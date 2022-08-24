function TableModule(utilsModule) {
    let obj = {};

    /**
     * Table
     * @param title     table标题
     * @param $element  将table渲染到指定元素
     * @param fields    table字段
     * @param url       post,put url
     * @param type      post,put
     * @param clean     是否清理table input值
     * @param callback  执行post,put后响应回调
     * @constructor
     */
    obj.Table = function (title, $element, fields, url, type, clean, callback) {
        this.title = title;
        this.$element = $element;
        this.fields = fields;
        this.$fields = null;
        this.url = url;
        this.type = type;
        this.clean = clean;
        this.callback = callback;
        this._init();
    }

    obj.Table._TEXT_HTML = `
<table>
    <tr>
        <td colspan="2">新增</td>
    </tr>
    <!--
    <tr>
        <td>标题</td>
        <td>
            <input id="title" />
        </td>
    </tr>
    <tr>
        <td>内容</td>
        <td>
            <textarea id="content"></textarea>
        </td>
    </tr>
    <tr>
        <td>权重</td>
        <td>
            <input id="weight" />
        </td>
    </tr>
    -->
    <tr>
        <td colspan="2">
            <button save>保存</button>
        </td>
    </tr>
</table>
`;

    obj.Table.prototype._init = function () {
        const self = this;
        self.$element.html(obj.Table._TEXT_HTML);

        // title
        $(self.$element.find('td')[0]).text(self.title);

        // btn
        let $saveBtn = $(self.$element.find('button[save]')[0]);
        $saveBtn.on('click', function () {
            let params = self.getParams();

            // console.log('params', params);

            function success(resp) {
                if (self.clean) {
                    for (let i = 0, length = self.$fields.length; i < length; i++) {
                        let $field = self.$fields[i];
                        $field.val('');
                    }
                }
                if (self.callback) {
                    self.callback(resp);
                }
            }

            // post
            if (self.type === 'post') {
                utilsModule.Utils.Http.post(self.url, params, success);
                return;
            }
            // put
            else if (self.type === 'put') {
                utilsModule.Utils.Http.put(self.url, params, success);
                return;
            }

            throw new Error('不支持此类型(type=' + self.type + ')');
        });

        // $fields
        self.$fields = [];

        // tr
        let $lastTr = $(self.$element.find('tr')[1]);
        self.fields.forEach(function (field) {

            let $tr = $('<tr><td>' + field.name + '</td></tr>');
            let $td = $('<td></td>');

            // input
            let $input = $('<input name="' + field.code + '"/>');
            // field type
            // date
            if (field.type === 'date') {
                $.datetimepicker.setLocale('ch');
                $input.datetimepicker({
                    format: 'Y-m-d H:i:00',
                });
            }
            // textarea
            else if (field.type === 'textarea') {
                $input = $('<textarea name="' + field.code + '"/>');
            }
            // number
            else if (field.type === 'number') {
                $input.attr('type', 'number');
            }
            // hidden
            else if (field.type === 'hidden') {
                $input.attr('type', 'hidden');
                $tr.attr('style', 'display: none');
            }
            // set value
            if (field.value) {
                $input.val(field.value);
            }
            self.$fields.push($input);

            $input.appendTo($td);
            $td.appendTo($tr);
            $tr.insertBefore($lastTr);
        });
    }

    obj.Table.prototype.getParams = function () {
        const self = this;
        let params = {};
        for (let i = 0, length = self.$fields.length; i < length; i++) {
            let $field = self.$fields[i];
            params[$field.attr('name')] = $field.val();
        }
        return params;
    }

    return obj;
}
