function EditorModule() {

    let obj = {};

    obj.Editor = function (elementId, value) {
        // Having done this, an editor instance can be created:
        // this.editor = new Editor();
        // this.editor.render();
        // The editor will take the position of the first <textarea> element.
        this.editor = new Editor({
            element: document.getElementById(elementId ? elementId : 'templates.editor'),
            // toolbar: []
        });
        this.editor.render();
        if (value) {
            this.setValue(value);
        }
    }

    obj.Editor.prototype.refresh = function () {
        this.editor.codemirror.refresh();
    }

    obj.Editor.prototype.getValue = function () {
        // Get the content
        // To get back the edited content you use:
        return this.editor.codemirror.getValue();
    }

    obj.Editor.prototype.setValue = function (value) {
        this.editor.codemirror.setValue(value);
    }

    obj.Editor.prototype.save = function () {
        this.editor.codemirror.save();
    }

    /**
     * 触发预览
     * @returns {boolean}
     */
    obj.Editor.prototype.togglePreview = function () {
        this.editor.togglePreview();
        return true;
    }

    obj.Editor.prototype.hash = null;

    obj.Editor.prototype.getHash = function () {
        return this.hash;
    }

    obj.Editor.prototype.setHash = function (hash) {
        this.hash = hash;
        return true;
    }

    return obj;
}

