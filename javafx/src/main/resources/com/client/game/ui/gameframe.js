var api = com.client.scripting.ScriptAPI
var out = java.lang.System.out

function setContentToDummy(event) {
    content.getChildren().setAll(dummy)
}

function handleProcessBtn(event) {
    var msg = moneyLabel.getText()
    var amount = parseInt(msg)

    amount += 1500

    moneyLabel.setText("$" + amount)

}

function changeContent(c) {
    if(content.getChildren()[0] !== c) {
        content.getChildren().setAll(c)
    }
}

function init() {
    api.onStringChange(moneyLabel.textProperty(), function (v) {
        var num = parseInt(v)

        if(num < 500 || isNaN(num)) {
            api.replaceStyleClass(moneyLabel, "money-high", "money-low")
        } else {
            api.replaceStyleClass(moneyLabel, "money-low", "money-high")
        }
    })
}

