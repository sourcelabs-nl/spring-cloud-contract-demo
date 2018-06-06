var templates = {};

function render(template, model, url) {
    var compiledTemplate;
    if (templates[url] === undefined) {
        compiledTemplate = Handlebars.compile(template);
        templates[url] = compiledTemplate;
    }
    else {
        compiledTemplate = templates[url];
    }
    return compiledTemplate(toJsonObject(model));
}

// Convert model Map items to a real JSON object
function toJsonObject(model) {
    var o = {};
    for (var k in model) {
       o[k] = JSON.parse(model[k]);
    }
    return o;
}