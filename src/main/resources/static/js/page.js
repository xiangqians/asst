/**
 * Page
 * @param $element
 * @param fields
 * @param searchFields
 * @param url
 * @constructor
 */
function Page($element, fields, searchFields, url) {
    this.$element = $element;
    this.$thead = null;
    this.$tbody = null;
    this.$tfoot = null;
    this.$current = null;
    this.$pages = null;
    this.$size = null;
    this.$total = null;
    this.$search = null;
    this.fields = fields;
    this.searchFields = searchFields;
    this.current = 1;
    this.size = 10;
    this.url = url;
    this._init();
}

Page._TEXT_HTML = `
<div search class="div-center">
    <!--
    标题：<input search name="title">
    内容：<input search name="content">
    <button search">搜索</button>
    -->
</div>
<table>
    <!-- 表格头 -->
    <thead>
    <!--
    <tr>
        <td>ID</td>
        <td>标题</td>
        <td>内容</td>
        <td>权重</td>
        <td>创建时间</td>
        <td>修改时间</td>
        <td>操作</td>
    </tr>
    -->
    </thead>

    <!-- 表格体 -->
    <tbody>
    </tbody>

    <!-- 表格脚注 -->
    <tfoot>
    <tr>
        <td colspan="7">
            current: <span current></span>&nbsp;&nbsp;
            pages: <span pages></span>&nbsp;&nbsp;
            size: <span size></span>&nbsp;&nbsp;
            total: <span total></span>&nbsp;&nbsp;
            <a href="javascript:;" previousPage>上一页</a>&nbsp;&nbsp;
            <a href="javascript:;" nextPage>下一页</a>
        </td>
    </tr>
    </tfoot>
</table>
`;

Page.prototype._init = function () {
    const self = this;
    self.$element.html(Page._TEXT_HTML);

    // 搜索框
    if (self.searchFields) {
        self.$search = $(self.$element.find('div[search]')[0]);
        self.searchFields.forEach(function (searchField) {
            $('<span>' + searchField.title + '：</span>').appendTo(self.$search);
            $('<input search name="' + searchField.name + '">').appendTo(self.$search);
            self.$search.append('&nbsp;&nbsp;');
        });
        let $btn = $('<button>搜索</button>');
        $btn.appendTo(self.$search);
        $btn.on('click', function () {
            self.current = 1;
            self.load();
        });
    }

    // thead-表格头
    self.$thead = $(self.$element.find('thead')[0]);
    let $tr = $('<tr></tr>');
    self.fields.forEach(function (field) {
        $('<td>' + field.title + '</td>').appendTo($tr);
    });
    $tr.appendTo(self.$thead);

    // tbody-表格体
    self.$tbody = $(self.$element.find('tbody')[0]);

    // tfoot-表格脚注
    self.$current = $(self.$element.find('span[current]')[0]);
    self.$pages = $(self.$element.find('span[pages]')[0]);
    self.$size = $(self.$element.find('span[size]')[0]);
    self.$total = $(self.$element.find('span[total]')[0]);
    self.$previousPage = $(self.$element.find('a[previousPage]')[0]);
    self.$previousPage.on('click', function () {
        if (self.current == 1) {
            return;
        }
        self.current--;
        self.load();
    });
    self.$nextPage = $(self.$element.find('a[nextPage]')[0]);
    self.$nextPage.on('click', function () {
        self.current++;
        self.load();
    });
}

Page.prototype._getSearchParams = function () {
    const self = this;
    let params = {};
    if (self.$search) {
        let searchFields = self.$search.find("input[search]");
        for (let i = 0, length = searchFields.length; i < length; i++) {
            let $searchField = $(searchFields[i]);
            params[$searchField.attr('name')] = $searchField.val();
        }
    }
    return params;
}

Page.prototype._resetSearchParams = function () {
    const self = this;
    if (self.$search) {
        let searchFields = self.$search.find("input[search]");
        for (let i = 0, length = searchFields.length; i < length; i++) {
            let $searchField = $(searchFields[i]);
            $searchField.val('');
        }
    }
}

Page.prototype.load = function () {
    const self = this;

    let params = self._getSearchParams();
    params.current = self.current;
    params.size = self.size;

    Utils.Http.get(self.url, params, function (resp) {
        // console.log('resp', resp);
        self.$tbody.html('');

        let body = resp.body;
        self.$current.text(self.current = body.current);
        self.$pages.text(body.pages);
        self.$size.text(body.size);
        self.$total.text(body.total);

        let editorUrl = null;
        if (self.url === '/note/page') {
            editorUrl = '/html/note-editor.html';
        } else if (self.url === '/event/page') {
            editorUrl = '/html/event-editor.html';
        }

        let data = body.data;
        for (let i = 0, length = data.length; i < length; i++) {
            let dat = data[i];
            let tr = $('<tr></tr>');
            self.fields.forEach(function (field) {
                if ('operate' === field.name) {
                    return;
                }
                $('<td>' + dat[field.name] + '</td>').appendTo(tr);
            });
            $('<td><a href="' + editorUrl + '?id=' + dat.id + '" target="_blank">查看</a></td>').appendTo(tr);
            tr.appendTo(self.$tbody);
        }
    }, function (resp) {
        alert(resp.message);
    });
}

Page.prototype.reload = function () {
    const self = this;
    self.current = 1;
    self._resetSearchParams();
    self.load();
}
