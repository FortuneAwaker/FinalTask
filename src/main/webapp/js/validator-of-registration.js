function validateRegistrationInfo(form) {
    if (!validateForm(form, [{
        id: "login",
        message: "Field should be filled and suit format [A-Za-z0-9]."
            + " It should also be no shorter than 4 and not longer than "
            + "20 symbols.",
        checker: checkLogin
    }, {
        id: "password",
        message: "Field should be filled and suit format [A-Za-z0-9]."
            + " It should also be no shorter than 4 and not longer than "
            + "20 symbols.",
        checker: checkStringInput
    }, {
        id: "repeat",
        message: "Field should be filled and suit format [A-Za-z0-9]."
            + " It should also be no shorter than 4 and not longer than "
            + "20 symbols.",
        checker: checkStringInput
    }, {
        id: "name",
        message: "Field should be filled and suit format [A-Za-z]."
            + " It should also be no shorter than 4 and not longer than "
            + "20 symbols.",
        checker: checkStringInput
    }, {
        id: "surname",
        message: "Field should be filled and suit format [A-Za-z]."
            + " It should also be no shorter than 4 and not longer than "
            + "20 symbols.",
        checker: checkStringInput
    }, {
        id: "patro",
        message: "Field should be filled and suit format [A-Za-z]."
            + " It should also be no shorter than 4 and not longer than "
            + "20 symbols.",
        checker: checkStringInput
    }, {
        id: "phone",
        message: "Phone number should consist of numbers only and be "
            + "at least 7 and at most 12 symbols long.",
        checker: checkPhoneNumber
    }])) {
        return false;
    }
    console.log("All fields are ok!");
    if (form["password"].value != form["repeat"].value) {
        errorMessage(form["repeat"], "Passwords should be the same!");
        return false;
    }
    console.log("Passwords are the same!");
    return true;
}

function checkStringInput(string) {
    if (checkRegexp(string, "^[a-zA-Z]+$")
        && checkLengthString(string)
        && checkNotEmpty(string)) {
        return true;
    }
    return false;
}

function checkLogin(login) {
    if (checkRegexp(string, "^[a-zA-Z0-9]+$")
        && checkLengthString(string)
        && checkNotEmpty(string)) {
        return true;
    }
    return false;
}