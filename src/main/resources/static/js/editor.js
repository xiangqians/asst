function EditorHelper(elementId) {
    // Having done this, an editor instance can be created:
    // this.editor = new Editor();
    // this.editor.render();
    // The editor will take the position of the first <textarea> element.
    this.editor = new Editor({
        element: document.getElementById(elementId ? elementId : 'editor'),
        // toolbar: []
    });
    this.editor.render();
}

EditorHelper.prototype.refresh = function () {
    this.editor.codemirror.refresh();
}

EditorHelper.prototype.getValue = function () {
    // Get the content
    // To get back the edited content you use:
    return this.editor.codemirror.getValue();
}

EditorHelper.prototype.setValue = function (value) {
    this.editor.codemirror.setValue(value);
}
EditorHelper.prototype.save = function () {
    this.editor.codemirror.save();
}

EditorHelper.prototype.hash = null;
