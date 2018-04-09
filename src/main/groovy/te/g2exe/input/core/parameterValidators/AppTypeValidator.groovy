package te.g2exe.input.core.parameterValidators

class AppTypeValidator {

    static def consoleAndGuiFlagsBothSet = { input ->
        return input.console && input.gui
    }

}
