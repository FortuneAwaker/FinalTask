function checkRegexp(value, regexp) {
    return new RegExp(regexp).test(value);
}

function checkNotEmpty(value) {
    return value.length != 0;
}

function checkPhoneNumber(value) {
    if ((value.length >= 7) && value.length <= 12
        && checkRegexp(value, "^[0-9]+$")
        && checkNotEmpty(value)) {
        return true;
    }
    return false;
}

function checkLengthString(value) {
    if ((value.length >= 4) && value.length <= 20) {
        return true;
    }
    return false;
}

function validateForm(form, data) {
    for (i in data) {
        if (!data[i].checker(form[data[i].id].value)) {
            errorMessage(form[data[i].id], data[i].message);
            return false;
        }
    }
    return true;
}

function errorMessage(element, message) {
    startMessage = message;
    show(message, function () {
        element.focus()
    });
}
