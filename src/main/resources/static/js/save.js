/**
 *
 * @constructor
 */
function Save(title, $element, fields, url, callback) {
    this.title = title;
    this.$element = $element;
    this.fields = fields;
    this.$fields = null;
    this.url = url;
    this.callback = callback;
    this._init();
}

Save._TEXT_HTML = `
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

Save.prototype._init = function () {
    const self = this;
    self.$element.html(Save._TEXT_HTML);

    // title
    $(self.$element.find('td')[0]).text(self.title);

    // btn
    let $saveBtn = $(self.$element.find('button[save]')[0]);
    $saveBtn.on('click', function () {
        let params = {};
        for (let i = 0, length = self.$fields.length; i < length; i++) {
            let $field = self.$fields[i];
            params[$field.attr('name')] = $field.val();
        }
        // console.log('params', params);
        Utils.Http.post(self.url, params, function (resp) {
            for (let i = 0, length = self.$fields.length; i < length; i++) {
                let $field = self.$fields[i];
                $field.val('');
            }
            if (self.callback) {
                self.callback(resp);
            }
        });
    });

    // tr
    let $lastTr = $(self.$element.find('tr')[1]);
    self.fields.forEach(function (field) {
        let $tr = $('<tr><td>' + field.title + '</td><td><input name="' + field.name + '"/></td></tr>');
        if (field.type === 'date') {
            $.datetimepicker.setLocale('ch');
            let $input = $($tr.find('input')[0]);
            $input.datetimepicker({
                format: 'Y-m-d H:i:00',
            });
        }
        $tr.insertBefore($lastTr);
    });

    // $fields
    self.$fields = [];
    let inputs = self.$element.find('input[name]');
    for (let i = 0, length = inputs.length; i < length; i++) {
        self.$fields.push($(inputs[i]));
    }
}
